import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePageFrame extends JFrame implements ActionListener {
    private JButton createNewGameButton;
    private JButton loadGameButton;
    private JLabel infoLabel;  // Label for displaying instructions
    
    public GamePageFrame() {
        // Frame initialization
        super("Game Page");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        // Create the instructional text label
        infoLabel = new JLabel("<html>Welcome to the Game Center! <br/>Please choose an option:</html>", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Create buttons
        createNewGameButton = new JButton("Create new game");
        loadGameButton = new JButton("Load game");

        // Set button size and font
        createNewGameButton.setPreferredSize(new Dimension(200, 50));
        loadGameButton.setPreferredSize(new Dimension(200, 50));
        createNewGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        loadGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Add action listeners to buttons
        createNewGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);

       // Add buttons to frame at different locations
       add(infoLabel, BorderLayout.CENTER);           // Label in the center
       add(createNewGameButton, BorderLayout.NORTH);  // Button at the top
       add(loadGameButton, BorderLayout.SOUTH);       // Button at the bottom

       // Center frame on screen
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createNewGameButton) {
            // Open CreateNewGameFrame
            CreateNewGameFrame createFrame = new CreateNewGameFrame();
            createFrame.setVisible(true);
        } else if (e.getSource() == loadGameButton) {
            // Open LoadGameFrame
            LoadGameFrame loadFrame = new LoadGameFrame();
            loadFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        // Run the frame
        SwingUtilities.invokeLater(() -> {
            GamePageFrame gamePage = new GamePageFrame();
            gamePage.setVisible(true);
        });
    }
}
