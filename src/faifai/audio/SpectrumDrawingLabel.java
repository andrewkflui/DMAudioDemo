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

public class SpectrumDrawingLabel extends JLabel implements Runnable {

  protected int marginTop = 2;
  protected int marginLeft = 5;
  protected byte[] dataBuffer = null;
  protected double frameRate;
  protected double sampleRate;
  protected int frameSize;
  protected int channel;
  protected boolean isBigEndian;
  protected int dataLength;
  private Color backgroundColor = new Color(255, 153, 0);
  private Color foregroundColor = new Color(96, 96, 96);
  private Color spikeColor = new Color(32, 32, 32);
  private Color scaleColor = new Color(204, 255, 0);
  private Font freqTextFont = new Font("Arial", Font.ITALIC, 9);
  protected boolean toClose = false;
  protected static final double spikeThreshold = 1.0;
  protected double maxAmplitude = spikeThreshold;
  protected final Spike spikeArray[] = new Spike[20];
  protected int spikeCount = 0;
  protected int printSpikeIndex = 0;
  public static final int SPIKES = 0;
  public static final int SCALE = 1;
  protected int displayMode = SPIKES;
  private boolean toStop = false;
  private boolean isGenerating = false;
  private boolean isUpdatedData = false;
  private boolean isPainting = false;

  public SpectrumDrawingLabel() {
    for (int i = 0; i < spikeArray.length; i++) {
      spikeArray[i] = new Spike();
    }
    Thread theThread = new Thread(this);
    theThread.start();
  }

  public void setDataBuffer(byte[] dataBuffer, int dataLength, AudioFormat format) {
    if (isGenerating) {
      return;
    }
    this.dataBuffer = dataBuffer;
    this.dataLength = dataLength;
    this.frameSize = format.getFrameSize();
    this.frameRate = format.getFrameRate();
    this.channel = format.getChannels();
    this.sampleRate = format.getSampleRate();
    this.isBigEndian = format.isBigEndian();
    isUpdatedData = true;
  }

  public void close() {
    toStop = true;
  }

  public void run() {
    while (true) {
      if (toStop) {
        break;
      }
      if (!isPainting) {
        if (isUpdatedData) {
          generateSpectrum();
          repaint();
          isUpdatedData = false;
        }
      }
      try {
        Thread.currentThread().sleep(200);
      } catch (Exception ex) {
      }
    }
  }

  public void setDisplayMode(int displayMode) {
    this.displayMode = displayMode;
  }

  public void increasePrintSpikeIndex() {
    this.printSpikeIndex = (this.printSpikeIndex + 1) % spikeCount;
    repaint();
  }

  public void decreasePrintSpikeIndex() {
    this.printSpikeIndex = (this.printSpikeIndex + spikeCount - 1) % spikeCount;
    repaint();
  }

  private void generateSpectrum() {
    if (dataBuffer == null) {
      return;
    }
    isGenerating = true;
    int repeatedDataLength = dataLength;
    if (repeatedDataLength < 2000) {
      repeatedDataLength = (int) (dataLength * 16);
    }
    int frameCount = repeatedDataLength / frameSize;
    //Calculate the length in seconds of the sample
    float T = (float) (frameCount / frameRate);
    //System.out.println("T = " + T + " (length of sampled sound in seconds)");
    //Calculate the number of equidistant points in time
    int n = (int) (T * sampleRate) / 2;
    //System.out.println("n = " + n + " (number of equidistant points)");
    //Calculate the time interval at each equidistant point
    float h = (T / n);
    //System.out.println("h = " + h + " (length of each time interval in seconds)");
    //this array is the value of the signal at time i*h
    int x[] = new int[n];
    //convert each pair of byte values from the byte array to an Endian value
    for (int frameIndex = 0; frameIndex < n; frameIndex++) {
      int wrapFrameIndex = frameIndex % (dataLength / frameSize);
      int value = ByteArrayHelper.getValue(dataBuffer, wrapFrameIndex, frameSize, isBigEndian);
      x[frameIndex] = value;
    }
    //do the DFT for each value of x sub j and store as f sub j
    synchronized (spikeArray) {
      spikeCount = 0;
      maxAmplitude = 0;
      double f[] = new double[n / 2];
      for (int j = 0; j < n / 2; j++) {
        double firstSummation = 0;
        double secondSummation = 0;
        for (int k = 0; k < n; k++) {
          double twoPInjk = ((2 * Math.PI) / n) * (j * k);
          firstSummation += x[k] * Math.cos(twoPInjk);
          secondSummation += x[k] * Math.sin(twoPInjk);
        }
        f[j] = Math.abs(Math.sqrt(Math.pow(firstSummation, 2) +
                Math.pow(secondSummation, 2)));
        double amplitude = 2 * f[j] / n;
        double frequency = j * h / T * sampleRate;
        if (amplitude >= spikeThreshold && frequency >= 20) {
          if (spikeCount >= spikeArray.length) {
            isGenerating = false;
            return;
          }
          spikeArray[spikeCount].freq = frequency;
          spikeArray[spikeCount].amp = amplitude;
          spikeCount++;
          if (amplitude > maxAmplitude) {
            maxAmplitude = amplitude;
          }
        }
      }
    }
    isGenerating = false;
  }
  private double scaleFreq[] = {150, 300, 1000, 5000, 10000, 18000};
  private String scaleFreqString[] = {"150Hz", "300Hz", "1kHz", "5KHz", "10kHz", "18kHz"};

  public void paintScale(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(scaleColor);
    int width = super.getWidth() - marginLeft * 2;
    int height = super.getHeight() - marginTop * 2;
    for (int i = 0; i < scaleFreq.length; i++) {
      int x = (int) (((Math.log10(scaleFreq[i])) - 2) / 2.5 * width);
      g2d.drawString(scaleFreqString[i], x - 5, marginTop + 8);
      g2d.drawLine(x, 0, x, marginTop);
    }
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    isPainting = true;
    int width = super.getWidth() - marginLeft * 2;
    int height = super.getHeight() - marginTop * 2;
    g2d.setBackground(backgroundColor);
    g2d.clearRect(0, 0, super.getWidth(), super.getHeight());
    g2d.setFont(freqTextFont);
    g2d.setColor(foregroundColor);
    g2d.drawLine(marginLeft, height - marginTop, width - marginLeft, height - marginTop);
    if (this.displayMode == SCALE) {
      paintScale(g);
    }
    if (spikeCount == 0) {
      isPainting = false;
      return;
    }
    if (printSpikeIndex >= spikeCount) {
      printSpikeIndex = 0;
    }

    g2d.setColor(spikeColor);
    for (int i = 0; i < spikeCount; i++) {
      double freq = spikeArray[i].freq;
      double amp = spikeArray[i].amp;
      int x = (int) (((Math.log10(freq)) - 2) / 2.5 * width);
      int y = (int) ((amp / maxAmplitude) * height * 0.8);
      //g2d.drawLine(x, height - marginTop, x, height - marginTop - y);
      g2d.fillRect(x, height - marginTop - y, 1, y);
      if (this.displayMode == SPIKES && i == printSpikeIndex) {
        g2d.drawString((int) freq + " Hz", x - 5, height - marginTop - y - 2);
      }
    }

    isPainting = false;
  }

  class Spike {

    double freq;
    double amp;
  }
}
