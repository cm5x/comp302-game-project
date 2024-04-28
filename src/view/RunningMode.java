package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    JButton saveButton;
    JButton loadButton;
    JButton resumeButton;
    JButton pauseButton;

    public RunningMode(){
        setTitle("Play Game");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creating the map panel where game objects will interact
        this.mapPanel = new MapPanel(this);
        this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        this.add(mapPanel, BorderLayout.CENTER);

        // Panel on the left that will include the buttons to load, resume, save and load game
        this.blockChooserPanel = new JPanel();
        this.blockChooserPanel.setPreferredSize(new Dimension(230, 600));
        this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
        this.blockChooserPanel.setLayout(new GridLayout(4,1));


        // Create buttons 
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        resumeButton = new JButton("Resume");
        pauseButton = new JButton("Pause");

        // Add buttons to the left pannel
        blockChooserPanel.add(saveButton);
        blockChooserPanel.add(loadButton);
        blockChooserPanel.add(resumeButton);
        blockChooserPanel.add(pauseButton);

        //Adding action listeners to buttons
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

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Melike buraya eklersin eklersin
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Melike buraya eklersin :(((((((((((((((((((((((((((((((((((((((((())))))))))))))))))))))))))))))))))))))))))
            }
        });

        this.setVisible(true);
    }    
    
    class MapPanel extends JPanel {
        // Initialize Magic staff 
        paddle = new Rectangle(100, 450, 100, 20);  // Initial position and size of the paddle
        ballPosition = new Point(150, 435);  // Initial position of the ball

        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(10, e -> moveBall());
        timer.start();

        public MapPanel(RunningMode frame){
            List<Barrier> simpleBarriers = barriers.get(0);
            List<Barrier> firmBarriers = barriers.get(1);
            List<Barrier> explosiveBarriers = barriers.get(2);
            List<Barrier> rewardingBarriers = barriers.get(3);
            this.frame = frame;
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
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

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

    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    //     g.fillOval(ballPosition.x, ballPosition.y, 10, 10);
    // }


    @Override
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
}
