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

import javax.sound.sampled.AudioFormat;

public class QuantizationProcess implements Runnable {

  // flags for controlling the playing of audi
  protected boolean toStop = false;
  protected Thread thread = null; // the thread that plays audio data
  private AudioDataBuffer audioDataBuffer = null;  // buffer for holding the audio data to be played
  private AudioDataBuffer outputDataBuffer = null;  // buffer for holding the audio data to be played
  private int bitsPerSample = -1;

  public QuantizationProcess() {
    thread = new Thread(this);
    thread.start();
  }

  public void setBitsPerSample(int bitsPerSample) {
    this.bitsPerSample = bitsPerSample;

  }

  public int getBitsPerSample() {
    return this.bitsPerSample;
  }

  public AudioDataBuffer connect(AudioDataBuffer audioDataBuffer) {
    this.audioDataBuffer = audioDataBuffer;
    this.outputDataBuffer = new AudioDataBuffer();
    return this.outputDataBuffer;
  }

  AudioDataBuffer getInputDataBuffer() {
    return this.audioDataBuffer;
  }

  AudioDataBuffer getOutputDataBuffer() {
    return this.outputDataBuffer;
  }

  public float getSamplingRate() {
    if (audioDataBuffer == null) {
      return 0;
    }
    return audioDataBuffer.getFrameRate();
  }

  public static int maskBits(byte[] dataBuffer, int bitsToMask, int frameSize, boolean isBigEndian) {
    int len = dataBuffer.length;
    int mask = (0xffffffff >> bitsToMask) << bitsToMask;
    int ormask = 0x01 << (bitsToMask - 2);
    if (bitsToMask <= 1) {
      ormask = 0;
    }
    for (int frameIndex = 0; frameIndex * frameSize < len; frameIndex++) {
      int value = ByteArrayHelper.getUnsignedValue(dataBuffer, frameIndex, frameSize, isBigEndian);
      int newvalue = (value & mask) | ormask;
      ByteArrayHelper.setUnsignedValue(newvalue, dataBuffer, frameIndex, frameSize, isBigEndian);
    }
    return 0;
  }

  public void run() {
    while (true) {
      if (toStop) {
        break;
      }
      if (audioDataBuffer != null && audioDataBuffer.isReady()) {
        AudioFormat format = audioDataBuffer.getAudioFormat();
        outputDataBuffer.setAudioFormat(format,
                audioDataBuffer.getFrameCount());
        if (bitsPerSample == -1) {
          bitsPerSample = format.getSampleSizeInBits();
        }
        byte[] outputData = outputDataBuffer.getDataBuffer();
        byte[] inputData = audioDataBuffer.getDataBuffer();
        int len = audioDataBuffer.getDataLength();
        int frameSize = audioDataBuffer.getFrameSize();
        int bitsToClear = format.getSampleSizeInBits() - bitsPerSample;
        for (int index = 0; index < len; index++) {
          outputData[index] = inputData[index];
        }
        maskBits(outputData, bitsToClear, frameSize, format.isBigEndian());
        outputDataBuffer.setDataLength(audioDataBuffer.getDataLength());
      }
      try {
        thread.sleep(50);
      } catch (InterruptedException ex) {
        break;
      }
    }
  }

  public void close() {
    toStop = true;
    thread.interrupt();
  }
}
