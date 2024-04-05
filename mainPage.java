package user_interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class mainPage extends JFrame implements ActionListener{

	JButton play;
	JButton buildmode;
	JButton helpcenter;
	
	
	mainPage(){
		
		
		play = new JButton("PLAY");
		buildmode = new JButton("BUILDING MODE");
		helpcenter = new JButton("HELP CENTER");
		
		play.addActionListener(this);
		buildmode.addActionListener(this);
		helpcenter.addActionListener(this);
		
		
		this.setTitle("Main Page");
		this.setSize(500,300);
		this.add(play);
		this.add(buildmode);
		this.add(helpcenter);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
