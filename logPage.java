package user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class logPage extends JFrame implements ActionListener{
	JButton login;
	JButton back;
	JTextField username;
	JTextField password;
	JLabel n1;
	JLabel n2;
	
	logPage(){
		n1 = new JLabel();
		n2 = new JLabel();
		login = new JButton("LOG IN");
		back = new JButton("BACK");
		username = new JTextField();
		password = new JTextField();
		
		
		username.setBounds(160, 50, 200, 30);
		password.setBounds(160, 120, 200, 30);
		
		login.setBounds(350, 200,80,50);
		back.setBounds(70,200,80,50);
		
		n1.setText("Username");
		n1.setBounds(160, 30, 200, 30);
		n2.setText("Password");
		n2.setBounds(160,100,200,30);
		
		login.addActionListener(this);
		back.addActionListener(this);
		
		this.setTitle("Log in with an existing account");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

		this.add(n1);
		this.add(n2);
		this.add(back);
		this.add(username);
		this.add(password);
		this.add(login);
		
		this.setVisible(true);
		
	}
	



@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
	String un = username.getText();
	String ps = password.getText();
	
	if (e.getSource() == back) {
		loginPage log = new loginPage();
		this.dispose();
	}
	
	if (e.getSource() == login) {
	
		for (player p : controller.playerList) {
			if (p.getUsername().equals(un) && p.getPassword().equals(ps)) {
				mainPage mp = new mainPage();
				this.dispose();
			}
		}
		
		
		
	}
	
 }

}
