package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Homepage extends JFrame {

    public Homepage() {

        setTitle("Homepage");

        setSize(500,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column

        JButton playButton = new JButton("Play Game");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("play game clicked");
            }
        });
        panel.add(playButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle settings button action
            }
        });
        panel.add(settingsButton);
        
        JButton helpButton = new JButton("Help Page");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle help button action
            }
        });
        panel.add(helpButton);
        
        JButton buildingButton = new JButton("Building Mode");
        buildingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println();
            }
        });
        
        panel.add(buildingButton);
        
        add(panel);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Homepage();
            }
        });
    }

}