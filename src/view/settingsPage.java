package view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameComponents.Player;
import gameMechanics.GameController;


public class settingsPage extends JFrame implements ActionListener{

    JButton back;
    JPanel textpanel;
    JPanel passchange;
    JTextField name1;
    JTextField newname;
    JTextField name2;
    JTextField newpass;
    JButton submitname;
    JLabel changename;
    JButton submitpass;
    JLabel passwordchanger;
    GameController gc = new GameController();
    settingsPage(){

        back = new JButton("BACK");
        back.addActionListener(this);
        back.setSize(200,50);
        back.setLocation(150, 500);

        name1 = new JTextField();
        newname = new JTextField();
        name1.setText("Old username");
        newname.setText("New username");
        name1.setPreferredSize(new Dimension(250, 40));
        newname.setPreferredSize(new Dimension(250, 40));

        name2 = new JTextField();
        newpass = new JTextField();
        name2.setText("Username");
        newpass.setText("New password");
        name2.setPreferredSize(new Dimension(250, 40));
        newpass.setPreferredSize(new Dimension(250, 40));

        textpanel = new JPanel();
        textpanel.setSize(400,200);
        textpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50,10));
        textpanel.setVisible(true);
        textpanel.setBackground(Color.GRAY);
        textpanel.setOpaque(true);
        textpanel.setLocation(50, 10);
        changename = new JLabel("Update your username");
        submitname = new JButton("SUBMIT");
        submitname.addActionListener(this);
        textpanel.add(changename);
        textpanel.add(name1);
        textpanel.add(newname);
        textpanel.add(submitname);

        passchange = new JPanel();
        passchange.setSize(400,200);
        passchange.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        passchange.setVisible(true);
        passchange.setBackground(Color.gray);
        passchange.setOpaque(true);
        submitpass = new JButton("SUBMIT");
        submitpass.addActionListener(this);
        passwordchanger = new JLabel("Update your password");
        passchange.add(passwordchanger);
        passchange.add(name2);
        passchange.add(newpass);
        passchange.add(submitpass);
        passchange.setLocation(50, 250);

        





        this.add(textpanel);
        this.add(passchange);

        this.setTitle("Settings");
        this.setSize(500,600);
        this.setLayout(null);
        




        this.add(back);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        

        
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            this.dispose();
            //settings açıldığında eski sayfa (homepage) kapanmamalı, bu sadece kendini kapatıcak
        }

        if (e.getSource() == submitname){
            String oldname = name1.getText();
            String updatename = newname.getText();
            //update the username of player with username oldname to updatename
            //if success,
            boolean success = false;
            ArrayList<Player> players = gc.getPlayerList();
            for (Player p : players){
                if (p.getName().equals(oldname)){
                    p.setName(updatename);
                    success = true;
                    gc.update(players);
                    break;

                }
            }
            if (success){
                JOptionPane.showMessageDialog(null, "Username successfully changed!", "Message", JOptionPane.PLAIN_MESSAGE);
            }

            else{
                JOptionPane.showMessageDialog(null, "An error occured, please try again.", "Message", JOptionPane.PLAIN_MESSAGE);
            }
            
        }

        if (e.getSource() == submitpass){
            String username = name2.getText();
            String passw = newpass.getText();
            boolean success1 = false;
            ArrayList<Player> players = gc.getPlayerList();
            for (Player p : players){
                if (p.getName().equals(username)){
                    p.setpass(passw);
                    success1 = true;
                    gc.update(players);
                    break;
                    
                }
            }
            if (success1){
                JOptionPane.showMessageDialog(null, "Password successfully changed!", "Message", JOptionPane.PLAIN_MESSAGE);
            }

            else{
                JOptionPane.showMessageDialog(null, "An error occured, please try again.", "Message", JOptionPane.PLAIN_MESSAGE);
            }
            
        }
    }

}
