package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gameMechanics.Server;
import java.io.IOException;

public class HostJoinPage extends JFrame {

    private Server server;

    public HostJoinPage() {
        setTitle("Host or Join Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton hostButton = new JButton("Host Game");
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
                MultiplayerWaitingScreen waitingScreen = MultiplayerWaitingScreen.getInstance();
                waitingScreen.setVisible(true);
                
            }
        });
        panel.add(hostButton);

        JButton joinButton = new JButton("Join Game");
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JoinGame joinGameScreen = new JoinGame();
                joinGameScreen.setVisible(true);
            }
        });
        panel.add(joinButton);

        add(panel);
        setVisible(true);
    }

    private void startServer() {
        int port = 1001; // You can make this configurable if needed
        if (server == null) {
            try {
                server = new Server(port);
                server.start();
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Server is already running.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HostJoinPage::new);
    }
}
