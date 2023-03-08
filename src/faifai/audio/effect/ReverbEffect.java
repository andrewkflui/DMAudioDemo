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
public class ReverbEffect extends AbstractEffect {
  private double delay[] = {0, 0};
  private double amplifier[] = {0, 0};
  private double frameRate = 44100.0;
  
  public void setFrameRate(double frameRate) {
    this.frameRate = frameRate;
  }
  // delay is in ms
  public void setReverbDelay(int channel, double delay) {
    if (channel < 0 || channel >= 2)
      return;
    this.delay[channel] = delay;
  }

  public void setReverbAmplifier(int channel, double amplifier) {
    if (channel < 0 || channel >= 2)
      return;
    this.amplifier[channel] = amplifier;
  }

  public void apply(byte data[], int length) {
    if (!this.enabled)
      return;
    AudioProcessingEffect.reverb(data, length, frameRate, 2, delay, amplifier);
  }
}
