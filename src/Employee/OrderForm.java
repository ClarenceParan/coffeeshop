/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import Startups.Coffeeshop;
import admin.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class OrderForm extends javax.swing.JFrame {

     private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    
    public OrderForm() {
        initComponents();
        NotShowDeletedUsers();
//    displayData();



    }
    
    
    
    
    
    
    
    
//    boolean addClickable  = true;

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
                        rs.getInt("u_id"),
                        rs.getString("p_name"),
                        rs.getString("p_price"),
                        rs.getString("p_status"),
                        rs.getString("p_quantity"),
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

                    } catch (SQLException ex) {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        account_table = new javax.swing.JTable();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel7 = new javax.swing.JLabel();
        Mname = new javax.swing.JTextField();
        PID = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        con = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Qnty = new javax.swing.JTextField();
        add1 = new javax.swing.JPanel();
        con1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        Main.setBackground(new java.awt.Color(153, 137, 118));
        Main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        Main.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 850, 540));

        Header.setBackground(new java.awt.Color(0, 0, 0));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 255, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Orders Form");
        Header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1310, 40));

        Main.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 100));

        jScrollPane2.setViewportView(jEditorPane1);

        Main.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Flavor");
        Main.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 220, 110, 30));

        Mname.setEditable(false);
        Mname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Mname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnameActionPerformed(evt);
            }
        });
        Main.add(Mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 220, 330, 30));

        PID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PID.setEnabled(false);
        PID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIDActionPerformed(evt);
            }
        });
        Main.add(PID, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 170, 330, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Product ID:");
        Main.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 170, 90, 30));

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

        con.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        con.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        con.setText("CONFIRM");
        add.add(con);
        con.setBounds(10, 10, 130, 22);

        Main.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 600, 150, 40));

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
        jLabel10.setText("Cancel");
        logout.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 130, -1));

        Main.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 600, -1, 40));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Quanity:");
        Main.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 270, 80, 30));

        Qnty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Qnty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                QntyMouseClicked(evt);
            }
        });
        Qnty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QntyActionPerformed(evt);
            }
        });
        Main.add(Qnty, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 270, 330, 30));

        add1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add1MouseExited(evt);
            }
        });
        add1.setLayout(null);

        con1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        con1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        con1.setText("Pay");
        add1.add(con1);
        con1.setBounds(10, 10, 130, 22);

        Main.add(add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 600, 150, 40));

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
        
        try {
            dbConnect dbc = new dbConnect(); // Assuming this class gives you the DB connection
            Connection conn = dbc.getConnection();

            String sql = "DELETE FROM tbl_temporary_orders"; // Change 'tbl_orders' to your table name
            PreparedStatement stmt = conn.prepareStatement(sql);

            int rowsAffected = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted " + rowsAffected + " rows from tbl_orders.");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }

        
        ed.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseEntered
        logout.setBackground(h);
    }//GEN-LAST:event_logoutMouseEntered

    private void logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseExited
        logout.setBackground(d);
    }//GEN-LAST:event_logoutMouseExited

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
       
            dbConnect dbc = new dbConnect();
            Session sess = Session.getInstance();
            dbConnect connector = new dbConnect();
            int userId = 0;
            double price = 0;
            int d_qnty = 0;
            double py = 0;
            int sold_qnty = 0;
            int minusQnty = 0;
            int plusQnty = 0;
            String uname2 = null;
            String mn = Mname.getText().trim();
            String pid = PID.getText().trim();
            String qnty = Qnty.getText().trim();
            Timestamp time = new Timestamp(new java.util.Date().getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new java.util.Date());



            if (mn.isEmpty() || qnty.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill All Boxes");

            } else if (!qnty.matches("\\d+")) 
            {
                JOptionPane.showMessageDialog(null, "Quantity Must Only Contain Numbers");
            }else {
//                try {
            int q = Integer.parseInt(Qnty.getText().trim());

                    
                    try {
                        String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                        PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) {
                            userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                            
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQL Exception: " + ex);
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    try 
                    {
                        String query2 = "SELECT * FROM tbl_products WHERE p_id = '" + pid + "'";
                        PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) 
                        {
                            price = resultSet.getInt("p_price");  
                            py = price * q;
                            

                        }
                    } catch (SQLException ex) 
                    {
                        System.out.println("SQL Exception: " + ex);
                    }
                    
                    
                    
                    
                    
                    
                    
                    if (dbc.insertData("INSERT INTO tbl_temporary_orders (u_id, p_id, quantity, date, o_total) " //change to insert orders table
                            + "VALUES ('" + userId + "', '" + pid + "', '" + q + "', '" + date + "', '" + py + "')")) {

                        
                        
//                        try {
//                            String query2 = "SELECT * FROM tbl_products WHERE p_id = '" + pid + "'";
//                            System.out.println("pid: " + pid);
//                            PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
//
//                            ResultSet resultSet = pstmt.executeQuery();
//
//                            if (resultSet.next()) {
////                                d_qnty = resultSet.getInt("p_quantity");
////                                minusQnty = d_qnty - q;
//
//                                sold_qnty = resultSet.getInt("p_sold");
//                                plusQnty = sold_qnty + q;
//
////                                dbc.updateData("UPDATE tbl_products SET p_quantity = '" + minusQnty + "', p_sold = '" + plusQnty + "' WHERE p_id = '" + pid + "'");
//                                dbc.updateData("UPDATE tbl_products SET p_sold = '" + plusQnty + "' WHERE p_id = '" + pid + "'");
////                                System.out.println("minusQnty: " + minusQnty + " pid:" + pid);
//                            }
//                        } catch (SQLException ex) {
//                            System.out.println("SQL Exception: " + ex);
//                        }
                        
                        
                        
                        
//                        try {
//                            String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
//                            PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
//
//                            ResultSet resultSet = pstmt.executeQuery();
//
//                            if (resultSet.next()) {
//                                userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
//                                uname2 = resultSet.getString("u_username");
//                            }
//                        } catch (SQLException ex) {
//                            System.out.println("SQL Exception: " + ex);
//                        }
//
//                        logEvent(userId, uname2, "User made transaction ID: " + mn);

                        JOptionPane.showMessageDialog(null, "Added succesfully!");
                        
                        PID.setText("");
                        Mname.setText("");
                        Qnty.setText("");
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "An error occured");
//                        System.out.println("Dan, Error occured in line: 710, OrderForm");
                        EmployeeDashboard ed = new EmployeeDashboard();
                        ed.setVisible(true);
                        this.dispose();
                    }
                    //                }
//                } catch (SQLException ex) {
//                    System.out.println("" + ex);
//                }
            }
        
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
        add.setBackground(h);
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
        add.setBackground(d);
    }//GEN-LAST:event_addMouseExited

    private void MnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MnameActionPerformed

    private void PIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PIDActionPerformed

    private void account_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_account_tableMouseClicked
        int rowIndex = account_table.getSelectedRow();
        System.out.println("[DEBUG] Selected rowIndex: " + rowIndex);

        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select an Item");
            System.out.println("[DEBUG] No row selected. Exiting.");
        } else {
            try {
                dbConnect dbc = new dbConnect();
                TableModel tbl = account_table.getModel();
                Object selectedID = tbl.getValueAt(rowIndex, 0);
                System.out.println("[DEBUG] Selected Product ID: " + selectedID);

                ResultSet rs = dbc.getData("SELECT * FROM tbl_products WHERE p_id = '" + selectedID + "'");
                if (rs.next()) {
                    PID.setText(rs.getString("p_id"));
                    Mname.setText(rs.getString("p_name"));

//                    addClickable = false;
//                    ad.setForeground(red);

                    System.out.println("[DEBUG] Product details loaded into fields.");
                } else {
                    System.out.println("[DEBUG] No product found for selected ID.");
                }
            } catch (SQLException e) {
                System.out.println("[ERROR] SQL Exception: " + e);
            }
        }
    }//GEN-LAST:event_account_tableMouseClicked

    private void QntyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QntyActionPerformed
        

    }//GEN-LAST:event_QntyActionPerformed

    private void QntyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QntyMouseClicked
        String qtyText = Qnty.getText().trim();
        int q = Integer.parseInt(Qnty.getText().trim());
//        int q = Integer.parseInt(qtyText);
//        int payment = Integer.parseInt(Payment.getText().trim());

        String pid = PID.getText();
        
        
        try
        {
            

            dbConnect dbc = new dbConnect();
            TableModel tbl = account_table.getModel();
            ResultSet rs = dbc.getData("SELECT * FROM tbl_products WHERE p_id = '" + pid + "'");
            if (rs.next()) 
            {
                int price = rs.getInt("p_price");
                int total = q * price;
                System.out.println("total: " + total);
//                addClickable = false;
//                con.setForeground(red);
            }
    
        }catch (SQLException | NumberFormatException e) 
    {
        System.out.println("" + e);
    }
    }//GEN-LAST:event_QntyMouseClicked

    private void add1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add1MouseClicked
        ConfirmOrdersForm cof = new ConfirmOrdersForm();
        cof.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_add1MouseClicked

    private void add1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_add1MouseEntered

    private void add1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_add1MouseExited

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
            java.util.logging.Logger.getLogger(OrderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new OrderForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    public javax.swing.JTextField Mname;
    public javax.swing.JTextField PID;
    public javax.swing.JTextField Qnty;
    private javax.swing.JTable account_table;
    private javax.swing.JPanel add;
    private javax.swing.JPanel add1;
    private javax.swing.JLabel con;
    private javax.swing.JLabel con1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel logout;
    // End of variables declaration//GEN-END:variables
}
