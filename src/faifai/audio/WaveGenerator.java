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
public class WaveGenerator extends AudioClip {

  protected double freq = 440; // the frequency of the wave in Hz
  protected double amp = 1; // the amplitude in the range 0 to 1.0
  public static float FRAMERATE;
  public static int CHANNEL;
  public static int SAMPLESIZE;
  public static boolean ISBIGENDIAN;
  public static int FRAMESIZE;
  public static int FRAMECOUNT;
  protected byte[] dataBuffer;
  protected String waveformNames[] = {"SINE", "TRIANGLE", "SQUARE", "SAWTOOTH", "CLICK-SINE"};
  protected int waveformSelected = 0;

  public WaveGenerator() {
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
    switch (waveformSelected) {
      case 0:
        generateSineWave();
        break;
      case 1:
        generateTriangleWave();
        break;
      case 2:
        generateSquareWave();
        break;
      case 3:
        generateSawtoothWave();
        break;
      case 4:
        generateClick();
        break;
    }
    audioDataBuffer.setDataLength((int) (audioDataBuffer.getFrameRate() / freq) * audioDataBuffer.getFrameSize());
  }

  public String[] getWaveformNames() {
    return waveformNames;
  }

  public int getSelectedWaveform() {
    return this.waveformSelected;
  }

  public int countWaveforms() {
    return this.waveformNames.length;
  }

  public void selectWaveform(int index) {
    this.waveformSelected = index;
  }

  public void selectNextWaveform() {
    this.waveformSelected = (this.waveformSelected + 1) % waveformNames.length;
    refreshDataBuffer();
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
   */
  public void setAmplitude(double amp) {
    if (amp > 1) {
      amp = 1;
    }
    if (amp < 0) {
      amp = 0;
    }
    this.amp = amp;
    refreshDataBuffer();
  }

  void generateTriangleWave() {
    // calculate the maximum value for audio data
    float ampReal = (float) (amp * (Math.pow(2, SAMPLESIZE - 1) - 1));
    double period = FRAMERATE / freq;  // the number of frames in a period
    // generating the audio wave
    for (int frame = 0; frame < period; frame++) {
      float periodPosition = (float) frame / (float) period;
      float periodPositionPoint = periodPosition - (int) periodPosition;
      float fValue;
      if (periodPositionPoint < 0.25F) {
        fValue = 4.0F * periodPositionPoint;
      } else if (periodPositionPoint < 0.75F) {
        fValue = -4.0F * (periodPositionPoint - 0.5F);
      } else {
        fValue = 4.0F * (periodPositionPoint - 1.0F);
      }
      short value = (short) Math.round(fValue * ampReal);
      int basePos = frame * FRAMESIZE;
      // this is for 16 bit mono

      dataBuffer[basePos + 0] = (byte) (value & 0xFF);
      dataBuffer[basePos + 1] = (byte) ((value >>> 8) & 0xFF);
    }
  }

  void generateSineWave() {
    // calculate the maximum value for audio data
    double ampReal = (amp * (Math.pow(2, SAMPLESIZE - 1) - 1));
    double period = FRAMERATE / freq;  // the number of frames in a period
    // generating the audio wave
    for (int frame = 0; frame < period; frame++) {
      double periodPosition = frame / period;
      short value = (short) Math.floor(Math.sin(periodPosition * 2.0 * Math.PI) * ampReal);
      int basePos = frame * FRAMESIZE;
      // this is for 16 bit mono
      dataBuffer[basePos + 0] = (byte) (value & 0xFF);
      dataBuffer[basePos + 1] = (byte) ((value >>> 8) & 0xFF);
    }
  }

  void generateSquareWave() {
    // calculate the maximum value for audio data
    float ampReal = (float) (amp * (Math.pow(2, SAMPLESIZE - 1) - 1));
    int period = (int) (FRAMERATE / freq);

    // generating the audio wave
    for (int frame = 0; frame < period; frame++) {
      float periodPosition = (float) frame / (float) period;
      float periodPositionPoint = periodPosition - (int) periodPosition;
      int value;
      if (periodPositionPoint < 0.5F) {
        value = (int) Math.round(-1 * ampReal);
      } else {
        value = (int) Math.round(1 * ampReal);
      }
      int basePos = frame * FRAMESIZE;
      // this is for 16 bit mono
      dataBuffer[basePos + 0] = (byte) (value & 0xFF);
      dataBuffer[basePos + 1] = (byte) ((value >>> 8) & 0xFF);
    }
  }

  void generateSawtoothWave() {
    // calculate the maximum value for audio data
    float ampReal = (float) (amp * (Math.pow(2, SAMPLESIZE - 1) - 1));
    double period = FRAMERATE / freq;  // the number of frames in a period
    // generating the audio wave
    for (int frame = 0; frame < period; frame++) {
      float periodPosition = (float) frame / (float) period;
      float periodPositionPoint = periodPosition - (int) periodPosition;
      float fValue = 2.0F * (1 - periodPositionPoint) - 1.0F;
      short value = (short)Math.round(fValue * ampReal);
      int basePos = frame * FRAMESIZE;
      // this is for 16 bit mono
      dataBuffer[basePos + 0] = (byte) (value & 0xFF);
      dataBuffer[basePos + 1] = (byte) ((value >>> 8) & 0xFF);
    }
  }

  void generateClick() {
    boolean sign = false;
    // calculate the maximum value for audio data
    double ampReal = (amp * (Math.pow(2, SAMPLESIZE - 1) - 1));
    double period = FRAMERATE / freq;  // the number of frames in a period
    // generating the audio wave
    for (int frame = 0; frame < period; frame++) {
      if (Math.random() < 0.1) {
        sign = !sign;
      }
      double periodPosition = frame / period;
      int value = (int) Math.floor(Math.sin(periodPosition * 2.0 * Math.PI) * ampReal);
      int basePos = frame * FRAMESIZE;
      if (sign) {
        value = -value;
      }
      // this is for 16 bit mono
      dataBuffer[basePos + 0] = (byte) (value & 0xFF);
      dataBuffer[basePos + 1] = (byte) ((value >>> 8) & 0xFF);
    }
  }
}
