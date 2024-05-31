package view;

import javax.swing.*;
import java.awt.*;

public class MultiplayerWaitingScreen extends JFrame {
    private static MultiplayerWaitingScreen instance;

    private MultiplayerWaitingScreen() {
        setTitle("Waiting for Player...");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel waitingLabel = new JLabel("Waiting for the other player to connect...", SwingConstants.CENTER);
        add(waitingLabel);

        setVisible(true);
    }

    public static MultiplayerWaitingScreen getInstance() {
        if (instance == null) {
            instance = new MultiplayerWaitingScreen();
        }
        return instance;
    }

    public void close() {
        instance = null;
        this.dispose();
    }
}
