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

import java.io.*;
import javax.sound.sampled.*;

/**
 * This class plays audio data stream to an output line (speaker).  The audio playing
 * is carried out by a separate thread, which could be stopped.  The audio playing
 * could be repeated in a specified number or indefinitely.
 */
public class SimpleStreamPlayer extends SimpleClipPlayer implements Runnable {

  private AudioInputStream inputStream = null;

  public SimpleStreamPlayer() {
    super();
  }

  /**
   * Thie method attempts to convert the format of AudioInputStream if the
   * format is not matched by the output line
   */
  private AudioInputStream convertFormat(AudioInputStream source) {
    AudioFormat format = source.getFormat();
    System.out.println("[SimplePlayer] Converting sound format");
    AudioFormat targetFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            format.getSampleRate(),
            16,
            format.getChannels(),
            format.getChannels() * 2,
            format.getSampleRate(),
            false);
    AudioInputStream converted = AudioSystem.getAudioInputStream(targetFormat, source);
    return converted;
  }

  public void run() {
    System.out.println("[SimpleStreamPlayer] Player is ready");
    while (true) {
      try {
        if (inputStream != null && inputStream.available() > 0) {
          System.out.println("Stream available");
          if (toRun) {
            AudioFormat format = inputStream.getFormat();
            // get the output line
            SourceDataLine line = getSourceDataLine(format);
            /*
            if (line == null) {
              dataStream = convertFormat(dataStream);
              format = dataStream.getFormat();
            }
             */
            line = getSourceDataLine(format);
            if (line == null) {
              System.out.println("[SimpleStreamPlayer] Line unavailable");
              continue;
            }
            // open the output line
            line.open();
            /*
            FloatControl gainControl = getGainControl(line.getControls());
            if (gainControl != null) {
              double scale = gainControl.getMaximum() - gainControl.getMinimum();
              double adjustedGain = gain * scale + gainControl.getMinimum();
              gainControl.setValue((float)adjustedGain);
            }
             */
            line.start();
            int bufferLengthInBytes = line.getBufferSize() / 8 * format.getFrameSize();
            byte[] data = new byte[bufferLengthInBytes];
            int numBytesRead = 0;
            // loops to make the audio data to be played repeatedly
            for (int j = 0; j < loop || loop < 0; j++) {
              // reset the AudioInputStream back to the front
              inputStream.reset();
              while (true) {
                if ((numBytesRead = inputStream.read(data)) == -1) {
                  break;
                }
                // write the audio data to the output line
                int remaining = numBytesRead;
                while (remaining > 0) {
                  remaining -= line.write(data, 0, remaining);
                }
              }
            }
            // if it is not stopped abruptedly then drain the remaining line output buffer
            line.drain();
            line.stop();
            line.close();
            toRun = false;
            this.inputStream = null;
          } //toRun
        }
        Thread.currentThread().sleep(100);
      } catch (IOException ex) {
        System.err.println(ex);
        break;
      } catch (InterruptedException ex) {
        if (toClose)
        return;
      } catch (Exception ex) {
      }
    } // while
  }

  /**
   * Instruct the player to play the given audio data
   */
  public void play(AudioInputStream aistream) {
    this.inputStream = aistream;
    toRun = true;
  }


}
