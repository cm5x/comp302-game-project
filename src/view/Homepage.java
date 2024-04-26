package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Helppage;
import view.MapDesigner;

public class Homepage extends JFrame {

    private String username;
    private static Homepage instance = null;


    public Homepage(String username) {

        // Save username data
        this.username = username;

        setTitle("Homepage");

        setSize(500,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

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
                settingsPage settingsp = new settingsPage();
            }
        });
        panel.add(settingsButton);
        
        JButton helpButton = new JButton("Help Page");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle help button action

                // initiallize help page but dont dispose the homepage
                Helppage helppage = new Helppage();
                helppage.setVisible(true);
            }
        });
        panel.add(helpButton);
        
        // Add path to build mode
        JButton buildingButton = new JButton("Building Mode");
        buildingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MapDesigner mapdesigner = new MapDesigner();
                mapdesigner.setVisible(true);
                
            }
        });
        
        panel.add(buildingButton);
        
        add(panel);
        setVisible(true);

    }

    public static Homepage getInstance() {
        // Create the instance if it's not already created
        if (instance == null) {
            instance = new Homepage("username");
        }
        return instance;
    }
}