/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import FromMain.frmChuongTrinh;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author zduyt
 */
public class Server extends JFrame implements ActionListener{

	private JButton close;
	public JTextArea user;
	private ServerSocket server;
	public Hashtable<String, ConnectClient> listUser;

	public Server(){
		super("Server : Ai Là Triệu Phú");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {
					//gởi tin nhắn tới tất cả client
					server.close();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		setSize(500, 500);
		addItem();
		setVisible(true);
                this.setLocationRelativeTo(null);
	}

	private void addItem() {
		setLayout(new BorderLayout());

		add(new JLabel("Trạng thái server : \n"),BorderLayout.NORTH);
		add(new JPanel(),BorderLayout.EAST);
		add(new JPanel(),BorderLayout.WEST);

		user = new JTextArea(10,20);
		user.setEditable(false);
		add(new JScrollPane(user),BorderLayout.CENTER);

		close = new JButton("Close Server");
		close.addActionListener(this);
		add(close,BorderLayout.SOUTH);

		user.append("Máy chủ đang được mở.\n");
	}

	private void go(){
		try {
			listUser = new Hashtable<String, ConnectClient>();
			server = new ServerSocket(2207);
			user.append("Máy chủ đã bắt đầu. \n");
			while(true){
				Socket client = server.accept();
				new ConnectClient(this,client);
			}
		} catch (IOException e) {
			user.append("Không thể khởi động máy chủ\n");
		}
	}

	public static void main(String[] args) {
		new Server().go();
	}

	public void actionPerformed(ActionEvent e) {
			try {
				server.close();
                                frmChuongTrinh frm= new frmChuongTrinh();
                                //frm.dispose();
			} catch (IOException e1) {
				user.append("Không thể dừng được máy chủ\n");
			}
			System.exit(0);
	}

	

	public String getAllName(){
		Enumeration e = listUser.keys();
		String name="";
		while(e. hasMoreElements()){
			name+=(String) e.nextElement()+"\n";
		}
		return name;
	}

    public void sendAll(String from, String msg){
		Enumeration e = listUser.keys();
		String name=null;
		while(e. hasMoreElements()){
			name=(String) e.nextElement();
			//System.out.println(name);
			if(name.compareTo(from)!=0) listUser.get(name).sendMSG("3",msg);
		}
	}
	public void sendAllUpdate(String from){
		Enumeration e = listUser.keys();
		String name=null;
		while(e. hasMoreElements()){
			name=(String) e.nextElement();
			//System.out.println(name);
			if(name.compareTo(from)!=0) listUser.get(name).sendMSG("4",getAllName());
		}
	}

}

