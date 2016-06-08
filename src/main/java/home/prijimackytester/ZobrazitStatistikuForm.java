/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Janco1
 */
public class ZobrazitStatistikuForm extends javax.swing.JFrame {

    List<String> useri;
    Autentifikator auth = new Autentifikator();
    DefaultComboBoxModel<String> useriComboboxModel;
    int[] aktualnaStatistika;
    private List<Autentifikator.Statistika> aktUserStatistiky;

    /**
     * Creates new form ZobrazitStatistikuForm
     */
    public ZobrazitStatistikuForm() {
        initComponents();
        useri = auth.getUseri();
        System.out.println("nacitani useri: " + useri);
        useriComboboxModel = new DefaultComboBoxModel();
        for (String s : useri) {
            useriComboboxModel.addElement(s);
        }
        menoComboBox.setModel(useriComboboxModel);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grafikaLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        menoComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        posledneStatistikyComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        grafikaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grafikaLabelMouseClicked(evt);
            }
        });

        jLabel1.setText("meno:");

        menoComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        menoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menoComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("posledne statistiky:");

        posledneStatistikyComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        posledneStatistikyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                posledneStatistikyComboBoxActionPerformed(evt);
            }
        });

        jButton1.setText("nacitaj do DB");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(menoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(posledneStatistikyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(grafikaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(menoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(posledneStatistikyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(grafikaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grafikaLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grafikaLabelMouseClicked
//        //System.out.println("grafikaLabelMouseClicked: " + evt.getY() + ":" + evt.getX());
        BufferedImage obrazok = new BufferedImage(784, 128, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < aktualnaStatistika.length; i++) {
            int x = (i - 1) % 98;
            int y = (i - 1) / 98;

            if (aktualnaStatistika[i] > 2) {
                nakresliStvorcek(obrazok, x, y, Color.green, false);
            } else {
                if (aktualnaStatistika[i] > 1) {
                    nakresliStvorcek(obrazok, x, y, Color.orange, false);
                } else {
                    if (aktualnaStatistika[i] > 0) {
                        nakresliStvorcek(obrazok, x, y, Color.yellow, false);
                    } else {
                        if (aktualnaStatistika[i] > -1) {
                            nakresliStvorcek(obrazok, x, y, Color.white, false);
                        } else {
                            nakresliStvorcek(obrazok, x, y, Color.red, false);
                        }
                    }
                }
            }
        }

        grafikaLabel.setIcon(new ImageIcon(obrazok));
    }//GEN-LAST:event_grafikaLabelMouseClicked

    private void menoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menoComboBoxActionPerformed
        System.out.println("vybrany user: " + menoComboBox.getSelectedItem());
        // vybrany user, refreshneme jeho posledne statistiky
        aktUserStatistiky = auth.getStatistika((String) menoComboBox.getSelectedItem());
        DefaultComboBoxModel posledneStatistikyComboboxModel = new DefaultComboBoxModel();
        for (Autentifikator.Statistika stat : aktUserStatistiky) {
            posledneStatistikyComboboxModel.addElement((String) stat.cas);
        }

        posledneStatistikyComboBox.setModel(posledneStatistikyComboboxModel);
        posledneStatistikyComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_menoComboBoxActionPerformed

    private void posledneStatistikyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_posledneStatistikyComboBoxActionPerformed
        aktualnaStatistika = aktUserStatistiky.get(posledneStatistikyComboBox.getSelectedIndex()).stats;
        System.out.println("nacitana statistika:\n" + Arrays.toString(aktualnaStatistika));
        grafikaLabelMouseClicked(null);
    }//GEN-LAST:event_posledneStatistikyComboBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Database db=new Database();
        StringBuilder sb=new StringBuilder();
        int[] stats = aktualnaStatistika;
        for (int i = 1; i < stats.length; i++) {
            sb.append("UPDATE statistika_biologia SET default_user=" + stats[i] + " WHERE otazka_id=" + i + ";\n");
        }
        System.out.println("update skript: \n"+sb.toString());
        
        // updatneme databazu s touto verziou
//        try {
//            db.executeSkript(sb.toString());
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "VYNIMKA: "+e+"\n ulozenie zlyhalo");
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ZobrazitStatistikuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZobrazitStatistikuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZobrazitStatistikuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZobrazitStatistikuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ZobrazitStatistikuForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel grafikaLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox menoComboBox;
    private javax.swing.JComboBox posledneStatistikyComboBox;
    // End of variables declaration//GEN-END:variables

    private void nakresliStvorcek(BufferedImage obrazok, int x, int y, Color farba, boolean jeVRozsahu) {
        // nakreslime stvorcek
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                obrazok.setRGB(x * 8 + k, y * 8 + l, farba.getRGB());
            }
        }
    }
}
