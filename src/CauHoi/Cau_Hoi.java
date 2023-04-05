/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CauHoi;

/**
 *
 * @author huyph
 */
public class Cau_Hoi {
    private String MaQT;
    private String Question;
    private String AnswerA;
    private String AnswerB;
    private String AnswerC;
    private String AnswerD;
    private String Answer;

    public Cau_Hoi() {
    }

    public Cau_Hoi(String MaQT, String Question, String AnswerA, String AnswerB, String AnswerC, String AnswerD, String Answer) {
        this.MaQT = MaQT;
        this.Question = Question;
        this.AnswerA = AnswerA;
        this.AnswerB = AnswerB;
        this.AnswerC = AnswerC;
        this.AnswerD = AnswerD;
        this.Answer = Answer;
    }

    public String getMaQT() {
        return MaQT;
    }

    public void setMaQT(String MaQT) {
        this.MaQT = MaQT;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String AnswerA) {
        this.AnswerA = AnswerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String AnswerB) {
        this.AnswerB = AnswerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String AnswerC) {
        this.AnswerC = AnswerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String AnswerD) {
        this.AnswerD = AnswerD;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }
   
    
    
}
