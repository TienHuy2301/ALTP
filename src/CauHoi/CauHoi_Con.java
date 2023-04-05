/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CauHoi;

import java.util.List;

/**
 *
 * @author huyph
 */
public class CauHoi_Con {
    private CauHoiDao question_DAO;
    
    public  CauHoi_Con() {
        question_DAO = new CauHoiDao();
    }
    
    public List<Cau_Hoi> getAllquestion(){
        return question_DAO.getAllquestion();
    }
    
    public void Addquestion(Cau_Hoi nv) {
        question_DAO.Addquestion(nv);
    }
    
    public void Updatequestion(Cau_Hoi nv){
        question_DAO.Updatequestion(nv);
    }
    
    public void Deletequestion(String maNV){
        question_DAO.Deletequestion(maNV);
    }
    
    
}
