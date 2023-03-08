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

public class LogHelper {

  private static String defaultFilename = "defaultlog.txt";

  private static PrintWriter writer = null;

  private static void setDefaultFilename() {
    try {
      if (writer != null) {
        writer.close();
      }
      writer = new PrintWriter(new FileOutputStream(defaultFilename, true));
    } catch (Exception ex) {

    }
  }

  public static void setFilename(String filename) {
    try {
      if (writer != null) {
        writer.close();
      }
      if (filename == null)
        setDefaultFilename();
      else
        writer = new PrintWriter(new FileOutputStream(filename, true));
    } catch (Exception ex) {
      setDefaultFilename();
    }
  }

  public static void close() {
    if (writer != null)
      writer.close();
    writer = null;
  }

  public static void print(String message, int level) {
    if (writer == null)  {
      setFilename(null);
      if (writer == null)
        return;
    }
    writer.print(message);
  }

  public static void println(String message, int level) {
    print(message, level);
    print("\n", level);
  }

  public static void println(String message) {
    println(message, 0);
  }
}