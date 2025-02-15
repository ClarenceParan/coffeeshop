/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import config.dbConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author DANIEL FAILADONA
 */
public class U_Admin extends javax.swing.JFrame {

    
    public U_Admin() {
        initComponents();displayData();
    }

    public void displayData()
    {
        try
        {
            dbConnect dbc = new dbConnect();
            ResultSet rs = dbc.getData("SELECT u_username, u_accType, u_email, u_status FROM tbl_accounts");
            account_table.setModel(DbUtils.resultSetToTableModel(rs));
             rs.close();
        }catch(SQLException ex){
            System.out.println("Errors: "+ex.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Manager_Login1 = new javax.swing.JPanel();
        Header1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Navigation1 = new javax.swing.JPanel();
        register = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        account_table = new javax.swing.JTable();
        register1 = new javax.swing.JButton();
        register2 = new javax.swing.JButton();
        register3 = new javax.swing.JButton();

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

        Manager_Login1.add(Navigation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 640));

        account_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(account_table);

        Manager_Login1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 182, 1090, 460));

        register1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register1.setText("DELETE");
        register1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register1ActionPerformed(evt);
            }
        });
        Manager_Login1.add(register1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, 140, 50));

        register2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register2.setText("ADD");
        register2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register2ActionPerformed(evt);
            }
        });
        Manager_Login1.add(register2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 140, 50));

        register3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register3.setText("UPDATE");
        register3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register3ActionPerformed(evt);
            }
        });
        Manager_Login1.add(register3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, 140, 50));

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
            .addGap(0, 642, Short.MAX_VALUE)
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

    private void register2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_register2ActionPerformed

    private void register3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_register3ActionPerformed

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked
        adminDashboard ad = new adminDashboard();
        ad.setVisible(true);
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
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(U_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new U_Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header1;
    private javax.swing.JPanel Manager_Login1;
    private javax.swing.JPanel Navigation1;
    private javax.swing.JTable account_table;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton register;
    private javax.swing.JButton register1;
    private javax.swing.JButton register2;
    private javax.swing.JButton register3;
    // End of variables declaration//GEN-END:variables
}
