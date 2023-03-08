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

import java.util.ArrayList;

import faifai.audio.effect.AbstractEffect;
import faifai.audio.effect.AmplifyEffect;
import faifai.audio.effect.BandPassEffect;
import faifai.audio.effect.BandRejectEffect;
import faifai.audio.effect.BoostEffect;
import faifai.audio.effect.FadeInEffect;
import faifai.audio.effect.FadeOutEffect;
import faifai.audio.effect.HighPassEffect;
import faifai.audio.effect.LowPassEffect;
import faifai.audio.effect.ReverbEffect;
import faifai.audio.effect.ReverseEffect;

public class EffectProcess implements Runnable {

  // flags for controlling the playing of audi
  protected boolean toStop = false;
  protected Thread thread = null; // the thread that plays audio data
  private AudioDataBuffer audioDataBuffer = null;  // buffer for holding the audio data to be played
  private AudioDataBuffer outputDataBuffer = null;  // buffer for holding the audio data to be played
  protected ArrayList<AbstractEffect> effectList = new ArrayList<AbstractEffect>();

  public EffectProcess() {
    effectList.add(new AmplifyEffect());
    effectList.add(new BoostEffect());
    effectList.add(new FadeInEffect());
    effectList.add(new FadeOutEffect());
    effectList.add(new HighPassEffect());
    effectList.add(new LowPassEffect());
    effectList.add(new BandPassEffect());
    effectList.add(new BandRejectEffect());
    effectList.add(new ReverseEffect());
    effectList.add(new ReverbEffect());
    thread = new Thread(this);
    thread.start();
  }

  public AbstractEffect getEffect(Class theClass) {
    for (AbstractEffect effect : effectList) {
      if (effect.getClass() == theClass) {
        return effect;
      }
    }
    return null;
  }

  public AudioDataBuffer connect(AudioDataBuffer audioDataBuffer) {
    this.audioDataBuffer = audioDataBuffer;
    this.outputDataBuffer = new AudioDataBuffer();
    return this.outputDataBuffer;
  }

  AudioDataBuffer getInputDataBuffer() {
    return this.audioDataBuffer;
  }

  AudioDataBuffer getOutputDataBuffer() {
    return this.outputDataBuffer;
  }

  public float getSamplingRate() {
    if (audioDataBuffer == null) {
      return 0;
    }
    return audioDataBuffer.getFrameRate();
  }

  public void run() {
    while (true) {
      if (toStop) {
        break;
      }
      if (audioDataBuffer != null && audioDataBuffer.isReady()) {
        outputDataBuffer.setAudioFormat(audioDataBuffer.getAudioFormat(),
                audioDataBuffer.getFrameCount());
        byte[] outputData = outputDataBuffer.getDataBuffer();
        byte[] inputData = audioDataBuffer.getDataBuffer();
        outputDataBuffer.setDataLength(audioDataBuffer.getDataLength());
        int len = audioDataBuffer.getDataLength();
        for (int index = 0; index < len; index++) {
          outputData[index] = inputData[index];
        }
        ReverbEffect reverbEffect = (ReverbEffect)getEffect(ReverbEffect.class);
        for (AbstractEffect effect : effectList) {
          effect.apply(outputData, len);
        }
      }
      try {
        thread.sleep(50);
      } catch (InterruptedException ex) {
        break;
      }
    }
  }

  public void close() {
    toStop = true;
    thread.interrupt();
  }
}
