/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CauHoi;

import Database.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huyph
 */
public class CauHoiDao {
    
    Connection connection = ConnectDB.getJDBCConnection();
    
    public List<Cau_Hoi> getAllquestion(){
        List<Cau_Hoi> listCH = new ArrayList<>();

        String sql = "Select * From Question";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            
            while(rs.next()){
                Cau_Hoi nv = new Cau_Hoi();
                
                nv.setMaQT(rs.getString("MaQT"));
                nv.setQuestion(rs.getString("Question"));
                nv.setAnswerA(rs.getString("AnswerA"));
                nv.setAnswerB(rs.getString("AnswerB"));
                nv.setAnswerC(rs.getString("AnswerC"));
                nv.setAnswerD(rs.getString("AnswerD"));
                nv.setAnswer(rs.getString("Answer"));
                listCH.add(nv);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCH;
    }
    
    public void Addquestion(Cau_Hoi nv){
        
        String sql = "INSERT INTO Question(MaQT,Question, AnswerA, AnswerB, AnswerC, AnswerD, Answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nv.getMaQT());
            preparedStatement.setString(2, nv.getQuestion());
            preparedStatement.setString(3, nv.getAnswerA());
            preparedStatement.setString(4, nv.getAnswerB());
            preparedStatement.setString(5, nv.getAnswerC());
            preparedStatement.setString(6, nv.getAnswerD());
            preparedStatement.setString(7, nv.getAnswer());
            
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void Updatequestion(Cau_Hoi nv){
        String sql = "UPDATE Question SET Question = ?, AnswerA = ?, AnswerB = ?, AnswerC = ?, AnswerD = ?, Answer = ? WHERE MaQT = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, nv.getQuestion());
            preparedStatement.setString(2, nv.getAnswerA());
            preparedStatement.setString(3, nv.getAnswerB());
            preparedStatement.setString(4, nv.getAnswerC());
            preparedStatement.setString(5, nv.getAnswerD());
            preparedStatement.setString(6, nv.getAnswer());
            preparedStatement.setString(7, nv.getMaQT());
            
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Deletequestion(String Maqt){
        String sql = "DELETE FROM Question WHERE MaQT = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Maqt);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
