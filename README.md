# Digital Audio Effects Interactive Demo Applications

A set of Java applications each of which an interactive demonstration application of digital audio effects and processes.

Developed in 2009 to 2010

Copyright (C) 2010 - Andrew Kwok-Fai Lui

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
> 
> `java -cp "./bin" ApplicationLauncher`

### Running Individual Application

Alternatively, each of the 11 applications can be executed from their main classes (as in the above list).

> `cd /app/DMAudioDemo`
> 
> `java -cp "./bin" faifai.audio.WaveGeneratorApplication`

## Overview of the Applications

### Wave Generator 
Class: `faifai.audio.WaveGeneratorApplication`
* Generate sine, triangle and square, sawtooth, click-sine waveforms
* Set frequency and amplitude
* Visualize the waveform

<img width="472" alt="Screenshot 2022-10-30 at 10 07 49 PM" src="https://user-images.githubusercontent.com/8808539/198888792-345a469e-6624-46f3-8aa2-12d67849391b.png">

### Wave Harmonics Generator 
Class: `faifai.audio.WaveHarmonicsGeneratorApplication`
* Set frequency
* Set the amplitudes of 4 harmonics
* Visualize the waveform

<img width="472" alt="Screenshot 2022-10-31 at 12 05 26 AM" src="https://user-images.githubusercontent.com/8808539/198889078-3f0fa760-f6e7-4f11-983d-de141aab81ad.png">

### Audio File Playback
Class: `faifai.audio.AudioFilePlaybackApplication`
* Load audio file, play and record
* Visualize the waveform

<img width="472" alt="Screenshot 2022-10-30 at 10 01 22 PM" src="https://user-images.githubusercontent.com/8808539/198889167-8ebb4cb2-8e7b-4198-8d81-3bad9cf90625.png">

### Resampling Demo 
Class: `faifai.audio.ResamplingDemoApplication`
* Upsample and Downsample
* Visualize the waveforms before and after the processing

<img width="432" alt="Screenshot 2022-10-30 at 10 07 32 PM" src="https://user-images.githubusercontent.com/8808539/198889292-433e6f34-2f71-451c-8ff2-eaeb6c44bc6c.png">

### Quantization Demo 
Class: `faifai.audio.QuantizationDemoApplication`
* Change quantization levels between 8 bits to 1 bit
* Visualize the waveforms before and after the processing

<img width="432" alt="Screenshot 2022-10-30 at 10 07 18 PM" src="https://user-images.githubusercontent.com/8808539/198889346-f5d59f0c-5ae5-4f39-88e8-75a24a012347.png">

### Frequency Masking Demo 
Class: `faifai.audio.MaskingDemoApplication`
* Play two audio signal of different frequencies at the same time.

<img width="592" alt="Screenshot 2022-10-30 at 10 07 01 PM" src="https://user-images.githubusercontent.com/8808539/198889392-f8af6fb8-6bce-4807-9c47-b2f2ae2b2b49.png">

Instruction: set two signals to the same frequency with one at a significantly lower amplitude than the other. Adjust the weaker signal's frequency (turning it up) until you start hearing it. The difference in frequency is the marked zone. Refer to this [page](https://en.wikipedia.org/wiki/Auditory_masking) for information about frequency masking

### Audio Effect Demo 
Class: `faifai.audio.EffectApplication`
* Supports digital audio processing such as amplify, boost, fade-in, fade-out, and high-pass , low-pass, band-pass and band-reject filters. It also supports reverse and reverb

<img width="472" alt="Screenshot 2022-10-30 at 10 04 05 PM" src="https://user-images.githubusercontent.com/8808539/198889697-24ac3936-f98b-4fa3-a824-bf7515b0c874.png">

### The Midi Demo

The Midi demo include the following classes.

* Midi Instrument Demo (`faifai.midi.MidiInstrumentApplication`)
* Midi Chord Demo (`faifai.midi.MidiChordApplication`)
* Midi File Player (`faifai.midi.MidiFilePlayerApplication`)
* Midi Instrument Record Demo (`faifai.midi.MidiInstrumentSaveApplication`)

These demo applications play MIDI sound either interactively or from a Midi file.

