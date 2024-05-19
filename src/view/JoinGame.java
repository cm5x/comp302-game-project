package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gameMechanics.Client;

public class JoinGame extends JFrame {
    
    private static Login instance = null;

    // input fields in the frame
    private JTextField ipAdressField;
    private JTextField portField;

    public JoinGame() {

        setTitle("Join Game");
        setSize(500,250);
        setLayout(new GridLayout(3,2));

        //Open the frame in the center
        setLocationRelativeTo(null);

        //close automatically
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Writing IP Adress
        JLabel ipAdressLabel = new JLabel("Ip Adress:");
        ipAdressLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(ipAdressLabel);

        //Text field to enter IP Adress
        ipAdressField = new JTextField();
        add(ipAdressField);

        //Writing Port
        JLabel portLabel = new JLabel("Port:");
        portLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(portLabel);
        
        //Text field to enter port
        portField = new JTextField();
        add(portField);

        JButton joinButton = new JButton("Join");
        add(joinButton);

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ipAdress = ipAdressField.getText();
                int port = Integer.parseInt(portField.getText());

                
                Client multiplierClient = new Client(ipAdress, port);
                multiplierClient.connectToServer();

            }
        });


    }

}
