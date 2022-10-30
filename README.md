# Digital Audio Effects Interactive Demo Applications

A set of Java applications each of which an interactive demonstration application of digital audio effects and processes.

Developed in 2009 to 2010

Copyright (C) 2021 - Andrew Kwok-Fai Lui

The Open University of Hong Kong

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, see http://www.gnu.org/licenses/.

## Introduction

There are 11 interactive demo applications included in this package

1. Wave Generator (`faifai.audio.WaveGeneratorApplication`)
2. Audio Playback (`faifai.audio.AudioFilePlaybackApplication`)
3. Resampling Demo (`faifai.audio.ResamplingDemoApplication`)
4. Quantization Demo (`faifai.audio.QuantizationDemoApplication`)
5. Frequency Masking Demo (`faifai.audio.MaskingDemoApplication`)
6. Midi Instrument Demo (`faifai.midi.MidiInstrumentApplication`)
7. Midi Chord Demo (`faifai.midi.MidiChordApplication`)
8. Midi File Player (`faifai.midi.MidiFilePlayerApplication`)
9. Midi Instrument Record Demo (`faifai.midi.MidiInstrumentSaveApplication`)
10. Audio Effect Demo (`faifai.audio.EffectApplication`)
11. Wave Harmonics Generator (`faifai.audio.WaveHarmonicsGeneratorApplication`)

## Running the Applications

### Pre-requisites

1. Java JRE 1.8 or above

### Running the Applications Through the Launcher

A launcher application `ApplicationLauncher.class` is provided for running each of the 11 applications.

1. Download the repository to a folder, assuming that it is `/app/DMAudioDemo`. The Java classes are found in the `bin` folder.
2. Execute `ApplicationLauncher.class` insider the folder

> `cd /app/DMAudioDemo`
> `java -cp "./bin" ApplicationLauncher`

### Running Individual Application

Alternatively, each of the 11 applications can be executed from their main classes (as in the above list).

> `cd /app/DMAudioDemo`
> `java -cp "./bin" faifai.audio.WaveGeneratorApplication`

