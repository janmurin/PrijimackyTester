/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.awt.Color;
import java.awt.Frame;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Janco1
 */
public class StatistikaDialogForm extends javax.swing.JDialog {

    private int[] povodna;
    private int[] aktualna;
    List<Integer> aktualneUlohy;
    private DecimalFormat df = new DecimalFormat("##.##");
    ArrayList<Integer> neuspesneOtazky;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    Autentifikator aut;

    /**
     * Creates new form StatistikaDialogForm
     */
    public StatistikaDialogForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    StatistikaDialogForm(int[] povodnaStatistika, int[] aktualnaStatistika, List<Integer> aktualneUlohy, int aktualnaUloha, Frame parent, int minusBodov, ExecutorService es, Autentifikator aut) {
        this(parent, true);
        vyriesenychLabel.setText(aktualnaUloha + "/" + (aktualneUlohy.size() - 1));
        int zelenych = 0;
        int oranzovych = 0;
        int zltych = 0;
        int bielych = 0;
        int cervenych = 0;
        int uspesnych = 0;
        neuspesneOtazky = new ArrayList<>();
        // prejdeme ulohy ktore sme splnili a pocitame statistiku
        for (int i = 1; i <= aktualnaUloha; i++) {
            int ulohaID = aktualneUlohy.get(i);
            // ci je uspesna
            if (povodnaStatistika[ulohaID] < aktualnaStatistika[ulohaID]) {
                // uloha bola urcite uspesna
                uspesnych++;
                switch (povodnaStatistika[ulohaID]) {
                    case 0:
                        zltych++;
                        bielych--;
                        break;
                    case 1:
                        oranzovych++;
                        zltych--;
                        break;
                    case 2:
                        zelenych++;
                        oranzovych--;
                        // toto nemoze nikdy nastat preco to tu je?
//                        if (povodnaStatistika[ulohaID] == aktualnaStatistika[ulohaID]) {
//                            // nepribudla nijaka zelena
//                            zelenych--;
//                            oranzovych++;
//                        }
                        break;
                    case -1:
                        zltych++;
                        cervenych--;
                        break;
                    default:;
                }
            } else {
                // povodna >= aktualna statistika
                // ak bola povodna -1 alebo 3, tak sa mozu rovnat povodna a aktualna   
                // urcite sa nemozu rovnat ak bola povodna 0, 1, 2
                switch (povodnaStatistika[ulohaID]) {
                    case 0:
                        cervenych++;
                        bielych--;
                        neuspesneOtazky.add(ulohaID);
                        break;
                    case 1: // biela pribudla
                        bielych++;
                        zltych--;
                        neuspesneOtazky.add(ulohaID);
                        break;
                    case 2:
                        oranzovych--;
                        zltych++;
                        neuspesneOtazky.add(ulohaID);
                        break;
                    case 3:
                        if (povodnaStatistika[ulohaID] == aktualnaStatistika[ulohaID]) {
                            // uloha bola urcite uspesna, iba sa 3 nezmenila na 4  
                            uspesnych++;
                        } else {
                            //uloha bola urcite neuspesna lebo povodna > aktualna
                            zelenych--;
                            oranzovych++;
                            neuspesneOtazky.add(ulohaID);
                        }
                        break;
                    case -1:
                        // predosla bola -1 a aktualna == povodna, lebo mensia nemoze byt a vyssia je v prvom ife
                        // teda znova je uloha neuspesna a pocet cervenych sa nezvysil
                        // z cervenej sa preslo znova do cervenej
                        neuspesneOtazky.add(ulohaID);
                        break;
                    default:;
                }
            }
        }
        aut.statistika=aktualnaStatistika;
        es.execute(aut);
        uspesnychPocetLabel.setText(uspesnych + "/" + aktualnaUloha);
        double percent = (double) uspesnych;
        if (aktualnaUloha != 0) {
            uspesnychPercentLabel.setText(df.format(percent / aktualnaUloha * 100) + " %");
        } else {
            // nevyriesili sme ziadnu ulohu
            uspesnychPercentLabel.setText("0 %");
        }
        pribudloZelenychLabel.setText(Integer.toString(zelenych));
        pribudloOranzovychLabel.setText(Integer.toString(oranzovych));
        pribudloZltychLabel.setText(Integer.toString(zltych));
        pribudloBielychLabel.setText(Integer.toString(bielych));
        pribudloCervenychLabel.setText(Integer.toString(cervenych));
        if (minusBodov == 0) {
            minusBodovLabel.setForeground(Color.green);
        }
        minusBodovLabel.setText(Integer.toString(minusBodov));
        znovaPrejstButton.setEnabled(false);
        if (!neuspesneOtazky.isEmpty()) {
            znovaPrejstButton.setEnabled(true);
            neuspesnychPocetLabel.setText(Integer.toString(neuspesneOtazky.size()));
        } else {
            neuspesnychPocetLabel.setText("0");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        znovaPrejstButton = new javax.swing.JButton();
        zavrietButton = new javax.swing.JButton();
        vyriesenychLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        uspesnychPocetLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        uspesnychPercentLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pribudloZelenychLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pribudloOranzovychLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pribudloZltychLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pribudloCervenychLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        neuspesnychPocetLabel = new javax.swing.JLabel();
        pribudloBielychLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        minusBodovLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Štatistika");
        setResizable(false);

        znovaPrejstButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        znovaPrejstButton.setText("Znova prejsť zle zodpovedané");
        znovaPrejstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                znovaPrejstButtonActionPerformed(evt);
            }
        });

        zavrietButton.setText("Zavrieť");
        zavrietButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zavrietButtonActionPerformed(evt);
            }
        });

        vyriesenychLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        vyriesenychLabel.setText("jLabel8");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Vyriešených otázok:");

        uspesnychPocetLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        uspesnychPocetLabel.setText("jLabel9");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Úspešných:");

        uspesnychPercentLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        uspesnychPercentLabel.setText("jLabel10");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Úspešnosť:");

        pribudloZelenychLabel.setBackground(new java.awt.Color(0, 255, 0));
        pribudloZelenychLabel.setText("jLabel11");
        pribudloZelenychLabel.setOpaque(true);

        jLabel4.setText("pribudlo zelených:");

        pribudloOranzovychLabel.setBackground(new java.awt.Color(255, 153, 0));
        pribudloOranzovychLabel.setText("jLabel12");
        pribudloOranzovychLabel.setOpaque(true);

        jLabel5.setText("pribudlo oranžových:");

        pribudloZltychLabel.setBackground(new java.awt.Color(255, 255, 0));
        pribudloZltychLabel.setText("jLabel13");
        pribudloZltychLabel.setOpaque(true);

        jLabel6.setText("pribudlo žltých:");

        pribudloCervenychLabel.setBackground(new java.awt.Color(255, 0, 0));
        pribudloCervenychLabel.setText("jLabel14");
        pribudloCervenychLabel.setOpaque(true);

        jLabel7.setText("pribudlo červených:");

        jLabel8.setText("Neúspešných:");

        neuspesnychPocetLabel.setText("jLabel9");

        pribudloBielychLabel.setBackground(new java.awt.Color(255, 255, 255));
        pribudloBielychLabel.setText("jLabel13");
        pribudloBielychLabel.setOpaque(true);

        jLabel9.setText("pribudlo bielych:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Mínus bodov:");

        minusBodovLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        minusBodovLabel.setForeground(new java.awt.Color(255, 0, 0));
        minusBodovLabel.setText("jLabel10");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel8)
                        .addGap(29, 29, 29)
                        .addComponent(neuspesnychPocetLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(zavrietButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10))
                                    .addGap(29, 29, 29)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(vyriesenychLabel)
                                        .addComponent(uspesnychPocetLabel)
                                        .addComponent(uspesnychPercentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pribudloZelenychLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pribudloOranzovychLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pribudloZltychLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pribudloCervenychLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pribudloBielychLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(minusBodovLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(znovaPrejstButton)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(vyriesenychLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(uspesnychPocetLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(uspesnychPercentLabel))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(minusBodovLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pribudloZelenychLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pribudloOranzovychLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(pribudloZltychLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(pribudloBielychLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(pribudloCervenychLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(neuspesnychPocetLabel))
                .addGap(18, 18, 18)
                .addComponent(znovaPrejstButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(zavrietButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void znovaPrejstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_znovaPrejstButtonActionPerformed
        System.out.println("znova prejdeme " + neuspesneOtazky.size() + " otazok.");
        changes.firePropertyChange("znovaPrejst", false, true);
        dispose();
    }//GEN-LAST:event_znovaPrejstButtonActionPerformed

    private void zavrietButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zavrietButtonActionPerformed

        dispose();
    }//GEN-LAST:event_zavrietButtonActionPerformed

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
            java.util.logging.Logger.getLogger(StatistikaDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatistikaDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatistikaDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatistikaDialogForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StatistikaDialogForm dialog = new StatistikaDialogForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel minusBodovLabel;
    private javax.swing.JLabel neuspesnychPocetLabel;
    private javax.swing.JLabel pribudloBielychLabel;
    private javax.swing.JLabel pribudloCervenychLabel;
    private javax.swing.JLabel pribudloOranzovychLabel;
    private javax.swing.JLabel pribudloZelenychLabel;
    private javax.swing.JLabel pribudloZltychLabel;
    private javax.swing.JLabel uspesnychPercentLabel;
    private javax.swing.JLabel uspesnychPocetLabel;
    private javax.swing.JLabel vyriesenychLabel;
    private javax.swing.JButton zavrietButton;
    private javax.swing.JButton znovaPrejstButton;
    // End of variables declaration//GEN-END:variables
}
