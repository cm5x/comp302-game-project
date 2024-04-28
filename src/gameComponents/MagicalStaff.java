import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MagicalStaff implements KeyListener {
    private double length; // Length of the staff
    private double thickness; // Thickness of the staff
    private double rotationAngle; // Current rotation angle of the staff
    private double rotationRate; // Rate at which the staff rotates (in degrees/second)
    private double movementSpeed; // Movement speed of the staff
    private boolean expanded; // Flag indicating if the staff is expanded
    private long expansionStartTime; // Time when the staff was expanded
    private boolean hexActivated; // Flag indicating if the hex spell is activated
    private long hexStartTime; // Time when the hex spell was activated
    private boolean rotateLeft; // Flag to track left rotation key state
    private boolean rotateRight; // Flag to track right rotation key state

    // Constants
    private static final double ROTATION_LIMIT = 45.0; // Maximum rotation angle in degrees (45 or 135)

    // Constructor
    public MagicalStaff(Dimension screenSize) {
        this.length = screenSize.getWidth() * 0.1; // Length is 10% of the screen width
        this.thickness = 20.0; // Staff thickness is 20px
        this.rotationAngle = 0.0; // Initial rotation angle (horizontal)
        this.rotationRate = 45.0; // Default rotation rate (45 degrees/second)
        this.movementSpeed = length; // Default movement speed (L/second)
        this.expanded = false; // Staff starts as not expanded
        this.expansionStartTime = 0; // No expansion start time initially
        this.hexActivated = false; // Hex spell starts as not activated
        this.hexStartTime = 0; // No hex spell start time initially
        this.rotateLeft = false; // Left rotation key state
        this.rotateRight = false; // Right rotation key state
    }

    // Getters for staff properties
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

    // Method to handle horizontal movement of the staff
    public void moveHorizontally(boolean isPressed) {
        double moveOffset = length / 2.0;
        if (isPressed) {
            // Button is held down (fast movement)
            movementSpeed = 2.0 * length;
        } else {
            // Button is released (normal movement)
            movementSpeed = length;
        }
    }

    // Method to update rotation of the staff based on input direction (A for left, D for right)
    public void updateRotation() {
        if (rotateLeft && rotationAngle > -ROTATION_LIMIT) {
            rotationAngle -= rotationRate * 0.02; // Update rotation angle (A key pressed)
        } else if (rotateRight && rotationAngle < ROTATION_LIMIT) {
            rotationAngle += rotationRate * 0.02; // Update rotation angle (D key pressed)
        } else {
            // No rotation keys pressed, reset rotation rate
            rotationRate = 45.0; // Reset rotation rate to default
        }
    }

    // Method to expand the staff
    public void expand() {
        this.length *= 2.0; // Double the length
        expanded = true;
        expansionStartTime = System.currentTimeMillis();
    }

    // Method to check if the expansion duration has elapsed
    public boolean isExpansionDurationElapsed(int duration) {
        if (!expanded) return true;

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - expansionStartTime;
        return elapsedTime >= duration * 1000;
    }

    // Method to shorten the staff
    public void shorten() {
        this.length /= 2.0; // Halve the length
        expanded = false;
    }

    // Method to activate the hex spell
    public void activateHex() {
        hexActivated = true;
        hexStartTime = System.currentTimeMillis();
    }

    // Getter to check if the staff is expanded
    public boolean isExpanded() {
        return expanded;
    }

    // Getter to check if the hex spell is activated
    public boolean isHexActivated() {
        return hexActivated;
    }

    // Method to check if the hex spell duration has elapsed
    public boolean isHexDurationElapsed(int duration) {
        if (!hexActivated) return true;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - hexStartTime;
        return elapsedTime >= duration * 1000;
    }

    // Method to deactivate the hex spell
    public void deactivateHex() {
        hexActivated = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            // Arrow keys for horizontal movement
            moveHorizontally(true); // Set horizontal movement state to true
        } else if (key == KeyEvent.VK_A) {
            // Key A for left rotation
            rotateLeft = true; // Set left rotation state to true
        } else if (key == KeyEvent.VK_D) {
            // Key D for right rotation
            rotateRight = true; // Set right rotation state to true
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            // Arrow keys released
            moveHorizontally(false); // Set horizontal movement state to false
        } else if (key == KeyEvent.VK_A) {
            // Key A released
            rotateLeft = false; // Set left rotation state to false
        } else if (key == KeyEvent.VK_D) {
            // Key D released
            rotateRight = false; // Set right rotation state to false
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Empty 
    }
}
