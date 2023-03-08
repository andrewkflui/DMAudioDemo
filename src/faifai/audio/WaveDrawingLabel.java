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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.sound.sampled.AudioFormat;
import javax.swing.JLabel;

public class WaveDrawingLabel extends JLabel {

  protected int marginTop = 2;
  protected int marginLeft = 10;
  protected byte[] dataBuffer = null;
  protected double frameRate;
  protected int frameSize;
  protected int channel;
  protected boolean isBigEndian;
  protected int dataLength;
  private Color backgroundColor = new Color(255, 153, 0);
  private Color foregroundColor = new Color(64, 64, 64);
  private Color tickColor = new Color(192, 192, 192);
  private Font tickTextFont = new Font("Monospaced", 0, 9);
  protected double timeDataLength = 0; /* the length of data in time */
  protected double scaleX = 0.025; /* the width scale is 0.025s */
  protected double scaleY = 1.0; /* the height scale is 1.00 */
  protected double timeStart = 0.0; /* the starting time */

  protected int[] sampledFrameIndex = null;
  protected int htickCount = 0;
  protected boolean isPrintTime = false;
  protected boolean isRepeatDrawWave = false;

  public void setDataBuffer(byte[] dataBuffer, int dataLength, AudioFormat format) {
    this.dataBuffer = dataBuffer;
    this.dataLength = dataLength;
    this.frameSize = format.getFrameSize();
    this.frameRate = format.getFrameRate();
    this.channel = format.getChannels();
    this.isBigEndian = format.isBigEndian();
    this.timeDataLength = dataLength / frameSize / frameRate;
    repaint();
  }

  public void setHorizontalTicks(int[] sampledFrameIndex) {
    this.sampledFrameIndex = sampledFrameIndex;
  }

  public void setPrintTime(boolean isPrintTime) {
    this.isPrintTime = isPrintTime;
  }

  public void setRepeatDrawWave(boolean isRepeatDrawWave) {
    this.isRepeatDrawWave = isRepeatDrawWave;
  }

  public void increaseStartTime() {
    if (this.timeStart > timeDataLength) {
      return;
    }
    this.timeStart += this.scaleX;
    repaint();
  }

  public void decreaseStartTime() {
    this.timeStart -= this.scaleX;
    if (this.timeStart < 0) {
      this.timeStart = 0;
    }
    repaint();
  }

  public void setTimeScale(double scaleX) {
    this.scaleX = scaleX;
  }

  public void downTimeScale() {
    scaleX = scaleX / 2;
    repaint();
  }

  public void upTimeScale() {
    scaleX = scaleX * 2;
    repaint();
  }

  public double getTimeScale() {
    return scaleX;
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int width = super.getWidth() - marginLeft * 2;
    int height = super.getHeight() - marginTop * 2;
    g2d.setBackground(backgroundColor);
    g2d.clearRect(0, 0, super.getWidth(), super.getHeight());
      g2d.setFont(tickTextFont);
    if (dataBuffer == null) {
      return;
    }
    if (width <= 0 || height <= 0) {
      return;
    }
    if (dataLength == 0) {
      return;
    }
    g2d.setColor(foregroundColor);
    int lastx = -1;
    int lasty = -1;
    int valueScale = (int) Math.pow(2, frameSize * 8 - 1);
    int frameCount = dataLength / frameSize;
    int offsetX = (int)(timeStart * frameRate);
    for (int x = 0; x < width; x++) {
      int frameIndex = (int) (scaleX * frameRate * (x + offsetX) / width);
      if (frameIndex >= frameCount && !this.isRepeatDrawWave)
        break;
      int wrapFrameIndex = frameIndex % frameCount;
      int value = ByteArrayHelper.getValue(dataBuffer, wrapFrameIndex, frameSize, isBigEndian);
      value = (-value) + valueScale;
      int y = (int) (value * height / (valueScale * 2)) + marginTop;
      if (lastx != -1) {
        g2d.drawLine(lastx + marginLeft, lasty, x + marginLeft, y);
      }
      lastx = x;
      lasty = y;
    }
    if (isPrintTime) {
      String timeStartStr = "" + ((int)(timeStart * 1000))/1000.0;
      String timeEndStr = "" + ((int)((timeStart + scaleX) * 1000))/1000.0;

      g2d.drawString(timeStartStr, 5, height);
      g2d.drawString(timeEndStr, width - 5, height);
    }
    if (sampledFrameIndex == null) {
      return;
    }
    g2d.setColor(tickColor);
    for (int i = 0; i < sampledFrameIndex.length; i++) {
      int x = (int) (sampledFrameIndex[i] / scaleX / frameRate * width);
      g2d.drawLine(x + marginLeft, marginTop, x + marginLeft, height - marginTop);
    }
  }
}
