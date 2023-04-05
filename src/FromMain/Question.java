/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FromMain;

import Database.ConnectDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author zduyt
 */
public class Question {
public ConnectDB cn= new ConnectDB();
     public ResultSet ShowAll() throws SQLException{
           cn.connectSQL();
           String sql = "SELECT * FROM Question";        
           return cn.LoadData(sql);
    }
    public ResultSet ShowQuestion() throws SQLException{
        cn.connectSQL();
        String sql=" SELECT TOP 1 * FROM Question ORDER BY NEWID()";
        return cn.LoadData(sql);
    }
    public ResultSet ShowAnswer() throws SQLException{
        cn.connectSQL();
        String sql="SELECT Answer FROM Question";
        return cn.LoadData(sql);
    }
    public ResultSet RandomMaQuestion() throws SQLException{
        cn.connectSQL();
        String sql="SELECT TOP 1[MaQT] FROM Question a ORDER BY NEWID()";
        return cn.LoadData(sql);
    }
    public ResultSet ShowQuestbyRandomMaQuestion(int maqt) throws SQLException{
        cn.connectSQL();
        String sql="Select * from Question where MaQT='"+maqt+"'";
        return cn.LoadData(sql);
    }
    /***************************************************************************************/
    /* public void InsertData(String ques, String a, String b,String c, String d, String da) throws SQLException{   
           String sql = "INSERT INTO Question values(N'"+ques+"',N'" + a +"',N'" + b +"',N'"+c+"',N'"+d+"',N'"+da+"')"; 
           cn.UpdateData(sql);
        }
        //Dieu chinh 1 dong du lieu vao table LoaiSP
        public void EditData(int ma,String ques, String a, String b,String c, String d, String da) throws SQLException{   
           String sql = "Update Question set Question=N'" + ques +"',AnswerA=N'"+a+"',AnswerB=N'"+b+"'AnswerC=N'"+c+"',AnswerD=N'"+c+"',Answer=N'"+da+"' where MaQT='" + ma +"'";        
           cn.UpdateData(sql);
        }
        //Xoa 1 dong du lieu vao table LoaiSP
        public void DeleteData(int ma) throws SQLException{   
           String sql = "Delete from Question where MaQT='" + ma +"'";        
           cn.UpdateData(sql);
        }
        public MediaPlayer loadMusic(String status) {
        try {
            String source = getClass().getResource("/Audio/" + status + ".mp3").toString();
            Media media = null;
            media = new Media(source);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            return mediaPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

   
    
}

