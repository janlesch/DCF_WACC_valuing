package uw_gui;

import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;


public class F_Chooser extends javax.swing.JFrame {
    
    GUIManager gm;
    
    public F_Chooser(GUIManager gm) {
        this.gm = gm;
        initComponents();
     }
    
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        b_Neu = new javax.swing.JButton();
        b_Saved = new javax.swing.JButton();
        b_Exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Auswahl");
        setLocationByPlatform(true);
        setName("F_Chooser");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        b_Neu.setText("Neue Analyse");
        b_Neu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_NeuActionPerformed(evt);
            }
        });

        b_Saved.setText("Gespeicherte Analyse");
        b_Saved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_SavedActionPerformed(evt);
            }
        });

        b_Exit.setText("Beenden");
        b_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_ExitActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(b_Neu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 183, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(b_Saved, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 183, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(b_Exit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(b_Neu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(b_Saved, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(b_Exit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        gm.closeApplication();
    }//GEN-LAST:event_formWindowClosing

    
    private void b_NeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_NeuActionPerformed
        gm.newAnalysis();
    }//GEN-LAST:event_b_NeuActionPerformed

    
    private void b_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_ExitActionPerformed
        gm.closeApplication();
    }//GEN-LAST:event_b_ExitActionPerformed

    
    private void b_SavedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_SavedActionPerformed
        gm.openSavedAnalysis();
    }//GEN-LAST:event_b_SavedActionPerformed

        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_Exit;
    private javax.swing.JButton b_Neu;
    private javax.swing.JButton b_Saved;
    // End of variables declaration//GEN-END:variables
    
}
