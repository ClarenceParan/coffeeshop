package coffeeshop;


import coffeeshop.Coffeeshop;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DANIEL FAILADONA
 */
public class Registration extends javax.swing.JFrame {

    /**
     * Creates new form Registration
     */
    public Registration() {
        initComponents();
        this.setResizable(false);
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Epassword = new javax.swing.JPasswordField();
        Eusername = new javax.swing.JTextField();
        register = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        Eemail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        ECpassword = new javax.swing.JPasswordField();
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
        Header1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 690, 40));

        Manager_Login1.add(Header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 690, 100));

        Navigation1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout Navigation1Layout = new javax.swing.GroupLayout(Navigation1);
        Navigation1.setLayout(Navigation1Layout);
        Navigation1Layout.setHorizontalGroup(
            Navigation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );
        Navigation1Layout.setVerticalGroup(
            Navigation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        Manager_Login1.add(Navigation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 640));

        jLabel9.setBackground(new java.awt.Color(153, 153, 153));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Registration");
        Manager_Login1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 160, 30));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("PASSWORD:");
        Manager_Login1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, 100, 30));

        Epassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Epassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EpasswordActionPerformed(evt);
            }
        });
        Manager_Login1.add(Epassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 250, 330, 30));

        Eusername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Eusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EusernameActionPerformed(evt);
            }
        });
        Manager_Login1.add(Eusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 200, 330, 30));

        register.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register.setText("Cancel");
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });
        Manager_Login1.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 550, 110, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("EMAIL:");
        Manager_Login1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 350, 70, 30));

        Eemail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Eemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EemailActionPerformed(evt);
            }
        });
        Manager_Login1.add(Eemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 350, 330, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("USERNAME:");
        Manager_Login1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 90, 30));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        Manager_Login1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 160, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("CONFIRM PASSWORD:");
        Manager_Login1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 170, 30));

        ECpassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ECpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ECpasswordActionPerformed(evt);
            }
        });
        Manager_Login1.add(ECpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 300, 330, 30));

        register1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register1.setText("REGISTER");
        register1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register1ActionPerformed(evt);
            }
        });
        Manager_Login1.add(register1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 400, 140, 30));

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
            .addGap(0, 640, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Manager_Login1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void EpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EpasswordActionPerformed

    private void EusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EusernameActionPerformed

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
    Coffeeshop c = new Coffeeshop();
    c.setVisible(true);
    this.dispose(); 
    }//GEN-LAST:event_registerActionPerformed

    private void EemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EemailActionPerformed

    private void ECpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ECpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ECpasswordActionPerformed

    private void register1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register1ActionPerformed
    String password = new String(Epassword.getPassword()).trim();
    String Cpassword = new String(ECpassword.getPassword()).trim();
    String username = Eusername.getText().trim();
    String email = Eemail.getText().trim();
    
    if(password.isEmpty() || Cpassword.isEmpty() || username.isEmpty() || email.isEmpty())
    {
        JOptionPane.showMessageDialog(null, "Please Fill All Boxes");
    }else if(password.length() < 8)
    {
        JOptionPane.showMessageDialog(null, "Password Must Be 8 Characters Long");
    }else if(!password.equals(Cpassword))
    {
        JOptionPane.showMessageDialog(null, "Password Does Not Match");
    }
    }//GEN-LAST:event_register1ActionPerformed

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
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField ECpassword;
    private javax.swing.JTextField Eemail;
    private javax.swing.JPasswordField Epassword;
    private javax.swing.JTextField Eusername;
    private javax.swing.JPanel Header1;
    private javax.swing.JPanel Manager_Login1;
    private javax.swing.JPanel Navigation1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton register;
    private javax.swing.JButton register1;
    // End of variables declaration//GEN-END:variables
}
