/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author huyph
 */
public class ConnectDB {
    //String connectionString = "jdbc:sqlserver://DESKTOP-JH196N3:1433;" 
     //       + "databaseName=Millionaire;user=sa;password=sa;";
    public static Connection con= null;

    public static Connection getJDBCConnection() {
        String user = "sa";
        String password = "sa";
        String serverName = "DESKTOP-JH196N3";
        String dbName = "ALTP";
        String url = "jdbc:sqlserver://" + serverName + ":1433;DatabaseName=" + dbName + ";user=" + user + ";password=" + password + ";encrypt=true;trustServerCertificate=true";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public void connectSQL() throws SQLException{
        try{
            String userName ="sa";
            String passWord="sa";
            String url="jdbc:sqlserver://DESKTOP-JH196N3:1433;databaseName=ALTP;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=java.sql.DriverManager.getConnection(url,userName,passWord);
             if(con != null){
                System.out.println("Kết nối CSDL SQL Server thành công!");
            }
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"Kết nối CSDL thất bại","Thông báo", 1);
        }
    } 
    
    public ResultSet LoadData(String sql){
     ResultSet result = null;
        try {
            Statement statement = con.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static ResultSet resultSet(String sql) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = query().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = statement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
     public void UpdateData(String sql){        
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }   
    public static Connection query() {
        return con;
    }
  
}
