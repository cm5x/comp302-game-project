package view;

import javax.swing.*;

import gameComponents.Player;
import gameMechanics.Client;
import gameMechanics.Server;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class MultiplayerWaitingScreen extends JFrame {
        
    public MultiplayerWaitingScreen(ArrayList<int[]> barrierList) {

        setTitle("Waiting Room");

        setSize(300,200);
        setLayout(new GridLayout(3,1));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JLabel portLabel = new JLabel( "Enter Port:");
        portLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JTextField portField = new JTextField();

        JButton hostButton = new JButton("Host Game");

        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int port = Integer.parseInt(portField.getText());
                
                try {
                    Server multiplayerServer = new Server(port);
                    
                    multiplayerServer.sendMap(barrierList);

                    Player p = new Player("uname", "pass");
                    RunningMode run = new RunningMode(6,p,barrierList);
                    run.setVisible(true);


                } catch (IOException | InterruptedException e1) {
                    
                    e1.printStackTrace();
                }
            

            }
        });
        

        add(portLabel);
        add(portField);
        add(hostButton);
        
        setVisible(true);
    }

}
