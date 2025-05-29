/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Startups.Coffeeshop;
import config.Session;
import config.dbConnect;
import config.passwordHasher;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class CreateUserForm extends javax.swing.JFrame {

    private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    
    public CreateUserForm() {
        initComponents();
    }
    
    public String destination = "";
    File selectedFile;
    public String oldpath;
    public String path;
    
    public int FileExistenceChecker(String path) {
        File file = new File(path);
        String fileName = file.getName();

        Path filePath = Paths.get("src/userimages", fileName);
         boolean fileExists = Files.exists(filePath);

        if (fileExists) {
            return 1;
        } else {
            return 0;
        }
    }
    
    
    
    public static int getHeightFromWidth(String imagePath, int desiredWidth) {
        try {
            // Read the image file
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            // Get the original width and height of the image
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();

            // Calculate the new height based on the desired width and the aspect ratio
            int newHeight = (int) ((double) desiredWidth / originalWidth * originalHeight);

            return newHeight;
        } catch (IOException ex) {
            System.out.println("No image found!");
        }

        return -1;
    }
    
    
    
    public ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
        ImageIcon MyImage = null;
        if (ImagePath != null) {
            MyImage = new ImageIcon(ImagePath);
        } else {
            MyImage = new ImageIcon(pic);
        }

        int newHeight = getHeightFromWidth(ImagePath, label.getWidth());

        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), newHeight, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    
    
    
    
    public void imageUpdater(String existingFilePath, String newFilePath) {
        File existingFile = new File(existingFilePath);
        if (existingFile.exists()) {
            String parentDirectory = existingFile.getParent();
            File newFile = new File(newFilePath);
            String newFileName = newFile.getName();
            File updatedFile = new File(parentDirectory, newFileName);
            existingFile.delete();
            try {
                Files.copy(newFile.toPath(), updatedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image updated successfully.");
            } catch (IOException e) {
                System.out.println("Error occurred while updating the image: " + e);
            }
        } else {
            try {
                Files.copy(selectedFile.toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Error on update!");
            }
        }
    }


    
    public static String mail, usname;

    public boolean duplicateCheck() {
        dbConnect dbc = new dbConnect();
        String e = email.getText().trim();
        String us = username.getText().trim();

        try {
            String query = "SELECT * FROM tbl_accounts WHERE u_username='" + us + "'OR u_email='" + e + "'";
            ResultSet resultSet = dbc.getData(query);
            if (resultSet.next()) {
                mail = resultSet.getString("u_email");
                if (mail.equals(e)) {
                    JOptionPane.showMessageDialog(null, "Email is Already Used");
                    email.setText("");
                }

                usname = resultSet.getString("u_username");
                if (usname.equals(us)) {
                    JOptionPane.showMessageDialog(null, "Username is Already Used");
                    username.setText("");
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("" + ex);
            return false;
        }
    }

    public boolean updateCheck() {
        dbConnect dbc = new dbConnect();
        String u = UID.getText().trim();
        String e = email.getText().trim();
        String us = username.getText().trim();

        try {
            System.out.println("1");
            String query = "SELECT * FROM tbl_accounts WHERE (u_username='" + us + "'OR u_email='" + e + "') AND u_id != '" + u + "'";
            ResultSet resultSet = dbc.getData(query);
            if (resultSet.next()) {
                mail = resultSet.getString("u_phone");
                if (mail.equals(e)) {
                    JOptionPane.showMessageDialog(null, "Phone Number is Already Used");
                    email.setText("");
                }

                usname = resultSet.getString("u_username");
                if (usname.equals(us)) {
                    JOptionPane.showMessageDialog(null, "Username is Already Used");
                    username.setText("");
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("" + ex);
            return false;
        }
    }
    
    
    
    public void logEvent(int userId, String username, String action) 
    {
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

        New_Manager = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        refresh = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        clear = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        Navigation = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        logo_jpg = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        update = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        delete = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        Remove = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        Select = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        cancel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        username = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        Cpassword = new javax.swing.JPasswordField();
        accType = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        UID = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        New_Manager.setBackground(new java.awt.Color(153, 137, 118));
        New_Manager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(0, 0, 0));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 255, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add User");
        Header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 180, 40));

        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshMouseExited(evt);
            }
        });
        refresh.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Refresh");
        refresh.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 90, 10));

        Header.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 30, -1, 30));

        clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearMouseExited(evt);
            }
        });
        clear.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Clear");
        clear.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 90, 10));

        Header.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 30, -1, 30));

        New_Manager.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 100));

        Navigation.setBackground(new java.awt.Color(51, 51, 51));
        Navigation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Navigation.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));
        Navigation.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 60));

        logo_jpg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.jpg"))); // NOI18N
        Navigation.add(logo_jpg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-290, 0, 880, 660));

        New_Manager.add(Navigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, 590, 660));

        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMouseExited(evt);
            }
        });
        add.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Add");
        add.add(jLabel11);
        jLabel11.setBounds(0, 10, 90, 10);

        New_Manager.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 580, 90, 30));

        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateMouseExited(evt);
            }
        });
        update.setLayout(null);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Update");
        update.add(jLabel17);
        jLabel17.setBounds(0, 10, 90, 10);

        New_Manager.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 580, 90, 30));

        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteMouseExited(evt);
            }
        });
        delete.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Delete");
        delete.add(jLabel18);
        jLabel18.setBounds(0, 10, 90, 10);

        New_Manager.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 580, 90, 30));

        jPanel1.setLayout(null);
        jPanel1.add(image);
        image.setBounds(10, 10, 190, 170);

        New_Manager.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, 210, 190));

        Remove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemoveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RemoveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RemoveMouseExited(evt);
            }
        });
        Remove.setLayout(null);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Remove");
        Remove.add(jLabel21);
        jLabel21.setBounds(0, 10, 90, 10);

        New_Manager.add(Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 400, 90, 30));

        Select.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SelectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SelectMouseExited(evt);
            }
        });
        Select.setLayout(null);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Select");
        Select.add(jLabel22);
        jLabel22.setBounds(0, 10, 90, 10);

        New_Manager.add(Select, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, 90, 30));

        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelMouseExited(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Cancel");
        cancel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 90, 10));

        New_Manager.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 580, -1, 30));

        jLabel9.setBackground(new java.awt.Color(153, 153, 153));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Registration");
        New_Manager.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 160, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("PASSWORD:");
        New_Manager.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 100, 30));

        password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });
        New_Manager.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 330, 30));

        username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        New_Manager.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 330, 30));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setText("EMAIL:");
        New_Manager.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 490, 60, 30));

        email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        New_Manager.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 330, 30));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setText("USERNAME:");
        New_Manager.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 90, 30));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        New_Manager.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 160, 30));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setText("CONFIRM PASSWORD:");
        New_Manager.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 170, 30));

        Cpassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Cpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CpasswordActionPerformed(evt);
            }
        });
        New_Manager.add(Cpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 330, 30));

        accType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "Admin" }));
        accType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accTypeActionPerformed(evt);
            }
        });
        New_Manager.add(accType, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 330, 30));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("ACCOUNT TYPE:");
        New_Manager.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 120, 30));

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Pending" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        New_Manager.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 330, 30));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("ACCOUNT STATUS:");
        New_Manager.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 140, 30));

        UID.setEditable(false);
        UID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UIDActionPerformed(evt);
            }
        });
        New_Manager.add(UID, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 330, 30));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setText("ID:");
        New_Manager.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 30, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(New_Manager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(New_Manager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
        U_Admin ua = new U_Admin();
        ua.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancelMouseClicked

    private void cancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseEntered
        cancel.setBackground(h);
    }//GEN-LAST:event_cancelMouseEntered

    private void cancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseExited
        cancel.setBackground(d);
    }//GEN-LAST:event_cancelMouseExited

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
        int userId = 0;
        String uname2 = null;
        String uname = username.getText().trim();
        String pass = new String(password.getPassword()).trim();
        String Cpass = new String(Cpassword.getPassword()).trim();
        String e = email.getText().trim();
        String at = accType.getSelectedItem().toString().trim();
        String s = status.getSelectedItem().toString().trim();
        String sq = "";
        String sa   = "";

        if(uname.isEmpty() || pass.isEmpty() || Cpass.isEmpty() || e.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");

        }else if(!pass.equals(Cpass))
        {
            JOptionPane.showMessageDialog(null, "Password Does Not Match");
            //System.out.println("Password ["+password+"] Length: "+password.length());
            //System.out.println("Confirm Password ["+Cpassword+"] Length: "+Cpassword.length());
        }else if (!e.contains("@") && !e.contains(".com")) {
            JOptionPane.showMessageDialog(null, "Enter Valid Email");
        }else if(pass.length() <= 7)
        {
            JOptionPane.showMessageDialog(null, "Password Must be Exactly 8 Characters Long");
        }else if(duplicateCheck())
        {
//            System.out.println("Duplicate Exists");
            JOptionPane.showMessageDialog(null, "Duplicate Exists");
        }else
        {
            try
            {
                /*                String query = "SELECT * FROM tbl_accounts WHERE u_id='" + sess.getUid() + "'";
                ResultSet rs = dbc.getData(query);
                if (rs.next())
                {*/
                String npass = passwordHasher.hashPassword(pass);

                if(dbc.insertData("INSERT INTO tbl_accounts (u_username, u_accType, u_pass, u_email, u_status, u_image, security_question, security_answer) "
                    + "VALUES ('" + uname + "', '" + at + "','" + npass + "', '" + e + "',  '" + s + "',  '" + destination + "',  '" + sq + "',  '" + sa + "')"))
                {
                    




                    try 
                    {
                        String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                        PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) 
                        {
                            userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                            uname2 = resultSet.getString("u_username");
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQL Exception: " + ex);
                    }



                    logEvent(userId, uname2, "Admin Added Account: "+uname);


//                    Files.copy(selectedFile.toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING );
                    JOptionPane.showMessageDialog(null, "Registered succesfully!");
                    U_Admin ua = new U_Admin();
                    ua.setVisible(true);
                    this.dispose();
                }else
                {
                    JOptionPane.showMessageDialog(null, "An error occured");
                    System.out.println("Dan, Error occured in line: 757");
                    U_Admin ua = new U_Admin();
                    ua.setVisible(true);
                    this.dispose();
                }
//                }
            } catch(NoSuchAlgorithmException ex)
            {
                System.out.println("" + ex);
            }
        }
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
        add.setBackground(h);
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
        add.setBackground(d);
    }//GEN-LAST:event_addMouseExited

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshMouseClicked

    private void refreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshMouseEntered

    private void refreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshMouseExited

    private void updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseClicked
//        dbConnect dbc = new dbConnect();
//        Session sess = Session.getInstance();
//        dbConnect connector = new dbConnect();
//        int userId = 0;
//        String uname2 = null;
//        String u = UID.getText().trim();
//        String fn = Fname.getText().trim();
//        String ln = Lname.getText().trim();
//        String uname = MR_username.getText().trim();
//        String pass = new String(MR_password.getPassword()).trim();
//        String Cpass = new String(MR_passwordConfirm.getPassword()).trim();
//        String p = PhoneNum.getText().trim();
//        String at = type.getSelectedItem().toString().trim();
//        String s = status.getSelectedItem().toString().trim();
//        
//
//        if(uname.isEmpty() || ln.isEmpty() || fn.isEmpty())
//        {
//            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");
//
//        }else if(!p.matches("\\d+"))
//        {
//            JOptionPane.showMessageDialog(null, "Phone Must Only Contain Numbers");
//        }else if(p.length() > 15 || p.length() < 11)
//        {
//            JOptionPane.showMessageDialog(null, "Invalid Phone num");
//        }else if(updateCheck())
//        {
//            System.out.println("Duplicate Exists");
//        }else
//        {
//            try {

        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
        int userId = 0;
        String uname2 = null;
        String u = UID.getText().trim();
        String uname = username.getText().trim();
        String e = email.getText().trim();
        String at = accType.getSelectedItem().toString().trim();
        String s = status.getSelectedItem().toString().trim();
        String sq = "";
        String sa = "";

        if (uname.isEmpty() || e.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");

        } else if (!e.contains("@") && !e.contains(".com")) {
            JOptionPane.showMessageDialog(null, "Enter Valid Email");
        } else if (updateCheck()) {
//            System.out.println("Duplicate Exists");
            JOptionPane.showMessageDialog(null, "Duplicate Exists");
        } else {
            try {
                String query = "SELECT * FROM tbl_accounts WHERE u_id='" + sess.getUid() + "'";
                ResultSet rs = dbc.getData(query);
                if (rs.next()) 
                {
                    String npass = rs.getString("u_pass");
                    
                    dbc.updateData("UPDATE tbl_accounts SET u_username = '" + uname + "',u_pass = '"+npass+"', u_email = '"+e+"',"
                            + " u_accType = '"+at+"', u_status = '"+s+"', u_image = '"+destination+"' WHERE u_id = '"+u+"'");
                    
                    System.out.println("destination: "+destination);
                    
                    
                    
                    
                    try 
                    {
                        String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                        PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) 
                        {
                            userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                            uname2 = resultSet.getString("u_username");
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQL Exception: " + ex);

                    }


                    logEvent(userId, uname2, "Admin Updated Account: "+uname);
                    
                    
                    
                    if (destination.isEmpty()) {
                        File existingFile = new File(oldpath);
                        if (existingFile.exists()) {
                            existingFile.delete();
                        }
                    } else {
                        if (!(oldpath.equals(path))) {
                            imageUpdater(oldpath, path);
                        }
                    }
                    
                    
//                    if(destination.isEmpty())
//                    {
//                        File existingFile = new File(oldpath);
//                        if(existingFile.exists())
//                        {
//                            existingFile.delete();
//                        }
//                    }else
//                    {
//                        if(!(oldpath.equals(path)))
//                        {
//                            imageUpdater(oldpath,path);
//                        }
//                    }
                    
                    U_Admin ua = new U_Admin();
                    ua.setVisible(true);
                    this.dispose();
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
                U_Admin ua = new U_Admin();
                ua.setVisible(true);
                this.dispose();
            }
        }

    }//GEN-LAST:event_updateMouseClicked

    private void updateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseEntered
        update.setBackground(h);
    }//GEN-LAST:event_updateMouseEntered

    private void updateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseExited
        update.setBackground(d);
    }//GEN-LAST:event_updateMouseExited

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteMouseClicked

    private void deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteMouseEntered

    private void deleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteMouseExited

    private void clearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearMouseClicked

    private void clearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_clearMouseEntered

    private void clearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_clearMouseExited

    private void RemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveMouseClicked
        Remove.setEnabled(false);
        Select.setEnabled(true);
        image.setIcon(null);
        destination = "";
        path = "";  

    }//GEN-LAST:event_RemoveMouseClicked

    private void RemoveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveMouseEntered
        Remove.setBackground(h);
    }//GEN-LAST:event_RemoveMouseEntered

    private void RemoveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveMouseExited
        Remove.setBackground(d);
    }//GEN-LAST:event_RemoveMouseExited

    private void SelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectMouseClicked
//         imageuploadjava.txt 
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                selectedFile = fileChooser.getSelectedFile();
                destination = "src/userimages/" + selectedFile.getName();
                path = selectedFile.getAbsolutePath();

                if (FileExistenceChecker(path) == 1) {
                    JOptionPane.showMessageDialog(null, "File Already Exist, Rename or Choose another!");
                    destination = "";
                    path = "";
                } else {
                    image.setIcon(ResizeImage(path, null, image));
                    Select.setEnabled(false);
                    Remove.setEnabled(true);
                }
            } catch (Exception ex) {
                System.out.println("File Error!");
            }
        }
    }//GEN-LAST:event_SelectMouseClicked

    private void SelectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectMouseEntered
        Select.setBackground(h);
    }//GEN-LAST:event_SelectMouseEntered

    private void SelectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectMouseExited
        Select.setBackground(d);
    }//GEN-LAST:event_SelectMouseExited

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void CpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CpasswordActionPerformed

    private void accTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accTypeActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void UIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UIDActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Session sess = Session.getInstance();
        if (sess.getUid() == 0) {

            Coffeeshop l = new Coffeeshop();
            l.setVisible(true);
            this.dispose();
            JOptionPane.showMessageDialog(null, "No Account, Login FIrst");
        }
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateUserForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPasswordField Cpassword;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Navigation;
    private javax.swing.JPanel New_Manager;
    public javax.swing.JPanel Remove;
    public javax.swing.JPanel Select;
    public javax.swing.JTextField UID;
    public javax.swing.JComboBox<String> accType;
    public javax.swing.JPanel add;
    private javax.swing.JPanel cancel;
    private javax.swing.JPanel clear;
    private javax.swing.JPanel delete;
    public javax.swing.JTextField email;
    public javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel logo_jpg;
    public javax.swing.JPasswordField password;
    private javax.swing.JPanel refresh;
    public javax.swing.JComboBox<String> status;
    public javax.swing.JPanel update;
    public javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
