package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gameMechanics.GameController;

public class Login extends JFrame {

    private static Login instance = null;

    private JTextField usernameField;
    private JPasswordField passwordField;

    GameController controller = new GameController();

    public Login() {

        setTitle("Login Page");
        setSize(500,250);
        setLayout(new GridLayout(4,2));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Username textfield
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        //password textfield
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        
        passwordField = new JPasswordField();
        add(passwordField);

        //Button for login
        JButton loginButton = new JButton("Login");
        add(loginButton);

        // button for creating user
        JButton createUserButton = new JButton("Create User");
        add(createUserButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // check if it is admin
                if (username.matches("admin") && password.matches("admin")) {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful");

                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);

                    dispose();
                } else if (controller.verifyPlayer(username, password)) {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful");
                    Homepage homepage = new Homepage(username);
                    homepage.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Login Failed. Please try again.");
                }
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateUserFrame createUserFrame = new CreateUserFrame();
                createUserFrame.setVisible(true);
            }
        });
    }
}
