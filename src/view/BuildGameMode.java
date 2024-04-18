package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuildGameMode extends JFrame {
    
    private JPanel gamePanel;


    // Constructor
    public BuildGameMode(){
        setTitle("Build Mode");
        setSize(500,500);

        gamePanel = new JPanel(null);
        

    }
}
