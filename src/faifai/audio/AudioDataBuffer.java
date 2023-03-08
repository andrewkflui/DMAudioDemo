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

import helper.io.ArrayHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioDataBuffer {

  protected byte[] dataBuffer;
  protected int dataLength = 0;
  protected File audioFile = null;
  protected String audioResource = null;
  protected AudioFormat format = null;

  public AudioDataBuffer() {
    this.dataBuffer = null;
  }

  public AudioDataBuffer(AudioFormat format, int frameCount) {
    setAudioFormat(format, frameCount);
  }

  public void setAudioFormat(AudioFormat format, int frameCount) {
    this.format = format;
    int frameSize = format.getFrameSize();
    this.dataBuffer = new byte[frameCount * frameSize]; // one second data size
  }

  public final static AudioFormat getCommonAudioFormat() {
    int FRAMERATE = 44100; // 44100 frames per second
    int CHANNEL = 1; // mono
    int SAMPLESIZE = 8; // 8 bit sampling
    boolean ISBIGENDIAN = true;
    int FRAMESIZE = CHANNEL * SAMPLESIZE / 8;
    AudioFormat newformat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
            FRAMERATE, SAMPLESIZE, CHANNEL, FRAMESIZE, FRAMERATE, ISBIGENDIAN);
    return newformat;
  }

  public void loadAudioFile(File audioFile)
          throws IOException, UnsupportedAudioFileException {
    loadAudioFile(new FileInputStream(audioFile));
    this.audioFile = audioFile;
  }

  public void loadAudioFile(String audioResource)
          throws IOException, UnsupportedAudioFileException {
    loadAudioFile(audioResource.getClass().getResourceAsStream(audioResource));
    this.audioResource = audioResource;
  }

  public synchronized void loadAudioFile(InputStream audioStream)
          throws IOException, UnsupportedAudioFileException {
    /* Initializing an AudioInputStream for reading */
    AudioInputStream aistream = AudioSystem.getAudioInputStream(audioStream);
    this.format = aistream.getFormat();
    if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
      throw new UnsupportedAudioFileException("[AudioDataBuffer] Do not support " + format.getEncoding());
    }

    this.dataLength = 16384;
    this.dataBuffer = new byte[this.dataLength];
    int amount;
    int count = 0;
    while (true) {
      amount = aistream.read(dataBuffer, count, dataLength - count);
      if (amount == -1) {
        break;
      }
      count += amount;
      if (count >= this.dataLength) {
        //System.out.println(count + " Changing from " + this.dataLength + " to " + (this.dataLength * 4));
        this.dataBuffer = (byte[]) ArrayHelper.resizeArray(this.dataBuffer, this.dataLength * 4);
        this.dataLength = this.dataLength * 4;
      }
    }
    this.dataLength = count;
  }

  public synchronized boolean is8BitMono() {
    if (format == null) {
      return false;
    }
    return format.getSampleSizeInBits() == 8 && format.getChannels() == 1;
  }

  public synchronized void to8BitMono() {
    if (this.format == null) {
      return;
    }
    int frameSize = format.getFrameSize();
    int newDataLength = this.dataLength / frameSize;
    byte newDataBuffer[] = new byte[newDataLength];
    int frameCount = newDataLength;
    int channel = format.getChannels();
    int sampleSize = format.getSampleSizeInBits() / 8;
    boolean isBigEndian = format.isBigEndian();
    float frameRate = format.getFrameRate();
    for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
      if (isBigEndian) {
        newDataBuffer[frameIndex] = dataBuffer[frameIndex * frameSize];
      } else {
        newDataBuffer[frameIndex] = dataBuffer[frameIndex * frameSize + (sampleSize - 1) * channel];
      }
    }
    this.format = new AudioFormat(frameRate, 8, 1, true, isBigEndian);
    this.dataBuffer = newDataBuffer;
    this.dataLength = newDataLength;
  }

  public synchronized boolean isReady() {
    return this.format != null && this.dataBuffer != null;
  }

  public synchronized byte[] getDataBuffer() {
    return this.dataBuffer;
  }

  public synchronized AudioFormat getAudioFormat() {
    return this.format;
  }

  public synchronized int getDataLength() {
    return this.dataLength;
  }

  public synchronized float getFrameRate() {
    return format.getFrameRate();
  }

  public synchronized int getFrameSize() {
    return format.getFrameSize();
  }

  public synchronized int getFrameCount() {
    return this.dataLength / format.getFrameSize();
  }

  public synchronized boolean isBigEndian() {
    return format.isBigEndian();
  }

  public synchronized void setDataLength(int dataLength) {
    this.dataLength = dataLength;
  }

  public static void main(String args[]) throws Exception {
    AudioDataBuffer buffer = new AudioDataBuffer();
    buffer.loadAudioFile(new File("1-welcome.wav"));
    System.out.println(buffer.getAudioFormat());
    SimpleClipPlayer player = new SimpleClipPlayer();
    player.setLoop(-1);
    player.load(buffer);
    player.play();
  }
}
