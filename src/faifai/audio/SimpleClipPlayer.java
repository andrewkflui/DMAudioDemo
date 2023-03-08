/***************************************************************
 *
 * Interactive Learning Platform: Digital Audio
 * Copyright (c) 2010 Dr. Andrew Kwok-Fai LUI
 * The Open University of Hong Kong
 *
 * Enhance the learning effectiveness of students through greater interactions
 */
/*  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package faifai.audio;

import javax.sound.sampled.*;

/**
 * This class plays audio data to an output line (speaker).  The audio playing
 * is carried out by a separate thread, which could be stopped.  The audio playing
 * could be repeated in a specified number or indefinitely.
 */
public class SimpleClipPlayer implements Runnable {

  // flags for controlling the playing of audio
  protected boolean toRun = false;
  protected boolean toClose = false;
  private AudioDataBuffer audioDataBuffer = null;  // buffer for holding the audio data to be played
  protected Thread thread = null; // the thread that plays audio data
  protected int loop = 1; // the number of repeats of playing the audio data
  protected double gain = 1.0;
  protected FloatControl gainControl = null;
  protected double averageLevel = 0.0;
  protected int dataLength = 0;
  // loops to make the audio data to be played repeatedly
  protected byte[] dataBuffer;

  public SimpleClipPlayer() {
    thread = new Thread(this);
    thread.start();
  }

  protected static SourceDataLine getSourceDataLine(AudioFormat format) {
    return getSourceDataLine(format, AudioSystem.NOT_SPECIFIED);
  }

  /**
   * This method gets and returns an output line for playing audio data
   */
  protected static SourceDataLine getSourceDataLine(AudioFormat format, int bufferSize) {

    SourceDataLine line = null;
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, bufferSize);
    try {
      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(format, bufferSize);
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return line;
  }

  /**
   * Set the number of loops to play on the stream.  -1 means non-stop
   */
  public void setLoop(int loop) {
    this.loop = loop;
  }

  /**
   * Sets the gain level from 0.0 to 1.0
   */
  public void setGain(double gain) {
    this.gain = gain;
    if (gainControl != null) {
      double scale = gainControl.getMaximum() - gainControl.getMinimum();
      double adjustedGain = gain * scale + gainControl.getMinimum();
      gainControl.setValue((float) adjustedGain);
    }
  }

  // return the average audio level from 0.0 to 1.0
  public double getAverageLevel() {
    if (dataLength == 0) {
      return 0.0;
    }
    return averageLevel;
  }

  private double calculateAverage(int startIndex, int sampleCount) {
    AudioFormat format = audioDataBuffer.getAudioFormat();
    int frameSize = format.getFrameSize();
    int endIndex = startIndex + sampleCount * frameSize;
    if (endIndex >= dataBuffer.length) {
      endIndex = dataBuffer.length - 1;
    }
    long sum = 0;
    for (int i = startIndex; i < endIndex; i = i + frameSize) {
      if (format.isBigEndian()) {
        sum += dataBuffer[i] * dataBuffer[i];
      } else {
        sum += dataBuffer[i + (frameSize - 1)] * dataBuffer[i + (frameSize - 1)];
      }
    }
    return Math.sqrt(sum / sampleCount) / 128.0;
  }

  // internal method for obtaining the gain control
  protected FloatControl getGainControl(Control controls[]) {
    FloatControl volControl = null;
    for (Control c : controls) {
      if (c.getType() == FloatControl.Type.MASTER_GAIN) {
        volControl = (FloatControl) c;
      }
      if (volControl != null) {
        break;
      }
    }
    return volControl;
  }

  public void run() {
    System.out.println("[SimplePlayer] Player is ready");
    while (true) {
      SourceDataLine line = null;
      try {
        if (audioDataBuffer != null && audioDataBuffer.isReady()) {
          if (toRun) {
            // get the output line
            AudioFormat format = audioDataBuffer.getAudioFormat();
            line = getSourceDataLine(format);
            if (line == null) {
              System.out.println("[SimplePlayer] Line unavailable");
              continue;
            }
            // open the output line
            line.open();
            line.start();
            gainControl = getGainControl(line.getControls());
            setGain(this.gain);
            // loops to make the audio data to be played repeatedly
            for (int j = 0; j < loop || loop < 0; j++) {
              dataBuffer = audioDataBuffer.getDataBuffer();
              dataLength = audioDataBuffer.getDataLength();
              int remainder = 0;
              int defaultPeriod = format.getChannels() * format.getFrameSize();
              int count = 0;
              // write the audio data to the output line
              while (remainder < dataLength) {
                //System.out.println("Started");
                //remainder += line.write(dataBuffer, remainder, dataLength - remainder);
                if (!toRun)
                  break;
                int period = defaultPeriod;
                if (defaultPeriod > dataLength - remainder) {
                  period = dataLength - remainder;
                }
                if (count++ % 4 == 0) {
                  averageLevel = calculateAverage(remainder, period);
                }
                remainder += line.write(dataBuffer, remainder, period);
              //System.out.println(remainder + " " + averageLevel);
              }
              if (!toRun)
                break;
            }
            //System.out.println("Finished " + toStop + " " + toRun);
            // if it is not stopped abruptedly then drain the remaining line output buffer
            line.drain();
            line.stop();
            line.close();
            averageLevel = 0;
            toRun = false;
          } //toRun
        }
        if (toClose)
          return;        
        Thread.currentThread().sleep(100);
      } catch (InterruptedException ex) {
        if (toClose)
          return;
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } // while
  }

  /**
   * Instruct the player to play the given audio data
   */
  public void play() {
    if (toRun == true) {
      thread.interrupt();
    }
    toRun = true;
  }

  /**
   * Load the player withthe given audio data
   */
  public void load(AudioDataBuffer audioDataBuffer) {
    if (toRun)
      stop();
    this.audioDataBuffer = audioDataBuffer;
  }

  /**
   * Instruct the player to stop playing audio data
   */
  public void stop() {
    toRun = false;
  }

  public void close() {
    toClose = true;
    thread.interrupt();
  }

  public static void main(String args[]) throws Exception {
    WaveGenerator generator = new WaveGenerator();
    SimpleClipPlayer player = new SimpleClipPlayer();
    EffectProcess effectProcess = new EffectProcess();
    generator.setFrequency(240);
    AudioDataBuffer outputDataBuffer = effectProcess.connect(generator.getAudioDataBuffer());
    player.load(outputDataBuffer);
    player.setLoop(-1);
    player.play();
  }
}
