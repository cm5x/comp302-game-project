package view;
import javax.swing.*;

import gameMechanics.GameController;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import view.Homepage;

public class Login extends JFrame{

    private static Login instance = null;

    // input fields in the frame
    private JTextField usernameField;
    private JPasswordField passwordField;

    // HashMap that stores all usernames and password combos
    //bunu gamecontrollerda defineladım
    //private HashMap<String,String> userPasswordHashMap;
    GameController controller = new GameController();

    //Constructor
    public Login(){

        //Set the GUI
        setTitle("Login Page");
        setSize(500,250);
        setLayout(new GridLayout(3,2));

        //Open the frame in the center
        setLocationRelativeTo(null);

        //close automatically
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add components

        //Writing Username
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        //Text field to enter username
        usernameField = new JTextField();
        add(usernameField);

        //Writing Password
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        
        //Text field to enter password
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        // Add action listener for buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check login ccredentials 
                if (username.matches("admin") && password.matches("admin")){
                    // If the user is authenticated show a new panel and then dispose it
                    JOptionPane.showMessageDialog(Login.this, "Login Successfull");

                    //Open HomePage
                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);

                    dispose();
                }
                /*  
                else {
                    JOptionPane.showMessageDialog(Login.this, "Login Failed. Please try again.");
                }
                */
                if (controller.verifyPlayer(username, password)){
                    JOptionPane.showMessageDialog(Login.this, "Login Successfull");
                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);
                }
                else if (!controller.verifyPlayer(username, password)){
                    JOptionPane.showMessageDialog(Login.this, "Login Failed. Please try again.");
                }

            }
        });



    }


/*     // Write a method to authenticate the user
       //controllerda verifyPlayer
    private boolean authenticate(String username, String password){
        if (userPasswordHashMap.containsKey(username)){
            if (userPasswordHashMap.get(username) == password) return true;
            else return false;
        }
        return false;
    } */

}
