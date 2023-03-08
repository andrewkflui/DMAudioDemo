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
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class MasterSaveAudioPanel extends javax.swing.JPanel implements Runnable {

  private AudioDataBuffer audioDataBuffer = null;
  private TargetDataLine targetDataLine = null;
  private AudioInputStream aistream = null;
  private AudioFileFormat.Type saveFileType = AudioFileFormat.Type.WAVE;
  private Thread thisThread;
  private boolean toClose = false;
  private boolean toRecord = false;

  /** Creates new form WaveGeneratorPanel */
  public MasterSaveAudioPanel() {
    initComponents();
    thisThread = new Thread(this);
    thisThread.start();
    updateDisplay();
  }

  private void updateDisplay() {
    if (selectedFile == null) {
      jTextArea1.setText("NO OUTPUT AUDIO FILE SELECTED");
    } else {
      jTextArea1.setText("OUTPUT AUDIO FILE SELECTED: " + selectedFile.getAbsolutePath());
    }
  }

  public void hideControlPanel() {
    jPanel1.setVisible(false);
  }

  public void close() {
    toClose = true;
  }

  public void run() {
    while (true) {
      if (toClose) {
        break;
      }
      try {
        if (toRecord) {
          AudioSystem.write(aistream, saveFileType, selectedFile);
        }
        thisThread.sleep(250);
      } catch (InterruptedException ex) {
      } catch (Exception ex) {
        ex.printStackTrace();
        break;
      }
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jPanel1 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jTextArea1 = new javax.swing.JTextArea();
    jButton2 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();

    setBackground(new java.awt.Color(153, 0, 102));
    setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Master Audio Recorder", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(255, 255, 255))); // NOI18N
    setMinimumSize(new java.awt.Dimension(240, 120));
    setPreferredSize(new java.awt.Dimension(240, 120));
    setLayout(new java.awt.GridBagLayout());

    jPanel1.setOpaque(false);
    jPanel1.setLayout(new java.awt.GridBagLayout());

    jButton1.setBackground(new java.awt.Color(255, 255, 0));
    jButton1.setFont(new java.awt.Font("Tahoma", 2, 8)); // NOI18N
    jButton1.setText("Select Output File");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1selectButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
    jPanel1.add(jButton1, gridBagConstraints);

    jTextArea1.setBackground(new java.awt.Color(204, 255, 51));
    jTextArea1.setColumns(20);
    jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(3);
    jTextArea1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jTextArea1.setMaximumSize(new java.awt.Dimension(120, 32));
    jTextArea1.setMinimumSize(new java.awt.Dimension(120, 32));
    jTextArea1.setPreferredSize(new java.awt.Dimension(120, 32));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jPanel1.add(jTextArea1, gridBagConstraints);

    jButton2.setBackground(new java.awt.Color(0, 0, 0));
    jButton2.setFont(new java.awt.Font("Tahoma", 2, 8)); // NOI18N
    jButton2.setForeground(new java.awt.Color(255, 255, 255));
    jButton2.setText("Record");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2selectButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel1.add(jButton2, gridBagConstraints);

    jButton3.setBackground(new java.awt.Color(0, 0, 0));
    jButton3.setFont(new java.awt.Font("Tahoma", 2, 8)); // NOI18N
    jButton3.setForeground(new java.awt.Color(255, 255, 255));
    jButton3.setText("Stop");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3selectButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
    jPanel1.add(jButton3, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    add(jPanel1, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents
  private JFileChooser fileChooser = null;
  private File selectedFile = null;

  private void jButton1selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1selectButtonActionPerformed
    if (fileChooser == null) {
      fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setDialogTitle("Select Output Audio File");
      fileChooser.setFileFilter(new AudioFileFilter());
      if (selectedFile == null) {
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
      } else {
        fileChooser.setCurrentDirectory(selectedFile);
      }
    }
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.CANCEL_OPTION) {
      selectedFile = null;
      return;
    }
    selectedFile = fileChooser.getSelectedFile();
    String filename = selectedFile.getName().toLowerCase();
    if (!filename.endsWith(".wav")) {
      selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".wav");
    }
    updateDisplay();
}//GEN-LAST:event_jButton1selectButtonActionPerformed

  private void jButton2selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2selectButtonActionPerformed
    if (toRecord)
      return;
    AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
            44100.0F, 8, 1, 1, 44100.0F, false);
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
    try {
      targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
      targetDataLine.open(audioFormat);
      aistream = new AudioInputStream(targetDataLine);
      targetDataLine.start();
      toRecord = true;
      jTextArea1.setText("RECORDING ...");
      jButton2.setBackground(Color.RED);
    } catch (LineUnavailableException ex) {
      ex.printStackTrace();
    }
  }//GEN-LAST:event_jButton2selectButtonActionPerformed

  private void jButton3selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3selectButtonActionPerformed
    toRecord = false;
    targetDataLine.stop();
    targetDataLine.close();
    jTextArea1.setText("RECORDING STOPPED\n" + "SAVED TO AUDIO FILE: " + selectedFile.getAbsolutePath());
    jButton2.setBackground(Color.BLACK);
  }//GEN-LAST:event_jButton3selectButtonActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JTextArea jTextArea1;
  // End of variables declaration//GEN-END:variables

  class AudioFileFilter extends FileFilter {

    private String suffixArray[] = {".wav"};

    public boolean accept(File file) {
      if (file == null) {
        return false;
      }
      String name = file.getName().toLowerCase();
      if (file.isDirectory()) {
        return true;
      }
      for (int i = 0; i < suffixArray.length; i++) {
        if (name.endsWith(suffixArray[i])) {
          return true;
        }
      }
      return false;
    }

    public String getDescription() {
      return "Audio WAV File (wav)";
    }
  }
}
