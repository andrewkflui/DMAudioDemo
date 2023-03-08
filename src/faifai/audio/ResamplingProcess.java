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

import java.util.HashSet;
import java.util.Iterator;

public class ResamplingProcess implements Runnable {

  // flags for controlling the playing of audi
  protected boolean toStop = false;
  protected Thread thread = null; // the thread that plays audio data
  private AudioDataBuffer audioDataBuffer = null;  // buffer for holding the audio data to be played
  private AudioDataBuffer outputDataBuffer = null;  // buffer for holding the audio data to be played
  private int downSampleFactor = 1;
  private int[] sampleFrameIndex = null;

  public ResamplingProcess() {
    thread = new Thread(this);
    thread.start();
    updateSampledFrameIndex();
  }

  public void setDownSampleFactor(int downSampleFactor) {
    this.downSampleFactor = downSampleFactor;
    updateSampledFrameIndex();
  }

  public int[] getSampledFrameIndex() {
    return sampleFrameIndex;
  }

  public void updateSampledFrameIndex() {
    if (sampleFrameIndex == null) {
      return;
    }
    HashSet<Integer> frameSet = new HashSet<Integer>();
    int frameCount = outputDataBuffer.getFrameCount();
    double resampledCount = (double) frameCount / downSampleFactor;
    for (int frameindex = 0; frameindex < frameCount; frameindex++) {
      int resampledLen = (int) ((double) frameCount / (Math.ceil(resampledCount)));
      int resampledIndex = (int) (frameindex / resampledLen);
      int sourceindex = (int) ((resampledIndex + 0.5) * resampledLen);
      frameSet.add(sourceindex + 1);
    }
    Iterator<Integer> it = frameSet.iterator();
    int size = frameSet.size();
    int i = 0;
    while (it.hasNext()) {
      sampleFrameIndex[i++] = it.next();
    }
    for (int f = 0; f < sampleFrameIndex.length; f++) {
      sampleFrameIndex[f] = sampleFrameIndex[f % size] + audioDataBuffer.getFrameCount() * (f / size);
    }
  }

  public AudioDataBuffer connect(AudioDataBuffer audioDataBuffer) {
    this.audioDataBuffer = audioDataBuffer;
    this.outputDataBuffer = new AudioDataBuffer();
    //this.sampleFrameIndex = new int[audioDataBuffer.getDataLength()];
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

  public void run() {
    while (true) {
      if (toStop) {
        break;
      }
      if (audioDataBuffer != null && audioDataBuffer.isReady()) {
        outputDataBuffer.setAudioFormat(audioDataBuffer.getAudioFormat(),
                audioDataBuffer.getFrameCount());
        byte[] outputData = outputDataBuffer.getDataBuffer();
        byte[] inputData = audioDataBuffer.getDataBuffer();
        int frameCount = audioDataBuffer.getFrameCount();
        int frameSize = audioDataBuffer.getFrameSize();
        double resampledCount = (double) frameCount / downSampleFactor;
        for (int frameindex = 0; frameindex < frameCount; frameindex++) {
          int resampledLen = (int) ((double) frameCount / (Math.ceil(resampledCount)));
          int resampledIndex = (int) (frameindex / resampledLen);
          int sourceindex = (int) ((resampledIndex + 0.5) * resampledLen);
          //System.out.println(frameindex + " " + resampledIndex + ", " + ByteArrayHelper.getShortValue(inputData, sourceindex, false));
          // copy data of one frame
          for (int x = 0; x < frameSize; x++) {
            outputData[frameindex * frameSize + x] = inputData[sourceindex * frameSize + x];
          }
        }
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
    /*
    try {
    thread.join();
    } catch (InterruptedException ex) {

    } */
  }
}
