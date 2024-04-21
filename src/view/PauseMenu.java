package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PauseMenu extends JFrame {
    private String username;
    private int currentScore;
    private int remainingChances;

    public PauseMenu(String username, int currentScore, int remainingChances) {
        this.username = username;
        this.currentScore = currentScore;
        this.remainingChances = remainingChances;
        //burası player classı yazılınca yeniden düzenlenecek
        
        //JFrame properties
        setTitle("Pause Menu");        
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //JPanel for components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createRigidArea(new Dimension(0, 30))); // Add vertical space before the game paused line
        
        JLabel titleLabel = new JLabel("Game Paused");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical space between game paused line and user info

        //Add labels for user's current info
        JLabel usernameLabel = new JLabel("Username: " + username);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameLabel);

        JLabel scoreLabel = new JLabel("Score: " + currentScore);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scoreLabel);

        JLabel chancesLabel = new JLabel("Remaining Chances: " + remainingChances);
        chancesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chancesLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); //Add vertical space between buttons and user info

        //Add buttons
        
        JButton resumeButton = new JButton("Resume");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //buraya resumebuttonun işlevi eklenecek
            }
        });
        panel.add(resumeButton);

        JButton saveButton = new JButton("Save Game");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//buraya save etme kısmı eklenecek
            }
        });
        panel.add(saveButton);

        JButton helpButton = new JButton("Help");
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // buraya helpscreeni açma kısmı eklenmeli
            }
        });
        panel.add(helpButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(PauseMenu.this,
                        "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        panel.add(quitButton);

        //added panel to JFrame
        add(panel);
        setVisible(true);
    }
    
    // for testing
//    public static void main(String[] args) {
//    	new PauseMenu("Player1", 1000, 3);
//    }
}
