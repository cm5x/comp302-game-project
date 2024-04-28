package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import utilities.*;
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

    private ArrayList<ArrayList<Barrier>> barriers; // list that will store all barriers
    private final MapPanel mapPanel;
    private final JPanel blockChooserPanel;
    JButton pauseButton;

    public RunningMode() {
        setTitle("Play Game");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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

        // Add buttons to the left pannel
        blockChooserPanel.add(pauseButton);

        //Adding action listeners to buttons

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PauseMenu pauseMenu = new PauseMenu();
                pauseMenu.setVisible(true);
            }
        });


        add(blockChooserPanel, BorderLayout.WEST);
        //add(mapPanel,BorderLayout.EAST);
        this.setVisible(true);
    }    
    
    class MapPanel extends JPanel {
        // Initialize Magic staff 
        private ArrayList<ColoredBlock> blocks;
        private ArrayList<int[]> barrierList;
        private String selectedColor = "red";  // Default color
        private static final int BLOCK_WIDTH = 100; // Width of the block
        private static final int BLOCK_HEIGHT = 20; // Height of the block
        private final RunningMode frame;
        private String filePath = "/Users/idenizalan/Desktop/testBar.dat";


        public MapPanel(RunningMode frame) {
            this.frame = frame;
            this.blocks = new ArrayList<>();
            this.barrierList = new ArrayList<int[]>();

            File file = new File(filePath); // File path should be in String data
            System.out.println("dadada");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                barrierList = (ArrayList<int[]>) ois.readObject(); //get the barrierList from saved map file
                
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            for (int[] i : barrierList) {
                
                addBlock(i[0], i[1]);
                repaint();

            }
            
        }

        public boolean addBlock(int x, int y) {
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
                    oos.writeObject(barriers);
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
                    barriers = reader.readBarriers(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
/* 
    private void moveBall() {

        ballPosition.x += ballSpeedX;
        ballPosition.y += ballSpeedY;
        if (ballPosition.x < 0 || ballPosition.x > getWidth()) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballPosition.y < 0) {
            ballSpeedY = -ballSpeedY;
        }
        if (ballPosition.y > getHeight()) { // Ball goes below the paddle
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
        }

        // Check collision with the paddle
        if (new Rectangle(ballPosition.x, ballPosition.y, 10, 10).intersects(paddle)) {
            ballSpeedY = -ballSpeedY;
            ballPosition.y = paddle.y - 10; // Adjust ball position to avoid sticking
        }

        // // Check collision with barriers
        // Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
        // while (it.hasNext()) {
        //     MapPanel.ColoredBlock block = it.next();
        //     if (new Rectangle(ballPosition.x, ballPosition.y, 10, 10).intersects(block.rectangle)) {
        //         ballSpeedY = -ballSpeedY; // Reflect the ball

        //         it.remove(); // Remove the barrier on hit
        //         break;
        //     }
        // }

        //ALTERNATIVE

        // Collision with barriers
        Rectangle ballRect = new Rectangle(ballPosition.x, ballPosition.y, 10, 10);
        Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
        while (it.hasNext()) {
            MapPanel.ColoredBlock block = it.next();
            if (ballRect.intersects(block.rectangle)) {
                // Determine the collision direction
                int ballCenterX = ballPosition.x + 5;
                int ballCenterY = ballPosition.y + 5;
                int blockCenterX = block.rectangle.x + block.rectangle.width / 2;
                int blockCenterY = block.rectangle.y + block.rectangle.height / 2;

                int deltaX = ballCenterX - blockCenterX;
                int deltaY = ballCenterY - blockCenterY;

                // Check which side (top, bottom, left, right) of the block the ball has hit
                boolean collisionFromTopOrBottom = Math.abs(deltaY) > Math.abs(deltaX);
                if (collisionFromTopOrBottom) {
                    ballSpeedY = -ballSpeedY; // Vertical bounce
                    if (deltaY > 0) { // Ball is below the block
                        ballPosition.y = block.rectangle.y + block.rectangle.height;
                    } else { // Ball is above the block
                        ballPosition.y = block.rectangle.y - 10;
                    }
                } else {
                    ballSpeedX = -ballSpeedX; // Horizontal bounce
                    if (deltaX > 0) { // Ball is to the right of the block
                        ballPosition.x = block.rectangle.x + block.rectangle.width;
                    } else { // Ball is to the left of the block
                        ballPosition.x = block.rectangle.x - 10;
                    }
                }

                it.remove(); // Remove the barrier on hit
                break; // Assuming only one collision can occur per frame
            }
        }

        repaint();
    }
 */
    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    //     g.fillOval(ballPosition.x, ballPosition.y, 10, 10);
    // }


/*     @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        g.setColor(Color.RED);
        g.fillOval(ballPosition.x, ballPosition.y, 10, 10);
        for (MapPanel.ColoredBlock block : barriers) {
            g.setColor(Color.GREEN);
            g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
        }
    }
 */
    // @Override
    // public void keyPressed(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    //     repaint();
    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    // }

    // //???
    // @Override
    // public void keyReleased(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    // }


    // //???
    // @Override
    // public void keyTyped(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    // }

    public static void main(String args[]){
        RunningMode run = new RunningMode();
        run.setVisible(true);
    }

}