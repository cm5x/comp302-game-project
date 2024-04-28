import javax.swing.*;
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

import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;





public class MapDesigner extends JFrame {
    private final MapPanel mapPanel;
    private final JPanel blockChooserPanel;
    private final JTextArea infoTextArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapDesigner::new);
    }

    public MapDesigner() {
        super("Map Designer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1920, 1080);

        // Map panel
        this.mapPanel = new MapPanel(this); 
        this.mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4   ));  // Add a black line border
        this.mapPanel.setBackground(Color.WHITE);  // Set a different background color
        this.add(mapPanel, BorderLayout.CENTER);

        // Block chooser panel
        this.blockChooserPanel = new JPanel();
        this.blockChooserPanel.setPreferredSize(new Dimension(230, 600));
        this.blockChooserPanel.setBackground(Color.LIGHT_GRAY);  // Differentiate by color
        initializeBlockChooser();
        this.add(blockChooserPanel, BorderLayout.WEST);

        // Info text area

        infoTextArea = new JTextArea(5, 20);
        infoTextArea.setEditable(false);  // Make text area non-editable
        infoTextArea.setLineWrap(rootPaneCheckingEnabled);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        blockChooserPanel.add(scrollPane, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void initializeBlockChooser() {
        String[] colors = {"Red", "Blue", "Green"};
        for (String color : colors) {
            JButton button = new JButton("Place " + color + " Block");
            // button.addActionListener(e -> mapPanel.setSelectedColor(color.toLowerCase()));
            button.addActionListener(e -> {
                mapPanel.setSelectedColor(color.toLowerCase());
                infoTextArea.append("Selected " + color + " block.\n");  // Update text area
            });
            blockChooserPanel.add(button);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));  // Arrange buttons vertically

        // JButton saveButton = new JButton("Save Map");
        // saveButton.addActionListener(e -> mapPanel.saveMap("map_data.dat"));
        // buttonPanel.add(saveButton);

        // JButton loadButton = new JButton("Load Map");
        // loadButton.addActionListener(e -> mapPanel.loadMap("map_data.dat"));
        // buttonPanel.add(loadButton);


        JButton saveButton = new JButton("Save Map");
        saveButton.addActionListener(e -> mapPanel.saveMap());
        buttonPanel.add(saveButton);

        JButton loadButton = new JButton("Load Map");
        loadButton.addActionListener(e -> mapPanel.loadMap());
        buttonPanel.add(loadButton);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> switchToGameMode());
        blockChooserPanel.add(startGameButton, BorderLayout.NORTH);


        blockChooserPanel.add(buttonPanel, BorderLayout.NORTH);



    }

    public void appendInfoText(String text) {
        infoTextArea.append(text + "\n");
    }



    // public void switchToGameMode() {
    //     remove(mapPanel);
    //     GamePanel gamePanel = new GamePanel();
    //     add(gamePanel, BorderLayout.CENTER);
    //     validate();
    //     repaint();
    // }

    public void switchToGameMode() {
        remove(mapPanel);
        // remove(blockChooserPanel);
        // remove(infoTextArea);
        GamePanel gamePanel = new GamePanel(this, mapPanel.getBarriers());
        add(gamePanel, BorderLayout.CENTER);
        validate();
        repaint();
    }
}


class MapPanel extends JPanel {
    private ArrayList<ColoredBlock> blocks;
    private String selectedColor = "red";  // Default color
    private static final int BLOCK_WIDTH = 200; // Width of the block
    private static final int BLOCK_HEIGHT = 40; // Height of the block
    private final MapDesigner frame;


    public MapPanel(MapDesigner frame) {
        this.frame = frame; 
        this.blocks = new ArrayList<>();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if the click is within the upper half of the panel
                // if (e.getY() < getHeight() / 1.3) {
                //     addBlock(e.getX(), e.getY());
                //     repaint();
                // }
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e.getX(), e.getY(), e);
                } else if (SwingUtilities.isLeftMouseButton(e) && e.getY() < getHeight() / 1.2) {
                    if (addBlock(e.getX(), e.getY())) {
                        frame.appendInfoText("Placed " + selectedColor + " block at (" + e.getX() + ", " + e.getY() + ")");
                    } else {
                        frame.appendInfoText("Failed to place block at (" + e.getX() + ", " + e.getY() + ") - Overlap");
                    }
                    repaint();
                }

            }


        });





    }

    private void showContextMenu(int x, int y, MouseEvent e) {
        for (ColoredBlock block : blocks) {
            if (block.rectangle.contains(x, y)) {
                JPopupMenu contextMenu = new JPopupMenu();
                JMenuItem deleteItem = new JMenuItem("Delete");
                deleteItem.addActionListener(ev -> {
                    blocks.remove(block);
                    repaint();
                    frame.appendInfoText("Deleted " + block.color + " block at (" + block.rectangle.x + ", " + block.rectangle.y + ")");
                });
                contextMenu.add(deleteItem);
                contextMenu.show(this, x, y);
                break;
            }
        }
    }

    public void setSelectedColor(String color) {
        this.selectedColor = color;
    }

    public ArrayList<MapPanel.ColoredBlock> getBarriers(){

        return this.blocks;
    }

    private boolean addBlock(int x, int y) {
        int gridX = x - (x % BLOCK_WIDTH);
        int gridY = y - (y % BLOCK_HEIGHT);
        for (ColoredBlock block : blocks) {
            if (block.rectangle.intersects(new Rectangle(gridX, gridY, BLOCK_WIDTH, BLOCK_HEIGHT))) {
                return false; // Block already exists or overlaps in this area
            }
        }
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
        // Draw a line indicating the maximum Y value for placing blocks
        int maxY = (int)(getHeight() / 1.2);
        g.setColor(Color.GRAY); // Set line color
        g.drawLine(0, maxY, getWidth(), maxY); // Draw line from the left to the right side of the panel

        // Optionally, add a label or some text to explain the line
        g.drawString("Designable area boundary", 5, maxY - 5); // Display text just above the line
    }

    public static class ColoredBlock implements Serializable {
        Rectangle rectangle;
        String color;

        ColoredBlock(Rectangle rectangle, String color) {
            this.rectangle = rectangle;
            this.color = color;
        }
    }

    // public void saveMap(String filePath) {
    //     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
    //         oos.writeObject(blocks);
    //         frame.appendInfoText("Map saved successfully.");
    //     } catch (IOException e) {
    //         frame.appendInfoText("Error saving map: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }

    // public void loadMap(String filePath) {
    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
    //         blocks = (ArrayList<ColoredBlock>) ois.readObject();
    //         repaint();
    //         frame.appendInfoText("Map loaded successfully.");
    //     } catch (IOException | ClassNotFoundException e) {
    //         frame.appendInfoText("Error loading map: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }

    public void saveMap() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(blocks);
                frame.appendInfoText("Map saved successfully to " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                frame.appendInfoText("Error saving map: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

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
    }


}


class GamePanel extends JPanel implements KeyListener {
    private List<MapPanel.ColoredBlock> barriers; // Use ColoredBlock from MapPanel

    private Rectangle paddle;
    private Point ballPosition;
    private int ballSpeedX = 2;
    private int ballSpeedY = 2;
    private Timer timer;
    private int paddleSpeed = 10; // Speed of paddle movement
    private int paddleMoveDirection = 0; // 0 = no movement, -1 = left, 1 = right

    private final MapDesigner frame;

    // public GamePanel() {
    //     paddle = new Rectangle(100, 450, 100, 20);  // Initial position and size of the paddle
    //     ballPosition = new Point(150, 435);  // Initial position of the ball
    //     setFocusable(true);
    //     addKeyListener(this);
    //     timer = new Timer(10, e -> moveBall());
    //     timer.start();
    // }

    // public GamePanel(MapDesigner frame, List<MapPanel.ColoredBlock> barriers) {
    //     this.frame = frame;
    //     this.barriers = new ArrayList<>(barriers); // Copy barriers from the map
    //     paddle = new Rectangle(600, 950, 150, 20);  // Initial position and size of the paddle
    //     ballPosition = new Point(650, 950);  // Initial position of the ball
    //     setFocusable(true);
    //     addKeyListener(this);
    //     timer = new Timer(10, e -> moveBall());
    //     timer.start();

    //     requestFocusInWindow();

    // }
    public GamePanel(MapDesigner frame, List<MapPanel.ColoredBlock> barriers) {
        this.frame = frame;
        this.barriers = new ArrayList<>(barriers);
        paddle = new Rectangle(600, 950, 150, 20);
        ballPosition = new Point(650, 940);
        setFocusable(true);
        requestFocusInWindow();  // Request focus

        addKeyListener(this);
        timer = new Timer(10, e -> updateGame());
        timer.start();
    }

//  this moveBall or checkCollision are same, checkcollision includes also barrier crush.
    // private void moveBall() {
    //     ballPosition.x += ballSpeedX;
    //     ballPosition.y += ballSpeedY;
    //     // Collision with walls
    //     if (ballPosition.x < 0 || ballPosition.x > getWidth()) {
    //         ballSpeedX = -ballSpeedX;
    //     }
    //     if (ballPosition.y < 0 || ballPosition.y > getHeight()) {
    //         ballSpeedY = -ballSpeedY;
    //     }
    //     // Collision with the paddle
    //     if (ballPosition.y + 10 >= paddle.y && ballPosition.x + 5 >= paddle.x && ballPosition.x <= paddle.x + paddle.width) {
    //         ballSpeedY = -ballSpeedY;
    //     }
    //     repaint();
    // }


    //This is checkCollision function, I named it as moveBall
    // private void moveBall() {

    //     ballPosition.x += ballSpeedX;
    //     ballPosition.y += ballSpeedY;
    //     if (ballPosition.x < 0 || ballPosition.x > getWidth()) {
    //         ballSpeedX = -ballSpeedX;
    //     }
    //     if (ballPosition.y < 0) {
    //         ballSpeedY = -ballSpeedY;
    //     }
    //     if (ballPosition.y > getHeight()) { // Ball goes below the paddle
    //         timer.stop();
    //         JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
    //     }

    //     // Check collision with the paddle
    //     if (new Rectangle(ballPosition.x, ballPosition.y, 10, 10).intersects(paddle)) {
    //         ballSpeedY = -ballSpeedY;
    //         ballPosition.y = paddle.y - 10; // Adjust ball position to avoid sticking
    //     }

    //     // Check collision with barriers
    //     Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
    //     while (it.hasNext()) {
    //         MapPanel.ColoredBlock block = it.next();
    //         if (new Rectangle(ballPosition.x, ballPosition.y, 10, 10).intersects(block.rectangle)) {
    //             ballSpeedY = -ballSpeedY; // Reflect the ball
    //             it.remove(); // Remove the barrier on hit
    //             break;
    //         }
    //     }

    //     repaint();
    // }

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
    //     System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode()));  // Debugging line
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         // paddle.x = Math.max(0, paddle.x - 10);
    //         paddle.x = paddle.x-100;
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         // paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //         paddle.x = paddle.x+100;
    //     }
    //     repaint();
    // }
    

    // @Override
    // public void keyTyped(KeyEvent e) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
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
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddleMoveDirection = -1; // Move left
            frame.appendInfoText("key activation.");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleMoveDirection = 1; // Move right
            frame.appendInfoText("key activation.");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleMoveDirection = 0; // Stop moving when key is released
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method can be left empty if not used
    }

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

}


