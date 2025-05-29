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
public class ConfirmOrdersForm extends javax.swing.JFrame {

     private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    
    
    private void updateChange() {
        try {
            String payment = Payment.getText().trim();
            String priceText = Price.getText().trim();

            System.out.println("[DEBUG] Payment entered: " + payment);
            System.out.println("[DEBUG] Price value: " + priceText);

            if (payment.isEmpty()) {
                Change.setText("0");
                System.out.println("[DEBUG] Empty payment. Change set to 0.");
                return;
            }

            if (!payment.matches("\\d+(\\.\\d+)?")) {
                Change.setText("Payment must be a number");
                System.out.println("[DEBUG] Invalid payment input.");
                return;
            }

            int py = Integer.parseInt(payment);
            int pr = Integer.parseInt(priceText.isEmpty() ? "0" : priceText);
            int ch = py - pr;

            if (py >= pr) {
                Change.setText(String.valueOf(ch));
                System.out.println("[DEBUG] Change: " + ch);
            } else {
                Change.setText("Insufficient Cash");
                System.out.println("[DEBUG] Payment < Price. Not enough cash.");
            }

        } catch (NumberFormatException e) {
            Change.setText("0");
            System.out.println("[ERROR] Invalid number format: " + e.getMessage());
        }
    }
    
    public ConfirmOrdersForm() {
        initComponents();
        NotShowDeletedUsers();
//    displayData();

        // --- LISTENER: Qnty field updates Price based on DB ---
        
        Qnty.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalAndChange();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalAndChange();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTotalAndChange();
            }

            private void updateTotalAndChange() {
                try {
                    dbConnect dbc = new dbConnect();
                    ResultSet rs = dbc.getData("SELECT o_total FROM tbl_temporary_orders");

                    int grandTotal = 0;
                    while (rs.next()) {
                        grandTotal += rs.getInt("o_total");
                    }

                    Price.setText(String.valueOf(grandTotal));
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("SQL Error: " + e.getMessage());
                    Price.setText("0");
                }

                updateChange(); // Recalculate change
            }
        });

// --- LISTENER: Payment field updates Change ---
        Payment.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateChange();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateChange();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateChange();
            }

            
        });



    }
    
    
    
    
    
    private void deleteUser() {
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
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
                String query2 = "SELECT * FROM tbl_temporary_orders WHERE to_id = ?";
                PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
                pstmt.setInt(1, productId);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String pid = rs.getString("p_id");
//                    String pn = rs.getString("p_name");

                    // Actually delete the product
//                    String deleteQuery = "DELETE FROM tbl_products WHERE p_id = ?";
//                    PreparedStatement deleteStmt = connector.getConnection().prepareStatement(deleteQuery);
//                    deleteStmt.setString(1, pid);
//                    deleteStmt.executeUpdate();

                    // Optionally, also remove from tbl_temporary_orders if needed:
                     String deleteOrderQuery = "DELETE FROM tbl_temporary_orders WHERE to_id = ?";
                     PreparedStatement deleteOrderStmt = connector.getConnection().prepareStatement(deleteOrderQuery);
                     deleteOrderStmt.setInt(1, productId);
                     deleteOrderStmt.executeUpdate();
//                    try {
//                        String query = "SELECT * FROM tbl_accounts WHERE u_id = ?";
//                        PreparedStatement pstmt2 = connector.getConnection().prepareStatement(query);
//                        pstmt2.setInt(1, sess.getUid());
//                        ResultSet rs2 = pstmt2.executeQuery();
//
//                        if (rs2.next()) {
//                            userId = rs2.getInt("u_id");
//                            String uname2 = rs2.getString("u_username");
//                            logEvent(userId, uname2, "Admin Deleted Product: " + pn);
//                            loadUsersData();
//                        }
//                    } catch (SQLException ex) {
//                        System.out.println("Error logging event: " + ex);
//                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQL Exception: " + ex);
            }
        }
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
            ResultSet rs = dbc.getData("SELECT * FROM tbl_temporary_orders");

            while (rs.next()) {
                // Store each column value in a separate variable
                int to_u = rs.getInt("to_id");
                int u = rs.getInt("u_id");
                String pid = rs.getString("p_id");
                String q = rs.getString("quantity");
                String date = rs.getString("date");
                String total = rs.getString("o_total");
                
                
                
              
                

                // Check if the user status is not "Deleted"
//                if (!status.equals("Deleted")) {
                    
                    // Add the row to the list
                    rowData.add(new Object[]{
                        to_u,
                        u,
                        pid,
                        q, 
                        date,
                        total
                    });
                    
                    
                    
                    
                    
                    /*System.out.println("\n==========");
                    System.out.println(""+u);
                    System.out.println(""+fn);
                    System.out.println(""+ln);
                    System.out.println(""+uname);
                    System.out.println(""+at);
                    System.out.println(""+p);
                    System.out.println(""+status);*/
//                }
            }

            // After processing all rows, update the table on the Swing event dispatch thread
            SwingUtilities.invokeLater(() -> {
                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"Order ID","User ID","Product ID", "Quantity", "Date", "Total"}, 0
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

        String sql = "SELECT * FROM tbl_temporary_orders";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_db", "root", "");
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) 
        {

            while (rs.next()) 
            {
                // Check if the user's status is not "Deleted"
//                String userStatus = rs.getString("p_status");
//                if (!"Deleted".equals(userStatus)) 
//                {
                    model.addRow(new Object[]
                    {
                        rs.getInt("to_id"),
                        rs.getInt("u_id"),
                        rs.getString("p_id"),
                        rs.getString("quantity"),
                        rs.getString("date"),
                        rs.getString("o_total"),
                        
                        





                    });
//                }
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
    
    
    
    
    
    
//    private void deleteUser() {
//        dbConnect dbc = new dbConnect();
//        Session sess = Session.getInstance();
//        dbConnect connector = new dbConnect();
////        int userId = 0;
//        String uname3 = null;
//        String uname2 = null;
//        String uname = null;
//        int userId = 0;
//
//        int selectedRow = account_table.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
//            return;
//        }
//
//        int productId = Integer.parseInt(account_table.getValueAt(selectedRow, 0).toString());
//        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
//
//        if (confirm == JOptionPane.YES_OPTION) {
//
//            try {
//                String query2 = "SELECT * FROM tbl_products WHERE p_id = '" + productId + "'";
//                PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
//
//                ResultSet rs = pstmt.executeQuery();
//
//                if (rs.next()) {
//                    String pid = rs.getString("p_id");
//                    String pn = rs.getString("p_name");
//                    String pr = rs.getString("p_price");
//                    String s = "Deleted";
//
//                    dbc.updateData("UPDATE tbl_products SET p_name = '" + pn + "', p_price = '" + pr + "', p_status = '" + s + "' WHERE p_id = '" + pid + "'");
//
//                    try {
//                        System.out.println("1");
//                        String query = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
//                        PreparedStatement pstmt2 = connector.getConnection().prepareStatement(query);
//
//                        ResultSet rs2 = pstmt2.executeQuery();
//
//                        if (rs2.next()) {
//                            System.out.println("2");
//                            userId = rs2.getInt("u_id");
//                            uname2 = rs2.getString("u_username");
//                            loadUsersData();
//                        }
//                        logEvent(userId, uname2, "Admin Deleted Account: " + uname2);
//
//                    } catch (SQLException ex) {
//                        System.out.println("" + ex);
//                    }
//                }
//            } catch (SQLException ex) {
//                System.out.println("SQL Exception: " + ex);
//            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        account_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel6 = new javax.swing.JLabel();
        Price = new javax.swing.JTextField();
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
        jLabel9 = new javax.swing.JLabel();
        Payment = new javax.swing.JTextField();
        add1 = new javax.swing.JPanel();
        con1 = new javax.swing.JLabel();
        add2 = new javax.swing.JPanel();
        con2 = new javax.swing.JLabel();
        OID = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        Change = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

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
        jLabel1.setText("Confirm Orders Form");
        Header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1310, 40));

        Main.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1320, 100));

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

        Main.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 810, 540));

        jScrollPane2.setViewportView(jEditorPane1);

        Main.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Price to Pay:");
        Main.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 100, 30));

        Price.setEditable(false);
        Price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceActionPerformed(evt);
            }
        });
        Main.add(Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, 330, 30));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Flavor");
        Main.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 110, 30));

        Mname.setEditable(false);
        Mname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Mname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnameActionPerformed(evt);
            }
        });
        Main.add(Mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 330, 30));

        PID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PID.setEnabled(false);
        PID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIDActionPerformed(evt);
            }
        });
        Main.add(PID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 330, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Product ID:");
        Main.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 90, 30));

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
        con.setText("Update");
        add.add(con);
        con.setBounds(10, 10, 130, 22);

        Main.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 600, 150, 40));

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

        Main.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, -1, 40));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Quanity:");
        Main.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 80, 30));

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
        Main.add(Qnty, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 330, 30));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Enter Payment:");
        Main.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 120, 30));

        Payment.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentActionPerformed(evt);
            }
        });
        Main.add(Payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, 330, 30));

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

        Main.add(add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 600, 150, 40));

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

        con2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        con2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        con2.setText("Remove");
        add2.add(con2);
        con2.setBounds(10, 10, 130, 22);

        Main.add(add2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 150, 40));

        OID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        OID.setEnabled(false);
        OID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OIDActionPerformed(evt);
            }
        });
        Main.add(OID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 330, 30));

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Order ID:");
        Main.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 90, 30));

        Change.setEditable(false);
        Change.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeActionPerformed(evt);
            }
        });
        Main.add(Change, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 500, 330, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Change:");
        Main.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 100, 30));

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
//        EmployeeDashboard ed = new EmployeeDashboard();
//        ed.setVisible(true);
//        this.dispose();

        OrderForm of = new OrderForm();
        of.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseEntered
        logout.setBackground(h);
    }//GEN-LAST:event_logoutMouseEntered

    private void logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseExited
        logout.setBackground(d);
    }//GEN-LAST:event_logoutMouseExited

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked

//        int rowIndex = account_table.getSelectedRow();
//        int to_id_selected = Integer.parseInt(account_table.getValueAt(rowIndex, 0).toString()); // 0 = to_id column
        int rowIndex = account_table.getSelectedRow();
        TableModel tbl = account_table.getModel();


//        System.out.println("[DEBUG] Selected row index: " + tbl.getValueAt(rowIndex, 0));

        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();

        int to_id = 0;
        int u_id = 0;
        int p_id = 0;
        int quantity = 0;
        double o_total = 0;
        double new_o_total = 0;
        double p_price = 0;

        String mn = Mname.getText().trim();
        String pid = PID.getText().trim();
        int q = Integer.parseInt(Qnty.getText().trim());

//        System.out.println("[DEBUG] Input - Mname: " + mn);
//        System.out.println("[DEBUG] Input - PID: " + pid);
//        System.out.println("[DEBUG] Input - Quantity: " + q);

        if (mn.isEmpty() || pid.isEmpty() || Qnty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");
//            System.out.println("[DEBUG] Validation failed - one or more fields are empty");
        } else if (!Qnty.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Quantity Must Only Contain Numbers");
//            System.out.println("[DEBUG] Validation failed - quantity not numeric");
        } else {
            try {
                String query2 = "SELECT * FROM tbl_temporary_orders WHERE to_id = '" + tbl.getValueAt(rowIndex, 0) + "'";
//                String query2 = "SELECT * FROM tbl_temporary_orders WHERE to_id = '" + to_id_selected + "'";

//                System.out.println("[DEBUG] Executing query: " + query2);

                PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    to_id = resultSet.getInt("to_id");
                    u_id = resultSet.getInt("u_id");
                    p_id = resultSet.getInt("p_id");
                    quantity = resultSet.getInt("quantity");
                    o_total = resultSet.getDouble("o_total");

//                    System.out.println("[DEBUG] Fetched from tbl_temporary_orders:");
//                    System.out.println("        to_id: " + to_id);
//                    System.out.println("        u_id: " + u_id);
//                    System.out.println("        p_id: " + p_id);
//                    System.out.println("        quantity: " + quantity);
//                    System.out.println("        o_total: " + o_total);
                } else {
                    System.out.println("[DEBUG] No row found in tbl_temporary_orders for to_id: " + tbl.getValueAt(rowIndex, 0));
                }
            } catch (SQLException ex) {
                System.out.println("[SQL ERROR] " + ex);
            }

            try {
                String query2 = "SELECT * FROM tbl_products WHERE p_id = '" + p_id + "'";
//                System.out.println("[DEBUG] Executing query: " + query2);

                PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    p_price = resultSet.getDouble("p_price");
                    new_o_total = p_price * q;

//                    System.out.println("[DEBUG] Fetched from tbl_products:");
//                    System.out.println("        p_price: " + p_price);
//                    System.out.println("        Calculated new_o_total: " + new_o_total);
                } else {
                    System.out.println("[DEBUG] No product found for p_id: " + p_id);
                }
            } catch (SQLException ex) {
                System.out.println("[SQL ERROR] " + ex);
            }

//            System.out.println("[DEBUG] Running update query...");
            dbc.updateData("UPDATE tbl_temporary_orders SET quantity = '" + q + "', o_total = '" + new_o_total + "' WHERE to_id = '" + to_id + "'");
//            System.out.println("quantity: "+quantity);
//            System.out.println("[DEBUG] Update query executed");

            loadUsersData();
            PID.setText("");
            Mname.setText("");
            Qnty.setText("");
            OID.setText("");
//            System.out.println("[DEBUG] Fields cleared and data reloaded");
        }

    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
        add.setBackground(h);
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
        add.setBackground(d);
    }//GEN-LAST:event_addMouseExited

    private void PriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceActionPerformed
        // TODO add your handling code here:THanks
    }//GEN-LAST:event_PriceActionPerformed

    private void MnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MnameActionPerformed

    private void PIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PIDActionPerformed

    private void account_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_account_tableMouseClicked
//        int rowIndex = account_table.getSelectedRow();
//        int to_id_selected = Integer.parseInt(account_table.getValueAt(rowIndex, 0).toString()); // 0 = to_id column
           int rowIndex = account_table.getSelectedRow();
           TableModel tbl = account_table.getModel();


        
        OID.setText(""+tbl.getValueAt(rowIndex, 0));
        System.out.println("[DEBUG] Selected rowIndex: " + rowIndex);

        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select an Item");
            System.out.println("[DEBUG] No row selected. Exiting.");
        } else {
            
            
            
            
            
            try {
                dbConnect dbc = new dbConnect();
//                TableModel tbl = account_table.getModel();
                Object selectedID = tbl.getValueAt(rowIndex, 0);
//                System.out.println("[DEBUG] Selected Product ID: " + selectedID);

                ResultSet rs = dbc.getData("SELECT * FROM tbl_temporary_orders WHERE to_id = '" + tbl.getValueAt(rowIndex, 0) + "'");
                if (rs.next()) {
                    PID.setText(rs.getString("p_id"));

                    /*addClickable = false;
                    ad.setForeground(red);*/
//                    System.out.println("[DEBUG] Product details loaded into fields.");
                } else {
                    System.out.println("1");
                }
            } catch (SQLException e) {
                System.out.println("[ERROR] SQL Exception: " + e);
            }
            
            
            
            
            
            
            
            
            
            
            try {
                dbConnect dbc = new dbConnect();
//                TableModel tbl = account_table.getModel();
                Object selectedID = tbl.getValueAt(rowIndex, 0);
                System.out.println("[DEBUG] Selected Product ID: " + selectedID);

//                ResultSet rs = dbc.getData("SELECT * FROM tbl_temporary_orders WHERE to_id = '" + to_id_selected + "'  ");
                ResultSet rs = dbc.getData("SELECT * FROM tbl_products WHERE p_id = '" + selectedID + "'");
//                String query2 = "SELECT * FROM tbl_temporary_orders WHERE to_id = '" + to_id_selected + "'";
                if (rs.next()) {
                    PID.setText(rs.getString("p_id"));
                    Mname.setText(rs.getString("p_name"));

                    /*addClickable = false;
                    ad.setForeground(red);*/

                    System.out.println("[DEBUG] Product details loaded into fields.");
                } else {
                    System.out.println("[DEBUG] No product found for selected ID.");
                }
            } catch (SQLException e) {
                System.out.println("[ERROR] SQL Exception: " + e);
            }
            
            
            
            
            
            
            try 
            {
                dbConnect dbc = new dbConnect();
//                TableModel tbl = account_table.getModel();
                Object selectedID = tbl.getValueAt(rowIndex, 0);
//                System.out.println("[DEBUG] Selected Product ID: " + selectedID);

                ResultSet rs = dbc.getData("SELECT * FROM tbl_temporary_orders WHERE to_id = '" + tbl.getValueAt(rowIndex, 0) + "'");
                if (rs.next()) {
                    Qnty.setText(rs.getString("quantity"));

                    /*addClickable = false;
                    ad.setForeground(red);*/
//                    System.out.println("[DEBUG] Product details loaded into fields.");
                } else {
                    System.out.println("1");
                }
            } catch (SQLException e) 
            {
                System.out.println("[ERROR] SQL Exception: " + e);
            }
        }
    }//GEN-LAST:event_account_tableMouseClicked

    private void QntyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QntyActionPerformed
        

    }//GEN-LAST:event_QntyActionPerformed

    private void PaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PaymentActionPerformed

    private void QntyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QntyMouseClicked
        String qtyText = Qnty.getText().trim();
        int q = Integer.parseInt(qtyText);
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
                Price.setText("" + total);
//                addClickable = false;
//                con.setForeground(red);
            }
    
        }catch (SQLException | NumberFormatException e) 
    {
        System.out.println("" + e);
    }
    }//GEN-LAST:event_QntyMouseClicked

    private void add1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_add1MouseExited

    private void add1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_add1MouseEntered

    private void add1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add1MouseClicked
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
        int userId = 0;
        int d_qnty = 0;
        int sold_qnty = 0;
        int minusQnty = 0;
        int plusQnty = 0;
        String uname2 = null;
        String mn = Mname.getText().trim();
        String pr = Price.getText().trim();
        String pid = PID.getText().trim();
//        int q = Integer.parseInt(Qnty.getText().trim());
        Timestamp time = new Timestamp(new java.util.Date().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new java.util.Date());
        String py = Payment.getText().trim();
//        int price = Integer.parseInt(Price.getText().trim());
//        int payment = Integer.parseInt(Payment.getText().trim());
        ReceiptForm rf = new ReceiptForm();

        String qntyText = Qnty.getText().trim();
        String priceText = Price.getText().trim();
        String paymentText = Payment.getText().trim();
        boolean error = false;

        if (mn.isEmpty() || pr.isEmpty() || qntyText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Fill All Boxes");
        } else if (!qntyText.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Quantity Must Only Contain Numbers");
        } else if (!paymentText.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Payment Must Only Contain Numbers");
        } else 
        {
            int q = Integer.parseInt(qntyText);
            int price = Integer.parseInt(priceText);
            int payment = Integer.parseInt(paymentText);

            if (price > payment) 
            {
                JOptionPane.showMessageDialog(null, "Insufficient Cash");
            } else 
            {
                System.out.println("1");
                try 
                {
                    System.out.println("2");
    //                dbConnect dbc = new dbConnect();
                    Connection conn = dbc.getConnection();
                    String query = "SELECT * FROM tbl_temporary_orders";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    System.out.println("3");

                    
                    // === Generate Receipt ===
                    System.out.println("Test start");
                    rf.area.setText("*********************************************\n");
                    rf.area.setText(rf.area.getText() + "*              Theather's Receipt System         *\n");
                    
                    while (rs.next()) 
                    {
                        
                        System.out.println("4");
                        // Extract values from each row
                        int to_id = rs.getInt("to_id");
                        int u_id = rs.getInt("u_id");
                        int p_id = rs.getInt("p_id");
                        int quantity = rs.getInt("quantity");
    //                    Timestamp date = rs.getTimestamp("date");
                        double o_total = rs.getDouble("o_total");

                        // Example usage: print each variable
//                        System.out.println("Order ID (to_id): " + to_id);
//                        System.out.println("User ID (u_id): " + u_id);
//                        System.out.println("Product ID (p_id): " + p_id);
//                        System.out.println("Quantity: " + quantity);
//                        System.out.println("Date: " + date);
//                        System.out.println("Order Total: " + o_total);
//                        System.out.println("-----");
                        
                        
                        if(dbc.insertData("INSERT INTO tbl_orders (u_id, p_id, quantity, date, status, o_total) "
                        + "VALUES ('" + u_id + "', '" + p_id + "','" + quantity + "', '" + date + "',  'Successful',  '" + o_total + "')"))
                        {
                            rf.area.setText(rf.area.getText() + "*********************************************\n");

                            Date obj = new Date();
                            String now = obj.toString();
                            rf.area.setText(rf.area.getText() + "\nDate: " + now + "\n");
                            rf.area.setText(rf.area.getText() + "---------------------------------------------\n");

                            rf.area.setText(rf.area.getText() + "Transaction ID   : " + mn + "\n");
                            rf.area.setText(rf.area.getText() + "Product ID       : " + pid + "\n");
                            rf.area.setText(rf.area.getText() + "Quantity         : " + q + "\n");
                            rf.area.setText(rf.area.getText() + "Price (each)     : " + (price / q) + "\n"); // single item price
                            rf.area.setText(rf.area.getText() + "Total Price      : " + price + "\n");
                            rf.area.setText(rf.area.getText() + "Amount Paid      : " + payment + "\n");
                            rf.area.setText(rf.area.getText() + "Change           : " + (payment - price) + "\n");

                            rf.area.setText(rf.area.getText() + "---------------------------------------------\n");
                            
                            
                        }else
                        {
                            System.out.println("Something went wrong");
                            EmployeeDashboard ed = new EmployeeDashboard();
                            ed.setVisible(true);
                            this.dispose();
                            error = true;
                        }
                        
                        if(!error)
                        {
                            rf.setVisible(true);
                            this.dispose();
                        }else
                        {
                            System.out.println("Something went wrong 2");
                            EmployeeDashboard ed = new EmployeeDashboard();
                            ed.setVisible(true);
                            this.dispose();
                            error = true;
                        }
                    }
                    rf.area.setText(rf.area.getText() + "Status           : Successful\n");
                    rf.area.setText(rf.area.getText() + "Handled by       : " + uname2 + "\n");
                    rf.area.setText(rf.area.getText() + "*********************************************\n");
                    rf.area.setText(rf.area.getText() + "*         THANK YOU FOR YOUR PURCHASE!      *\n");
                    rf.area.setText(rf.area.getText() + "*********************************************\n");
                    System.out.println("Test end");
                    
                    
                    try {
                        String sql = "TRUNCATE TABLE tbl_temporary_orders";
                        PreparedStatement truncateStmt = conn.prepareStatement(sql);
                        truncateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Table cleared successfully!");
                        System.out.println("[INFO] tbl_temporary_orders has been truncated.");

                        truncateStmt.close(); // Don't close the original stmt prematurely
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Failed to truncate table: " + e.getMessage());
                        System.out.println("[ERROR] " + e.getMessage());
                    }                  
                } catch (SQLException e) 
                {
                    System.out.println("5");
                    System.out.println("[ERROR] " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_add1MouseClicked

    private void add2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add2MouseClicked
        deleteUser();
        OrderForm of = new OrderForm();
        of.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_add2MouseClicked

    private void add2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_add2MouseEntered

    private void add2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_add2MouseExited

    private void OIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OIDActionPerformed

    private void ChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChangeActionPerformed

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
            java.util.logging.Logger.getLogger(ConfirmOrdersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfirmOrdersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfirmOrdersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfirmOrdersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ConfirmOrdersForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField Change;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    public javax.swing.JTextField Mname;
    public javax.swing.JTextField OID;
    public javax.swing.JTextField PID;
    public javax.swing.JTextField Payment;
    public javax.swing.JTextField Price;
    public javax.swing.JTextField Qnty;
    private javax.swing.JTable account_table;
    private javax.swing.JPanel add;
    private javax.swing.JPanel add1;
    private javax.swing.JPanel add2;
    private javax.swing.JLabel con;
    private javax.swing.JLabel con1;
    private javax.swing.JLabel con2;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel logout;
    // End of variables declaration//GEN-END:variables
}
