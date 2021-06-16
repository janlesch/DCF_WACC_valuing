package uw_gui;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uw_core.Enterprise;
import uw_core.LineDiagram;
import uw_core.ResultSpectrum;


public class F_Analyse extends javax.swing.JFrame {
    
    private GUIManager gm;
    private Enterprise enterprise;
    private ResultSpectrum resultSpectrum;
    private LineDiagram lineDiagram;
    private boolean updating = false;
    
    
    public F_Analyse(GUIManager gm, Enterprise enterprise) {
        this.gm = gm;
        this.enterprise = enterprise;
        this.lineDiagramGUI = new LineDiagramGUI();
        initComponents();
        
        
        t_Werte.setModel(new ValueModel(enterprise));
        t_Werte.getColumnModel().getColumn(0).setPreferredWidth((int)Math.rint(t_Werte.getWidth()* 0.8));
        
        t_Intervalle.setModel(new IntervallModel(enterprise));
        
        t_Residual.setModel(new ResidualModel(enterprise));
        
        c_Steuer.setModel(
                new javax.swing.DefaultComboBoxModel(
                new String[] {
            "Natürliche Person hält Kapitalgesellschaft",
            "Kapitalgesellschaft hält Kapitalgesellschaft",
            "Natürliche Person hält Personengesellschaft bzw. Einzelunternehmen",
            "Standard CAPM"
        })
        );
        c_Residual.setModel(
                new javax.swing.DefaultComboBoxModel(
                new String[] {
            "Vorgegebener Residualwert",
            "Ewiger Free Cash Flow",
        })
        );
        tf_Name.getDocument().addDocumentListener(
                new DocumentListener() {
            public void changedUpdate(DocumentEvent documentEvent) {
                tf_NameChanged(documentEvent);
            }
            public void insertUpdate(DocumentEvent documentEvent) {
                tf_NameChanged(documentEvent);
            }
            public void removeUpdate(DocumentEvent documentEvent) {
                tf_NameChanged(documentEvent);
            }
        }
        );
        
        z_x.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                zoomChanged();
            }
        });
        
        z_y.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                zoomChanged();
            }
        });
        
    }
    
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        p_Daten = new javax.swing.JPanel();
        tf_Name = new javax.swing.JTextField();
        s_Werte = new javax.swing.JScrollPane();
        t_Werte = new javax.swing.JTable();
        s_Intervalle = new javax.swing.JScrollPane();
        t_Intervalle = new javax.swing.JTable();
        b_NeuFCF = new javax.swing.JButton();
        s_Residual = new javax.swing.JScrollPane();
        t_Residual = new javax.swing.JTable();
        b_NeuResidual = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        c_Steuer = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        c_Residual = new javax.swing.JComboBox();
        b_Sichern = new javax.swing.JButton();
        b_Text = new javax.swing.JButton();
        b_Berechne = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        b_ResImg = new javax.swing.JButton();
        b_ResTxt = new javax.swing.JButton();
        lineDiagramGUI = new uw_gui.LineDiagramGUI();
        z_y = new uw_gui.ZoomChooser();
        z_x = new uw_gui.ZoomChooser();
        b_zoomExt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Analyse");
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        p_Daten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        s_Werte.setMinimumSize(new java.awt.Dimension(10, 10));
        t_Werte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bezeichner", "Wert"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        t_Werte.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        s_Werte.setViewportView(t_Werte);

        s_Intervalle.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        s_Intervalle.setMinimumSize(new java.awt.Dimension(20, 50));
        t_Intervalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Periode", "von", "bis", "W-keit", "Kommentar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        s_Intervalle.setViewportView(t_Intervalle);

        b_NeuFCF.setText("Neues FCF-Intervall");
        b_NeuFCF.setMinimumSize(new java.awt.Dimension(10, 10));
        b_NeuFCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_NeuFCFActionPerformed(evt);
            }
        });

        s_Residual.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        t_Residual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Periode", "von", "bis", "W-keit", "Kommentar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        t_Residual.setMinimumSize(new java.awt.Dimension(10, 10));
        s_Residual.setViewportView(t_Residual);

        b_NeuResidual.setText("Neues Residual-Intervall");
        b_NeuResidual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_NeuResidualActionPerformed(evt);
            }
        });

        jLabel2.setText("Besteuerungsform");

        c_Steuer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_Steuer.setMinimumSize(new java.awt.Dimension(10, 10));
        c_Steuer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_SteuerActionPerformed(evt);
            }
        });

        jLabel1.setText("Form des Residualwertes");

        c_Residual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_Residual.setMinimumSize(new java.awt.Dimension(10, 10));
        c_Residual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_ResidualActionPerformed(evt);
            }
        });

        b_Sichern.setText("Sichern ...");
        b_Sichern.setMaximumSize(new java.awt.Dimension(107, 23));
        b_Sichern.setMinimumSize(new java.awt.Dimension(10, 10));
        b_Sichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_SichernActionPerformed(evt);
            }
        });

        b_Text.setText("Als Text ausgeben ...");
        b_Text.setMinimumSize(new java.awt.Dimension(10, 10));
        b_Text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_TextActionPerformed(evt);
            }
        });

        b_Berechne.setText("Berechnen");
        b_Berechne.setMinimumSize(new java.awt.Dimension(10, 10));
        b_Berechne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_BerechneActionPerformed(evt);
            }
        });

        jLabel3.setText("Name der Unternehmung");

        b_ResImg.setText("Diagramm als Bild ...");
        b_ResImg.setMinimumSize(new java.awt.Dimension(10, 10));
        b_ResImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_ResImgActionPerformed(evt);
            }
        });

        b_ResTxt.setText("Diagramm als Text ...");
        b_ResTxt.setMinimumSize(new java.awt.Dimension(10, 10));
        b_ResTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_ResTxtActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout p_DatenLayout = new org.jdesktop.layout.GroupLayout(p_Daten);
        p_Daten.setLayout(p_DatenLayout);
        p_DatenLayout.setHorizontalGroup(
            p_DatenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, s_Werte, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(b_Berechne, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(b_Sichern, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .add(20, 20, 20)
                .add(b_Text, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(c_Residual, 0, 302, Short.MAX_VALUE)
                .addContainerGap())
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addContainerGap(192, Short.MAX_VALUE))
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(c_Steuer, 0, 302, Short.MAX_VALUE)
                .addContainerGap())
            .add(p_DatenLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(b_NeuResidual, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .add(0, 0, 0))
            .add(s_Residual, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .add(p_DatenLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(b_NeuFCF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .add(0, 0, 0))
            .add(s_Intervalle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tf_Name, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2)
                .addContainerGap(224, Short.MAX_VALUE))
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(b_ResImg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .add(20, 20, 20)
                .add(b_ResTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );
        p_DatenLayout.setVerticalGroup(
            p_DatenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(p_DatenLayout.createSequentialGroup()
                .addContainerGap()
                .add(p_DatenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(tf_Name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(s_Werte, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(s_Intervalle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .add(0, 0, 0)
                .add(b_NeuFCF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(s_Residual, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(b_NeuResidual)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .add(0, 0, 0)
                .add(c_Steuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .add(0, 0, 0)
                .add(c_Residual, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(p_DatenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(b_Sichern, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(b_Text, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(b_Berechne, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(p_DatenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(b_ResImg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(b_ResTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        p_DatenLayout.linkSize(new java.awt.Component[] {b_ResImg, b_ResTxt, b_Sichern, b_Text}, org.jdesktop.layout.GroupLayout.VERTICAL);

        lineDiagramGUI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        org.jdesktop.layout.GroupLayout lineDiagramGUILayout = new org.jdesktop.layout.GroupLayout(lineDiagramGUI);
        lineDiagramGUI.setLayout(lineDiagramGUILayout);
        lineDiagramGUILayout.setHorizontalGroup(
            lineDiagramGUILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 729, Short.MAX_VALUE)
        );
        lineDiagramGUILayout.setVerticalGroup(
            lineDiagramGUILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 731, Short.MAX_VALUE)
        );

        z_y.setOrientation(((byte)1));
        org.jdesktop.layout.GroupLayout z_yLayout = new org.jdesktop.layout.GroupLayout(z_y);
        z_y.setLayout(z_yLayout);
        z_yLayout.setHorizontalGroup(
            z_yLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 16, Short.MAX_VALUE)
        );
        z_yLayout.setVerticalGroup(
            z_yLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 722, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout z_xLayout = new org.jdesktop.layout.GroupLayout(z_x);
        z_x.setLayout(z_xLayout);
        z_xLayout.setHorizontalGroup(
            z_xLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 720, Short.MAX_VALUE)
        );
        z_xLayout.setVerticalGroup(
            z_xLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 17, Short.MAX_VALUE)
        );

        b_zoomExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_zoomExtActionPerformed(evt);
            }
        });
        b_zoomExt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_zoomExtMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(p_Daten, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(11, 11, 11)
                        .add(z_x, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(0, 0, 0)
                        .add(lineDiagramGUI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .add(0, 0, 0)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(b_zoomExt, 0, 0, Short.MAX_VALUE)
                    .add(z_y, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(p_Daten, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(11, 11, 11)
                        .add(z_y, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(lineDiagramGUI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(0, 0, 0)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(b_zoomExt, 0, 0, Short.MAX_VALUE)
                    .add(z_x, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void b_zoomExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_zoomExtActionPerformed
        gm.zoomDiagramExtents(this);
    }//GEN-LAST:event_b_zoomExtActionPerformed
    
    private void b_zoomExtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_zoomExtMouseClicked
        if(evt.getButton() == evt.BUTTON1 && evt.getClickCount() >=2){
            gm.zoomDiagramExtents(this);
        }
    }//GEN-LAST:event_b_zoomExtMouseClicked
    
    private void c_ResidualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_ResidualActionPerformed
        if(!isUpdating()){
            gm.setResidual(this,(byte)c_Residual.getSelectedIndex());
        }
    }//GEN-LAST:event_c_ResidualActionPerformed
    
    private void b_ResTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_ResTxtActionPerformed
        gm.saveDiagramAsText(this);
    }//GEN-LAST:event_b_ResTxtActionPerformed
    
    private void b_ResImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_ResImgActionPerformed
        gm.saveDiagramAsImage(this);
    }//GEN-LAST:event_b_ResImgActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int answer = JOptionPane.showOptionDialog(null,"Schliessen?","Schliessen?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,null,null);
        if (answer == JOptionPane.YES_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing
    
    private void zoomChanged(){
        lineDiagramGUI.setZx1(z_x.getZ1());
        lineDiagramGUI.setZx2(z_x.getZ2());
        lineDiagramGUI.setZy1(z_y.getZ1());
        lineDiagramGUI.setZy2(z_y.getZ2());
        lineDiagramGUI.repaint();
    }
    
    public void repaintZX(){
        z_x.repaint();
    }
    public void repaintZY(){
        z_y.repaint();
    }
    
    public void setZx1(double zx1){
        z_x.setZ1(zx1);
    }
    
    public void setZx2(double zx2){
        z_x.setZ2(zx2);
    }
    
    public void setZy1(double zy1){
        z_y.setZ1(zy1);
    }
    
    public void setZy2(double zy2){
        z_y.setZ2(zy2);
    }
    
    public double getZx1(){
        return z_x.getZ1();
    }
    
    public double getZx2(){
        return z_x.getZ2();
    }
    
    public double getZy1(){
        return z_y.getZ1();
    }
    
    public double getZy2(){
        return z_y.getZ2();
    }
    
    private void b_BerechneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_BerechneActionPerformed
        gm.compute(this);
        invalidate();
    }//GEN-LAST:event_b_BerechneActionPerformed
    
    private void tf_NameChanged(javax.swing.event.DocumentEvent evt){
        if (!isUpdating()){
            gm.setName(this,tf_Name.getText());
        }
    }
    
    private void b_TextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_TextActionPerformed
        gm.saveAnalysisAsText(this);
    }//GEN-LAST:event_b_TextActionPerformed
    
    private void b_SichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_SichernActionPerformed
        gm.saveAnalysis(this);
    }//GEN-LAST:event_b_SichernActionPerformed
    
    private void b_NeuResidualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_NeuResidualActionPerformed
        ((ResidualModel) t_Residual.getModel()).addIntervall();
    }//GEN-LAST:event_b_NeuResidualActionPerformed
    
    private void b_NeuFCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_NeuFCFActionPerformed
        ((IntervallModel) t_Intervalle.getModel()).addIntervall();
        ((ValueModel)t_Werte.getModel()).valuesChanged();
    }//GEN-LAST:event_b_NeuFCFActionPerformed
    
    private void c_SteuerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_SteuerActionPerformed
        if (!isUpdating()){
            gm.setSteuer(this,(byte)c_Steuer.getSelectedIndex());
        }
    }//GEN-LAST:event_c_SteuerActionPerformed
     
    public void setWerte(int i, double w){
        t_Werte.getModel().setValueAt(w,i,1);
    }
    
    
    public void setSteuer(byte s){
        c_Steuer.setSelectedIndex(s);
    }
    
    public void setResidual(byte r){
        c_Residual.setSelectedIndex(r);
    }
    
    public void setName(String name){
        tf_Name.setText(name);
    }
    
    public Enterprise getEnterprise() {
        return enterprise;
    }
    
    public ResultSpectrum getResultSpectrum(){
        return resultSpectrum;
    }
    public void setResultSpectrum(ResultSpectrum res){
        this.resultSpectrum = res;
    }
    
    public LineDiagram getLineDiagram(){
        return lineDiagram;
    }
    
    public LineDiagramGUI getLineDiagramGUI(){
        return lineDiagramGUI;
    }
    
    public void setLineDiagram(LineDiagram lineDiagram){
        this.lineDiagram = lineDiagram;
        this.lineDiagramGUI.setLineDiagram(lineDiagram);
        lineDiagramGUI.repaint();
    }
    
    public boolean isUpdating() {
        return updating;
    }
    
    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_Berechne;
    private javax.swing.JButton b_NeuFCF;
    private javax.swing.JButton b_NeuResidual;
    private javax.swing.JButton b_ResImg;
    private javax.swing.JButton b_ResTxt;
    private javax.swing.JButton b_Sichern;
    private javax.swing.JButton b_Text;
    private javax.swing.JButton b_zoomExt;
    private javax.swing.JComboBox c_Residual;
    private javax.swing.JComboBox c_Steuer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private uw_gui.LineDiagramGUI lineDiagramGUI;
    private javax.swing.JPanel p_Daten;
    private javax.swing.JScrollPane s_Intervalle;
    private javax.swing.JScrollPane s_Residual;
    private javax.swing.JScrollPane s_Werte;
    private javax.swing.JTable t_Intervalle;
    private javax.swing.JTable t_Residual;
    private javax.swing.JTable t_Werte;
    private javax.swing.JTextField tf_Name;
    private uw_gui.ZoomChooser z_x;
    private uw_gui.ZoomChooser z_y;
    // End of variables declaration//GEN-END:variables
    
}
