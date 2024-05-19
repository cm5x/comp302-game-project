package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Helppage;
import view.MapDesigner;
import view.settingsPage;
public class PlayGame extends JFrame {

    private String username;


    public PlayGame() {

        // Save username data

        setTitle("Play Game");

        setSize(300,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10)); // 4 rows, 1 column

        JButton playButton = new JButton("Singleplayer");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MapSlotsFrame run = new MapSlotsFrame();
                run.setVisible(true);
            }
        });
        panel.add(playButton);

        JButton settingsButton = new JButton("Multiplayer");
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle settings button action
                HostJoinPage run = new HostJoinPage();
                run.setVisible(true);
            }
        });
        panel.add(settingsButton);
        
        add(panel);
        setVisible(true);

    }
}
