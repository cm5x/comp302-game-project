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

import spells.MagicalStaffExpansion;
import spells.Hex;

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
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;
import utilities.BarrierReader;

import java.util.logging.Logger;
import java.util.logging.Level;


import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RunningMode extends JFrame {

    private ArrayList<ArrayList<Barrier>> barriers;
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
    private MagicalStaffExpansion magicalStaffExpansion;
    private Hex hexSpell;
    private static final Logger LOGGER = Logger.getLogger(RunningMode.class.getName());

    public RunningMode() {
        setTitle("Running Mode");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.blockChooserPanel = new JPanel();
        this.blockChooserPanel.setPreferredSize(new Dimension(230, 600));
        this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);
        this.blockChooserPanel.setLayout(new GridLayout(4, 1));

        this.mapPanel = new MapPanel(this);
        this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.mapPanel.setBackground(Color.WHITE);
        this.add(mapPanel);

        pauseButton = new JButton("Pause");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        blockChooserPanel.add(pauseButton);
        blockChooserPanel.add(saveButton);
        blockChooserPanel.add(loadButton);

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
        this.setVisible(true);

        // Initialize the spells
        magicalStaffExpansion = new MagicalStaffExpansion(this);
        hexSpell = new Hex(this);
    }

    class MapPanel extends JPanel implements KeyListener {
        private ArrayList<ColoredBlock> blocks;
        private ArrayList<int[]> barrierIndexList;
        private String selectedColor = "simple";

        private static final int BLOCK_WIDTH = 100;
        private static final int BLOCK_HEIGHT = 20;
        private final RunningMode frame;
        private String filePath = "src/utilities/exampleMap1.dat";

        private Rectangle paddle;
        private Point ballPosition;
        private double ballSpeedX = 3;
        private double ballSpeedY = 3;
        private Timer timer;
        private int paddleSpeed = 10;
        private int paddleMoveDirection = 0;
        private double paddleAngle = 0;
        private int originalPaddleWidth;
        private boolean hexCanonsActive = false;
        private Point leftCanon;
        private Point rightCanon;
        private List<Projectile> projectiles = new ArrayList<>();

        public MapPanel(RunningMode frame) {
            this.frame = frame;
            this.blocks = new ArrayList<>();
            this.barrierIndexList = new ArrayList<int[]>();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            paddle = new Rectangle(screenSize.width / 2, screenSize.height - 60, 150, 20);
            originalPaddleWidth = paddle.width;
            ballPosition = new Point(screenSize.width / 2, screenSize.height - 70);
            timer = new Timer(10, (ActionEvent e) -> updateGame());
            timer.start();

            File file = new File(filePath);

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                barrierIndexList = (ArrayList<int[]>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (int[] i : barrierIndexList) {
                switch (i[2]) {
                    case 1:
                        addBlock(i[0], i[1], "simple");
                        SimpleBarrier sbar = new SimpleBarrier(i[0], i[1]);
                        bArrayList.add(sbar);
                        break;
                    case 2:
                        addBlock(i[0], i[1], "reinforced");
                        Random random = new Random();
                        int hitnum = random.nextInt(3) + 1;
                        ReinforcedBarrier rbar = new ReinforcedBarrier(hitnum, i[0], i[1]);
                        bArrayList.add(rbar);
                        break;
                    case 3:
                        addBlock(i[0], i[1], "explosive");
                        ExplosiveBarrier ebar = new ExplosiveBarrier(i[0], i[1]);
                        bArrayList.add(ebar);
                        break;
                    case 4:
                        addBlock(i[0], i[1], "rewarding");
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

            AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(paddleAngle), paddle.x, paddle.y);
            Shape rotatedPaddle = rotation.createTransformedShape(new Rectangle(paddle.x, paddle.y, paddle.width, paddle.height));
            double rotationAngle = Math.toDegrees(Math.atan2(rotation.getShearY(), rotation.getScaleY()));

            if (new Rectangle(ballPosition.x, ballPosition.y, 1, 1).intersects(paddle) && rotationAngle == 0) {
                ballSpeedY = -ballSpeedY;
                ballPosition.y = paddle.y - 1;
            } else if (new Rectangle(ballPosition.x, ballPosition.y, 1, 1).intersects(paddle)) {
                double angle = Math.atan2(ballPosition.getY() - rotatedPaddle.getBounds2D().getCenterY(), ballPosition.getX() - rotatedPaddle.getBounds2D().getCenterX());
                double incomingAngle = Math.atan2(ballSpeedY, ballSpeedX);
                double reflectionAngle = 2 * angle - incomingAngle;
                double velocityMagnitude = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
                double newVelocityX = Math.cos(reflectionAngle) * velocityMagnitude;
                double newVelocityY = Math.sin(reflectionAngle) * velocityMagnitude;

                ballSpeedX = (int) newVelocityX;
                ballSpeedY = (int) -newVelocityY;

                double check = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
                if (check < 1) {
                    double scale = 3 / check;
                    ballSpeedX *= 2 * scale;
                    ballSpeedY *= 2 * scale;
                }
                ballPosition.y = (int) (rotatedPaddle.getBounds2D().getMinY() - 1);
            }

            Rectangle ballRect = new Rectangle(ballPosition.x, ballPosition.y, 1, 1);
            Iterator<MapPanel.ColoredBlock> it = blocks.iterator();
            while (it.hasNext()) {
                MapPanel.ColoredBlock block = it.next();
                if (ballRect.intersects(block.rectangle)) {
                    Rectangle intersection = ballRect.intersection(block.rectangle);
                    if (intersection.width < intersection.height) {
                        ballSpeedX = -ballSpeedX;
                    } else {
                        ballSpeedY = -ballSpeedY;
                    }
                    it.remove();
                    break;
                }
            }

            // Move hex projectiles
            moveProjectiles();

            repaint();
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

            Graphics2D g2d = (Graphics2D) g.create();
            g.drawImage(backimg, 0, 0, this.getWidth(), this.getHeight(), this);

            for (ColoredBlock block : blocks) {
                switch (block.color) {
                    case "simple":
                        g.drawImage(img1, block.rectangle.x, block.rectangle.y, null);
                        break;
                    case "reinforced":
                        g.drawImage(img2, block.rectangle.x, block.rectangle.y, null);
                        for (Barrier barrier : bArrayList) {
                            if (barrier.getXCoordinate() == block.rectangle.x && barrier.getYCoordinate() == block.rectangle.y) {
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
                    default:
                        break;
                }
                g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
                g.setColor(Color.BLACK);
                g.fillOval(ballPosition.x, ballPosition.y, 15, 15);
            }

            int centerX = paddle.x + paddle.width / 2;
            int centerY = paddle.y + paddle.height / 2;
            g2d.rotate(Math.toRadians(paddleAngle), centerX, centerY);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);

            if (hexCanonsActive) {
                g2d.setColor(Color.MAGENTA);
                g2d.fillRect(leftCanon.x - 5, leftCanon.y - 20, 10, 20);
                g2d.fillRect(rightCanon.x - 5, rightCanon.y - 20, 10, 20);
            }

            for (Projectile projectile : projectiles) {
                g.setColor(Color.MAGENTA);
                g.fillRect(projectile.x, projectile.y, 5, 10);
            }

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

        private void updateGame() {
            moveBall();
            movePaddle();
            repaint();
        }

        private void movePaddle() {
            setUpKeyBindings();
            if (paddleMoveDirection != 0) {
                int newPaddleX = paddle.x + (paddleSpeed * paddleMoveDirection);
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

            im.put(KeyStroke.getKeyStroke("T"), "activateMagicalStaff");
            im.put(KeyStroke.getKeyStroke("H"), "activateHexSpell");

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
        }

        public void activateHexCanons() {
            hexCanonsActive = true;
            updateCanonPositions();
            repaint();
        }

        public void deactivateHexCanons() {
            hexCanonsActive = false;
            repaint();
        }

        private void updateCanonPositions() {
            int paddleCenterX = paddle.x + paddle.width / 2;
            int paddleCenterY = paddle.y + paddle.height / 2;
            double angleRadians = Math.toRadians(paddleAngle);

            leftCanon = new Point(
                (int) (paddle.x - 10 * Math.cos(angleRadians)),
                (int) (paddle.y - 10 * Math.sin(angleRadians))
            );

            rightCanon = new Point(
                (int) (paddle.x + paddle.width + 10 * Math.cos(angleRadians)),
                (int) (paddle.y - 10 * Math.sin(angleRadians))
            );
        }

        private void fireHexes() {
            if (hexCanonsActive) {
                fireHex(leftCanon);
                fireHex(rightCanon);
            }
        }

        private void fireHex(Point canonPosition) {
            projectiles.add(new Projectile(canonPosition.x, canonPosition.y, -5, 0));
        }

        private void moveProjectiles() {
            Iterator<Projectile> it = projectiles.iterator();
            while (it.hasNext()) {
                Projectile projectile = it.next();
                projectile.y += projectile.speedY;
                if (projectile.y < 0) {
                    it.remove();
                    continue;
                }

                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, 5, 10);
                Iterator<MapPanel.ColoredBlock> blockIt = blocks.iterator();
                while (blockIt.hasNext()) {
                    MapPanel.ColoredBlock block = blockIt.next();
                    if (projectileRect.intersects(block.rectangle)) {
                        blockIt.remove();
                        it.remove();
                        break;
                    }
                }
            }
        }

        private class Projectile {
            int x, y;
            int speedY;

            Projectile(int x, int y, int speedX, int speedY) {
                this.x = x;
                this.y = y;
                this.speedY = speedY;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddleMoveDirection = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddleMoveDirection = 1;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

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

    public void expandPaddle() {
        mapPanel.paddle.width *= 2;
        mapPanel.repaint();
    }

    public void resetPaddle() {
        mapPanel.paddle.width = mapPanel.originalPaddleWidth;
        mapPanel.repaint();
    }

    public void activateHexCanons() {
        mapPanel.activateHexCanons();
    }
    public void deactivateHexCanons() {
        mapPanel.deactivateHexCanons();
    }
    
    public static void main(String args[]) {
        RunningMode run = new RunningMode();
        run.setVisible(true);
    }
}
