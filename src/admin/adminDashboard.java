/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import coffeeshop.Coffeeshop;

/**
 *
 * @author DANIEL FAILADONA
 */
public class adminDashboard extends javax.swing.JFrame {

    /**
     * Creates new form adminDashboard
     */
    public adminDashboard() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Manager_Login1 = new javax.swing.JPanel();
        Header1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Navigation1 = new javax.swing.JPanel();
        register = new javax.swing.JButton();
        register1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Manager_Login1.setBackground(new java.awt.Color(153, 153, 153));
        Manager_Login1.setForeground(new java.awt.Color(204, 204, 204));
        Manager_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header1.setBackground(new java.awt.Color(51, 51, 51));
        Header1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 255, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("COFFEE SHOP");
        Header1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1320, 40));

        Manager_Login1.add(Header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 100));

        Navigation1.setBackground(new java.awt.Color(102, 102, 102));
        Navigation1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register.setText("Back");
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerMouseClicked(evt);
            }
        });
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });
        Navigation1.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 140, 30));

        register1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register1.setText("Users");
        register1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                register1MouseClicked(evt);
            }
        });
        register1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register1ActionPerformed(evt);
            }
        });
        Navigation1.add(register1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 230, 40));

        Manager_Login1.add(Navigation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1320, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Manager_Login1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 671, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Manager_Login1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed

    }//GEN-LAST:event_registerActionPerformed

    private void register1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_register1ActionPerformed

    private void register1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_register1MouseClicked
        U_Admin ua = new U_Admin();
        ua.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_register1MouseClicked

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked
        Coffeeshop c = new Coffeeshop();
        c.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registerMouseClicked

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
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header1;
    private javax.swing.JPanel Manager_Login1;
    private javax.swing.JPanel Navigation1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton register;
    private javax.swing.JButton register1;
    // End of variables declaration//GEN-END:variables
}
