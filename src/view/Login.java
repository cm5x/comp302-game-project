package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import gameMechanics.GameController;

public class Login extends JFrame {

    private static Login instance = null;

    private JTextField usernameField;
    private JPasswordField passwordField;

    GameController controller = new GameController();

    String backgroundpath = "assets/images/200background.png";
    Image backimg = new ImageIcon(backgroundpath).getImage();   
    //Constructor
    public Login(){


        setTitle("Login Page");

        setSize(500,300);
        setLayout(null);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Add components
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backimg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLocation(0, 0);
        backgroundPanel.setSize(500, 275);
        backgroundPanel.setLayout(new GridLayout(3,2));
        //Writing Username

        // Username textfield

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        backgroundPanel.add(usernameLabel);

        usernameField = new JTextField();
        backgroundPanel.add(usernameField);

        //password textfield
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        backgroundPanel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        backgroundPanel.add(passwordField);

        //Button for login
        JButton loginButton = new JButton("Login");
        backgroundPanel.add(loginButton);

        // button for creating user
        JButton createUserButton = new JButton("Create User");
        backgroundPanel.add(createUserButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // check if it is admin
                if (username.matches("admin") && password.matches("admin")) {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful");
                    playAudio("assets/audio/welcome.wav");
                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);

                    dispose();
                } else if (controller.verifyPlayer(username, password)) {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful");
                    playAudio("assets/audio/welcome.wav");
                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Login Failed. Please try again.");
                }
            }
        });


        this.add(backgroundPanel);

    



/*     // Write a method to authenticate the user
       //controllerda verifyPlayer
    private boolean authenticate(String username, String password){
        if (userPasswordHashMap.containsKey(username)){
            if (userPasswordHashMap.get(username) == password) return true;
            else return false;
        }
        return false;
    } */


        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateUserFrame createUserFrame = new CreateUserFrame();
                createUserFrame.setVisible(true);
            }
        });
    }
    // Method to play audio
    private void playAudio(String audioFilePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
