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
package faifai.audio.effect;

/**
 * It contains code for audio processing.
 * The methods in this class are usually initially by an event such as the
 * pressing of a button.
 */
public class AudioProcessingEffect {
  /**
   * The methods in this class uses mainly three member attributes, 'buffer' which
   * contains the audio data in 8 bit mono PCM_SIGNED, '
   */

  /**
   * This method changes the loudness (amplitude) of the audio data stored in
   * the buffer according to the input multiplier.
   */
  public static void amplify(byte dataBuffer[], int length, double multiplier) {
    // Loop through all audio data in the buffer
    for (int i=0; i<length; i++) {
      int temp = (int)(dataBuffer[i] * multiplier);
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
  public static void boost(byte dataBuffer[], int length) {
    // first find out the peak value in both positive and negative.
    int min = 127;
    int max = -128;
    for (int i=0; i<length; i++) {
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
    amplify(dataBuffer, length, multiplier);
  }

  /**
   * This method reverse the audio data sequence
   */
  public static void reverse(byte dataBuffer[], int length) {
    // work out the mid point of the audio data
    int mid = (length - 1) / 2;
    // starts swapping data from both ends and moves towards the middle
    for (int i=0; i<mid; i++) {
      byte temp = dataBuffer[i];
      dataBuffer[i] = dataBuffer[length - i - 1];
      dataBuffer[length - i - 1] = temp;
    }
  }

  /**
   * Thie method applies the fade-in effect to the audio data from the beginning
   * until the time specified by the given parameter.
   *
   * @param fadeInLength the end of the fade-in period in seconds
   */
  public static void fadeIn(byte dataBuffer[], int length, double fadeInLength) {
    int samplesToFade = Math.round(length * (float)fadeInLength);
    if (samplesToFade > length)
      samplesToFade = length;
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
  public static void fadeOut(byte dataBuffer[], int length, double fadeOutLength) {
    int samplesToFade = Math.round(length * (float)fadeOutLength);
    if (samplesToFade > length)
      samplesToFade = length;
    for (int i=length - samplesToFade; i<length; i++) {
      float multiplier = (float)(length - i) / (float)samplesToFade;
      dataBuffer[i] = (byte)Math.round(dataBuffer[i] * multiplier);
    }
  }

  /**
   * This method applies pitch-up operation by the number of semitone given in
   * the parameter
   */
  public static int pitchUp(byte dataBuffer[], int length, int semitone) {
    float step = (float)(1F - ( 1F / Math.pow(2, ((float)semitone / 12))));
    float count = step;
    int newIndex = 0;
    // loop through the audio data
    for (int i = 0; i < length; i++) {
      count += step;
      if (count < 1)
        // keep the sample
        dataBuffer[newIndex++] = dataBuffer[i];
      else
        // drop the sample
        count -= 1;
    }
    return newIndex;
  }

  static byte[] auxbuffer = null; // a temporary buffer used by pitchDown and reverb

  /**
   * This method applies pitch-down operation by the number of semitone given in
   * the parameter
   */
  public static int pitchDown(byte dataBuffer[], int length, int semitone) {
    float step = (float)Math.pow(2, ((float)semitone / 12));
    float count = 0;
    if (auxbuffer == null)
      auxbuffer = new byte[dataBuffer.length];
    int newIndex = 0;
    // first copy the audio data from buffer to the temporary buffer
    System.arraycopy(dataBuffer, 0, auxbuffer, 0, dataBuffer.length);
    // loop through the audio data in the temporary buffer
    for (int i=0; i<length; i++) {
      count += step;
      while (count > 0) {
        // keep the samples
        dataBuffer[newIndex++] = auxbuffer[i];
        count -= 1;
      }
    }
    return newIndex;
  }

  /**
   * This method applies a high pass filter to the audio data in the buffer
   */
  public static void highPassFilter(byte dataBuffer[], int length) {
    for (int i=0; i<length; i++) {
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
  public static void lowPassFilter(byte dataBuffer[], int length) {
    for (int i=0; i<length; i++) {
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
  public static void bandPassFilter(byte dataBuffer[], int length) {
    for (int i=0; i<length; i++) {
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
  public static void bandRejectFilter(byte dataBuffer[], int length) {
    for (int i=0; i<length; i++) {
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
  public static boolean reverb(byte dataBuffer[], int length, double frameRate,
          int lineCount, double[] delay, double[] amplifier) {
    float soundSpeed = 343;  // speed of sound
    boolean clipping = false;  // a flag to record if any clipping occured caused by processing
    // fix the problem that the distance or multiplier arrays are too short
    if (lineCount > delay.length) lineCount = delay.length;
    if (lineCount > amplifier.length) lineCount = amplifier.length;
    // create the temporary buffer if not already created
    if (auxbuffer == null)
      auxbuffer = new byte[dataBuffer.length];
    System.arraycopy(dataBuffer, 0, auxbuffer, 0, dataBuffer.length);

    // the pointers used to signal the beginning of the delay line
    int linePointer[] = new int[lineCount];
    for (int line=0; line<lineCount; line++) {
      //linePointer[line] = (int)(-Math.round(distance[line] / soundSpeed * frameRate));
      linePointer[line] = (int)(-Math.round(delay[line] * frameRate));
    }

    // loops through the audio data in the buffer
    for (int i=0; i<length; i++) {
      // calculate the accumulated output of all the delay lines
      float reverbSample = 0;
      for (int line=0; line<lineCount; line++) {
        if (linePointer[line] + i >= 0)
          reverbSample += (float)auxbuffer[linePointer[line] + i] * amplifier[line];
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
