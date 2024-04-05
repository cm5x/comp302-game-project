package user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class loginPage extends JFrame implements ActionListener{
	
	JButton login;
	JButton signup;
	JLabel msg;
	JButton exit;
	
	loginPage(){
		msg = new JLabel();
		login = new JButton("Login");
		signup = new JButton("Sign Up");
		exit = new JButton("EXIT");
		
		
		msg.setText("Welcome to Lance of Destiny! \n Login or Sign up ");
		msg.setBounds(0, 0, 500, 500);
		msg.setOpaque(true);
		msg.setHorizontalAlignment(JLabel.CENTER);
		msg.setVerticalAlignment(JLabel.TOP);
		
		login.setBounds(100, 100, 100, 100);
		login.addActionListener(this);
		msg.add(login);
		signup.setBounds(300,100,100,100);
		signup.addActionListener(this);
		msg.add(signup);
		exit.setBounds(400,220,80,50);
		exit.addActionListener(this);
		
		this.add(exit);
		this.add(msg);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			logPage lp = new logPage();
			this.dispose();
		}
		
		if (e.getSource() == signup) {
			signPage sp = new signPage();
			this.dispose();
		}
		
		if (e.getSource() == exit) {
			this.dispose();
		}
		
	}

}
