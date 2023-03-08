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
 * This class specifies the needed attributes and methods for all the wave generator
 * classes
 */
public class WaveHarmonicsGenerator extends AudioClip {

  protected double freq = 440; // the frequency of the wave in Hz
  public static float FRAMERATE;
  public static int CHANNEL;
  public static int SAMPLESIZE;
  public static boolean ISBIGENDIAN;
  public static int FRAMESIZE;
  public static int FRAMECOUNT;
  protected byte[] dataBuffer;
  public static int HARMONICSCOUNT = 4;
  protected double harmonicsAmp[] = {1.0, 0.5, 0.2, 0.1};

  public WaveHarmonicsGenerator() {
    AudioFormat format = AudioDataBuffer.getCommonAudioFormat();
    FRAMERATE = format.getFrameRate();
    CHANNEL = format.getChannels();
    FRAMESIZE = format.getFrameSize();
    SAMPLESIZE = format.getSampleSizeInBits();
    ISBIGENDIAN = format.isBigEndian();
    FRAMECOUNT = (int) FRAMERATE; // number of frames in the data buffer
    audioDataBuffer = new AudioDataBuffer(format, FRAMECOUNT);
    dataBuffer = audioDataBuffer.getDataBuffer();
  }

  public void refreshDataBuffer() {
    generateSineWave();
    audioDataBuffer.setDataLength((int) (audioDataBuffer.getFrameRate() / freq) * audioDataBuffer.getFrameSize());
  }

  /**
   * Sets the frequency for a wave generator
   */
  public void setFrequency(double freq) {
    if (freq < 20) {
      freq = 20;
    }
    this.freq = freq;
    refreshDataBuffer();
  }

  /**
   * Sets the amplitude for a wave generator
   * harmonicIndex 0 is the main frequency
   */
  public void setAmplitude(int harmonicIndex, double amp) {
    if (amp > 1) {
      amp = 1;
    }
    if (amp < 0) {
      amp = 0;
    }
    this.harmonicsAmp[harmonicIndex] = amp;
    refreshDataBuffer();
  }

  void generateSineWave() {
    // calculate the maximum value for audio data
    double ampReal[] = new double[HARMONICSCOUNT];
    double period[] = new double[HARMONICSCOUNT];
    for (int i = 0; i < HARMONICSCOUNT; i++) {
      ampReal[i] = (harmonicsAmp[i] * (Math.pow(2, SAMPLESIZE - 1) - 1));
      period[i] = FRAMERATE / (freq * (i + 1));  // the number of frames in a period
    }
    // generating the audio wave
    for (int frame = 0; frame < period[0]; frame++) {
      int value = 0;
      for (int i = 0; i < HARMONICSCOUNT; i++) {
        double periodPosition = frame / period[i];
        value += (int) Math.floor(Math.sin(periodPosition * 2.0 * Math.PI) * ampReal[i]);
      }
      value = (int)(value / HARMONICSCOUNT);
      int basePos = frame * FRAMESIZE;
      dataBuffer[basePos + 0] = (byte) (value & 0xFF);
    }
  }
}