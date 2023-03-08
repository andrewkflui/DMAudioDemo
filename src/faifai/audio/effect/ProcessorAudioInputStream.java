package faifai.audio.effect;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import faifai.audio.*;

public class ProcessorAudioInputStream extends AudioInputStream implements Runnable {

  protected InputStream inputStream;
  protected final int BUFFERSIZE = 44100;
  protected byte[] data = new byte[BUFFERSIZE];
  protected int startIndex = 0;
  protected int endIndex = 0;
  protected int dataCount = 0;
  protected Thread thisThread;
  protected boolean toClose = false;
  protected boolean endOfStream = false;
  protected AmplifyEffect amEffect;
  protected int loop;

  public ProcessorAudioInputStream(InputStream inputStream, AudioFormat format, int loop) {
    super(null, format, AudioSystem.NOT_SPECIFIED);
    this.inputStream = inputStream;
    this.loop = loop;
    this.amEffect = new AmplifyEffect();
    thisThread = new Thread(this);
    thisThread.start();
  }
  /*
  public ProcessorAudioInputStream(TargetDataLine line) {
  this(new AudioInputStream(line), line.getFormat(), AudioSystem.NOT_SPECIFIED);
  }
   */

  public void run() {
    byte buffer[] = new byte[1024];
    int count = 0;
    System.out.println("[ProcessorAudioInputStream] Processor is ready");
    while (true) {
      if (toClose) {
        break;
      }
      try {
        if (endOfStream) {
          Thread.sleep(100);
          continue;
        }
        count = inputStream.read(buffer);
        if (count == -1) {
          if (loop == -1 || loop-- > 0) {
            inputStream.reset();
            continue;
          }
          endOfStream = true;
        } else {
          //amEffect.apply(buffer, count);
          synchronized (this) {
            if (count < BUFFERSIZE - dataCount) {
              for (int i = 0; i < count; i++) {
                data[endIndex] = buffer[i];
                endIndex = (endIndex + 1) % BUFFERSIZE;
              }
              dataCount += count;
              count = 0;
            }
          }
        }
        Thread.sleep(1);
      } catch (InterruptedException ex) {
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public synchronized int available() throws IOException {
    return dataCount;
  }

  public synchronized void close() throws IOException {
    toClose = true;
    inputStream.close();
  }

  public boolean markSupported() {
    return false;
  }

  public synchronized int read() throws IOException {
    if (endOfStream && dataCount == 0) {
      return -1;
    }
    if (dataCount == 0) {
      return 0;
    }
    byte theData = data[startIndex];
    startIndex = (startIndex + 1) % BUFFERSIZE;
    dataCount--;
    return theData;
  }

  public synchronized int read(byte[] b) throws IOException {
    return read(b, 0, b.length);
  }

  public synchronized int read(byte[] b, int off, int len) throws IOException {
    if (endOfStream && dataCount == 0) {
      return -1;
    }
    if (dataCount == 0) {
      return 0;
    }
    int count = 0;
    while (true) {
      if (count >= len || dataCount <= 0) {
        break;
      }
      b[off++] = data[startIndex];
      startIndex++;
      startIndex = (startIndex + 1) % BUFFERSIZE;
      dataCount--;
      count++;
    }
    return count;
  }

  public synchronized void reset() throws IOException {
    inputStream.reset();
    endOfStream = false;
    startIndex = endIndex = dataCount = 0;
  }

  public synchronized long skip(long n) throws IOException {
    if (n > dataCount) {
      n = dataCount;
    }
    startIndex = (int) (startIndex + n) % BUFFERSIZE;
    dataCount -= n;
    return n;
  }

  public static void main(String args[]) throws Exception {
    WaveGenerator generator = new WaveGenerator();
    SimpleStreamPlayer player = new SimpleStreamPlayer();
    generator.setFrequency(2000);
    Thread.sleep(100);
    player.setLoop(-1);
    AudioInputStream processedStream = new ProcessorAudioInputStream(generator.getAudioInputStream(),
            generator.getAudioFormat(), -1);
    //player.play(generator.getAudioInputStream());
    player.play(processedStream);
  }
}
