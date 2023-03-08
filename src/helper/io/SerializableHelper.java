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

public class SerializableHelper {

  /**
   * Method for loading the review records from a serialized object file.
   */
  public static synchronized Object load(File file) {
    try {
      File fileref = file;
      if (!fileref.exists()) {
        fileref = new File(file.getParentFile(), file.getName() + "~");
        if (!fileref.exists())
          return null;
      }
      load(new FileInputStream(fileref));
    } catch (Exception ex)  {
      System.err.println("[SerializedFileHelper] Error: " + ex);
    }
    return null;
  }

  public static synchronized Object load(InputStream instream) {
    try {
      ObjectInputStream objectStream = new ObjectInputStream(instream);
      Object obj = objectStream.readObject();
      objectStream.close();
      return obj;
    } catch (Exception ex)  {
      System.err.println("[SerializedFileHelper] Error: " + ex);
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * Private method for saving the review records to a serialized object file.
   */
  public static synchronized boolean save(File file, Object obj) {
    try {
      File fileref = file;
      // create a backup file first
      if (fileref.exists()) {
        File backup = new File(file.getParentFile(), file.getName() + "~");
        fileref.renameTo(backup);
        fileref = file;
      }
      ObjectOutputStream objectStream = new ObjectOutputStream(
        new FileOutputStream(fileref));
      objectStream.writeObject(obj);
      objectStream.close();
      return true;
    } catch (Exception ex)  {
      System.err.println("[SerializedFileHelper] Error: " + ex);
    }
    return false;
  }
}

