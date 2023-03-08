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
public class FadeInEffect extends AbstractEffect {
  private double fadeInLength = 0.2;

  public void setFadeInLength(double fadeInLength) {
    this.fadeInLength = fadeInLength;
  }

  public void apply(byte data[], int length) {
    if (!this.enabled)
      return;
    AudioProcessingEffect.fadeIn(data, length, fadeInLength);
  }
}
