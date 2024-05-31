package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import gameMechanics.Client;

public class JoinGame extends JFrame {

    private JTextField ipAddressField;
    private JTextField portField;

    public JoinGame() {
        setTitle("Join Game");
        setSize(500, 250);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel ipAddressLabel = new JLabel("IP Address:");
        ipAddressLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(ipAddressLabel);

        ipAddressField = new JTextField();
        add(ipAddressField);

        JLabel portLabel = new JLabel("Port:");
        portLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(portLabel);

        portField = new JTextField();
        add(portField);

        JButton joinButton = new JButton("Join");
        add(joinButton);

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ipAddress = ipAddressField.getText();
                int port = Integer.parseInt(portField.getText());

                Client multiplayerClient = new Client(ipAddress, port, JoinGame.this);
                multiplayerClient.connectToServer();
            }
        });

        setVisible(true);
    }

    public void onConnectionSuccess() {
        dispose();  // Close the JoinGame window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JoinGame::new);
    }
}
