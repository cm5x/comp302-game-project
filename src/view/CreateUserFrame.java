package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateUserFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public CreateUserFrame() {

        setTitle("Create New User");
        setSize(400,200);
        setLayout(new GridLayout(3,2));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Username textfield
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        // Password textfield
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        // create user button
        JButton createUserButton = new JButton("Create User");
        add(createUserButton);

        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(CreateUserFrame.this, "Username or Password cannot be empty.");
                    return;
                }

                try (BufferedWriter write = new BufferedWriter(new FileWriter("src/utilities/GameSaves/players.txt", true))) {
                    write.write(username + "," + password);
                    write.newLine();
                    JOptionPane.showMessageDialog(CreateUserFrame.this, "User created successfully!");
                    dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(CreateUserFrame.this, "Error saving user information.");
                }
            }
        });
    }
}
