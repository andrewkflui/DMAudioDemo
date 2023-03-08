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
package faifai.midi;

import java.util.Vector;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.swing.JOptionPane;

public class MidiChordPanel extends javax.swing.JPanel {

  private Synthesizer synth;
  private MidiChannel channels[];
  private Soundbank soundBank;
  Instrument instrumentArray[];
  private int midiNumberStart = 60;
  private int midiNumberEnd = 71;
  private String noteNames[] = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "Ab", "A", "Bb", "B"};
  private String chordNames[] = {"Major", "Minor (m)", "Augmented (aug)", "Diminished (dim)",
    "Suspended-second (sus2)", "Suspended-fourth (sus4)", "Dominant-seventh (7)", 
    "Major-seventh (Maj7)", "Minor-seventh (m7)"};
  private int pressure = 255;


  /** Creates new form MediaLoaderBean */
  public MidiChordPanel() {
    initComponents();
    jPanel2.setVisible(false);
    try {
      initMidi();
      jTextArea1.setText("MIDI SYSTEM IS READY");
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex, "Midi Error", JOptionPane.ERROR_MESSAGE);
      jTextArea1.setText("MIDI SYSTEM ERROR");
    }
    initSelectionLists();
  }

  private void initMidi() throws Exception {
    synth = MidiSystem.getSynthesizer();
    synth.open();
    channels = synth.getChannels();
    soundBank = synth.getDefaultSoundbank();
    synth.loadAllInstruments(soundBank);
    instrumentArray = synth.getLoadedInstruments();
    for (int i = 0; i < instrumentArray.length; i++) {
      System.out.println(instrumentArray[i].getName());
      if (instrumentArray[i].getName().equals("Violin")) {
        Patch thePatch = instrumentArray[i].getPatch();
        channels[1].programChange(thePatch.getBank(), thePatch.getProgram());
        break;
      }
    }
  }

  private String getMidiNoteName(int midiNum) {
    int cycle = (midiNum - 12) / 12;
    int notePos = (midiNum - 12) % 12;
    return noteNames[notePos] + cycle;
  }

  private void initSelectionLists() {
    jList2.setListData(chordNames);
    jList2.setSelectedIndex(0);

    Vector noteNameList = new Vector();
    for (int i = midiNumberStart; i <= midiNumberEnd; i++) {
      noteNameList.add(getMidiNoteName(i) + " (MIDI NUMBER " + i + ")");
    }
    jList1.setListData(noteNameList);
    jList1.setSelectedIndex(60 - midiNumberStart);
    jList1.ensureIndexIsVisible(60 - midiNumberStart);
  }

  private void updateDisplay() {
  }

  public void showPlayControl() {
    jPanel2.setVisible(true);
  }

  public void play() {
    int midiNumber = jList1.getSelectedIndex() + midiNumberStart;
    int chordType = jList2.getSelectedIndex();
    if (midiNumber == -1 || chordType == -1) {
      JOptionPane.showMessageDialog(this, "Select one musical note and one instrument", "Midi Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    int midiNumberArray[] = new int[4];
    midiNumberArray[0] = midiNumber;
    if (chordType == 0) { // major
      midiNumberArray[1] = midiNumber + 4;
      midiNumberArray[2] = midiNumber + 7;
    } else if (chordType == 1) { // minor
      midiNumberArray[1] = midiNumber + 3;
      midiNumberArray[2] = midiNumber + 7;
    } else if (chordType == 2) { // aug
      midiNumberArray[1] = midiNumber + 4;
      midiNumberArray[2] = midiNumber + 8;
    } else if (chordType == 3) { // dim
      midiNumberArray[1] = midiNumber + 3;
      midiNumberArray[2] = midiNumber + 6;
    } else if (chordType == 4) { // sus2
      midiNumberArray[1] = midiNumber + 5;
      midiNumberArray[2] = midiNumber + 7;
    } else if (chordType == 5) { // sus4
      midiNumberArray[1] = midiNumber + 2;
      midiNumberArray[2] = midiNumber + 7;
    } else if (chordType == 6) { // 7
      midiNumberArray[1] = midiNumber + 4;
      midiNumberArray[2] = midiNumber + 7;
      midiNumberArray[3] = midiNumber + 10;
    } else if (chordType == 7) { // Maj7
      midiNumberArray[1] = midiNumber + 4;
      midiNumberArray[2] = midiNumber + 7;
      midiNumberArray[3] = midiNumber + 11;
    } else if (chordType == 8) { // m7
      midiNumberArray[1] = midiNumber + 3;
      midiNumberArray[2] = midiNumber + 7;
      midiNumberArray[3] = midiNumber + 10;
    }

    for (int i = 0; i < midiNumberArray.length; i++) {
      if (midiNumberArray[i] > 0) {
        channels[1].noteOn(midiNumberArray[i], 255);
      }
    }
  }

  public void stop() {
    channels[1].allNotesOff();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jTextArea1 = new javax.swing.JTextArea();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList();
    jScrollPane2 = new javax.swing.JScrollPane();
    jList2 = new javax.swing.JList();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    setBackground(new java.awt.Color(153, 0, 102));
    setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Midi Chord Player", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(255, 255, 255))); // NOI18N
    setMaximumSize(new java.awt.Dimension(240, 240));
    setMinimumSize(new java.awt.Dimension(240, 240));
    setPreferredSize(new java.awt.Dimension(240, 240));
    setLayout(new java.awt.GridBagLayout());

    jTextArea1.setBackground(new java.awt.Color(204, 255, 51));
    jTextArea1.setColumns(20);
    jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 10));
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(3);
    jTextArea1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jTextArea1.setMaximumSize(new java.awt.Dimension(120, 32));
    jTextArea1.setMinimumSize(new java.awt.Dimension(120, 32));
    jTextArea1.setPreferredSize(new java.awt.Dimension(120, 32));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    add(jTextArea1, gridBagConstraints);

    jPanel1.setMinimumSize(new java.awt.Dimension(60, 20));
    jPanel1.setOpaque(false);
    jPanel1.setPreferredSize(new java.awt.Dimension(60, 20));
    jPanel1.setLayout(new java.awt.GridBagLayout());

    jScrollPane1.setMinimumSize(new java.awt.Dimension(80, 100));
    jScrollPane1.setPreferredSize(new java.awt.Dimension(80, 100));

    jList1.setBackground(new java.awt.Color(255, 204, 0));
    jList1.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
    jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane1.setViewportView(jList1);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
    jPanel1.add(jScrollPane1, gridBagConstraints);

    jScrollPane2.setMinimumSize(new java.awt.Dimension(80, 100));
    jScrollPane2.setPreferredSize(new java.awt.Dimension(80, 100));

    jList2.setBackground(new java.awt.Color(255, 204, 0));
    jList2.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
    jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane2.setViewportView(jList2);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    jPanel1.add(jScrollPane2, gridBagConstraints);

    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
    jLabel1.setText("Musical Note");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    jPanel1.add(jLabel1, gridBagConstraints);

    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("Chord");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    jPanel1.add(jLabel2, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    add(jPanel1, gridBagConstraints);

    jPanel2.setOpaque(false);

    jButton1.setBackground(new java.awt.Color(255, 255, 0));
    jButton1.setFont(new java.awt.Font("Tahoma", 2, 8));
    jButton1.setText("Play");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel2.add(jButton1);

    jButton2.setBackground(new java.awt.Color(255, 255, 0));
    jButton2.setFont(new java.awt.Font("Tahoma", 2, 8));
    jButton2.setText("Stop");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel2.add(jButton2);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jPanel2, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    play();
  }//GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    stop();
  }//GEN-LAST:event_jButton2ActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JList jList1;
  private javax.swing.JList jList2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTextArea jTextArea1;
  // End of variables declaration//GEN-END:variables
}
