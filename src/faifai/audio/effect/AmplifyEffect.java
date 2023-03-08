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
public class AmplifyEffect extends AbstractEffect {

  protected double multiplier = 1.0;

  public void setMultiplier(double multiplier) {
    this.multiplier = multiplier;
  }

  public void apply(byte data[], int length) {
    if (!enabled)
      return;
    for (int i = 0; i < length; i++) {
      int temp = (int)(data[i] * multiplier);
      // if clipping found restricting the range from -128 to 127 (8 bit)
      if (temp > 127) {
        data[i] = 127;
      } else if (temp < -128) {
        data[i] = -128;
      } else {
        data[i] = (byte) temp;
      }
    }
  }
}
