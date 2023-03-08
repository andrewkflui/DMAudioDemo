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

import helper.gui.SplashScreenFrame;
import helper.gui.TutorialFrame;

public class AudioFilePlaybackApplication extends javax.swing.JFrame {

    private SimpleClipPlayer simplePlayer;
    private TutorialFrame tutorialFrame;

    /** Creates new form WaveGeneratorApplication */
    public AudioFilePlaybackApplication() {
        initComponents();
        simplePlayer = streamPlayerPanel1.startSimplePlayer();
        AudioClip theAudioClip = audioFileSelectorPanel1.getAudioClip();
        simplePlayer.load(theAudioClip.getAudioDataBuffer());
        waveVisualiserPanel1.setAudioData(theAudioClip.getAudioDataBuffer());
        try {
            // tutorialFrame = new TutorialFrame();
            // tutorialFrame.loadText("/tutorial/AudioFilePlaybackApplication.htm");
            // tutorialFrame.showFrame();
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            javax.swing.SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch (Exception ex) {
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        streamPlayerPanel1 = new faifai.audio.SimpleClipPlayerPanel();
        waveVisualiserPanel1 = new faifai.audio.WaveVisualiserPanel();
        audioFileSelectorPanel1 = new faifai.audio.AudioFileSelectorPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Audio File Playback");
        setMinimumSize(new java.awt.Dimension(360, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        streamPlayerPanel1.setMinimumSize(new java.awt.Dimension(240, 80));
        streamPlayerPanel1.setPreferredSize(new java.awt.Dimension(240, 80));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(streamPlayerPanel1, gridBagConstraints);

        waveVisualiserPanel1.setMinimumSize(new java.awt.Dimension(320, 120));
        waveVisualiserPanel1.setPreferredSize(new java.awt.Dimension(160, 120));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(waveVisualiserPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(audioFileSelectorPanel1, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(153, 0, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Audio Playback Control",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jButton1.setFont(new java.awt.Font("Arial", 2, 8));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PlayButton.gif"))); // NOI18N
        jButton1.setMaximumSize(new java.awt.Dimension(20, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(20, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setFont(new java.awt.Font("Arial", 2, 8));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/StopButton.gif"))); // NOI18N
        jButton2.setMaximumSize(new java.awt.Dimension(20, 20));
        jButton2.setMinimumSize(new java.awt.Dimension(20, 20));
        jButton2.setPreferredSize(new java.awt.Dimension(20, 20));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
        SplashScreenFrame.showFrame("/images/SplashScreen.png");
    }// GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        simplePlayer.play();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        simplePlayer.stop();
    }// GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AudioFilePlaybackApplication().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private faifai.audio.AudioFileSelectorPanel audioFileSelectorPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private faifai.audio.SimpleClipPlayerPanel streamPlayerPanel1;
    private faifai.audio.WaveVisualiserPanel waveVisualiserPanel1;
    // End of variables declaration//GEN-END:variables
}
