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

import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class AudioClip {

  protected AudioDataBuffer audioDataBuffer = null;

  public AudioClip() {
    audioDataBuffer = new AudioDataBuffer();
  }

  /**
   * This method returns the format of the wave audio data
   */
  public AudioFormat getAudioFormat() {
    if (audioDataBuffer == null)
      return null;
    return audioDataBuffer.getAudioFormat();
  }

  /**
   * It returns an AudioDataBuffer object containing the PCM data samples of the waveform
   */
  public AudioDataBuffer getAudioDataBuffer() {
    return audioDataBuffer;
  }

  /**
   * This method returns the audio wave data in an AudioInputStream
   */
  public AudioInputStream getAudioInputStream() {
    if (audioDataBuffer == null)
      return null;
    byte[] data = audioDataBuffer.getDataBuffer();
    if (data == null)
      return null;
    ByteArrayInputStream bis = new ByteArrayInputStream(data);
    AudioInputStream audioInputStream = new AudioInputStream(bis, getAudioFormat(), audioDataBuffer.getFrameCount());
    return audioInputStream;
  }

}
