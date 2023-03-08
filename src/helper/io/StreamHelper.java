/***************************************************************
 *
 * Copyright (c) 2010 Dr. Andrew Kwok-Fai LUI
 * The Open University of Hong Kong
 *
 * Utilities
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
package helper.io;
import java.io.*;

public class StreamHelper {

  static byte buffer[] = new byte[256];

  public static void dumpStream(InputStream istream) {
    synchronized (buffer) {
      while (true) {
        try {
          int count = istream.read(buffer, 0, 256);
          if (count == -1) {
            return;
          }
          System.out.write(buffer, 0, count);
          System.out.flush();
        } catch (IOException ex) {
          return;
        }
      } // while
    }
  }

}