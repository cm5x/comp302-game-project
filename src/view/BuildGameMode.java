package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BuildGameMode extends JFrame {
    
    private JPanel gamePanel;
    private List<JLabel> barrierCountLabel;
    private List<JTextField> barrierCountTextField;
    //private List<Barrier> barriers; 
    private List<Integer> minBarrierCount;


    // Constructor
    public BuildGameMode(){
        setTitle("Build Mode");
        setSize(800,800);
        
        // Add minimum numbers for corresponding barriers
        minBarrierCount.add(75); // 0 for simple
        minBarrierCount.add(10); // 1 for firm
        minBarrierCount.add(5); // 2 for explosive
        minBarrierCount.add(10); // 3 for gift 


        // constructor for barrier
        
        //Creating game panel for barriers.
/*         gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw barriers
                for (Barrier barrier : barriers) {
                    g.setColor(-...);
                    g.fillRect(...);
                }
            }
        };
        */


        gamePanel.setLayout(null); 

        // creating labels for barrier displaying barrier types
        barrierCountLabel = new ArrayList<>();

        // create label for basic barrier
        JLabel label1 = new JLabel("Number Of Simple Obsticles");
        label1.setBounds(600, 10, 86, 34); // Position and size of the label
        gamePanel.add(label1); // Add label to game panel
        barrierCountLabel.add(label1);

        JLabel label2 = new JLabel("Number of Firm Obsticles");
        label2.setBounds(600, 50, 86, 34); // Position and size of the label
        gamePanel.add(label2); // Add label to game panel
        barrierCountLabel.add(label2);

        JLabel label3 = new JLabel("Number Of Explosive Obsticles");
        label3.setBounds(600, 90, 86, 34); // Position and size of the label
        gamePanel.add(label3); // Add label to game panel
        barrierCountLabel.add(label3);

        JLabel label4 = new JLabel("Number Of Gift Obsticles");
        label4.setBounds(600, 130, 86, 34); // Position and size of the label
        gamePanel.add(label4); // Add label to game panel
        barrierCountLabel.add(label4);

        // creating text fields to edit barrier counts
/*         barrierCountFields = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            JTextField textField = new JTextField();
            textField.setBounds(500, 10 + 20 * i, 50, 20); 

            textField.setText(String.valueOf(minBarrierCount[i])); 

            final int index = i; // To access within ActionListener
            textField.addActionListener(e -> {
                // Update barrier count when the user presses Enter
                try {
                    int count = Integer.parseInt(textField.getText());
                    if (count >= 0 ]) {
                        ... if less then min set to min value
                        
                    } 
                } catch (.. if the the number is lower than min) {
                    
                    textField.setText(String.valueOf(...));
                }
            });
            gamePanel.add(textField);
            barrierCountLabel.add(textField);
        } */


    }

    // a main here to test the code
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuildGameMode buildGameMode = new BuildGameMode(); 
            buildGameMode.setVisible(true);
        });
    }
}



