

package view;
import java.awt.*;
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

// import org.w3c.dom.events.MouseEvent;
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

import gameComponents.Barrier;
import gameComponents.ExplosiveBarrier;
import gameComponents.FireBall;
import gameComponents.MagicalStaff;
import gameComponents.Player;
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;
import utilities.BarrierReader;

import java.util.logging.Logger;
import java.util.logging.Level;


import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import spells.MagicalStaffExpansion;
import spells.Hex;
import spells.FelixFelicis;
import spells.OverwhelmingFireBall;
import spells.Hex.Projectile;

public class RunningMode extends JFrame{

    private ArrayList<ArrayList<Barrier>> barriers; // list that will store all barriers
    private final MapPanel mapPanel;
    private final JPanel blockChooserPanel;
    private final JPanel spellJPanel;
    private final JPanel chancePanel;
    private JLabel scoreLabel;
    private int selectedMap;
    JButton pauseButton;
    JButton saveButton;
    JButton loadButton;
    String imgpath1 = "assets/images/200iconbluegem.png";
    String imgpath2 = "assets/images/200iconfirm.png";
    String imgpath3 = "assets/images/200iconredgem.png";
    String imgpath4 = "assets/images/200icongreengem.png";
    String backgroundpath = "assets/images/200background.png";
    String stpath = "assets/images/200player.png";
    String chancePath = "assets/images/200Heart.png";

    Image img1 = new ImageIcon(imgpath1).getImage();
    Image img2 = new ImageIcon(imgpath2).getImage();
    Image img3 = new ImageIcon(imgpath3).getImage();
    Image img4 = new ImageIcon(imgpath4).getImage();
    Image backimg = new ImageIcon(backgroundpath).getImage();
    Image stff = new ImageIcon(stpath).getImage();
    ImageIcon heartimg = new ImageIcon(chancePath);

    ArrayList<Barrier> bArrayList = new ArrayList<>();
    // TODO: diğer spelleri ekle Melike
    private MagicalStaffExpansion magicalStaffExpansion;
    private Hex hexSpell;

    private static final Logger LOGGER = Logger.getLogger(RunningMode.class.getName());

    private FireBall fireBall;
    private int chances;
    private Timer timer;
    private MagicalStaff staff;
    private Player player;
    private ArrayList<JLabel> labels;
    private double score;

    public MagicalStaff getStaff() {
        return staff;
    }
    
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public RunningMode(int selectedMap, Player player) {
        setTitle("Running Mode");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.selectedMap = selectedMap;
        this.player = player;
        chances = player.getChances();
        score = 0;
        // Creating the map panel where game objects will interact
        //this.mapPanel = new MapPanel();
        //this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        //this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        //this.add(mapPanel, BorderLayout.CENTER);

        // Panel on the left that will include the buttons to load, resume, save and load game
        this.blockChooserPanel = new JPanel();
        this.spellJPanel = new JPanel();
        this.chancePanel = new JPanel();
        this.blockChooserPanel.setPreferredSize(new Dimension(230, 400));
        this.spellJPanel.setSize(230, 300);
        this.spellJPanel.setBackground(Color.ORANGE);
        this.spellJPanel.setLayout(null);
        this.spellJPanel.setLocation(0, 240);
        this.chancePanel.setSize(230, 540);
        this.chancePanel.setLayout(new FlowLayout());
        this.chancePanel.setLocation(0, 540);
        this.chancePanel.setBackground(Color.GRAY);
        this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
        this.blockChooserPanel.setLayout(null);

        //TODO: Initialize the spells
        this.magicalStaffExpansion = new MagicalStaffExpansion(this);
        this.hexSpell = new Hex(this);

        this.mapPanel = new MapPanel(this);
        this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));  // Add a black line border
        this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        this.add(mapPanel);

        // LOGGER.setLevel(Level.ALL);


        //setting up spellpanel
        JLabel slab = new JLabel("Spell Inventory");
        slab.setLocation(10, 10);
        slab.setSize(200, 20);
        JButton hexB = new JButton("Hex");
        JButton OFB = new JButton("Overwhelming Fireball");
        JButton ffButton = new JButton("Felix Felicis");
        JButton MSE = new JButton("Magical Staff Expansion");
        hexB.setSize(170, 40);
        OFB.setSize(170,40);
        ffButton.setSize(170, 40);
        MSE.setSize(170,40);
        hexB.setLocation(10, 50);
        OFB.setLocation(10, 110);
        ffButton.setLocation(10, 170);
        MSE.setLocation(10, 230);
        spellJPanel.add(slab);
        spellJPanel.add(hexB);
        spellJPanel.add(OFB);
        spellJPanel.add(ffButton);
        spellJPanel.add(MSE);

        //setting up chance panel
        JLabel clab = new JLabel("Remaining Chances");
        clab.setSize(200, 20);
        //adding chances
        labels = new ArrayList<>();
        for (int i = 0; i<chances; i++){
            JLabel tempLabel = new JLabel(heartimg);
            chancePanel.add(tempLabel);
            labels.add(tempLabel);
        }

        chancePanel.add(clab);
        

        // Create buttons 
        pauseButton = new JButton("Pause");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        pauseButton.setSize(200, 40);
        loadButton.setSize(200, 40);
        saveButton.setSize(200, 40);
        pauseButton.setLocation(20, 50);
        loadButton.setLocation(20, 110);
        saveButton.setLocation(20, 170);


        // Add buttons to the left pannel
        blockChooserPanel.add(pauseButton);
        blockChooserPanel.add(saveButton);
        blockChooserPanel.add(loadButton);
        blockChooserPanel.add(spellJPanel);
        blockChooserPanel.add(chancePanel);
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

        //action listeners for spell buttons
        hexB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //spell implementation
            }
        });

        OFB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //spell implementation
            }
        });

        ffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //spell implementation
                player.incChance(chancePanel, labels, heartimg);
            }
        });

        MSE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //spell implementation
            }
        });
        
        add(blockChooserPanel, BorderLayout.WEST);

        
        //add(mapPanel,BorderLayout.EAST);
        this.setVisible(true);

    }
    
    
    
    public class MapPanel extends JPanel implements KeyListener {
        // Initialize Magic staff 
        private ArrayList<ColoredBlock> blocks;

        //previous version
        //private ArrayList<int[]> barrierList;    
        //private String selectedColor = "red";  // Default color

        private ArrayList<int[]> barrierIndexList;
        private String selectedColor = "simple";  // Default color

        private static final int BLOCK_WIDTH = 86; // Width of the block
        private static final int BLOCK_HEIGHT = 26; // Height of the block
        private final RunningMode frame;
        private String filePath = "src/gameMapSaves/exampleMap" + selectedMap + ".dat";
        
        private Rectangle paddle;
        private Point ballPosition;
        private int ballSpeedX = 0;
        private int ballSpeedY = 0;
        private int paddleSpeed = 10; // Speed of paddle movement
        private int paddleMoveDirection = 0; // 0 = no movement, -1 = left, 1 = right
        private double paddleAngle = 0; // Paddle's rotation angle in degrees
        private boolean isMagicalStaffActive = false;
        private int originalPaddleWidth;
        private boolean remaintouched = false;
        private long currentTime;
        private long startTime;
        
        public int getOriginalPaddleWidth(){
            return originalPaddleWidth;
        }

        public ArrayList<ColoredBlock> getBlocks(){
            return blocks;
        }

        public MapPanel(RunningMode frame) {
            this.frame = frame;
            this.blocks = new ArrayList<>();
            this.barrierIndexList = new ArrayList<int[]>();

            //paddle = new Rectangle(600, 950, 150, 20);
            //ballPosition = new Point(650, 940);

            Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
            staff = new MagicalStaff(screenSize);
            paddle = new Rectangle((int)screenSize.getWidth()/2,(int) screenSize.getHeight()-100, 150, 20);
            ballPosition = new Point((int) staff.getXPos() + staff.getLength()/2, (int) screenSize.getHeight() - 120);
            fireBall = new FireBall((int) staff.getXPos() + staff.getLength()/2, (int) screenSize.getHeight() - 120);
            
            originalPaddleWidth = staff.getLength();
            
            scoreLabel = new JLabel("Player: " + player.getName() + "      Score: " + score);
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
            scoreLabel.setFont(font);
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setSize(100, 20);
            this.add(scoreLabel);
            
            // timer = new Timer(10, e -> updateGame());
            // timer.start();
            timer = new Timer(10, (ActionEvent e) -> updateGame());
            timer.start();
            startTime = System.currentTimeMillis();
            
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
                fireBall.setX(ballSpeedX+fireBall.getX());
                fireBall.setY(ballSpeedY+fireBall.getY());

                if (fireBall.getX() < 0 || fireBall.getX() > getWidth()) {
                    ballSpeedX = -ballSpeedX;
                }
                if (fireBall.getY() < 0) {
                    ballSpeedY = -ballSpeedY;
                }
                if (ballSpeedX == 0 && ballSpeedY == 0) {
                    ballSpeedX = 3;
                    ballSpeedY = 3;
                }

                // if (ballPosition.y > getHeight()) { // Ball goes below the paddle
                //     timer.stop();
                //     JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
                // }

                if (fireBall.getBounds().intersects(staff.getBounds())){
                    ballSpeedY = -ballSpeedY;
                    if (staff.getRotationAngle() > 0.087){
                        ballSpeedX = -ballSpeedX;
                    }
                    ballPosition.y = (int) staff.getYPos()/2-1;
                }
            //ALTERNATIVE

            // Collision with barriers
            Rectangle ballRect = new Rectangle(ballPosition.x, ballPosition.y, 1, 1);
            ballRect = fireBall.getBounds(); 

            // Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
            Iterator<MapPanel.ColoredBlock> it = blocks.iterator();
            
            while (it.hasNext()) {
                MapPanel.ColoredBlock block = it.next();
                if (ballRect.intersects(block.rectangle) && block.rectangle.getWidth() > 50) {
                    

                    // it.remove(); // Remove the barrier on hit
                     // Ensure the ball's speed doesn't drop to zero at any point
               
                    //ALT VX
                    Rectangle intersection = ballRect.intersection(block.rectangle);
                    intersection = fireBall.getBounds().intersection(block.rectangle);
                    if (intersection.width < intersection.height) {
                        ballSpeedX = -ballSpeedX;
                        LOGGER.log(Level.INFO, MessageFormat.format("Ball hit a barrier from the side. New ball speedX: {0}", ballSpeedX));
                    } else {
                        ballSpeedY = -ballSpeedY;
                        LOGGER.log(Level.INFO, MessageFormat.format("Ball hit a barrier from top/bottom. New ball speedY: {0}", ballSpeedY));
                    }
                    int xcoor = block.rectangle.x;
                    int ycoor = block.rectangle.y;
                    for (Barrier barr : bArrayList){
                        if (barr.getXCoordinate() == xcoor && barr.getYCoordinate() == ycoor){
                            barr.hit(1);
                            

                            if (barr.isDestroyed()){
                                it.remove();
                                if (barr.isRewarding()){
                                    Rectangle rewblock = new Rectangle(block.rectangle.x + 43, block.rectangle.y+ 23, 20, 20);
                                    blocks.add(new ColoredBlock(rewblock, "rewardbox"));       
                                      
                                }

                                if (barr instanceof ExplosiveBarrier){
                                    Rectangle remain;
                                    for (int i = 0; i < 3; i++) {
                                        int cons = 30*i;
                                        remain = new Rectangle(block.rectangle.x + cons , block.rectangle.y + 23 , 10, 20);
                                        blocks.add(new ColoredBlock(remain, "remain"));
                                        remaintouched = false;
                                        
                                    }
                                }
                                
                                score = score + 300 / (double) currentTime;
                                System.out.println(currentTime);
                                String scorest = String.format("%.2f", score);
                                scoreLabel.setText("Player: " + player.getName() + "      Score: " + scorest);
                                bArrayList.remove(barr);
                                break;
                                
                            }

                            
                        }

                    }

                    //it.remove();
                    LOGGER.log(Level.INFO, MessageFormat.format("Barrier removed at: ({0}, {1})", block.rectangle.x, block.rectangle.y));
                    
                    break; // Assuming only one collision can occur per frame
                }

                if (block.rectangle.intersects(staff.getBounds())){
                    if (block.rectangle.getWidth() == 10 && !remaintouched){
                        remaintouched = true;
                        player.decChance(chancePanel, labels);
                    }

                    else{
                        //burda power up pickleniyor spell seçme yazılabilir
                    }

                    
                    it.remove(); 
                    break;
                }
        }
            if(hexSpell.hexCanonsActive){
                hexSpell.moveProjectiles();
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
                        g.drawImage(img3, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "rewarding":
                        g.drawImage(img4, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "rewardbox":
                        g.setColor(Color.GREEN);
                        g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
                        g.setColor(Color.BLACK);
                        g.drawString("?", block.rectangle.x + 5, block.rectangle.y + 18);
                        block.rectangle.y = block.rectangle.y + 2;  
                        break; 
                    case "remain":
                        g.setColor(Color.RED);
                        g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
                        block.rectangle.y = block.rectangle.y + 5;
                        break;
                        
                    default:
                        break;
                        
                }
                fireBall.draw(g);
                staff.draw(g2d);
                
            }
            

            //Below is related to rotating function.
            // Calculate the center of the paddle
            int centerX = paddle.x + paddle.width / 2;
            int centerY = paddle.y + paddle.height / 2;
            
            // Rotate the graphics context
            g2d.rotate(Math.toRadians(paddleAngle), centerX, centerY);

            

            //hex related
            if (hexSpell.hexCanonsActive) {
                g2d.setColor(Color.MAGENTA);
                g2d.fillRect(hexSpell.leftCanon.x - 5, hexSpell.leftCanon.y - 20, 10, 20); // Depend these two paddle locations
                g2d.fillRect(hexSpell.rightCanon.x - 5, hexSpell.rightCanon.y - 20, 10, 20);
            }

            for (Projectile projectile : hexSpell.projectiles) {
                g.setColor(Color.MAGENTA);
                g.fillRect((int) projectile.x, (int) projectile.y, 5, 10);
            }


            g2d.dispose();
                    
        }

        public class ColoredBlock implements Serializable {
            Rectangle rectangle;
            String color;

            ColoredBlock(Rectangle rectangle, String color) {
                this.rectangle = rectangle;
                this.color = color;
            }
            public Rectangle getRectangle(){
                return rectangle;
            }
        }

        private void checkGameOver() {
            if (fireBall.getY() > staff.getYPos() + staff.getThickness() + 100) {
                player.decChance(chancePanel, labels);
                if (player.getChances() > 0) {
                    timer.stop();
                    resetBallAndContinue();
                } 
                else {
                    timer.stop();
                    showGameOverFrame("Game Over! No chances left.");
                }
            }
        
            if (blocks.isEmpty()) {
                timer.stop();
                showGameOverFrame("Game Over! All barriers cleared.");
            }
        
            if (player.getChances() == 0) {
                timer.stop();
                showGameOverFrame("Game Over! No chances left.");
            }
        }

        private void showGameOverFrame(String message) {
            JFrame gameOverFrame = new JFrame("Game Over");
            gameOverFrame.setSize(300, 150);
            gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameOverFrame.setLocationRelativeTo(null);
        
            JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
            JButton okButton = new JButton("OK");
        
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameOverFrame.dispose();
                }
            });
        
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(messageLabel, BorderLayout.CENTER);
            panel.add(okButton, BorderLayout.SOUTH);
        
            gameOverFrame.add(panel);
            gameOverFrame.setVisible(true);
        }
        

        private void removeAllBarriers() {
            blocks.clear();
            bArrayList.clear();
            repaint();
            //checkRemainingBarriers(); // Immediately check if there are any barriers left
        }

        private void resetBallAndContinue() {
            // Reset the ball position to the initial position
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            //fireBall.setX(screenSize.width / 2);
            //fireBall.setY(screenSize.height - 70);
            
            fireBall.setX((int) staff.getXPos() + staff.getLength()/2);
            fireBall.setY((int) screenSize.getHeight()-120);
            // Reset ball speed
            ballSpeedX = 0;
            ballSpeedY = 0;
        
            // Update the chances display
            // Assuming you have a JLabel or some component to display remaining chances
            //updateChancesDisplay();
        
            // Continue the game
            timer.start();
        }

        private void updateGame() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            //moveBall();
            movePaddle(); // Method to update paddle position
            updateTime();

            if (ballSpeedX == 0 && ballSpeedY == 0) {
                // Keep the fireball on top of the staff until Enter is pressed
                fireBall.setX((int) staff.getXPos()+ staff.getLength()/2);
                fireBall.setY((int)staff.getYPos() -20); // Adjust the offset as needed
            } else {
                moveBall();
            }
            repaint();
            checkGameOver();
        
        }

        private void updateTime(){
            long currentTime1 = System.currentTimeMillis();
            currentTime = currentTime1 - startTime - 50;
            currentTime = currentTime / 500;
        }

        private void movePaddle() {
            setUpKeyBindings();
            if (paddleMoveDirection != 0) {
                int newPaddleX = paddle.x + (paddleSpeed * paddleMoveDirection);
                // Ensure the paddle does not move out of the panel's bounds
                if (newPaddleX < 0) {
                    newPaddleX = 0;
                    staff.setXPos(0);
                } else if (newPaddleX + paddle.width > getWidth()) {
                    newPaddleX = getWidth() - paddle.width;
                    staff.setXPos( (int) (getWidth()-staff.getThickness()));
                }
                paddle.x = newPaddleX;
                staff.setXPos(newPaddleX);

                //hex related
                if (hexSpell.hexCanonsActive){
                    hexSpell.updateCanonPositions();
                    //fireHexes();
    
                }
            }
        }

        private void setUpKeyBindings() {
            InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();
        
            im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
            im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
            im.put(KeyStroke.getKeyStroke("released LEFT"), "stopMoving");
            im.put(KeyStroke.getKeyStroke("released RIGHT"), "stopMoving");
            im.put(KeyStroke.getKeyStroke("T"), "activateMagicalStaff");
            im.put(KeyStroke.getKeyStroke("H"), "activateHexSpell");
            im.put(KeyStroke.getKeyStroke("ENTER"), "startBallMovement");

                        // New bindings for rotation
            im.put(KeyStroke.getKeyStroke("UP"), "rotateClockwise");
            im.put(KeyStroke.getKeyStroke("DOWN"), "rotateCounterClockwise");
            // Secret key combination to remove all barriers
            im.put(KeyStroke.getKeyStroke("control shift D"), "removeAllBarriers");
                
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

            am.put("startBallMovement", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (ballSpeedX == 0 && ballSpeedY == 0) {
                        ballSpeedX = 3;
                        ballSpeedY = -3;
                    }
                }
            });
                        // Actions for rotation
            am.put("rotateClockwise", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (paddleAngle < 45) {
                        paddleAngle += 5; // Increment angle by 5 degrees
                        staff.setRotationAngle(staff.getRotationAngle() + 0.0872665);              
                        repaint();
                    }
                
                }
            });

            am.put("rotateCounterClockwise", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (paddleAngle > -45) {
                        paddleAngle -= 5; // Decrement angle by 5 degrees
                        staff.setRotationAngle(staff.getRotationAngle() - 0.0872665);
                        repaint();
                    }
                }
            });
            am.put("activateMagicalStaff", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   magicalStaffExpansion.activate();
                 }
             });
             am.put("activateHexSpell", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     hexSpell.activate();
                 }
             });
             // Action for removing all barriers
            am.put("removeAllBarriers", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeAllBarriers();
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
        Player p = new Player("admin", "pass");
        RunningMode run = new RunningMode(1,p);
        run.setVisible(true);
    }

}
