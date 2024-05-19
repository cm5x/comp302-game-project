package gameComponents;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Image;

public class MagicalStaff implements KeyListener {
    private int length;
    private int thickness;
    private double rotationAngle;
    private double rotationRate;
    private double movementSpeed;
    private int xPos; // Current x position of the staff
    private int yPos;
    private boolean expanded;
    private long expansionStartTime;
    private boolean hexActivated;
    private long hexStartTime;
    private boolean rotateLeft;
    private boolean rotateRight;
    private boolean moveLeft;
    private boolean moveRight;
    private Image image;

    private static final double ROTATION_LIMIT = 45.0;

    public MagicalStaff(Dimension screenSize) {
        this.length = (int) (screenSize.getWidth() * 0.1);
        this.thickness = 20;
        this.rotationAngle = 0.0;
        this.rotationRate = 45.0;
        this.movementSpeed = 5.0; // Adjust movement speed as needed
        this.xPos = (int) (screenSize.getWidth() / 2); // Start at the center of the screen
        this.yPos = (int) screenSize.getHeight()-200;
        this.expanded = false;
        this.expansionStartTime = 0;
        this.hexActivated = false;
        this.hexStartTime = 0;
        this.rotateLeft = false;
        this.rotateRight = false;
        this.moveLeft = false;
        this.moveRight = false;
        loadImage();
    }

    public double getLength() {
        return length;
    }

    public double getThickness() {
        return thickness;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void moveHorizontally() {
        if (moveLeft) {
            xPos -= movementSpeed; // Move left
        } else if (moveRight) {
            xPos += movementSpeed; // Move right
        }
    }

    public void updateRotation() {
        if (rotateLeft && rotationAngle > -ROTATION_LIMIT) {
            rotationAngle -= rotationRate * 0.02;
        } else if (rotateRight && rotationAngle < ROTATION_LIMIT) {
            rotationAngle += rotationRate * 0.02;
        }
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResource("/assets/images/200Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, xPos, yPos, length, thickness, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            moveLeft = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            moveRight = true;
        } else if (key == KeyEvent.VK_A) {
            rotateLeft = true;
        } else if (key == KeyEvent.VK_D) {
            rotateRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            moveLeft = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            moveRight = false;
        } else if (key == KeyEvent.VK_A) {
            rotateLeft = false;
        } else if (key == KeyEvent.VK_D) {
            rotateRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used for this example
    }

    // Update method to be called in the game loop
    public void update() {
        moveHorizontally();
        updateRotation();
    }

    // Getter for the current x position of the staff
    public double getXPos() {
        return xPos;
    }
}
