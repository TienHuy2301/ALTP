/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FromMain;

import Database.ConnectDB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author huyph
 */
public class admin {
    public ConnectDB cn= new ConnectDB();
    
    public ResultSet Showadmin() throws SQLException{
        cn.connectSQL();
        String sql="Select * from Admin";
        return cn.LoadData(sql);
    }
    
}
