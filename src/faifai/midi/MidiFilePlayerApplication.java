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
import faifai.audio.*;
import helper.gui.SplashScreenFrame;

public class MidiFilePlayerApplication extends javax.swing.JFrame {

  private SimpleClipPlayer simplePlayer;

  /** Creates new form WaveGeneratorApplication */
  public MidiFilePlayerApplication() {
    initComponents();
    masterWaveMonitorPanel1.hideControlPanel();
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

    masterWaveMonitorPanel1 = new faifai.audio.MasterMonitorPanel();
    jPanel2 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    midiFilePlayerPanel1 = new faifai.midi.MidiFilePlayerPanel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenuItem2 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Midi File Player");
    setMinimumSize(new java.awt.Dimension(360, 300));
    getContentPane().setLayout(new java.awt.GridBagLayout());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    getContentPane().add(masterWaveMonitorPanel1, gridBagConstraints);

    jPanel2.setBackground(new java.awt.Color(153, 0, 102));

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
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    getContentPane().add(jPanel2, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    getContentPane().add(midiFilePlayerPanel1, gridBagConstraints);

    jMenuBar1.setBackground(new java.awt.Color(102, 0, 102));

    jMenu1.setForeground(new java.awt.Color(255, 255, 255));
    jMenu1.setText("File");
    jMenu1.setFont(new java.awt.Font("Tahoma", 0, 10));
    jMenu1.setOpaque(false);

    jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 10));
    jMenuItem1.setText("Quit");
    jMenuItem1.setOpaque(false);
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu2.setForeground(new java.awt.Color(255, 255, 255));
    jMenu2.setText("Help");
    jMenu2.setFont(new java.awt.Font("Tahoma", 0, 10));
    jMenu2.setOpaque(false);

    jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 10));
    jMenuItem2.setText("About");
    jMenuItem2.setOpaque(false);
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });
    jMenu2.add(jMenuItem2);

    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    SplashScreenFrame.showFrame("/SplashScreen.png");
  }//GEN-LAST:event_jMenuItem2ActionPerformed

  private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    System.exit(0);
  }//GEN-LAST:event_jMenuItem1ActionPerformed

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    masterWaveMonitorPanel1.startSampling(true);
    midiFilePlayerPanel1.play();
}//GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    midiFilePlayerPanel1.stop();
}//GEN-LAST:event_jButton2ActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new MidiFilePlayerApplication().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JPanel jPanel2;
  private faifai.audio.MasterMonitorPanel masterWaveMonitorPanel1;
  private faifai.midi.MidiFilePlayerPanel midiFilePlayerPanel1;
  // End of variables declaration//GEN-END:variables
}
