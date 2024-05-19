package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Helppage;
import view.MapDesigner;
import view.settingsPage;
public class HostJoinPage extends JFrame {

    private String username;


    public HostJoinPage() {

        // Save username data

        setTitle("");

        setSize(300,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10)); // 4 rows, 1 column

        JButton playButton = new JButton("Host Game");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        panel.add(playButton);

        JButton settingsButton = new JButton("Join Game");
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle settings button action
                
                JoinGame run = new JoinGame();
                run.setVisible(true);
            }
        });
        panel.add(settingsButton);
        
        add(panel);
        setVisible(true);

    }
}
