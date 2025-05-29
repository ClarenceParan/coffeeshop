/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import Startups.Coffeeshop;
import static admin.CreateUserForm.mail;
import static admin.CreateUserForm.usname;
import config.Session;
import config.dbConnect;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author DANIEL FAILADONA
 */
public class accountDetails extends javax.swing.JFrame {

    
    private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    
    
    public accountDetails() {
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
    
    
    
    
    
    
    
    
    
    public static String mail,usname;
    
    public boolean updateCheck() {
        Session sess = Session.getInstance();
        dbConnect dbc = new dbConnect();
        String e = Email.getText().trim();
        String us = userName.getText().trim();

        try {
            String query = "SELECT * FROM tbl_accounts WHERE (u_username='" + us + "'OR u_email='" + e + "') AND u_id != '" + sess.getUid() + "'";
            ResultSet resultSet = dbc.getData(query);
            if (resultSet.next()) {
                mail = resultSet.getString("u_email");
                if (mail.equals(e)) {
                    JOptionPane.showMessageDialog(null, "Email Number is Already Used");
                    Email.setText("");
                }

                usname = resultSet.getString("u_username");
                if (usname.equals(us)) {
                    JOptionPane.showMessageDialog(null, "Username is Already Used");
                    userName.setText("");
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
    
    
//    private String selectedImagePath = "";
//    private File selectedFile = null; // Global file to remember user's pick
//
//    private void selectImage() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));
//
//        int result = fileChooser.showOpenDialog(this);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            selectedFile = fileChooser.getSelectedFile();
//
//            // Just preview here
//            image.setIcon(new javax.swing.ImageIcon(selectedFile.getAbsolutePath()));
//            System.out.println("Image Selected: " + selectedFile.getAbsolutePath());
//        }
//    }
//
////================================================================================
//    public int FileExistenceChecker(String path) {
//        File file = new File(path);
//        String fileName = file.getName();
//        Path filePath = Paths.get("src/images", fileName);
//        boolean fileExists = Files.exists(filePath);
//
//        return fileExists ? 1 : 0;
//    }
////================================================================================
//
//    public static int getHeightFromWidth(String imagePath, int desiredWidth) {
//        try {
//            File imageFile = new File(imagePath);
//            BufferedImage image = ImageIO.read(imageFile);
//            int originalWidth = image.getWidth();
//            int originalHeight = image.getHeight();
//            return (int) ((double) desiredWidth / originalWidth * originalHeight);
//        } catch (IOException ex) {
//            System.out.println("No image found!");
//        }
//        return -1;
//    }
////================================================================================
//
//    private void uploadImage(File selectedFile) {
//        if (selectedFile == null) {
//            System.out.println("1");
//            return;
//        }
//
//        System.out.println("3");
//        File destinationFolder = new File("src/images/");
//        if (!destinationFolder.exists()) {
//            System.out.println("4");
//            destinationFolder.mkdirs(); // Create folder if it does not exist
//        }
//
//        System.out.println("5");
//        String fileName = selectedFile.getName();
//        String destinationPath = "src/images/" + fileName;
//
//        if (FileExistenceChecker(destinationPath) == 1) {
//            System.out.println("6");
//            JOptionPane.showMessageDialog(this, "File already exists! Please rename or choose another image.");
//            selectedImagePath = ""; // Clear path since we won’t use it
//            return;
//        }
//
//        System.out.println("7");
//        File destinationFile = new File(destinationFolder, fileName);
//
//        try {
//            System.out.println("8");
//            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            selectedImagePath = destinationPath;
//
//            // Resize and display the image
//            ImageIcon originalIcon = new ImageIcon(destinationFile.getAbsolutePath());
//            int newHeight = getHeightFromWidth(destinationFile.getAbsolutePath(), image.getWidth());
//            Image scaledImage = originalIcon.getImage().getScaledInstance(image.getWidth(), newHeight, Image.SCALE_SMOOTH);
//            image.setIcon(new ImageIcon(scaledImage));
//
//            System.out.println("Image uploaded successfully to: " + selectedImagePath);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error uploading image: " + e.getMessage());
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Navigation = new javax.swing.JPanel();
        logout = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        acc_email = new javax.swing.JLabel();
        acc_type = new javax.swing.JLabel();
        acc_uname = new javax.swing.JLabel();
        acc_id = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Email = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cancel1 = new javax.swing.JPanel();
        acc_phone1 = new javax.swing.JLabel();
        confirm = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cancel2 = new javax.swing.JPanel();
        acc_phone2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        Remove = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        Select = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        Main.setBackground(new java.awt.Color(153, 137, 118));
        Main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(0, 0, 0));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 255, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Account Details");
        Header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1310, 40));

        Main.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 100));

        Navigation.setBackground(new java.awt.Color(102, 102, 102));
        Navigation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutMouseExited(evt);
            }
        });
        logout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Back");
        logout.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 130, -1));

        Navigation.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 130, 40));

        acc_email.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_email.setForeground(new java.awt.Color(255, 255, 255));
        acc_email.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_email.setText("Phone");
        Navigation.add(acc_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 300, 30));

        acc_type.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_type.setForeground(new java.awt.Color(255, 255, 255));
        acc_type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_type.setText("Type");
        Navigation.add(acc_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 300, 30));

        acc_uname.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_uname.setForeground(new java.awt.Color(255, 255, 255));
        acc_uname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_uname.setText("User Name");
        Navigation.add(acc_uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 300, 30));

        acc_id.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_id.setForeground(new java.awt.Color(255, 255, 255));
        acc_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_id.setText("ID");
        Navigation.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 300, 30));
        Navigation.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-70, -40, 380, 430));

        Main.add(Navigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 310, 560));

        Email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailActionPerformed(evt);
            }
        });
        Main.add(Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 340, 330, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Username:");
        Main.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 290, 80, 30));

        userName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        Main.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 290, 330, 30));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Email:");
        Main.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 340, 80, 30));

        cancel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancel1MouseExited(evt);
            }
        });
        cancel1.setLayout(null);

        acc_phone1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_phone1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_phone1.setText("Change Pass");
        cancel1.add(acc_phone1);
        acc_phone1.setBounds(0, 10, 150, 20);

        Main.add(cancel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 100, 150, 40));

        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirmMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmMouseExited(evt);
            }
        });
        confirm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Confirm");
        confirm.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 90, -1));

        Main.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 440, 90, 30));

        cancel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancel2MouseExited(evt);
            }
        });
        cancel2.setLayout(null);

        acc_phone2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_phone2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_phone2.setText("Add Recovery");
        cancel2.add(acc_phone2);
        acc_phone2.setBounds(0, 10, 150, 20);

        Main.add(cancel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, 150, 40));

        jPanel1.setLayout(null);
        jPanel1.add(image);
        image.setBounds(10, 10, 190, 170);

        Main.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 210, 190));

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

        Main.add(Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 430, 90, 30));

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

        Main.add(Select, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        EmployeeDashboard ed = new EmployeeDashboard();
        ed.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseEntered
        logout.setBackground(h);
    }//GEN-LAST:event_logoutMouseEntered

    private void logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseExited
        logout.setBackground(d);
    }//GEN-LAST:event_logoutMouseExited

    private void EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailActionPerformed

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    private void cancel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel1MouseClicked
        changePass cp = new changePass();
        cp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancel1MouseClicked

    private void cancel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_cancel1MouseEntered

    private void cancel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_cancel1MouseExited

    private void confirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseClicked
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        String un = userName.getText().trim();
        String em = Email.getText().trim();
        dbConnect connector = new dbConnect();
        int userId = 0;
        String uname2 = null;

        if(em.isEmpty() || un.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");
        }else if(updateCheck())
        {
            System.out.println("Duplicate Exists");
        }else
        {
//            if (selectedFile != null) 
//            {
//                uploadImage(selectedFile); // Upload before storing the path in DB

//                String image = selectedImagePath; // Now this will point to the saved location
                dbc.updateData("UPDATE tbl_accounts SET u_email = '" + em + "', u_username = '" + un + "', u_image = '" + destination + "' WHERE u_id = '"+sess.getUid()+"'");
            
                try 
                {
                    String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                    PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                        userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                        uname2 = resultSet.getString("u_username");
                    }
                } catch (SQLException ex) {
                    System.out.println("SQL Exception: " + ex);
                }

                logEvent(userId, uname2, "User Changed Their Details");

                EmployeeDashboard ed = new EmployeeDashboard();
                ed.setVisible(true);
                this.dispose();
//            } else {
//                JOptionPane.showMessageDialog(null, "Unknown Error Occured");
//                EmployeeDashboard ed = new EmployeeDashboard();
//                ed.setVisible(true);
//                this.dispose();
//            }
        }
    }//GEN-LAST:event_confirmMouseClicked

    private void confirmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseEntered
        confirm.setBackground(h);
    }//GEN-LAST:event_confirmMouseEntered

    private void confirmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseExited
        confirm.setBackground(d);
    }//GEN-LAST:event_confirmMouseExited

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       Session sess = Session.getInstance();
        if (sess.getUid() == 0) {

            Coffeeshop l = new Coffeeshop();
            l.setVisible(true);
            this.dispose();
            JOptionPane.showMessageDialog(null, "No Account, Login FIrst");
        } else {
        
            acc_uname.setText("Username: " + sess.getUname());
            acc_type.setText("Usertype: " + sess.getType());
            acc_email.setText("Mail: " + sess.getMail());
            acc_id.setText("User ID: " + sess.getUid());
        }
    }//GEN-LAST:event_formWindowActivated

    private void cancel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel2MouseClicked
    E_Add_Recovery ear = new E_Add_Recovery();
    ear.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_cancel2MouseClicked

    private void cancel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_cancel2MouseEntered

    private void cancel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_cancel2MouseExited

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
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new accountDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField Email;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    private javax.swing.JPanel Navigation;
    public javax.swing.JPanel Remove;
    public javax.swing.JPanel Select;
    private javax.swing.JLabel acc_email;
    private javax.swing.JLabel acc_id;
    private javax.swing.JLabel acc_phone1;
    private javax.swing.JLabel acc_phone2;
    private javax.swing.JLabel acc_type;
    private javax.swing.JLabel acc_uname;
    private javax.swing.JPanel cancel1;
    private javax.swing.JPanel cancel2;
    private javax.swing.JPanel confirm;
    public javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel logout;
    public javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}
