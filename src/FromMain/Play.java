/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FromMain;

import Client.Client;
import YKien.YKienA;
import YKien.YKienB;
import YKien.YKienC;
import YKien.YKienD;
import YKienNguoiThan.YKienNguoiThanA;
import YKienNguoiThan.YKienNguoiThanB;
import YKienNguoiThan.YKienNguoiThanC;
import YKienNguoiThan.YKienNguoiThanD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author huyph
 */
public class Play extends javax.swing.JFrame {
    private final String mucthuongz[] = new String[]{"200", "400", "600", "1,000", "2,000", "3,000", "6,000", "10,000", "14,000", "22,000", "30,000", "40,000", "60,000", "85,000", "150,000"};
    int demCauHoi = 1;
    String chon = null;
    private final Question qt = new Question();
    String anw = null;
    String a = null;
    String b = null;
    String c = null;
    String d = null;
    String dapan = null;
    String chona = "A";
    String chonb = "B";
    String chonc = "C";
    String chond = "D";
    int maqt = 0;
    ArrayList list = new ArrayList();
    public boolean checkrd = true;
    int lastquest = 16;
    String maqttest = null;
    int dem = 0;
    static Thread thread = new Thread();
    public Timer t;
    public int count = 0;
    public int tongtime = 60;
    public int hanchot = 0;
    Client cl= new Client();
    String test=cl.getUser();
    private DataOutputStream dos;
    private DataInputStream dis;
    public Socket client;
	
    String tim;
    
    
    
    public Play() throws SQLException {
        initComponents();
        System.out.println(""+cl.getUser());
        lblUser.setText(cl.getUser());
        go();
        
        getMaCH();
        // ShowData();
        ShowQuestbyMaCH();
        timecouter();
        t.start();
        mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 1.png")));   
    }

    private void go() {
        try {
            client = new Socket("localhost", 2207);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

            //client.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối, xem lại dây mạng hoặc máy chủ chưa mở.", "Message Dialog", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
    public void LoadCauHoi() {

    }
    
    public void timecouter() {
        count = 0;

        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                hanchot = tongtime - count;
                lbldemthoigian.setText(hanchot + "");
                tim = lbldemthoigian.getText();

                if (hanchot == 0) {
                    JOptionPane.showMessageDialog(null, "Hết giờ","Thông báo",1);
                    btnCauA.setEnabled(false);
                    btnCauB.setEnabled(false);
                    btnCauC.setEnabled(false);
                    btnCauD.setEnabled(false);
                    checkketqua();
                }
            }
        });

        // t.start();
    }
    public void timereset(){
        if(t.isRunning()){
            t.restart();
        }     
    }
    
    public void RandomMaCH() throws SQLException {
        ResultSet rs = qt.RandomMaQuestion();
        rs.next();
        maqttest = rs.getString(1);
    }
    
    public void checkrandomMaCH() {
        if (!list.contains(maqttest)) {
            checkrd = true;
            return;
        }
        if (list.contains(maqttest)) {
            checkrd = false;
            return;
        }
    }

    public void addMaCHvolist() throws SQLException {
        if (checkrd == true) {
            list.add(maqttest);
        }
        if (checkrd == false) {
            for (int i = 0; i < 20; i++) {
                RandomMaCH();
                if (!list.contains(maqttest)) {
                    list.add(maqttest);
                    return;
                }

            }
        }
    }
    
    public void getMaCH() throws SQLException {
        for (int i = 0; i < 20; i++) {
            RandomMaCH();
            checkrandomMaCH();
            addMaCHvolist();
        }
    }
    
    public void ShowQuestbyMaCH() throws SQLException {
        dem++;
        String change = String.valueOf(list.get(dem));
        maqt = Integer.parseInt(change);
        try {
            ResultSet rs = qt.ShowQuestbyRandomMaQuestion(maqt);
            rs.next();
            txtCauHoi.setText(rs.getString("Question"));
            btnCauA.setText(rs.getString("AnswerA"));
            a = btnCauA.getText();
            btnCauB.setText(rs.getString("AnswerB"));
            b = btnCauB.getText();
            btnCauC.setText(rs.getString("AnswerC"));
            c = btnCauC.getText();
            btnCauD.setText(rs.getString("AnswerD"));
            d = btnCauD.getText();
            lbldapan.setText(rs.getString("Answer"));
            dapan = lbldapan.getText();
            lblMa.setText(rs.getString("MaQT"));

        } catch (SQLException e) {
            Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void ShowData() throws SQLException {
        ResultSet rs = qt.ShowQuestion();

        while (rs.next()) {
            txtCauHoi.setText(rs.getString("Question"));
            btnCauA.setText(rs.getString("AnswerA"));
            a = btnCauA.getText();
            btnCauB.setText(rs.getString("AnswerB"));
            b = btnCauB.getText();
            btnCauC.setText(rs.getString("AnswerC"));
            c = btnCauC.getText();
            btnCauD.setText(rs.getString("AnswerD"));
            d = btnCauD.getText();
            lbldapan.setText(rs.getString("Answer"));
            dapan = lbldapan.getText();
            lblMa.setText(rs.getString("MaQT"));

        }

    }
    public void checkketqua() {
        if (demCauHoi == 1) {
            JOptionPane.showMessageDialog(null, "Bạn Không Nhận Được Tiền Thưởng Của Chương Trình", "THÔNG BÁO", 1);
        } else {
            if (demCauHoi < 5) {
                JOptionPane.showMessageDialog(null, "Bạn Đã Nhận Được:200.000đ Tiền Thưởng Của Chương Trình", "THÔNG BÁO", 1);
            }
            if (demCauHoi >= 5 && demCauHoi < 10) {
                JOptionPane.showMessageDialog(null, "Bạn Đã Nhận Được:2.000.000đ Tiền Thưởng Của Chương Trình", "THÔNG BÁO", 1);
            }
            if (demCauHoi >= 10 && demCauHoi < 15) {
                JOptionPane.showMessageDialog(null, "Bạn Đã Nhận Được:20.000.000đ Tiền Thưởng Của Chương Trình", "THÔNG BÁO", 1);
            }

        }
        this.dispose();
    }
    
    
    
    public void checkdapan() throws SQLException {
        if (chon.equals(dapan)) {

            demCauHoi++;

            lblthuong.setText(mucthuongz[demCauHoi - 2]);
            String thuong = lblthuong.getText();
            if (demCauHoi < 16) { //ShowData();
                ShowQuestbyMaCH();
                if(t.isRunning())
                {   t.restart();
                    
                }
                timecouter();
            } else {
                JOptionPane.showMessageDialog(null, "CHÚC MỪNG BẠN ĐÃ CHIẾN THẮNG VÀ ĐÃ NHẬN ĐƯỢC 150 TRIỆU TỪ CHƯƠNG TRÌNH", "THÔNG BÁOo", 1);
                this.dispose();
            }

            try {
                if (thuong == "200") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/múc 2.png")));
                } else if (thuong == "400") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 3.png")));
                } else if (thuong == "600") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 4.png")));
                } else if (thuong == "1,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 5.png")));
                } else if (thuong == "2,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 6.png")));
                } else if (thuong == "3,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 7.png")));
                } else if (thuong == "6,000") {
                   mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 8.png")));
                } else if (thuong == "10,000") {
                   mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 9.png")));
                } else if (thuong == "14,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 10.png")));
                } else if (thuong == "22,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 11.png")));
                } else if (thuong == "30,000") {
                   mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 12.png")));
                } else if (thuong == "40,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 13.png")));
                } else if (thuong == "60,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 14.png")));
                } else if (thuong == "85,000") {
                    mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/mức 15.png")));
                    
                }
            } catch (Exception e) {
            }

        } else {

            checkketqua();

        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCauHoi = new javax.swing.JTextField();
        btnCauA = new javax.swing.JButton();
        btnCauC = new javax.swing.JButton();
        btnCauB = new javax.swing.JButton();
        btnCauD = new javax.swing.JButton();
        mức = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lblIdNguoiDung = new javax.swing.JLabel();
        lbldapan = new javax.swing.JLabel();
        lblthuong = new javax.swing.JLabel();
        lblMa = new javax.swing.JLabel();
        btn5050 = new javax.swing.JButton();
        btnDTNguoiThan = new javax.swing.JButton();
        btnHoiYtruongQuay = new javax.swing.JButton();
        lbldemthoigian = new javax.swing.JLabel();
        btnDungQuocChoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GAME AI LÀ TRIỆU PHÚ");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCauHoi.setBackground(new java.awt.Color(0, 102, 204));
        txtCauHoi.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        txtCauHoi.setForeground(new java.awt.Color(255, 255, 255));
        txtCauHoi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCauHoiActionPerformed(evt);
            }
        });
        getContentPane().add(txtCauHoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 520, 740, 80));

        btnCauA.setBackground(new java.awt.Color(0, 51, 204));
        btnCauA.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCauA.setForeground(new java.awt.Color(255, 255, 255));
        btnCauA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCauAMouseClicked(evt);
            }
        });
        btnCauA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCauAActionPerformed(evt);
            }
        });
        getContentPane().add(btnCauA, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 620, 300, 40));

        btnCauC.setBackground(new java.awt.Color(0, 51, 204));
        btnCauC.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCauC.setForeground(new java.awt.Color(255, 255, 255));
        btnCauC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCauCMouseClicked(evt);
            }
        });
        getContentPane().add(btnCauC, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 680, 300, 40));

        btnCauB.setBackground(new java.awt.Color(0, 51, 204));
        btnCauB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCauB.setForeground(new java.awt.Color(255, 255, 255));
        btnCauB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCauBMouseClicked(evt);
            }
        });
        btnCauB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCauBActionPerformed(evt);
            }
        });
        getContentPane().add(btnCauB, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 620, 300, 40));

        btnCauD.setBackground(new java.awt.Color(0, 51, 204));
        btnCauD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCauD.setForeground(new java.awt.Color(255, 255, 255));
        btnCauD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCauDMouseClicked(evt);
            }
        });
        getContentPane().add(btnCauD, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 680, 300, 40));

        mức.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mức.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thuong/hình mức.png"))); // NOI18N
        getContentPane().add(mức, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 70, 300, 450));

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tiền Thưởng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lblUser.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jPanel1.add(lblUser);

        lblIdNguoiDung.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jPanel1.add(lblIdNguoiDung);

        lbldapan.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jPanel1.add(lbldapan);

        lblthuong.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblthuong.setForeground(new java.awt.Color(243, 1, 1));
        lblthuong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblthuong);

        lblMa.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jPanel1.add(lblMa);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 10, 200, 90));

        btn5050.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hình/5050.png"))); // NOI18N
        btn5050.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5050ActionPerformed(evt);
            }
        });
        getContentPane().add(btn5050, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, 90, 50));

        btnDTNguoiThan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hình/DTNguoiThan.png"))); // NOI18N
        btnDTNguoiThan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDTNguoiThanActionPerformed(evt);
            }
        });
        getContentPane().add(btnDTNguoiThan, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 90, -1));

        btnHoiYtruongQuay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hình/YKienTQ.png"))); // NOI18N
        btnHoiYtruongQuay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoiYtruongQuayActionPerformed(evt);
            }
        });
        getContentPane().add(btnHoiYtruongQuay, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 90, 50));

        lbldemthoigian.setFont(new java.awt.Font("Times New Roman", 1, 100)); // NOI18N
        lbldemthoigian.setForeground(new java.awt.Color(255, 255, 255));
        lbldemthoigian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbldemthoigian.setText("60");
        getContentPane().add(lbldemthoigian, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 210, 90));

        btnDungQuocChoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hình/stop.png"))); // NOI18N
        btnDungQuocChoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDungQuocChoiActionPerformed(evt);
            }
        });
        getContentPane().add(btnDungQuocChoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, 90, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hình/nền.png"))); // NOI18N
        jLabel1.setText("ảnh nền");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 1310, 790));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn5050ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5050ActionPerformed
        int deman = 0;

        if (!chona.equals(dapan) && deman < 2) {
            btnCauA.setText("");
            deman++;
        }
        if (!chonb.equals(dapan) && deman < 2) {
            btnCauB.setText("");
            deman++;
        }
        if (!chonc.equals(dapan) && deman < 2) {
            btnCauC.setText("");
            deman++;
        }
        if (!chond.equals(dapan) && deman < 2) {
            btnCauD.setText("");
            deman++;
        }
        btn5050.setEnabled(false);
    }//GEN-LAST:event_btn5050ActionPerformed

    private void btnHoiYtruongQuayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoiYtruongQuayActionPerformed
         if(chona.equals(dapan)){
            YKien.YKienA yk = new YKienA();
            yk.setVisible(true);
            btnHoiYtruongQuay.setEnabled(false);
        }
        
        if (chonb.equals(dapan)) {
            YKien.YKienB yk = new YKienB();
            yk.setVisible(true);
            btnHoiYtruongQuay.setEnabled(false);
        }
        if (chonc.equals(dapan)) {
            YKien.YKienC yk = new YKienC();
            yk.setVisible(true);
            btnHoiYtruongQuay.setEnabled(false);
        }
        if (chond.equals(dapan)) {
            YKien.YKienD yk = new YKienD();
            yk.setVisible(true);
            btnHoiYtruongQuay.setEnabled(false);
        }
    }//GEN-LAST:event_btnHoiYtruongQuayActionPerformed

    private void btnDungQuocChoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDungQuocChoiActionPerformed
        int thongbao = JOptionPane.showConfirmDialog(null, "Bạn Có Muốn Dừng Cuộc Chơi ?", "THÔNG BÁO", JOptionPane.YES_OPTION);
        if (thongbao == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Bạn Nhận Được: " + lblthuong.getText() + ".000đ Tiền Thưởng Của Chương Trình", "Thông Báo", 1);
            this.dispose();
        }
    }//GEN-LAST:event_btnDungQuocChoiActionPerformed

    private void btnCauAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCauAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCauAActionPerformed

    private void btnCauAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCauAMouseClicked
        chon = "A";

        String da = this.btnCauA.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn Có Chắc Muốn Chọn Câu Trả Lời A ?", "Xác nhận", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chona.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Xin Chúc Mừng Bạn Đã Chọn Câu Trả Lời Chính Xác ", "THÔNG BÁO", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Xin Chia Buồn Bạn Đã Chọn Câu Trả Lời Không Chính Xác ", "THÔNG BÁO", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnCauAMouseClicked

    private void btnCauBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCauBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCauBActionPerformed

    private void btnCauBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCauBMouseClicked
        chon = "B";

        String da = this.btnCauB.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn Có Chắc Muốn Chọn Câu Trả Lời B ?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chonb.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Xin Chúc Mừng Bạn Đã Chọn Câu Trả Lời Chính Xác ", "Thống Báo", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Xin Chia Buồn Bạn Đã Chọn Câu Trả Lời Không Chính Xác ", "Thống Báo", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnCauBMouseClicked

    private void btnCauCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCauCMouseClicked
        chon = "C";

        String da = this.btnCauC.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn Có Chắc Muốn Chọn Câu Trả Lời C ?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chonc.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Xin Chúc Mừng Bạn Đã Chọn Câu Trả Lời Chính Xác", "Thống Báo", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Xin Chia Buồn Bạn Đã Chọn Câu Trả Lời Không Chính Xác", "Thống Báo", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnCauCMouseClicked

    private void btnCauDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCauDMouseClicked
        chon = "D";

        String da = this.btnCauD.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn Có Chắc Muốn Chọn Câu Trả Lời D ?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chond.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Xin Chúc Mừng Bạn Đã Chọn Câu Trả Lời Chính Xác", "Thống Báo", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Xin Chia Buồn Bạn Đã Chọn Câu Trả Lời Không Chính Xác ", "Thống Báo", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnCauDMouseClicked

    private void txtCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCauHoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCauHoiActionPerformed

    private void btnDTNguoiThanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDTNguoiThanActionPerformed
        if(chona.equals(dapan)){
            YKienNguoiThan.YKienNguoiThanA yk = new YKienNguoiThanA();
            yk.setVisible(true);
            btnDTNguoiThan.setEnabled(false);
        }
        
        if (chonb.equals(dapan)) {
            YKienNguoiThan.YKienNguoiThanB yk = new YKienNguoiThanB();
            yk.setVisible(true);
            btnDTNguoiThan.setEnabled(false);
        }
        if (chonc.equals(dapan)) {
            YKienNguoiThan.YKienNguoiThanC yk = new YKienNguoiThanC();
            yk.setVisible(true);
            btnDTNguoiThan.setEnabled(false);
        }
        if (chond.equals(dapan)) {
            YKienNguoiThan.YKienNguoiThanD yk = new YKienNguoiThanD();
            yk.setVisible(true);
            btnDTNguoiThan.setEnabled(false);
        }
    }//GEN-LAST:event_btnDTNguoiThanActionPerformed

    private void showmaqt() throws SQLException {
        ResultSet rs = qt.RandomMaQuestion();
        while (rs.next()) {

            lblIdNguoiDung.setText(rs.getString("MaQT"));
            // maqt = lblIdQuestion.getText();

        }

    }

    public boolean checkRandom() throws SQLException {
        showmaqt();
        if (!list.contains(maqt)) {
            checkrd = true;
            list.add(maqt);
            return true;
        } else {
            checkrd = false;

            return false;

        }
    }

    public void xuat() throws SQLException {
        do {
            if (checkRandom() == true) {
                ShowData();
            } else {
                checkRandom();
            }
        } while (checkRandom() == false);
    }

    public void addMaqt() throws SQLException {
        if (checkrd == true) {
            list.add(maqt);
            ShowData();

        } else {
            checkRandom();
        }

    }

    public void getId() throws SQLException {
        for (int i = 0; i < 20; i++) {
            showmaqt();
            checkRandom();
            addMaqt();
        }
    }
    public static void main(String args[]) throws SQLException {
        Play play = new Play();

        play.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn5050;
    private javax.swing.JButton btnCauA;
    private javax.swing.JButton btnCauB;
    private javax.swing.JButton btnCauC;
    private javax.swing.JButton btnCauD;
    private javax.swing.JButton btnDTNguoiThan;
    private javax.swing.JButton btnDungQuocChoi;
    private javax.swing.JButton btnHoiYtruongQuay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblIdNguoiDung;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lbldapan;
    private javax.swing.JLabel lbldemthoigian;
    private javax.swing.JLabel lblthuong;
    private javax.swing.JLabel mức;
    private javax.swing.JTextField txtCauHoi;
    // End of variables declaration//GEN-END:variables
}
