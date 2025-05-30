/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Employee.E_Add_Recovery;
import Startups.Coffeeshop;
import config.Session;
import config.dbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;

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
    
    
    public void logEvent(int userId, String username, String action) {
        dbConnect dbc = new dbConnect();
        Connection con = dbc.getConnection();
        PreparedStatement pstmt = null;
        Timestamp time = new Timestamp(new Date().getTime());

        try {
            String sql = "INSERT INTO tbl_logs (u_id, u_username, action_time, log_action) "
                    + "VALUES ('" + userId + "', '" + username + "', '" + time + "', '" + action + "')";
            pstmt = con.prepareStatement(sql);

            /*            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            pstmt.setString(4, userType);*/
            pstmt.executeUpdate();
            System.out.println("Login log recorded successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error recording log: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
            }
        }
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
        register2 = new javax.swing.JButton();
        acc_phone = new javax.swing.JLabel();
        acc_phone1 = new javax.swing.JLabel();
        acc_uname1 = new javax.swing.JLabel();
        acc_type = new javax.swing.JLabel();
        acc_type1 = new javax.swing.JLabel();
        acc_uname = new javax.swing.JLabel();
        acc_id = new javax.swing.JLabel();
        register3 = new javax.swing.JButton();
        register4 = new javax.swing.JButton();
        register5 = new javax.swing.JButton();
        register6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        Manager_Login1.setBackground(new java.awt.Color(153, 137, 118));
        Manager_Login1.setForeground(new java.awt.Color(204, 204, 204));
        Manager_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header1.setBackground(new java.awt.Color(51, 51, 51));
        Header1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 255, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("COFFEE SHOP");
        Header1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-190, 30, 1320, 40));

        Manager_Login1.add(Header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1090, 100));

        Navigation1.setBackground(new java.awt.Color(133, 108, 91));
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
        Navigation1.add(register1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 230, 40));

        register2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register2.setText("Change Pass");
        register2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register2ActionPerformed(evt);
            }
        });
        Navigation1.add(register2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 230, 40));

        acc_phone.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_phone.setForeground(new java.awt.Color(255, 255, 255));
        acc_phone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_phone.setText("Email");
        Navigation1.add(acc_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, 150, 30));

        acc_phone1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_phone1.setForeground(new java.awt.Color(255, 255, 255));
        acc_phone1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acc_phone1.setText("Email:");
        Navigation1.add(acc_phone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 70, 30));

        acc_uname1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_uname1.setForeground(new java.awt.Color(255, 255, 255));
        acc_uname1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acc_uname1.setText("User:");
        Navigation1.add(acc_uname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 70, 30));

        acc_type.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_type.setForeground(new java.awt.Color(255, 255, 255));
        acc_type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_type.setText("Type");
        Navigation1.add(acc_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, 150, 30));

        acc_type1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_type1.setForeground(new java.awt.Color(255, 255, 255));
        acc_type1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acc_type1.setText("Type:");
        Navigation1.add(acc_type1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 70, 30));

        acc_uname.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_uname.setForeground(new java.awt.Color(255, 255, 255));
        acc_uname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_uname.setText("User Name");
        Navigation1.add(acc_uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 150, 30));

        acc_id.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_id.setForeground(new java.awt.Color(255, 255, 255));
        acc_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_id.setText("ID");
        Navigation1.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 230, 30));

        register3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register3.setText("Logs");
        register3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register3ActionPerformed(evt);
            }
        });
        Navigation1.add(register3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 230, 40));

        register4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register4.setText("Add Recovery");
        register4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register4ActionPerformed(evt);
            }
        });
        Navigation1.add(register4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 230, 40));

        register5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register5.setText("Manage Coffee");
        register5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register5ActionPerformed(evt);
            }
        });
        Navigation1.add(register5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 230, 40));

        register6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register6.setText("Transaction Logs");
        register6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register6ActionPerformed(evt);
            }
        });
        Navigation1.add(register6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 230, 40));

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
        dbConnect connector = new dbConnect();
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        int userId = 0;
        String uname = null;
        try {
            String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
            PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                uname = resultSet.getString("u_username");
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }

        logEvent(userId, uname, "Logged Out");
        Coffeeshop c = new Coffeeshop();
        c.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registerMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Session sess = Session.getInstance();
       if(sess.getUid() == 0)
       {
           JOptionPane.showMessageDialog(null,"No Account, Login FIrst");
           Coffeeshop c = new Coffeeshop();
           c.setVisible(true);
           this.dispose();
       }else
       {
           
           acc_uname.setText("" + sess.getUname());
           acc_type.setText("" + sess.getType());
           acc_phone.setText("" + sess.getMail());
           acc_id.setText("" + sess.getUid());
       }
    }//GEN-LAST:event_formWindowActivated

    private void register2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register2ActionPerformed
    Admin_ChangePass acp = new Admin_ChangePass();
    acp.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_register2ActionPerformed

    private void register3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register3ActionPerformed
    Logs_Admin la = new Logs_Admin();
    la.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_register3ActionPerformed

    private void register4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register4ActionPerformed
    A_Add_Recovery aar = new A_Add_Recovery();
    aar.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_register4ActionPerformed

    private void register5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register5ActionPerformed
        productForm pf = new productForm();
        pf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_register5ActionPerformed

    private void register6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register6ActionPerformed
        TransactionLogs tl = new TransactionLogs();
        tl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_register6ActionPerformed

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
    private javax.swing.JLabel acc_id;
    private javax.swing.JLabel acc_phone;
    private javax.swing.JLabel acc_phone1;
    private javax.swing.JLabel acc_type;
    private javax.swing.JLabel acc_type1;
    private javax.swing.JLabel acc_uname;
    private javax.swing.JLabel acc_uname1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton register;
    private javax.swing.JButton register1;
    private javax.swing.JButton register2;
    private javax.swing.JButton register3;
    private javax.swing.JButton register4;
    private javax.swing.JButton register5;
    private javax.swing.JButton register6;
    // End of variables declaration//GEN-END:variables
}
