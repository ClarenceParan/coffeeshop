
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnect 
{
    private Connection connect;

    
    
    
    //=========================================
    // CONSTRUCTOR TO CONNECT TO OUR DATABASE
    //=========================================
    public dbConnect()
    {
        try
        {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_db", "root", "");
        }catch(SQLException ex)
        {
            System.out.println("Can't connect to database: "+ex.getMessage());
        }
    }


    
    
    
    //=========================================
    //          FUNCTION TO SAVE DATA
    //=========================================

    public boolean insertData(String sql)
    {
        try
        {
            PreparedStatement pst = connect.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Inserted Successfully!");
            pst.close();
            return true;
        }catch(SQLException ex)
        {
            System.out.println("Connection Error: "+ex);
            return false;
        }
    }
    
    
    
    
    
    
    
    //Function to retrieve data
    public ResultSet getData(String sql) throws SQLException
    {
        Statement stmt = connect.createStatement();
        ResultSet rst = stmt.executeQuery(sql);
        return rst;
    }
}
