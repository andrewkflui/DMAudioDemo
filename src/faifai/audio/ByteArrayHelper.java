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

public class ByteArrayHelper {

  public static int getUnsignedValue(byte[] dataBuffer, int frameIndex, int frameSize, boolean isBigEndian) {
    int bufferIndex = frameIndex * frameSize;
    int value = 0;
    for (int i = 0; i < frameSize; i++) {
      int index = i;
      if (!isBigEndian) {
        index = frameSize - i - 1;
      }
      value = value << 8;
      value = value + (dataBuffer[bufferIndex + index] & 0x000000ff);
    }
    return value;
  }

  public static int getValue(byte[] dataBuffer, int frameIndex, int frameSize, boolean isBigEndian) {
    int frameSizeInBits = frameSize * 8;
    int value = getUnsignedValue(dataBuffer, frameIndex, frameSize, isBigEndian);
    int signbit = value >> (frameSizeInBits - 1);
    if (signbit != 0) {
      int mask = 0xffffffff;
      mask = mask >> frameSizeInBits << frameSizeInBits;
      value = value | mask;
    }
    return value;
  }

  public static void setUnsignedValue(int value, byte[] dataBuffer, int frameIndex, int frameSize, boolean isBigEndian) {
    int bufferIndex = frameIndex * frameSize;
    for (int i = 0; i < frameSize; i++) {
      int index = i;
      if (isBigEndian) {
        index = frameSize - i - 1;
      }
      dataBuffer[bufferIndex + index] = (byte) (value & 0xff);
      value = value >> 8;
    }
  }


}
