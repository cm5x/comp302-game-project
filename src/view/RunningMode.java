package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import utilities.*;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.w3c.dom.events.MouseEvent;

import gameComponents.Barrier;
import gameComponents.ExplosiveBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;

public class RunningMode extends JFrame {


    public RunningMode(){
        setTitle("Play Game");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creating the map panel where game objects will interact
        //this.mapPanel = new MapPanel(this);
        //this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        //this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        //this.add(mapPanel, BorderLayout.CENTER);
        
    }

    class MapPanel extends JPanel {
        // Create barrier arraylists to store the barriers
        private ArrayList<Barrier> barrierList;

        
    }
    // Load map and save map for the game
/*         public void saveMap() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                    oos.writeObject(barrierList);
                    frame.appendInfoText("Map saved successfully to " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    frame.appendInfoText("Error saving map: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } */
    
/*         @SuppressWarnings("unchecked")
        public void loadMap() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to load");
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {
                    blocks = (ArrayList<ColoredBlock>) ois.readObject();
                    repaint();
                    frame.appendInfoText("Map loaded successfully from " + fileToLoad.getAbsolutePath());
                } catch (IOException | ClassNotFoundException e) {
                    frame.appendInfoText("Error loading map: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } */

    public static void main(String args[]){
        RunningMode run = new RunningMode();
        run.setVisible(true);
    }

}
