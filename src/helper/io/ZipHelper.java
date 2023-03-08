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
import java.util.Enumeration;
import java.util.zip.*;

public class ZipHelper {

  private static byte[] buffer = new byte[256];

  public static synchronized void dumpZipEntry(InputStream istream, String name, File folder)
    throws IOException {

    File outFile = new File(folder, name);
    BufferedOutputStream ostream = new BufferedOutputStream(new FileOutputStream(outFile));
    int count;
    while ((count = istream.read(buffer, 0, 256)) != -1) {
      ostream.write(buffer, 0, count);
    }
    ostream.close();
  }

  public static synchronized void dumpZipFile(ZipFile zipFile, File folder)
    throws IOException {

    Enumeration e = zipFile.entries();
    while (e.hasMoreElements()) {
      ZipEntry entry = (ZipEntry)e.nextElement();
      File zipName = new File(entry.getName());
      InputStream istream = zipFile.getInputStream(entry);
      dumpZipEntry(istream, zipName.getName(), folder);
      istream.close();
    }
  }
}