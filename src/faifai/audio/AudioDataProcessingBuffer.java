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

/**
 * This is a subclass of AudioDataBuffer that contains code for audio processing.
 * The methods in this class are usually initially by an event such as the
 * pressing of a button.
 */
public class AudioDataProcessingBuffer extends AudioDataBuffer {
  /**
   * The methods in this class uses mainly three member attributes, 'buffer' which
   * contains the audio data in 8 bit mono PCM_SIGNED, '
   */

  /**
   * This method changes the loudness (amplitude) of the audio data stored in
   * the buffer according to the input multiplier.
   */
  public synchronized void changeLoudness(float multiplier) {
    // Loop through all audio data in the buffer
    for (int i=0; i<this.dataLength; i++) {
      int temp = Math.round(dataBuffer[i] * multiplier);
      // if clipping found restricting the range from -128 to 127 (8 bit)
      if (temp > 127) dataBuffer[i] = 127;
      else if (temp < -128) dataBuffer[i] = -128;
      else dataBuffer[i] = (byte)temp;
    }
  }

  /**
   * This method calculates the maximum loudness amplification possible without clipping.
   * It then uses the method changeLoudness to make the amplification
   */
  public synchronized void boost() {
    // first find out the peak value in both positive and negative.
    int min = 127;
    int max = -128;
    for (int i=0; i<this.dataLength; i++) {
      if (dataBuffer[i] < min) min = dataBuffer[i];
      else if (dataBuffer[i] > max) max = dataBuffer[i];
    }
    // find out the absolute maximum magnitude
    min = Math.abs(min);
    max = Math.abs(max);
    int biggest = (max > min)? max: min;
    // work out the optimal amplification possible to the audio data without clipping
    float multiplier = 127F / (float)biggest;
    // calls the method changeLoudness to do the amplification
    changeLoudness(multiplier);
  }

  /**
   * This method reverse the audio data sequence
   */
  public synchronized void reverse() {
    // work out the mid point of the audio data
    int mid = (this.dataLength - 1) / 2;
    // starts swapping data from both ends and moves towards the middle
    for (int i=0; i<mid; i++) {
      byte temp = dataBuffer[i];
      dataBuffer[i] = dataBuffer[this.dataLength - i - 1];
      dataBuffer[this.dataLength - i - 1] = temp;
    }
  }

  /**
   * Thie method applies the fade-in effect to the audio data from the beginning
   * until the time specified by the given parameter.
   *
   * @param fadeInLength the end of the fade-in period in seconds
   */
  public synchronized void fadeIn(float fadeInLength) {
    int samplesToFade = Math.round(this.format.getFrameRate() * fadeInLength);
    if (samplesToFade > this.dataLength)
      samplesToFade = this.dataLength;
    for (int i=0; i<samplesToFade; i++) {
      float multiplier = (float)i / (float)samplesToFade;
      dataBuffer[i] = (byte)Math.round(dataBuffer[i] * multiplier);
    }
  }

  /**
   * Thie method applies the fade-out effect to the audio data from
   * the time before the end specified by the given parameter to the end.
   *
   * @param fadeOutLength the beginning time of the fade-in period in seconds before the end
   */
  public synchronized void fadeOut(float fadeOutLength) {
    int samplesToFade = Math.round(this.format.getFrameRate() * fadeOutLength);
    if (samplesToFade > this.dataLength)
      samplesToFade = this.dataLength;
    for (int i=this.dataLength - samplesToFade; i<this.dataLength; i++) {
      float multiplier = (float)(this.dataLength - i) / (float)samplesToFade;
      dataBuffer[i] = (byte)Math.round(dataBuffer[i] * multiplier);
    }
  }

  /**
   * This method applies pitch-up operation by the number of semitone given in
   * the parameter
   */
  public synchronized void pitchUp(int semitone) {
    float step = (float)(1F - ( 1F / Math.pow(2, ((float)semitone / 12))));
    float count = step;
    int newIndex = 0;
    // loop through the audio data
    for (int i = 0; i < this.dataLength; i++) {
      count += step;
      if (count < 1)
        // keep the sample
        dataBuffer[newIndex++] = dataBuffer[i];
      else
        // drop the sample
        count -= 1;
    }
    this.dataLength = newIndex;
  }

  protected byte[] auxbuffer = null; // a temporary buffer used by pitchDown and reverb

  /**
   * This method applies pitch-down operation by the number of semitone given in
   * the parameter
   */
  public synchronized void pitchDown(int semitone) {
    float step = (float)Math.pow(2, ((float)semitone / 12));
    float count = 0;
    if (auxbuffer == null)
      auxbuffer = new byte[dataBuffer.length];
    int newIndex = 0;
    // first copy the audio data from buffer to the temporary buffer
    System.arraycopy(dataBuffer, 0, auxbuffer, 0, dataBuffer.length);
    // loop through the audio data in the temporary buffer
    for (int i=0; i<this.dataLength; i++) {
      count += step;
      while (count > 0) {
        // keep the samples
        dataBuffer[newIndex++] = auxbuffer[i];
        count -= 1;
      }
    }
    this.dataLength = newIndex;
  }

  /**
   * This method applies a high pass filter to the audio data in the buffer
   */
  public synchronized void highPassFilter() {
    for (int i=0; i<this.dataLength; i++) {
      if (i == 0)
       // special case to handle the first audio data
        dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5);
      else
        dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5 - dataBuffer[i-1] * 0.5);
    }
  }

  /**
   * This method applies a low pass filter to the audio data in the buffer
   */
  public synchronized void lowPassFilter() {
    for (int i=0; i<this.dataLength; i++) {
      if (i == 0)
       // special case to handle the first audio data
        dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5);
      else
        dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5 + dataBuffer[i-1] * 0.5);
    }
  }

  /**
   * This method applies a band pass filter to the audio data in the buffer
   */
  public synchronized void bandPassFilter() {
    for (int i=0; i<this.dataLength; i++) {
      switch (i) {
        // special case to handle the first two audio data
        case 0: dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5); break;
        case 1: dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5); break;
        default:
          dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5 - dataBuffer[i-2] * 0.5);
      }
    }
  }

  /**
   * This method applies a band reject filter to the audio data in the buffer
   */
  public synchronized void bandRejectFilter() {
    for (int i=0; i<this.dataLength; i++) {
      switch (i) {
        // special case to handle the first two audio data
        case 0: dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5); break;
        case 1: dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5); break;
        default:
          dataBuffer[i] = (byte)Math.round(dataBuffer[i] * 0.5 + dataBuffer[i-2] * 0.5);
      }
    }
  }

  /**
   * This method applies reverberation effects to the audio data in the buffer.  It supports
   * an arbitrarily number of delay lines and the number is given in the parameter lineCount.
   * The parameter distance contains an array specifyin the sound travelling distance for each
   * delay line, and the parameter multiplier specifies the reverberation coefficient (in the
   * range 0 to 1.0)
   */
  public synchronized boolean reverb(int lineCount, float[] distance, float[] multiplier) {
    float soundSpeed = 343;  // speed of sound
    boolean clipping = false;  // a flag to record if any clipping occured caused by processing
    // fix the problem that the distance or multiplier arrays are too short
    if (lineCount > distance.length) lineCount = distance.length;
    if (lineCount > multiplier.length) lineCount = multiplier.length;
    // create the temporary buffer if not already created
    if (auxbuffer == null)
      auxbuffer = new byte[dataBuffer.length];
    System.arraycopy(dataBuffer, 0, auxbuffer, 0, dataBuffer.length);

    // the pointers used to signal the beginning of the delay line
    float frameRate = this.format.getFrameRate();
    int linePointer[] = new int[lineCount];
    for (int line=0; line<lineCount; line++) {
      linePointer[line] = (int)(-Math.round(distance[line] / soundSpeed * frameRate));
    }

    // loops through the audio data in the buffer
    for (int i=0; i<this.dataLength; i++) {
      // calculate the accumulated output of all the delay lines
      float reverbSample = 0;
      for (int line=0; line<lineCount; line++) {
        if (linePointer[line] + i >= 0)
          reverbSample += (float)auxbuffer[linePointer[line] + i] * multiplier[line];
      }
      // calculate the final value of the data sample by adding the reverb output
      int finalValue = auxbuffer[i] + Math.round(reverbSample);
      // fix the sample if clipping occured
      if (finalValue > 127) {
        finalValue = 127;
        clipping = true;
      } else if (finalValue < -128) {
        finalValue = -128;
        clipping = true;
      }
      dataBuffer[i] = (byte)finalValue;
    }
    return clipping;
  }

}
