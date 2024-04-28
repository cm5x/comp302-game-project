

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MagicalStaff implements KeyListener {
    private double length;
    private double thickness;
    private double rotationAngle;
    private double rotationRate;
    private double movementSpeed;
    private double xPos; // Current x position of the staff
    private boolean expanded;
    private long expansionStartTime;
    private boolean hexActivated;
    private long hexStartTime;
    private boolean rotateLeft;
    private boolean rotateRight;
    private boolean moveLeft;
    private boolean moveRight;

    private static final double ROTATION_LIMIT = 45.0;

    public MagicalStaff(Dimension screenSize) {
        this.length = screenSize.getWidth() * 0.1;
        this.thickness = 20.0;
        this.rotationAngle = 0.0;
        this.rotationRate = 45.0;
        this.movementSpeed = length;
        this.xPos = screenSize.getWidth() /2 ; // Start at the center of the screen
        this.expanded = false;
        this.expansionStartTime = 0;
        this.hexActivated = false;
        this.hexStartTime = 0;
        this.rotateLeft = false;
        this.rotateRight = false;
        this.moveLeft = false;
        this.moveRight = false;
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

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void moveHorizontally() {
        if (moveLeft) {
            xPos -= movementSpeed * 0.02; // Move left
        } else if (moveRight) {
            xPos += movementSpeed * 0.02; // Move right
        }
    }

    
    
    
    
    
    public void updateRotation() {
        if (rotateLeft && rotationAngle > -ROTATION_LIMIT) {
            rotationAngle -= rotationRate * 0.02;
        } else if (rotateRight && rotationAngle < ROTATION_LIMIT) {
            rotationAngle += rotationRate * 0.02;
        } else {
            rotationRate = 45.0; // Reset rotation rate
        }
    }

    public void expand() {
        this.length *= 2.0;
        expanded = true;
        expansionStartTime = System.currentTimeMillis();
    }

    public boolean isExpansionDurationElapsed(int duration) {
        if (!expanded) return true;

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - expansionStartTime;
        return elapsedTime >= duration * 1000;
    }

    public void shorten() {
        this.length /= 2.0;
        expanded = false;
    }

    public void activateHex() {
        hexActivated = true;
        hexStartTime = System.currentTimeMillis();
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isHexActivated() {
        return hexActivated;
    }

    public boolean isHexDurationElapsed(int duration) {
        if (!hexActivated) return true;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - hexStartTime;
        return elapsedTime >= duration * 1000;
    }

    public void deactivateHex() {
        hexActivated = false;
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
}
