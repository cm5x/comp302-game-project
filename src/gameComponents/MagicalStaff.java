package gameComponents;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Rectangle;
/**
     * Constructor of MagicalStaff object.
     * @param screenSize the dimensions of the screen
     * @requires a valid screensize which is not equals to zero.
     * @modifies this
     * @effects Initializes the MagicalStaff with parameters also loads the image.
     */
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
        this.rotationAngle = 0;
        this.rotationRate = 45;
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

    public int getLength() {
        return length;
    }

    public void setLength(int newLength) {
        this.length = newLength;
    }

    public double getThickness() {
        return thickness;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public Rectangle getBounds() {
        return new Rectangle(xPos, yPos, length, thickness);
    }

    public void moveHorizontally() {
        if (moveLeft) {
            xPos -= movementSpeed; // Move left
        } else if (moveRight) {
            xPos += movementSpeed; // Move right
        }
    }
    /**
     * Update the rotation of the staff with user key input.
     * @requires none
     * @modifies modifies this.rotationAngle
     * @effects Rotates the staff left or right with settled rotationRate if the corresponding flags are set,
     * ensuring the rotation angle does not exceed the ROTATION_LIMIT.
     */
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
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        // Rotate around the center of the staff
        g2d.rotate(rotationAngle, xPos + length / 2, yPos + thickness / 2);
        g2d.drawImage(image, xPos, yPos, length, thickness, null);

        g2d.setTransform(old); // Restore original transformation
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

    public void setXPos(int num) {
        xPos = num;
    }

    public void setYPos(int num) {
        yPos = num;
    }

    public void setRotationAngle(double num) {
        rotationAngle = num;
    }

    public double getYPos() {
        return yPos;
    }    
}
