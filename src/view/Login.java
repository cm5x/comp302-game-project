package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Login extends JFrame{

    private static Login instance = null;

    // input fields in the frame
    private JTextField usernameField;
    private JPasswordField passwordField;

    // HashMap that stores all usernames and password combos
    private HashMap<String,String> userPasswordHashMap;

    //Constructor
    public Login(){

        //Set the GUI
        setTitle("Login Page");
        setSize(500,250);
        setLayout(new GridLayout(3,2));

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

        // Initiallize the hashmap to store all users
        HashMap<String, String> userPasswordHashMap = new HashMap<>();

        // Create the admin
        userPasswordHashMap.put("admin","adamin");

        // Add action listener for buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check login ccredentials 
                if (authenticate(username,password)){
                    // If the user is authenticated show a new panel and then dispose it
                    JOptionPane.showMessageDialog(Login.this, "Login Successfull");
                    dispose();
                } 
                else {
                    JOptionPane.showMessageDialog(Login.this, "Login Failed. Please try again.");
                }
                

            }
        });



    }


    // Write a method to authenticate the user
    private boolean authenticate(String username, String password){
        if (userPasswordHashMap.containsKey(username)){
            if (userPasswordHashMap.get(username) == password) return true;
            else return false;
        }
        return false;
    }

}
