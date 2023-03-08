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

public class WaveHarmonicsGeneratorPanel extends javax.swing.JPanel {

  private WaveHarmonicsGenerator waveGenerator;
  private static final double FREQ_MAX = 2000;
  private static final double FREQ_MIN = 20;

  /** Creates new form WaveGeneratorPanel */
  public WaveHarmonicsGeneratorPanel() {
    initComponents();
    waveGenerator = new WaveHarmonicsGenerator();
    updateFreq();
    updateAmp();
    updateDisplay();
  }

  public WaveHarmonicsGenerator getWaveHarmonicsGenerator() {
    return this.waveGenerator;
  }

  private void updateFreq() {
    int value = jSlider1.getValue();
    double freq = (value / 1000.0) * (FREQ_MAX - FREQ_MIN) + FREQ_MIN;
    waveGenerator.setFrequency(freq);
    jLabel2.setText((int) freq + "Hz");
  }

  private void updateAmp() {
    waveGenerator.setAmplitude(0, jSlider2.getValue() / 100.0);
    jLabel4.setText(jSlider2.getValue() + "%");
    waveGenerator.setAmplitude(1, jSlider3.getValue() / 100.0);
    jLabel6.setText(jSlider3.getValue() + "%");
    waveGenerator.setAmplitude(2, jSlider4.getValue() / 100.0);
    jLabel8.setText(jSlider4.getValue() + "%");
    waveGenerator.setAmplitude(3, jSlider5.getValue() / 100.0);
    jLabel10.setText(jSlider5.getValue() + "%");
  }

  private void updateDisplay() {
    jLabel1.setText("WAVEFORM: SINE");
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

    jLabel1 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jSlider1 = new javax.swing.JSlider();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    jSlider2 = new javax.swing.JSlider();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jPanel4 = new javax.swing.JPanel();
    jSlider3 = new javax.swing.JSlider();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    jPanel5 = new javax.swing.JPanel();
    jSlider4 = new javax.swing.JSlider();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jPanel6 = new javax.swing.JPanel();
    jSlider5 = new javax.swing.JSlider();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();

    setBackground(new java.awt.Color(153, 0, 102));
    setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sine Wave Harmonics Generator", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(255, 255, 255))); // NOI18N
    setLayout(new java.awt.GridBagLayout());

    jLabel1.setBackground(new java.awt.Color(204, 255, 51));
    jLabel1.setFont(new java.awt.Font("Monospaced", 0, 12));
    jLabel1.setForeground(new java.awt.Color(51, 51, 51));
    jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jLabel1.setMaximumSize(new java.awt.Dimension(4, 45));
    jLabel1.setMinimumSize(new java.awt.Dimension(38, 45));
    jLabel1.setOpaque(true);
    jLabel1.setPreferredSize(new java.awt.Dimension(38, 45));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
    gridBagConstraints.weightx = 1.0;
    add(jLabel1, gridBagConstraints);

    jPanel2.setOpaque(false);
    jPanel2.setLayout(new java.awt.GridBagLayout());

    jSlider1.setBackground(new java.awt.Color(102, 102, 102));
    jSlider1.setForeground(new java.awt.Color(51, 51, 51));
    jSlider1.setMaximum(1000);
    jSlider1.setToolTipText("Frequency");
    jSlider1.setValue(200);
    jSlider1.setOpaque(false);
    jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jSlider1StateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    jPanel2.add(jSlider1, gridBagConstraints);

    jLabel2.setBackground(new java.awt.Color(204, 255, 0));
    jLabel2.setFont(new java.awt.Font("Monospaced", 0, 10));
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jLabel2.setMinimumSize(new java.awt.Dimension(50, 20));
    jLabel2.setOpaque(true);
    jLabel2.setPreferredSize(new java.awt.Dimension(50, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    jPanel2.add(jLabel2, gridBagConstraints);

    jLabel3.setFont(new java.awt.Font("Tahoma", 3, 9));
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText("FREQ");
    jLabel3.setMaximumSize(new java.awt.Dimension(45, 15));
    jLabel3.setMinimumSize(new java.awt.Dimension(45, 15));
    jLabel3.setPreferredSize(new java.awt.Dimension(45, 15));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel2.add(jLabel3, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jPanel2, gridBagConstraints);

    jPanel3.setOpaque(false);
    jPanel3.setLayout(new java.awt.GridBagLayout());

    jSlider2.setBackground(new java.awt.Color(102, 102, 102));
    jSlider2.setForeground(new java.awt.Color(51, 51, 51));
    jSlider2.setToolTipText("Frequency");
    jSlider2.setOpaque(false);
    jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jSlider2StateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    jPanel3.add(jSlider2, gridBagConstraints);

    jLabel4.setBackground(new java.awt.Color(204, 255, 0));
    jLabel4.setFont(new java.awt.Font("Monospaced", 0, 10));
    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jLabel4.setMinimumSize(new java.awt.Dimension(50, 20));
    jLabel4.setOpaque(true);
    jLabel4.setPreferredSize(new java.awt.Dimension(50, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    jPanel3.add(jLabel4, gridBagConstraints);

    jLabel5.setFont(new java.awt.Font("Tahoma", 3, 9));
    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
    jLabel5.setText("AMP-0");
    jLabel5.setMaximumSize(new java.awt.Dimension(45, 15));
    jLabel5.setMinimumSize(new java.awt.Dimension(45, 15));
    jLabel5.setPreferredSize(new java.awt.Dimension(45, 15));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel3.add(jLabel5, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jPanel3, gridBagConstraints);

    jPanel4.setOpaque(false);
    jPanel4.setLayout(new java.awt.GridBagLayout());

    jSlider3.setBackground(new java.awt.Color(102, 102, 102));
    jSlider3.setForeground(new java.awt.Color(51, 51, 51));
    jSlider3.setToolTipText("Frequency");
    jSlider3.setValue(25);
    jSlider3.setOpaque(false);
    jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jSlider3StateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    jPanel4.add(jSlider3, gridBagConstraints);

    jLabel6.setBackground(new java.awt.Color(204, 255, 0));
    jLabel6.setFont(new java.awt.Font("Monospaced", 0, 10));
    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jLabel6.setMinimumSize(new java.awt.Dimension(50, 20));
    jLabel6.setOpaque(true);
    jLabel6.setPreferredSize(new java.awt.Dimension(50, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    jPanel4.add(jLabel6, gridBagConstraints);

    jLabel7.setFont(new java.awt.Font("Tahoma", 3, 9)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(255, 255, 255));
    jLabel7.setText("AMP-1");
    jLabel7.setMaximumSize(new java.awt.Dimension(45, 15));
    jLabel7.setMinimumSize(new java.awt.Dimension(45, 15));
    jLabel7.setPreferredSize(new java.awt.Dimension(45, 15));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel4.add(jLabel7, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jPanel4, gridBagConstraints);

    jPanel5.setOpaque(false);
    jPanel5.setLayout(new java.awt.GridBagLayout());

    jSlider4.setBackground(new java.awt.Color(102, 102, 102));
    jSlider4.setForeground(new java.awt.Color(51, 51, 51));
    jSlider4.setToolTipText("Frequency");
    jSlider4.setValue(12);
    jSlider4.setOpaque(false);
    jSlider4.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jSlider4StateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    jPanel5.add(jSlider4, gridBagConstraints);

    jLabel8.setBackground(new java.awt.Color(204, 255, 0));
    jLabel8.setFont(new java.awt.Font("Monospaced", 0, 10));
    jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jLabel8.setMinimumSize(new java.awt.Dimension(50, 20));
    jLabel8.setOpaque(true);
    jLabel8.setPreferredSize(new java.awt.Dimension(50, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    jPanel5.add(jLabel8, gridBagConstraints);

    jLabel9.setFont(new java.awt.Font("Tahoma", 3, 9)); // NOI18N
    jLabel9.setForeground(new java.awt.Color(255, 255, 255));
    jLabel9.setText("AMP-2");
    jLabel9.setMaximumSize(new java.awt.Dimension(45, 15));
    jLabel9.setMinimumSize(new java.awt.Dimension(45, 15));
    jLabel9.setPreferredSize(new java.awt.Dimension(45, 15));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel5.add(jLabel9, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jPanel5, gridBagConstraints);

    jPanel6.setOpaque(false);
    jPanel6.setLayout(new java.awt.GridBagLayout());

    jSlider5.setBackground(new java.awt.Color(102, 102, 102));
    jSlider5.setForeground(new java.awt.Color(51, 51, 51));
    jSlider5.setToolTipText("Frequency");
    jSlider5.setValue(6);
    jSlider5.setOpaque(false);
    jSlider5.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jSlider5StateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    jPanel6.add(jSlider5, gridBagConstraints);

    jLabel10.setBackground(new java.awt.Color(204, 255, 0));
    jLabel10.setFont(new java.awt.Font("Monospaced", 0, 10));
    jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    jLabel10.setMinimumSize(new java.awt.Dimension(50, 20));
    jLabel10.setOpaque(true);
    jLabel10.setPreferredSize(new java.awt.Dimension(50, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    jPanel6.add(jLabel10, gridBagConstraints);

    jLabel11.setFont(new java.awt.Font("Tahoma", 3, 9)); // NOI18N
    jLabel11.setForeground(new java.awt.Color(255, 255, 255));
    jLabel11.setText("AMP-3");
    jLabel11.setMaximumSize(new java.awt.Dimension(45, 15));
    jLabel11.setMinimumSize(new java.awt.Dimension(45, 15));
    jLabel11.setPreferredSize(new java.awt.Dimension(45, 15));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel6.add(jLabel11, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jPanel6, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
      updateFreq();
    }//GEN-LAST:event_jSlider1StateChanged

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
      updateAmp();
    }//GEN-LAST:event_jSlider2StateChanged

    private void jSlider3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider3StateChanged
      updateAmp();
    }//GEN-LAST:event_jSlider3StateChanged

    private void jSlider4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider4StateChanged
      updateAmp();
    }//GEN-LAST:event_jSlider4StateChanged

    private void jSlider5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider5StateChanged
      updateAmp();
    }//GEN-LAST:event_jSlider5StateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JSlider jSlider1;
  private javax.swing.JSlider jSlider2;
  private javax.swing.JSlider jSlider3;
  private javax.swing.JSlider jSlider4;
  private javax.swing.JSlider jSlider5;
  // End of variables declaration//GEN-END:variables
}