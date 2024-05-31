package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Helppage;
import view.MapDesigner;
import view.settingsPage;

public class Homepage extends JFrame {

    protected String username;
    private static Homepage instance = null;
    String backgroundpath = "assets/images/200background.png";
    Image backimg = new ImageIcon(backgroundpath).getImage();
    JButton backButton;
    
    public void closeHome(){
        this.dispose();
    }

    public Homepage(String username) {

        // Save username data
        this.username = username;

        setTitle("Homepage");

        setSize(500,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backimg, 0, 0, getWidth(), getHeight(), this);
            }
        }; 
        panel.setLayout(null); // 4 rows, 1 column

        JButton playButton = new JButton("Play Game");
        playButton.setSize(400, 100);
        playButton.setLocation(50, 20);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayGame run = new PlayGame();
                run.setVisible(true);
            }
        });
        panel.add(playButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setSize(400,100);
        settingsButton.setLocation(50, 140);
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle settings button action
                settingsPage settingsp = new settingsPage();
            }
        });
        panel.add(settingsButton);
        
        JButton helpButton = new JButton("Help Page");
        helpButton.setSize(400,100);
        helpButton.setLocation(50, 260);
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
        buildingButton.setSize(400,100);
        buildingButton.setLocation(50, 380);
        buildingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MapDesigner mapdesigner = new MapDesigner(username);
                mapdesigner.setVisible(true);
                
                
            }
        });
        
        panel.add(buildingButton);
        backButton = new JButton("Sign out");
        backButton.setSize(100, 20);
        backButton.setLocation(200, 500);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Login login = new Login();
                login.setVisible(true);
                closeHome();
            }
            
        });
        
        add(backButton);
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
