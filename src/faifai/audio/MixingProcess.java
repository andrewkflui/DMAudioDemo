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

public class MixingProcess implements Runnable {

  // flags for controlling the playing of audi
  protected boolean toStop = false;
  protected Thread thread = null; // the thread that plays audio data
  private AudioDataBuffer inputDataBufferA = null;  // data buffer from source A
  private AudioDataBuffer inputDataBufferB = null;  // data buffer from source B
  private AudioDataBuffer outputDataBuffer = null;  // buffer for holding the audio data to be played

  public MixingProcess() {
    thread = new Thread(this);
    thread.start();
  }

  public AudioDataBuffer connect(AudioFormat format, AudioDataBuffer inputDataBufferA, AudioDataBuffer inputDataBufferB) {
    this.inputDataBufferA = inputDataBufferA;
    this.inputDataBufferB = inputDataBufferB;
    this.outputDataBuffer = new AudioDataBuffer();
    return this.outputDataBuffer;
  }

  AudioDataBuffer getInputDataBufferA() {
    return this.inputDataBufferA;
  }

  AudioDataBuffer getInputDataBufferB() {
    return this.inputDataBufferB;
  }

  AudioDataBuffer getOutputDataBuffer() {
    return this.outputDataBuffer;
  }

  private static int findLCM(int num1, int num2) {
    int smallerNumber = num2;
    if (num1 < num2) {
      smallerNumber = num1;
    }
    int gcd = 1;
    for (int divisor = 1; divisor <= smallerNumber; divisor++) {
      if (num1 % divisor == 0 && num2 % divisor == 0) {
        gcd = divisor;
      }
    }
    return (num1 * num2) / gcd;
  }

  public void run() {
    while (true) {
      if (toStop) {
        break;
      }
      if (inputDataBufferA != null && inputDataBufferB != null && inputDataBufferA.isReady() && inputDataBufferB.isReady()) {
        AudioFormat format = inputDataBufferA.getAudioFormat();
        int frameCountA = inputDataBufferA.getFrameCount();
        int frameCountB = inputDataBufferB.getFrameCount();
        int frameCount = frameCountA;
        if (frameCountB > frameCount) {
          frameCount = frameCountB;
        }
        //frameCount = frameCountA * frameCountB; /* IMPORTANT: to increase wave cycles by 12 times */
        frameCount = findLCM(frameCountA, frameCountB);
        //System.out.println("Mixing framecount = " + frameCount);
        int frameSize = inputDataBufferA.getFrameSize();
        int mixedDataLength = frameCount * frameSize;
        outputDataBuffer.setAudioFormat(format, frameCount);
        byte[] outputData = outputDataBuffer.getDataBuffer();
        byte[] inputDataA = inputDataBufferA.getDataBuffer();
        byte[] inputDataB = inputDataBufferB.getDataBuffer();

        int maxValue = (int) (Math.pow(2, frameSize * 8 - 1) - 1);
        int minValue = -(int) (Math.pow(2, frameSize * 8 - 1));
        int valueA;
        int valueB;
        if (frameSize == 1) {
          for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
            valueA = inputDataA[frameIndex % frameCountA];
            valueB = inputDataB[frameIndex % frameCountB];
            int result = (valueA + valueB);
            if (result > maxValue) {
              result = maxValue;
            } else if (result < minValue) {
              result = minValue;
            }
            outputData[frameIndex] = (byte) (result & 0xff);
          }
        } else {
          for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
            valueA = ByteArrayHelper.getValue(inputDataA, frameIndex % frameCountA, frameSize, inputDataBufferA.isBigEndian());
            valueB = ByteArrayHelper.getValue(inputDataB, frameIndex % frameCountB, frameSize, inputDataBufferB.isBigEndian());
            int result = (valueA + valueB);
            if (result > maxValue) {
              result = maxValue;
            } else if (result < minValue) {
              result = minValue;
            }
            ByteArrayHelper.setUnsignedValue(result, outputData, frameIndex, frameSize, outputDataBuffer.isBigEndian());
          }
        }

        outputDataBuffer.setDataLength(mixedDataLength);
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
    /*
    try {
    thread.join();
    } catch (InterruptedException ex) {

    } */
  }
}
