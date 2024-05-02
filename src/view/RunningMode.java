package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import utilities.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.w3c.dom.events.MouseEvent;
import gameComponents.Barrier;
import gameComponents.ExplosiveBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;
import utilities.BarrierReader;

public class RunningMode extends JFrame{

    private ArrayList<ArrayList<Barrier>> barrierList; // list that will store all barriers
    private final MapPanel mapPanel;
    private final JPanel blockChooserPanel;
    private int selectedMap;
    JButton pauseButton;
    JButton saveButton;
    JButton loadButton;

    public RunningMode(int selectedMap) {
        setTitle("Running Mode");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.selectedMap = selectedMap;

        // Creating the map panel where game objects will interact
        //this.mapPanel = new MapPanel();
        //this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        //this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        //this.add(mapPanel, BorderLayout.CENTER);

        // Panel on the left that will include the buttons to load, resume, save and load game
        this.blockChooserPanel = new JPanel();
        this.blockChooserPanel.setPreferredSize(new Dimension(230, 600));
        this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
        this.blockChooserPanel.setLayout(new GridLayout(4,1));

        this.mapPanel = new MapPanel(this);
        this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        this.add(mapPanel);

        // Create buttons 
        pauseButton = new JButton("Pause");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");


        // Add buttons to the left pannel
        blockChooserPanel.add(pauseButton);
        blockChooserPanel.add(saveButton);
        blockChooserPanel.add(loadButton);

        //Adding action listeners to buttons

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PauseMenu pauseMenu = new PauseMenu();
                pauseMenu.setVisible(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMap();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMap();
            }
        });


        add(blockChooserPanel, BorderLayout.WEST);
        //add(mapPanel,BorderLayout.EAST);
        this.setVisible(true);
    }    
    
    class MapPanel extends JPanel {
        // Initialize Magic staff 
        private ArrayList<ColoredBlock> blocks;
        private ArrayList<int[]> barrierIndexList;
        private String selectedColor = "red";  // Default color
        private static final int BLOCK_WIDTH = 100; // Width of the block
        private static final int BLOCK_HEIGHT = 20; // Height of the block
        private final RunningMode frame;
        private String filePath = "src/gameMapSaves/exampleMap" + selectedMap + ".dat";
        // private Rectangle paddle;
        // private Point ballPosition;
        // private int ballSpeedX = 2;
        // private int ballSpeedY = 2;
        // private Timer timer;


        public MapPanel(RunningMode frame) {
            this.frame = frame;
            this.blocks = new ArrayList<>();
            this.barrierIndexList = new ArrayList<int[]>();

            File file = new File(filePath); // File path should be in String data
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                barrierIndexList = (ArrayList<int[]>) ois.readObject(); //get the barrierList from saved map file
                
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            for (int[] i : barrierIndexList) {
                System.out.println(i[2]);
                switch (i[2]) {
                    case 1:
                        SimpleBarrier simpleBarrier = new SimpleBarrier(20, i[0], i[1]);
                        addBlock(i[0], i[1],"red");
                        break;
                    case 2:
                        addBlock(i[0], i[1],"blue");
                        break;
                    case 3:
                        addBlock(i[0], i[1],"green");
                        break;
                        
                    default:
                        break;
                }

                repaint();

            }

            
        }

        public boolean addBlock(int x, int y, String selectedColor) {
            int gridX = x - (x % BLOCK_WIDTH);
            int gridY = y - (y % BLOCK_HEIGHT);
            
            blocks.add(new ColoredBlock(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT), selectedColor));
            
            return true;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (ColoredBlock block : blocks) {
                switch (block.color) {
                    case "red":
                        g.setColor(Color.RED);
                        break;
                    case "blue":
                        g.setColor(Color.BLUE);
                        break;
                    case "green":
                        g.setColor(Color.GREEN);
                        break;
                    default:
                        g.setColor(Color.BLACK); // Default case
                }
                
                g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
            }
            
        }

        private static class ColoredBlock implements Serializable {
            Rectangle rectangle;
            String color;
    
            ColoredBlock(Rectangle rectangle, String color) {
                this.rectangle = rectangle;
                this.color = color;
            }
        }


    }

    
    // Load map and save map for the game
        public void saveMap() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                    oos.writeObject(barrierList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @SuppressWarnings("unchecked")
        public void loadMap() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to load");
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {
                    String file = fileToLoad.getAbsolutePath();
                    BarrierReader reader = new BarrierReader();
                    barrierList = reader.readBarriers(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    public static void main(String args[]){
        RunningMode run = new RunningMode(1);
        run.setVisible(true);
    }

}