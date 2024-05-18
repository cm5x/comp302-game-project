

package view;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
// import java.util.Timer;
import javax.swing.*;
// import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.geom.AffineTransform;
import utilities.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;
import utilities.BarrierReader;

import java.util.logging.Logger;
import java.util.logging.Level;


import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RunningMode extends JFrame{

    private ArrayList<ArrayList<Barrier>> barriers; // list that will store all barriers
    private final MapPanel mapPanel;
    private final JPanel blockChooserPanel;
    JButton pauseButton;
    JButton saveButton;
    JButton loadButton;
    String imgpath1 = "assets/images/200iconbluegem.png";
    String imgpath2 = "assets/images/200iconfirm.png";
    String imgpath3 = "assets/images/200iconredgem.png";
    String imgpath4 = "assets/images/200icongreengem.png";
    String backgroundpath = "assets/images/200background.png";

    Image img1 = new ImageIcon(imgpath1).getImage();
    Image img2 = new ImageIcon(imgpath2).getImage();
    Image img3 = new ImageIcon(imgpath3).getImage();
    Image img4 = new ImageIcon(imgpath4).getImage();
    Image backimg = new ImageIcon(backgroundpath).getImage();

    ArrayList<Barrier> bArrayList = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(RunningMode.class.getName());

    public RunningMode() {
        setTitle("Running Mode");
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

        // LOGGER.setLevel(Level.ALL);

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
    
    class MapPanel extends JPanel implements KeyListener {
        // Initialize Magic staff 
        private ArrayList<ColoredBlock> blocks;

        //previous version
        //private ArrayList<int[]> barrierList;    
        //private String selectedColor = "red";  // Default color

        private ArrayList<int[]> barrierIndexList;
        private String selectedColor = "simple";  // Default color

        private static final int BLOCK_WIDTH = 100; // Width of the block
        private static final int BLOCK_HEIGHT = 20; // Height of the block
        private final RunningMode frame;
        private String filePath = "src/utilities/exampleMap1.dat";
        
        private Rectangle paddle;
        private Point ballPosition;
        private double ballSpeedX = 3;
        private double ballSpeedY = 3;
        private Timer timer;
        private int paddleSpeed = 10; // Speed of paddle movement
        private int paddleMoveDirection = 0; // 0 = no movement, -1 = left, 1 = right
        private double paddleAngle = 0; // Paddle's rotation angle in degrees




        public MapPanel(RunningMode frame) {
            this.frame = frame;
            this.blocks = new ArrayList<>();
            this.barrierList = new ArrayList<int[]>();
            paddle = new Rectangle(600, 950, 150, 20);
            ballPosition = new Point(650, 940);
            // timer = new Timer(10, e -> updateGame());
            // timer.start();
            timer = new Timer(10, (ActionEvent e) -> updateGame());
            timer.start();
            
            File file = new File(filePath); // File path should be in String data
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                barrierList = (ArrayList<int[]>) ois.readObject(); //get the barrierList from saved map file
                
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            for (int[] i : barrierList) {
                System.out.println(i[2]);
                switch (i[2]) {
                    case 1:

                        //addBlock(i[0], i[1],"red");

                        addBlock(i[0], i[1],"simple");
                        SimpleBarrier sbar = new SimpleBarrier(i[0], i[1]);
                        bArrayList.add(sbar);

                        break;
                    case 2:
                        addBlock(i[0], i[1],"reinforced");
                        Random random = new Random();     
                        int hitnum = random.nextInt(3) + 1;
                        ReinforcedBarrier rbar = new ReinforcedBarrier(hitnum, i[0], i[1]);
                        bArrayList.add(rbar);
                        break;
                    case 3:
                        addBlock(i[0], i[1],"explosive");
                        ExplosiveBarrier ebar = new ExplosiveBarrier(i[0], i[1]);
                        bArrayList.add(ebar);
                        break;
                    case 4:
                        addBlock(i[0], i[1],"rewarding");
                        RewardingBarrier rewbar = new RewardingBarrier(i[0], i[1]);
                        bArrayList.add(rewbar);
                        break;
                    default:
                        break;
                }

                repaint();

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
                if (ballSpeedX == 0 && ballSpeedY == 0) {
                    ballSpeedX = 3;
                    ballSpeedY = -3;
                }

                // if (ballPosition.y > getHeight()) { // Ball goes below the paddle
                //     timer.stop();
                //     JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
                // }

                // getting the rotated paddle and its rotation angle
                AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(paddleAngle), paddle.x,paddle.y);
                Shape rotatedPaddle = rotation.createTransformedShape(new Rectangle(paddle.x, paddle.y, paddle.width, paddle.height));            
                double rotationAngle = Math.toDegrees(Math.atan2(rotation.getShearY(), rotation.getScaleY()));

                // Check collision with the paddle
                if (new Rectangle(ballPosition.x, ballPosition.y, 1, 1).intersects(paddle) && rotationAngle == 0) {
                    ballSpeedY = -ballSpeedY;
                    // ballPosition.y = paddle.y - 1; // Adjust ball position to avoid sticking
                    ballPosition.y = paddle.y -1 ; // Adjust ball position to avoid sticking
                }
                else if (new Rectangle(ballPosition.x, ballPosition.y, 1, 1).intersects(paddle)){

                    // Calculate the angle between the center of the paddle and the ball
                    double angle = Math.atan2(ballPosition.getY() - rotatedPaddle.getBounds2D().getCenterY(), ballPosition.getX() - rotatedPaddle.getBounds2D().getCenterX());

                    // Reflect the ball's velocity vector across the normal of the paddle
                    double incomingAngle = Math.atan2(ballSpeedY, ballSpeedX);
                    double reflectionAngle = 2 * angle - incomingAngle;

                    // Calculate the magnitude of the velocity vector
                    double velocityMagnitude = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);

                    // Calculate the new velocity components
                    double newVelocityX = Math.cos(reflectionAngle) * velocityMagnitude;
                    double newVelocityY = Math.sin(reflectionAngle) * velocityMagnitude;

                    // Update the ball's velocity
                    ballSpeedX = (int) newVelocityX;
                    ballSpeedY = (int) -newVelocityY;

                    // If the ball is slower than some threshold, make it faster
                    double check = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
                    if (check < 1){
                        double scale = 1 / check;
                        // ballSpeedX *= 9;
                        // ballSpeedY *= 9;
                        ballSpeedX *= 2*scale;
                        ballSpeedY *= 2*scale; 

                    }
                    // Adjust ball position to avoid sticking
                    ballPosition.y = (int) (rotatedPaddle.getBounds2D().getMinY() - 1);
                    
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
            Rectangle ballRect = new Rectangle(ballPosition.x, ballPosition.y, 1, 1);

            // Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
            Iterator<MapPanel.ColoredBlock> it = blocks.iterator();
            while (it.hasNext()) {
                MapPanel.ColoredBlock block = it.next();
                if (ballRect.intersects(block.rectangle)) {
                    // Determine the collision direction
                    // double ballCenterX = ballPosition.x + 0.5; // changed from 5
                    // double ballCenterY = ballPosition.y + 0.5; // changed from 5
                    // int blockCenterX = block.rectangle.x + block.rectangle.width / 2;
                    // int blockCenterY = block.rectangle.y + block.rectangle.height / 2;

                    // double deltaX = ballCenterX - blockCenterX;
                    // double deltaY = ballCenterY - blockCenterY;

                    // // Check which side (top, bottom, left, right) of the block the ball has hit
                    // boolean collisionFromTopOrBottom = Math.abs(deltaY) > Math.abs(deltaX);
                    // if (collisionFromTopOrBottom) {
                    //     ballSpeedY = -ballSpeedY; // Vertical bounce
                    //     if (deltaY > 0) { // Ball is below the block
                    //         ballPosition.y = block.rectangle.y + block.rectangle.height;
                    //     } else { // Ball is above the block
                    //         ballPosition.y = block.rectangle.y - 10;
                    //     }
                    // } else {
                    //     ballSpeedX = -ballSpeedX; // Horizontal bounce
                    //     if (deltaX > 0) { // Ball is to the right of the block
                    //         ballPosition.x = block.rectangle.x + block.rectangle.width;
                    //     } else { // Ball is to the left of the block
                    //         ballPosition.x = block.rectangle.x - 10;
                    //     }
                    // }

                    //ALTERATIVE
                    // boolean hitVertical = Math.abs(deltaY) > Math.abs(deltaX);
                    // if (hitVertical) {
                    //     ballSpeedY = -ballSpeedY;
                    //     it.remove(); // Remove the barrier on hit
                    //     LOGGER.log(Level.INFO, "Ball hit a barrier from top/bottom. New ball speedY: {0}", ballSpeedY);
                    //     // if (deltaY > 0) {
                    //     //     ballPosition.y = block.rectangle.y + block.rectangle.height + 1; // Ball is below the block
                    //     // } else {
                    //     //     ballPosition.y = block.rectangle.y - 1; // Ball is above the block
                    //     // }
                    // } else {
                    //     ballSpeedX = -ballSpeedX;
                    //     it.remove(); // Remove the barrier on hit
                    //     LOGGER.log(Level.INFO, "Ball hit a barrier from the side. New ball speedX: {0}", ballSpeedX);
                    //     // if (deltaX > 0) {
                    //     //     ballPosition.x = block.rectangle.x + block.rectangle.width + 1; // Ball is to the right of the block
                    //     // } else {
                    //     //     ballPosition.x = block.rectangle.x - 1; // Ball is to the left of the block
                    //     // }
                    // }

                    // it.remove(); // Remove the barrier on hit
                     // Ensure the ball's speed doesn't drop to zero at any point
               
                    //ALT VX
                    Rectangle intersection = ballRect.intersection(block.rectangle);
                    if (intersection.width < intersection.height) {
                        ballSpeedX = -ballSpeedX;
                        LOGGER.log(Level.INFO, MessageFormat.format("Ball hit a barrier from the side. New ball speedX: {0}", ballSpeedX));
                    } else {
                        ballSpeedY = -ballSpeedY;
                        LOGGER.log(Level.INFO, MessageFormat.format("Ball hit a barrier from top/bottom. New ball speedY: {0}", ballSpeedY));
                    }
                    it.remove();
                    LOGGER.log(Level.INFO, MessageFormat.format("Barrier removed at: ({0}, {1})", block.rectangle.x, block.rectangle.y));
                    
                    break; // Assuming only one collision can occur per frame
                }
        }
            
            
            repaint();
        }

        //mape blok eklıyor
        public boolean addBlock(int x, int y, String selectedColor) {
            int gridX = x - (x % BLOCK_WIDTH);
            int gridY = y - (y % BLOCK_HEIGHT);

            System.out.println(selectedColor);

            blocks.add(new ColoredBlock(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT), selectedColor));
            return true;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();


            g.drawImage(backimg, 0, 0, this.getWidth(), this.getHeight(), this);

            for (ColoredBlock block : blocks) {
                switch (block.color) {
                    case "simple":
                        //g.setColor(Color.RED);
                        g.drawImage(img1, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "reinforced":
                        //g.setColor(Color.BLUE);
                        g.drawImage(img2, block.rectangle.x, block.rectangle.y, null);
                        for (Barrier barrier : bArrayList) {
                            
                            if (barrier.getXCoordinate() == block.rectangle.x && barrier.getYCoordinate() == block.rectangle.y){
                                int health = barrier.getHealth();
                                Font font = new Font("Arial", Font.BOLD, 24).deriveFont(Font.BOLD, 20);
                                FontMetrics metrics = g.getFontMetrics(font);
                                String text = Integer.toString(health); 
                                g.setFont(font);
                                g.setColor(Color.RED);
                                g.drawString(text, barrier.getXCoordinate() + 38, barrier.getYCoordinate() + 18);
                                
                            }
                            
                        }
                        break;
                    case "explosive":
                        //g.setColor(Color.GREEN);
                        g.drawImage(img3, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "rewarding":
                        g.drawImage(img4, block.rectangle.x, block.rectangle.y, null);
                        break;
                    default:
                        break;
                        // Default case
                }

                g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
                //g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
                g.setColor(Color.BLACK);
                
                
                // Burayı yunus editledi
                //g.setColor(Color.RED);
                g.fillOval(ballPosition.x, ballPosition.y, 15, 15);

                //g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);

            }
            

            //rOTATE İLE ALAKALI ASAGISI
            // Calculate the center of the paddle
            int centerX = paddle.x + paddle.width / 2;
            int centerY = paddle.y + paddle.height / 2;
            
            // Rotate the graphics context
            g2d.rotate(Math.toRadians(paddleAngle), centerX, centerY);

            // Draw the paddle with rotation
            g2d.setColor(Color.BLACK);
            g2d.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);

            // Clean up
            g2d.dispose();
                    
        }

        private class ColoredBlock implements Serializable {
            Rectangle rectangle;
            String color;
    
            ColoredBlock(Rectangle rectangle, String color) {
                this.rectangle = rectangle;
                this.color = color;
            }
        }

        // @Override
        // public void paintComponent(Graphics g) {
        //     super.paintComponent(g);
        //     g.setColor(Color.BLACK);
        //     g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        //     g.setColor(Color.RED);
        //     g.fillOval(ballPosition.x, ballPosition.y, 10, 10);
        //     // for (MapPanel.ColoredBlock block : barriers) {
        //     //     g.setColor(Color.GREEN);
        //     //     g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
        //     // }
            
        // }
        
        
        private void updateGame() {
            moveBall();
            movePaddle(); // Method to update paddle position
            repaint();
        }

        private void movePaddle() {
            setUpKeyBindings();
            if (paddleMoveDirection != 0) {
                int newPaddleX = paddle.x + (paddleSpeed * paddleMoveDirection);
                // Ensure the paddle does not move out of the panel's bounds
                if (newPaddleX < 0) {
                    newPaddleX = 0;
                } else if (newPaddleX + paddle.width > getWidth()) {
                    newPaddleX = getWidth() - paddle.width;
                }
                paddle.x = newPaddleX;
            }
        }

        private void setUpKeyBindings() {
            InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();
        
            im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
            im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
            im.put(KeyStroke.getKeyStroke("released LEFT"), "stopMoving");
            im.put(KeyStroke.getKeyStroke("released RIGHT"), "stopMoving");

                        // New bindings for rotation
            im.put(KeyStroke.getKeyStroke("UP"), "rotateClockwise");
            im.put(KeyStroke.getKeyStroke("DOWN"), "rotateCounterClockwise");
                
            am.put("moveLeft", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paddleMoveDirection = -1;
                }
            });
        
            am.put("moveRight", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paddleMoveDirection = 1;
                }
            });
        
            am.put("stopMoving", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paddleMoveDirection = 0;
                }
            });

                        // Actions for rotation
            am.put("rotateClockwise", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paddleAngle += 5; // Increment angle by 5 degrees
                    repaint();
                }
            });

            am.put("rotateCounterClockwise", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paddleAngle -= 5; // Decrement angle by 5 degrees
                    repaint();
                }
            });
        }


        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddleMoveDirection = -1; // Move left
                // frame.appendInfoText("key activation.");
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddleMoveDirection = 1; // Move right
                // frame.appendInfoText("key activation.");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //     paddleMoveDirection = 0; // Stop moving when key is released
            // }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // This method can be left empty if not used
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

    



    public static void main(String args[]){
        RunningMode run = new RunningMode();
        run.setVisible(true);
    }

}
