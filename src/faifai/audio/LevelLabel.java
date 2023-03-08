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
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;

public class LevelLabel extends JLabel {

  protected int marginTop = 2;
  protected int marginLeft = 5;
  protected int blockWidth = 5;
  protected int blockWidthMargin = 2;
  protected int blockHeight = 12;
  protected int blockHeightMargin = 5;
  private Color backgroundColor = new Color(204, 255, 51);
  private Color foregroundColor = new Color(64, 64, 64);
  private Color maxLevelColor = new Color(255, 64, 64);
  private int channelCount = 1;
  private double channelData[] = new double[channelCount];

  public void setChannelCount(int channelCount) {
    this.channelCount = channelCount;
    this.channelData = new double[channelCount];
  }

  public void setData(int channel, double data) {
    if (channel < 0 || channel > channelCount) {
      return;
    }
    channelData[channel] = data;
  }

  public void setData(double data) {
    setData(0, data);
  }

  public void refresh() {
    repaint();
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int width = super.getWidth() - marginLeft * 2;
    int height = super.getHeight() - marginTop * 2;
    g2d.setBackground(backgroundColor);
    g2d.clearRect(0, 0, super.getWidth(), super.getHeight());

    int blockBoxWidth = blockWidth + blockWidthMargin;
    int blockBoxHeight = blockHeight + blockHeightMargin;
    int maxBlockCount = width / blockBoxWidth;
    int ypos = marginTop;

    for (int c = 0; c < channelCount; c++) {
      int numBlocks = (int) (channelData[c] * maxBlockCount);
      g2d.setColor(foregroundColor);
      for (int x = 0; x < numBlocks; x++) {
        if (x >= maxBlockCount - 2) {
          g2d.setColor(maxLevelColor);
          g2d.fillRect(x * blockBoxWidth + marginLeft, ypos + blockHeightMargin, blockWidth, blockHeight);
          break;
        } else {
          g2d.fillRect(x * blockBoxWidth + marginLeft, ypos + blockHeightMargin, blockWidth, blockHeight);
        }
      }
      ypos = ypos + blockBoxHeight;
    }
    //super.setPreferredSize(new Dimension(width, ypos + marginTop));
  }
}
