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

public class FolderHelper {

  public static boolean deleteRecursive(File folder) {
    if (folder == null)
      return false;
    boolean failed = false;
    File[] files = folder.listFiles();
    for (int i=0; i<files.length; i++) {
      if (files[i].isDirectory()) {
        if (!deleteRecursive(files[i]))
          failed = true;
      }
      if (!files[i].delete())
        failed = true;
    }
    if (!folder.delete())
      failed = true;
    return !failed;
  }
}