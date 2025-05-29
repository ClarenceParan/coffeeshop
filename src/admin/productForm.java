/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Startups.Coffeeshop;
import java.sql.ResultSet;           // For ResultSet
import java.sql.SQLException;         // For SQLException
import java.util.ArrayList;          // For ArrayList
import java.util.List;               // For List
import javax.swing.table.DefaultTableModel;  // For DefaultTableModel
import config.dbConnect;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import config.Session;
import static java.awt.Color.black;
import static java.awt.Color.red;
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class productForm extends javax.swing.JFrame {

     private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    
    public productForm() {
        initComponents();
        NotShowDeletedUsers();
//    displayData();
    }
    boolean addClickable  = true;
    
    
    public static String pname;

    public boolean updateCheck() {
        dbConnect dbc = new dbConnect();
        String u = PID.getText().trim();
        String mn = Mname.getText().trim();

        try {
            String query = "SELECT * FROM tbl_products WHERE (p_name='" + mn + "') AND p_id != '" + u + "'";
            ResultSet resultSet = dbc.getData(query);
            if (resultSet.next()) 
            {
                pname = resultSet.getString("p_name");
                if (pname.equals(mn)) 
                {
                    JOptionPane.showMessageDialog(null, "Movie Already Exists");
                    Mname.setText("");
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
    
    
    
    
    
    public boolean duplicateCheck() {
        dbConnect dbc = new dbConnect();
        String mn = Mname.getText().trim();

        try {
            String query = "SELECT * FROM tbl_products WHERE p_name='" + mn + "'"; //If output mentions something about ''', there is a missing '
            ResultSet resultSet = dbc.getData(query);
            if (resultSet.next()) {
                pname = resultSet.getString("p_name");
                if (pname.equals(mn)) {
                    JOptionPane.showMessageDialog(null, "Movie Already Exists");
                    Mname.setText("");
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
    
    
    
    public void NotShowDeletedUsers() 
    {
        // Create a list to store filtered row data
        List<Object[]> rowData = new ArrayList<>();

        try {
            dbConnect dbc = new dbConnect();
            ResultSet rs = dbc.getData("SELECT * FROM tbl_products");

            while (rs.next()) {
                // Store each column value in a separate variable
                String u = rs.getString("p_id");
                String pn = rs.getString("p_name");
                String pp = rs.getString("p_price");
                String status = rs.getString("p_status");
                

                // Check if the user status is not "Deleted"
                if (!status.equals("Deleted")) {
                    
                    // Add the row to the list
                    rowData.add(new Object[]{
                        u,
                        pn,
                        pp, 
                        status 
                    });
                    /*System.out.println("\n==========");
                    System.out.println(""+u);
                    System.out.println(""+fn);
                    System.out.println(""+ln);
                    System.out.println(""+uname);
                    System.out.println(""+at);
                    System.out.println(""+p);
                    System.out.println(""+status);*/
                }
            }

            // After processing all rows, update the table on the Swing event dispatch thread
            SwingUtilities.invokeLater(() -> {
                
                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"ID", "Movie Name", "Price", "Status"}, 0
                );
                for (Object[] row : rowData) {
                    model.addRow(row);
                }
                account_table.setModel(model);
                account_table.repaint(); // Force visual refresh
            });


            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    

    public void displayData()
    {
        try
        {
            dbConnect dbc = new dbConnect();
            ResultSet rs = dbc.getData("SELECT * FROM tbl_products");
            account_table.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        }catch(SQLException ex)
        {
            System.out.println("Errors: "+ex.getMessage());
        }
    }
    
    
    private void loadUsersData() 
    {
        DefaultTableModel model = (DefaultTableModel) account_table.getModel();
        model.setRowCount(0); // Clear the table before reloading

        String sql = "SELECT * FROM tbl_products";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_db", "root", "");
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) 
        {

            while (rs.next()) 
            {
                // Check if the user's status is not "Deleted"
                String userStatus = rs.getString("p_status");
                if (!"Deleted".equals(userStatus)) 
                {
                    model.addRow(new Object[]
                    {
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getString("p_price"),
                        rs.getString("p_status"),
                    });
                }
            }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(this, "Error loading user data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
    
    
    
    
    
    
    private void deleteUser() {
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
//        int userId = 0;
        String uname3 = null;
        String uname2 = null;
        String uname = null;
        int userId = 0;

        int selectedRow = account_table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int productId = Integer.parseInt(account_table.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            try {
                String query2 = "SELECT * FROM tbl_products WHERE p_id = '" + productId + "'";
                PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String pid = rs.getString("p_id");
                    String pn = rs.getString("p_name");
                    String pr = rs.getString("p_price");
                    String s = "Deleted";

                    dbc.updateData("UPDATE tbl_products SET p_name = '" + pn + "', p_price = '" + pr + "', p_status = '" + s + "' WHERE p_id = '" + pid + "'");

                    try {
                        System.out.println("1");
                        String query = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                        PreparedStatement pstmt2 = connector.getConnection().prepareStatement(query);

                        ResultSet rs2 = pstmt2.executeQuery();

                        if (rs2.next()) {
                            System.out.println("2");
                            userId = rs2.getInt("u_id");
                            uname2 = rs2.getString("u_username");
                            loadUsersData();
                        }
                        logEvent(userId, uname2, "Admin Deleted Account: " + uname2);

                    } catch (SQLException ex) 
                    {
                        System.out.println("" + ex);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQL Exception: " + ex);
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

        Main = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Navigation = new javax.swing.JPanel();
        logout = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Price = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Mname = new javax.swing.JTextField();
        status = new javax.swing.JComboBox<>();
        PID = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        Remove = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        Select = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        account_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        add = new javax.swing.JPanel();
        ad = new javax.swing.JLabel();
        add2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        add3 = new javax.swing.JPanel();
        ad1 = new javax.swing.JLabel();
        add4 = new javax.swing.JPanel();
        ad2 = new javax.swing.JLabel();

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
        jLabel1.setText("Admin Dashboard");
        Header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1310, 40));

        Main.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 100));

        Navigation.setBackground(new java.awt.Color(153, 137, 118));
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

        Navigation.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 493, 130, 40));
        Navigation.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -80, 180, 80));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Status:");
        Navigation.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 80, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Price:");
        Navigation.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 80, 30));

        Price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceActionPerformed(evt);
            }
        });
        Navigation.add(Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 330, 30));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Coffee Name:");
        Navigation.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 110, 30));

        Mname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Mname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnameActionPerformed(evt);
            }
        });
        Navigation.add(Mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 330, 30));

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Unavailable" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        Navigation.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, 330, 30));

        PID.setEditable(false);
        PID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PID.setEnabled(false);
        PID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIDActionPerformed(evt);
            }
        });
        Navigation.add(PID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 330, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Product ID:");
        Navigation.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 90, 30));

        jPanel1.setLayout(null);
        jPanel1.add(image);
        image.setBounds(10, 10, 190, 170);

        Navigation.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 210, 190));

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

        Navigation.add(Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 90, 30));

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

        Navigation.add(Select, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 90, 30));

        Main.add(Navigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 460, 540));

        account_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        account_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                account_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(account_table);

        Main.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 530, 540));

        jScrollPane2.setViewportView(jEditorPane1);

        Main.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, -1, -1));

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

        ad.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ad.setText("ADD");
        add.add(ad);
        ad.setBounds(75, 10, 130, 22);

        Main.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 100, 300, 40));

        add2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add2MouseExited(evt);
            }
        });
        add2.setLayout(null);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("CLEAR");
        add2.add(jLabel14);
        jLabel14.setBounds(75, 10, 130, 22);

        Main.add(add2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 280, 300, 40));

        add3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add3MouseExited(evt);
            }
        });
        add3.setLayout(null);

        ad1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ad1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ad1.setText("UPDATE");
        add3.add(ad1);
        ad1.setBounds(75, 10, 130, 22);

        Main.add(add3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 160, 300, 40));

        add4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add4MouseExited(evt);
            }
        });
        add4.setLayout(null);

        ad2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ad2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ad2.setText("DELETE");
        add4.add(ad2);
        ad2.setBounds(75, 10, 130, 22);

        Main.add(add4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 220, 300, 40));

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
        adminDashboard as = new adminDashboard();
        as.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseEntered
        logout.setBackground(h);
    }//GEN-LAST:event_logoutMouseEntered

    private void logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseExited
        logout.setBackground(d);
    }//GEN-LAST:event_logoutMouseExited

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
//        if (addClickable)
//        {
            dbConnect dbc = new dbConnect();
            Session sess = Session.getInstance();
            dbConnect connector = new dbConnect();
            int userId = 0;
            String uname2 = null;
            String mn = Mname.getText().trim();
            String pr = Price.getText().trim();
            String sold = "";
            String st = status.getSelectedItem().toString().trim();

            if (mn.isEmpty() || pr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill All Boxes");

            } else if (!pr.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Price must be a valid number (e.g., 12 or 12.99)");
            } else if (duplicateCheck()) {
                JOptionPane.showMessageDialog(null, "Duplicate Exists");
            } else {
                try {
                    
                    
                    
                    
                    try {
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
                    
                    
                    
                    
                    

                    if (dbc.insertData("INSERT INTO tbl_products (p_name, p_price, p_status, p_image, p_sold) "
                            + "VALUES ('" + mn + "', '" + pr + "', '" + st + "', '" + destination + "', '" + sold + "')")) 
                    {
                        System.out.println("3");
                        

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
                        } catch (SQLException ex) 
                        {
                            System.out.println("SQL Exception: " + ex);
                        }

                        logEvent(userId, uname2, "Admin Added The Movie: " + mn);

                        if (selectedFile != null && destination != null) {
                            Files.copy(selectedFile.toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            System.out.println("selectedFile or destination is null!");
                        }

                        
                        adminDashboard ad = new adminDashboard();
                        ad.setVisible(true);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "An error occured");
//                        System.out.println("Dan, Error occured in line: 757, productForm");
                        adminDashboard ad = new adminDashboard();
                        ad.setVisible(true);
                        this.dispose();
                    }
                    //                }
                } catch (IOException ex) {
                    System.out.println("" + ex);
                }
            }
//        }else if (!addClickable)
//        {
//            JOptionPane.showMessageDialog(null, "Clear the Fields First");
//        }
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
        add.setBackground(h);
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
        add.setBackground(d);
    }//GEN-LAST:event_addMouseExited

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

    private void PriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PriceActionPerformed

    private void MnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MnameActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void PIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PIDActionPerformed

    private void account_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_account_tableMouseClicked
        int rowIndex = account_table.getSelectedRow();


        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select an Item");
        } else {
//            CU_Admin cua = new CU_Admin();

            try {
                dbConnect dbc = new dbConnect();
                TableModel tbl = account_table.getModel();
                ResultSet rs = dbc.getData("SELECT * FROM tbl_products WHERE p_id = '" + tbl.getValueAt(rowIndex, 0) + "'");
                if (rs.next()) {

                    PID.setText("" + rs.getString("p_id"));
                    Mname.setText("" + rs.getString("p_name"));
                    Price.setText("" + rs.getString("p_price"));
                    status.setSelectedItem("" + rs.getString("p_status"));
//                    addClickable = false;
//                    ad.setForeground(red);
                }

            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
    }//GEN-LAST:event_account_tableMouseClicked

    private void add2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add2MouseClicked
//        addClickable = true;
//        ad.setForeground(black);
        NotShowDeletedUsers();
        PID.setText("");
        Mname.setText("");
        Price.setText("");
        status.setSelectedItem(0);

    }//GEN-LAST:event_add2MouseClicked

    private void add2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add2MouseEntered
        add2.setBackground(h);

    }//GEN-LAST:event_add2MouseEntered

    private void add2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add2MouseExited
        add2.setBackground(d);

    }//GEN-LAST:event_add2MouseExited

    private void add3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add3MouseClicked
        String pid = PID.getText();
        if(pid.isEmpty())
        {            
            JOptionPane.showMessageDialog(null, "Please select an Item");
        }else
        {
            dbConnect dbc = new dbConnect();
            Session sess = Session.getInstance();
            dbConnect connector = new dbConnect();
            int userId = 0;
            String uname2 = null;

            String u = PID.getText().trim();
            String mn = Mname.getText().trim();
            String p = Price.getText().trim();
            String s = status.getSelectedItem().toString().trim();

            if (p.isEmpty() || mn.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill All Boxes");

            }else if (!p.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Price must be a valid number (e.g., 12 or 12.99)");
            } else if (updateCheck()) {
                System.out.println("Duplicate Exists");
            } else {
                try {
                    System.out.println("1");
                    String query = "SELECT * FROM tbl_products WHERE p_id='" + u + "'";
                    ResultSet rs = dbc.getData(query);
                    if (rs.next()) {
                        System.out.println("2");

                        dbc.updateData("UPDATE tbl_products SET p_name = '" + mn + "', p_price = '" + p + "', p_status = '" + s + "', p_image = '" + destination + "' WHERE p_id = '" + u + "'");

                        try {
                            System.out.println("3");
                            String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                            PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                            ResultSet resultSet = pstmt.executeQuery();

                            if (resultSet.next()) {
                                System.out.println("4");
                                userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                                uname2 = resultSet.getString("u_username");
                            }
                        } catch (SQLException ex) {
                            System.out.println("5");
                            System.out.println("SQL Exception: " + ex);

                        }

                        logEvent(userId, uname2, "Admin Updated The Movie: " + mn);

                        if (destination.isEmpty()) {
                            System.out.println("6");
                            if (oldpath != null) {
                                File existingFile = new File(oldpath);
                                if (existingFile.exists()) {
                                    existingFile.delete();
                                }
                            } else {
                                System.out.println("⚠️ Warning: oldpath is null, cannot delete.");
                            }
                        } else {
                            if (!(oldpath.equals(path))) {
                                imageUpdater(oldpath, path);
                            }
                        }

                        NotShowDeletedUsers();
//                        PID.setText("");
//                        Mname.setText("");
//                        Price.setText("");
//                        status.setSelectedItem(0);
                    }
                } catch (SQLException ex) {
                    System.out.println("7");
                    System.out.println("" + ex);
                }
            }
        }
    }//GEN-LAST:event_add3MouseClicked

    private void add3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add3MouseEntered
        add3.setBackground(h);
    }//GEN-LAST:event_add3MouseEntered

    private void add3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add3MouseExited
        add3.setBackground(d);
    }//GEN-LAST:event_add3MouseExited

    private void add4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add4MouseClicked
        deleteUser();
    }//GEN-LAST:event_add4MouseClicked

    private void add4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add4MouseEntered
        add4.setBackground(h);
    }//GEN-LAST:event_add4MouseEntered

    private void add4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add4MouseExited
        add4.setBackground(d);
    }//GEN-LAST:event_add4MouseExited

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
            java.util.logging.Logger.getLogger(productForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(productForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(productForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(productForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new productForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    public javax.swing.JTextField Mname;
    private javax.swing.JPanel Navigation;
    public javax.swing.JTextField PID;
    public javax.swing.JTextField Price;
    public javax.swing.JPanel Remove;
    public javax.swing.JPanel Select;
    private javax.swing.JTable account_table;
    private javax.swing.JLabel ad;
    private javax.swing.JLabel ad1;
    private javax.swing.JLabel ad2;
    private javax.swing.JPanel add;
    private javax.swing.JPanel add2;
    private javax.swing.JPanel add3;
    private javax.swing.JPanel add4;
    public javax.swing.JLabel image;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel logout;
    public javax.swing.JComboBox<String> status;
    // End of variables declaration//GEN-END:variables
}
