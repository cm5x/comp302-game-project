

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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import spells.MagicalStaffExpansion;
import spells.Hex;

//ADD

import gameMechanics.Client;
import gameMechanics.Server;

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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import gameComponents.Barrier;
import gameComponents.ExplosiveBarrier;
import gameComponents.FireBall;
import gameComponents.HollowPurpleBarrier;
import gameComponents.MagicalStaff;
import gameComponents.Player;
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;
import gameComponents.Ymir;

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
    public final MapPanel mapPanel;
    private final JPanel blockChooserPanel;
    private final JPanel spellJPanel;
    private final JPanel chancePanel;


    private JLabel playerScoreLabel;
    private JLabel opponentScoreLabel;
    private int playerScore;
    private int opponentScore;


    private Server server;
    private Client client;

    private ArrayList<int[]> barrierIndexList;
    private ArrayList<JLabel> spellLabels;
    public JLabel scoreLabel;
    private JLabel playerlabel;
    public JLabel barrcountlabel;
    public int selectedMap;
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
    String imgpath5 = "assets/images/200iconHollowPurple.png";
    String imgpath6 = "assets/images/frozenBarrier.png";

    Image img1 = new ImageIcon(imgpath1).getImage();
    Image img2 = new ImageIcon(imgpath2).getImage();
    Image img3 = new ImageIcon(imgpath3).getImage();
    Image img4 = new ImageIcon(imgpath4).getImage();
    Image backimg = new ImageIcon(backgroundpath).getImage();
    Image stff = new ImageIcon(stpath).getImage();
    ImageIcon heartimg = new ImageIcon(chancePath);
    Image img5 = new ImageIcon(imgpath5).getImage();
    Image img6 = new ImageIcon(imgpath6).getImage();
    public ArrayList<Barrier> bArrayList = new ArrayList<>();
    // TODO: diğer spelleri ekle Melike
    private MagicalStaffExpansion magicalStaffExpansion;
    private Hex hexSpell;
    private FelixFelicis felixFelicis;
    private OverwhelmingFireBall overwhelmingFireBall;

    private static final Logger LOGGER = Logger.getLogger(RunningMode.class.getName());

    private FireBall fireBall;
    private int chances;
    private Timer timer;
    private MagicalStaff staff;
    public Player player;
    public JButton backB;
    private Ymir ymir;
    
    public Player getPlayer() {
        return player;
    }

    public ArrayList<Integer> getInventory() {
        return player.getSpellInventory();
    }

    public JPanel getChancePanel() {
        return chancePanel;
    }

    public ImageIcon getHeartimg() {
        return heartimg;
    }

    public ArrayList<JLabel> getLabels() {
        return labels;
    }

    private ArrayList<JLabel> labels;
    public double score;

    public MagicalStaff getStaff() {
        return staff;
    }
    
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public void openHome(){
        this.dispose();
        Homepage hm = new Homepage(player.getName());
    }


    public RunningMode(int selectedMap, Player player) {
        setTitle("Running Mode");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.selectedMap = selectedMap;
        this.player = player;
        chances = player.getChances();
        score = 0;

        this.ymir = new Ymir(this, bArrayList, fireBall);




        // server = serverSide;

        // Creating the map panel where game objects will interact
        //this.mapPanel = new MapPanel();
        //this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        //this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        //this.add(mapPanel, BorderLayout.CENTER);

        // Panel on the left that will include the buttons to load, resume, save and load game
        this.blockChooserPanel = new JPanel();
        this.spellJPanel = new JPanel();
        this.spellLabels = new ArrayList<>();
        this.chancePanel = new JPanel();
        this.blockChooserPanel.setPreferredSize(new Dimension(230, 400));
        this.spellJPanel.setSize(230, 300);
        this.spellJPanel.setBackground(Color.ORANGE);
        this.spellJPanel.setLayout(null);
        this.spellJPanel.setLocation(0, 200);
        this.chancePanel.setSize(230, 200);
        this.chancePanel.setLayout(new FlowLayout());
        this.chancePanel.setLocation(0, 0);
        this.chancePanel.setBackground(Color.GRAY);
        this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
        this.blockChooserPanel.setLayout(null);

        //TODO: Initialize the spells
        this.magicalStaffExpansion = new MagicalStaffExpansion(this);
        this.hexSpell = new Hex(this);
        this.felixFelicis = new FelixFelicis(this);
        this.overwhelmingFireBall = new OverwhelmingFireBall(this);

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
        hexB.setLocation(10, 110);
        OFB.setLocation(10, 230);
        ffButton.setLocation(10, 170);
        MSE.setLocation(10, 50);
        spellJPanel.add(slab);
        spellJPanel.add(hexB);
        spellJPanel.add(OFB);
        spellJPanel.add(ffButton);
        spellJPanel.add(MSE);

        //setting up chance panel
        JLabel clab = new JLabel("    Remaining Chances:");
        clab.setSize(200, 20);
        //adding chances
        playerlabel = new JLabel("Player: " + player.getName() + "    ");
        scoreLabel = new JLabel("   Score: " + score);
        barrcountlabel = new JLabel("Remaining Barriers: " + bArrayList.size());
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        barrcountlabel.setFont(font);
        playerlabel.setFont(font);
        scoreLabel.setFont(font);
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setSize(100, 20);
        chancePanel.add(playerlabel);
        chancePanel.add(barrcountlabel);
        chancePanel.add(scoreLabel);
        chancePanel.add(clab);
        clab.setFont(font);
        labels = new ArrayList<>();
        for (int i = 0; i<chances; i++){
            JLabel tempLabel = new JLabel(heartimg);
            chancePanel.add(tempLabel);
            labels.add(tempLabel);
        }


        // Create buttons 
        pauseButton = new JButton("Pause");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        pauseButton.setSize(170, 40);
        loadButton.setSize(170, 40);
        saveButton.setSize(170, 40);
        pauseButton.setLocation(10, 540);
        loadButton.setLocation(10, 600);
        saveButton.setLocation(10, 660);


        // Add buttons to the left pannel
        blockChooserPanel.add(chancePanel);
        blockChooserPanel.add(spellJPanel);
        blockChooserPanel.add(pauseButton);
        blockChooserPanel.add(saveButton);
        blockChooserPanel.add(loadButton);
        //Adding action listeners to buttons
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PauseMenu pauseMenu = new PauseMenu(timer,barrierIndexList,mapPanel, player.getSpellInventory(), score, player.getChances());
                pauseMenu.setVisible(true);
                timer.stop();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame(barrierIndexList,getInventory());
                timer.stop();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                loadGame();
            }
        });

        //action listeners for spell buttons
        hexB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getSpellInventory().get(1)>0){
                    hexSpell.activate();
                    updateSpellInventory(1, "decrease");

                }
            }
        });

        OFB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getSpellInventory().get(3)>0){
                    overwhelmingFireBall.activate();
                    updateSpellInventory(3, "decrease");

                }
            }
        });

        ffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //spell implementation                
                if (player.getSpellInventory().get(2)>0){
                    felixFelicis.activate();
                    updateSpellInventory(2, "decrease");

                }
            }
        });

        MSE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getSpellInventory().get(0)>0){
                    magicalStaffExpansion.activate();
                    updateSpellInventory(0, "decrease");
                }
            }
        });
        add(blockChooserPanel, BorderLayout.WEST);
        updateSpellInventoryLabels();
        
        //add(mapPanel,BorderLayout.EAST);
        //this.setVisible(true);

    }

    //for multiplayer
    // Method to update the score labels
    public void updateScores(int playerScore, int opponentScore) {
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        SwingUtilities.invokeLater(() -> {
            scoreLabel.setText("Score: " + playerScore);
            opponentScoreLabel.setText("Opponent's Score: " + opponentScore);
        });
    }

    //for multiplayer
    // Method to set the player's score
    public void setScore(int score) {
        this.playerScore = score;
        scoreLabel.setText("Score: " + playerScore);
        // Update the server or client with the new score
        if (server != null) {
            server.updateScore(true, playerScore); // Assuming server.updateScore is modified to accept playerScore and opponentScore
        } else if (client != null) {
            client.sendScore(playerScore); // Assuming client.sendScore is modified to send playerScore
        }
    }

    private void updateSpellInventoryLabels(){
           // Create new labels based on current spell inventory
        for (JLabel label : spellLabels) {
            spellJPanel.remove(label);
            }
        spellLabels.clear();
        String[] spellNames = {"Magical Staff Expansion", "Hex", "Felix Felicis", "Overwhelming Fireball"};
        for (int i = 0; i < spellNames.length; i++) {
            JLabel spellLabel = new JLabel(player.getSpellInventory().get(i)+" ");
            spellLabel.setSize(200, 20);
            spellLabel.setLocation(190, 60 + i * 60);
            spellLabels.add(spellLabel);
            spellJPanel.add(spellLabel);
        }

        spellJPanel.revalidate();
        spellJPanel.repaint();
    }
    // private void updateSpellButtonLabels() {
    //     this.hexB.setText("Hex (" + player.getSpellInventory().get(1) + ")");
    //     this.OFB.setText("Overwhelming Fireball (" + player.getSpellInventory().get(3) + ")");
    //     this.ffButton.setText("Felix Felicis (" + player.getSpellInventory().get(2) + ")");
    //     this.MSE.setText("Magical Staff Expansion (" + player.getSpellInventory().get(0) + ")");
    //     this.spellJPanel.hexB
    // }

    public void updateSpellInventory(int index, String mode) {
        if (mode=="increase"){
            player.increaseSpellInventory(index);
        } else if (mode=="decrease"){
            player.decreaseSpellInventory(index);
        }
        updateSpellInventoryLabels();
    }
    
    
    
    public class MapPanel extends JPanel implements KeyListener, FrameCloseListener {
        // Initialize Magic staff 
        private ArrayList<ColoredBlock> blocks;

        //previous version
        //private ArrayList<int[]> barrierList;    
        //private String selectedColor = "red";  // Default color

        private ArrayList<int[]> barrierIndexList;
        private String selectedColor = "simple";  // Default color

        public static final int BLOCK_WIDTH = 86; // Width of the block
        public static final int BLOCK_HEIGHT = 26; // Height of the block
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
        public boolean remaintouched = false;
        public boolean spellDropped = false;
        public int droppedSpellIndex;
        public long currentTime;
        private long startTime;

        public int getBallSpeedX() {
            return ballSpeedX;
        }

        public void setBallSpeedX(int ballSpeedX) {
            this.ballSpeedX = ballSpeedX;
        }

        public int getBallSpeedY() {
            return ballSpeedY;
        }

        public void setBallSpeedY(int ballSpeedY) {
            this.ballSpeedY = ballSpeedY;
        }
        
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
            
            
            // timer = new Timer(10, e -> updateGame());
            // timer.start();
            timer = new Timer(10, (ActionEvent e) -> updateGame());
            timer.start();
            startTime = System.currentTimeMillis();
            
            File file = new File(filePath); // File path should be in String data
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                barrierIndexList = (ArrayList<int[]>) ois.readObject(); //get the barrierList from saved map file
                frame.barrierIndexList = this.barrierIndexList;
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            int rewardingCount = 0;
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
                        
                        if (rewardingCount<4){
                            rewbar.setSpellIndex(rewardingCount);
                        } else{
                            Random spellRandom = new Random();     
                            int randomSpellIndex = spellRandom.nextInt(3)+1;
                            rewbar.setSpellIndex(randomSpellIndex);
                        }
                        bArrayList.add(rewbar);
                        rewardingCount+=1;
                        break;
                    default:
                        break;
                }

                repaint();
                
            }
            
            // if (server == null) {
            //     timer = new Timer(2000, (ActionEvent e) -> {
            //         try {
            //             client.refreshInfo(barrierIndexList);
            //         } catch (IOException e1) {
            //             // TODO Auto-generated catch block
            //             e1.printStackTrace();
            //         }
            //     });
            //     timer.start();
            // } else {
            //     timer = new Timer(2000, (ActionEvent e) -> {
            //         try {
            //             server.refreshInfo(barrierIndexList);
            //         } catch (IOException e1) {
            //             // TODO Auto-generated catch block
            //             e1.printStackTrace();
            //         }
            //     });
            //     timer.start();
            // }
        }
        public void removeBlock(int x, int y) {
            Iterator<ColoredBlock> it = blocks.iterator();
            while (it.hasNext()) {
                ColoredBlock block = it.next();
                if (block.rectangle.x == x && block.rectangle.y == y) {
                    it.remove();
                    break;
                }
            }
            repaint();
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
                        if (ballSpeedX<0){
                            ballSpeedX = -ballSpeedX;
                        }
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
                    if (!overwhelmingFireBall.isActive()){
                    
                        if (intersection.width < intersection.height) {
                            ballSpeedX = -ballSpeedX;
                            LOGGER.log(Level.INFO, MessageFormat.format("Ball hit a barrier from the side. New ball speedX: {0}", ballSpeedX));
                        } else {
                            ballSpeedY = -ballSpeedY;
                            LOGGER.log(Level.INFO, MessageFormat.format("Ball hit a barrier from top/bottom. New ball speedY: {0}", ballSpeedY));
                        }
                    }
                    if (block.getColor() == "hollowpurple"){
                        it.remove();
                    }
                
                    int xcoor = block.rectangle.x;
                    int ycoor = block.rectangle.y;
                    for (Barrier barr : bArrayList){
                        if (barr.getXCoordinate() == xcoor && barr.getYCoordinate() == ycoor){

                            if (barr instanceof ReinforcedBarrier & overwhelmingFireBall.isActive()){
                                barr.hit(barr.getHealth());
                            } else{
                                barr.hit(1);
                            }
                            
                            
                            
                            if (barr.isDestroyed()){
                                
                                it.remove();
                                
                                
                                if (barr.isRewarding()){
                                    Rectangle rewblock = new Rectangle(block.rectangle.x + 43, block.rectangle.y+ 23, 20, 20);
                                    blocks.add(new ColoredBlock(rewblock, "rewardbox"));       
                                    spellDropped = true;
                                    droppedSpellIndex = barr.getSpellIndex();
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
                              
                                int[] targetArray = new int[4];

                                if (barr instanceof SimpleBarrier) {
                                    targetArray[0] = xcoor;
                                    targetArray[1] = ycoor;
                                    targetArray[2] = 1;
                                    targetArray[3] = 1;
                                } else if (barr instanceof ReinforcedBarrier) {
                                    targetArray[0] = xcoor;
                                    targetArray[1] = ycoor;
                                    targetArray[2] = 2;
                                    targetArray[3] = 1;
                                } else if (barr instanceof ExplosiveBarrier) {
                                    targetArray[0] = xcoor;
                                    targetArray[1] = ycoor;
                                    targetArray[2] = 3;
                                    targetArray[3] = 1;
                                } else {
                                    targetArray[0] = xcoor;
                                    targetArray[1] = ycoor;
                                    targetArray[2] = 4;
                                    targetArray[3] = 1;
                                }

                                System.out.println(barrierIndexList.removeIf(array -> Arrays.equals(array,targetArray)));
                                frame.barrierIndexList = this.barrierIndexList;
                              
                              
                                score = score + 300 / (double) currentTime;
                                System.out.println(currentTime);
                                String scorest = String.format("%.2f", score);
                                scoreLabel.setText("Score: " + scorest);
                                bArrayList.remove(barr);
                                barrcountlabel.setText("Remaining Barriers: " + bArrayList.size());
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

                    else if (spellDropped){
                        updateSpellInventory(droppedSpellIndex, "increase");
                        spellDropped = false;
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

        public void addCblock(Rectangle b, String selectedcolor){
            blocks.add(new ColoredBlock(b, selectedcolor));
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
                    case "hollowpurple":
                        g.drawImage(img5, block.rectangle.x, block.rectangle.y, null);
                        break;
                        
                    default:
                        break;
                        
                }
                fireBall.draw(g);
                staff.draw(g2d);
                
            }
            for (Barrier barrier : bArrayList) {
                if (barrier.isFrozen()) {
                    for (ColoredBlock block : blocks){
                        //System.out.println(block.rectangle.x + " barrier: " + (barrier.getXCoordinate() - (barrier.getXCoordinate() % BLOCK_WIDTH)));
                        if (block.rectangle.x == (barrier.getXCoordinate() - (barrier.getXCoordinate() % BLOCK_WIDTH))){
                            g.drawImage(img6, block.rectangle.x, block.rectangle.y, null);
                            barrier.setHealth(99);
                            break;
                        }
                    }
                    //g.drawImage(img6, barrier.getXCoordinate(), barrier.getYCoordinate(), null);
                    
                } /*else {
                    if (barrier instanceof SimpleBarrier) {
                        //g.drawImage(img1, barrier.getXCoordinate(), barrier.getYCoordinate(), null);
                    } else if (barrier instanceof ExplosiveBarrier) {
                        //g.drawImage(img3, barrier.getXCoordinate(), barrier.getYCoordinate(), null);
                    } else if (barrier instanceof ReinforcedBarrier) {
                        //g.drawImage(img2, barrier.getXCoordinate(), barrier.getYCoordinate(), null);
                    } else if (barrier instanceof RewardingBarrier) {
                        //g.drawImage(img4, barrier.getXCoordinate(), barrier.getYCoordinate(), null);
                    } else if (barrier instanceof HollowPurpleBarrier) {
                        //g.drawImage(img5, barrier.getXCoordinate(), barrier.getYCoordinate(), null);
                    }
                }
                */
                
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

            public String getColor(){
                return this.color;
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
                    showGameOverFrame("Game Over! No chances left.","assets/gifs/no.gif", "assets/audio/no.wav");
                }
            }
        
            if (bArrayList.size() == 0) {
                timer.stop();
                showGameOverFrame("Game Over! All barriers cleared.","assets/gifs/dance.gif", "assets/audio/dance.wav");
            }
        
            if (player.getChances() == 0) {
                timer.stop();
                showGameOverFrame("Game Over! No chances left.","assets/gifs/no.gif", "assets/audio/no.wav");
            }
        }

        private void showGameOverFrame(String message, String gifpath, String audioPath) {
            JFrame gameOverFrame = new JFrame("Game Over");
            gameOverFrame.setSize(300, 300);
            gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameOverFrame.setLocationRelativeTo(null);
        
            JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
            JButton okButton = new JButton("OK");
            ImageIcon gifIcon = new ImageIcon(gifpath);
            JLabel gifLabel = new JLabel(gifIcon);
            backB = new JButton("Return to home page");
            backB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameOverFrame.dispose();
                    openHome();
                }
            });
            

        
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameOverFrame.dispose();
                }
            });
        
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(messageLabel, gbc);
    
            gbc.gridy = 1;
            gbc.weighty = 0.8;
            gbc.fill = GridBagConstraints.BOTH;
            panel.add(gifLabel, gbc);
    
            gbc.gridy = 2;
            gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.PAGE_END;
            panel.add(okButton, gbc);

            gbc.gridy = 1;
            gbc.weighty = 0.2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.PAGE_END;
            panel.add(backB, gbc);
            
            gameOverFrame.add(panel);
            gameOverFrame.setVisible(true);
            playAudio(audioPath);
        }
        private void playAudio(String audioFilePath) {
            try {
                // Load the audio file
                File audioFile = new File(audioFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                // Get a sound clip resource
                Clip clip = AudioSystem.getClip();

                // Open the audio stream and start playing
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
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
            im.put(KeyStroke.getKeyStroke("F"), "activateFelixFelicis");
            im.put(KeyStroke.getKeyStroke("O"), "activateOverwhelmingFireball");

            im.put(KeyStroke.getKeyStroke("W"), "startBallMovement");
            
            // Adding a mouse listener to start the ball movement
            frame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Trigger the action on left mouse click
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        am.get("startBallMovement").actionPerformed(null);
                    }
                }
            });
            
                        // New bindings for rotation
            im.put(KeyStroke.getKeyStroke("UP"), "rotateClockwise");
            im.put(KeyStroke.getKeyStroke("DOWN"), "rotateCounterClockwise");
            // Secret key combination to remove all barriers
            im.put(KeyStroke.getKeyStroke("control shift D"), "removeAllBarriers");

            im.put(KeyStroke.getKeyStroke("P"), "pauseGame");
            im.put(KeyStroke.getKeyStroke("S"), "saveGame");
            im.put(KeyStroke.getKeyStroke("Q"), "quitGame");

                
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
                    timer.restart();
                    startTime = System.currentTimeMillis();
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
                    if (player.getSpellInventory().get(0)>0){
                        magicalStaffExpansion.activate();
                        updateSpellInventory(0, "decrease");

                    }
                 }
             });
             am.put("activateHexSpell", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                    if (player.getSpellInventory().get(1)>0){
                        hexSpell.activate();
                        updateSpellInventory(1, "decrease");

                    }   
                 }
             });
             am.put("activateFelixFelicis", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (player.getSpellInventory().get(2)>0){
                        felixFelicis.activate();
                        updateSpellInventory(2, "decrease");

                    }
                }
            });
            am.put("activateOverwhelmingFireball", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (player.getSpellInventory().get(3)>0){
                        overwhelmingFireBall.activate();
                        updateSpellInventory(3, "decrease");

                    }
                }
            });
             // Action for removing all barriers
            am.put("removeAllBarriers", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeAllBarriers();
                }
            });

            am.put("pauseGame", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PauseMenu pauseMenu = new PauseMenu(timer,barrierIndexList,mapPanel, player.getSpellInventory(), score, player.getChances());
                    pauseMenu.setVisible(true);
                    timer.stop();
                }
            });

            am.put("saveGame", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });

            am.put("quitGame", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    
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

         // Load map and save map for the game
    

    public void loadGame(int gameIndex) {

        this.blocks = new ArrayList<>();
        this.barrierIndexList = new ArrayList<int[]>();
        bArrayList.clear();

        File inventoryFile = new File("src/inventorySaves/inventorySave" + gameIndex + ".dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inventoryFile))) {

                ArrayList<Integer> playerMetricsArray = (ArrayList<Integer>) ois.readObject();
                ArrayList<Integer> newInventory = new ArrayList<Integer>();

                newInventory.add(playerMetricsArray.get(0));
                newInventory.add(playerMetricsArray.get(1));
                newInventory.add(playerMetricsArray.get(2));
                newInventory.add(playerMetricsArray.get(3));

                player.setSpellInventory(newInventory);
                updateSpellInventoryLabels();

                scoreLabel.setText("Score: " + playerMetricsArray.get(4));
                score = playerMetricsArray.get(4);

                int newChance = playerMetricsArray.get(5);
                int chanceBefore = player.getChances();

                System.out.println(newChance);
                System.out.println(chanceBefore);
                
                if (chanceBefore < newChance) {
                    for (int i = chanceBefore; i < newChance; i++) {
                        player.incChance(chancePanel, labels, heartimg);
                    }
                } else {
                    for (int i = newChance; i < chanceBefore; i++) {
                        player.decChance(chancePanel, labels);
                    }
                }

        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }

        File fileToLoad = new File("src/gameSaves/gameSave" + gameIndex + ".dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {

                barrierIndexList = (ArrayList<int[]>) ois.readObject();

                for (int i = 0; i < barrierIndexList.size(); i++) {
                    int[] currentBarrier = barrierIndexList.get(i);
                    switch (currentBarrier[2]) {
                        case 1:
                            //addBlock(i[0], i[1],"red");
    
                            addBlock(currentBarrier[0], currentBarrier[1],"simple");
                            SimpleBarrier sbar = new SimpleBarrier(currentBarrier[0], currentBarrier[1]);
                            bArrayList.add(sbar);
                            break;
                        case 2:
                            addBlock(currentBarrier[0], currentBarrier[1],"reinforced");
                            Random random = new Random();     
                            int hitnum = random.nextInt(3) + 1;
                            ReinforcedBarrier rbar = new ReinforcedBarrier(hitnum, currentBarrier[0], currentBarrier[1]);
                            bArrayList.add(rbar);
                            break;
                        case 3:
                            addBlock(currentBarrier[0], currentBarrier[1],"explosive");
                            ExplosiveBarrier ebar = new ExplosiveBarrier(currentBarrier[0], currentBarrier[1]);
                            bArrayList.add(ebar);
                            break;
                        case 4:
                            addBlock(currentBarrier[0], currentBarrier[1],"rewarding");
                            RewardingBarrier rewbar = new RewardingBarrier(currentBarrier[0], currentBarrier[1]);
                            bArrayList.add(rewbar);
                            break;
                        default:
                            break;
                    }
                }

                barrcountlabel.setText("Remaining Barriers: " + bArrayList.size());

                repaint();

        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
    }

    @Override
        public void onFrameClosed(int data) {
            System.out.println("Received data from SecondaryFrame: " + data);

            if (data == 0) {

            } else {
                loadGame(data);
            }
            
        }

    }
  
    public void saveGame(ArrayList<int[]> barrierList, ArrayList<Integer> playerInventory) {
        
        GameSlotsFrame gameSlotsFrame = new GameSlotsFrame(barrierList,mapPanel,timer, playerInventory, score, player.getChances());
        gameSlotsFrame.setVisible(true);
    
    }

    public void loadGame() {

        GameSlotsFrame gameSlotsFrame = new GameSlotsFrame(mapPanel, timer);
        gameSlotsFrame.setVisible(true);
    
    }
  
    public void expandPaddle() {
        mapPanel.paddle.width *= 2;
        mapPanel.repaint();
    }
    public void resetPaddle() {
        mapPanel.paddle.width = mapPanel.originalPaddleWidth;
        mapPanel.repaint();
    }

    // public void activateHexCanons() {
    //     mapPanel.activateHexCanons();
    // }
    // public void deactivateHexCanons() {
    //     mapPanel.deactivateHexCanons();
    // }

    public void startGame() {
        ymir.startCoinFlipping();
    }
        
    public static void main(String args[]){
        Player p = new Player("admin", "pass");
        RunningMode run = new RunningMode(1,p);
        run.setVisible(true);
        run.startGame();
    }

}
