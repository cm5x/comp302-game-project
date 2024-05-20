package deneme;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler listens to key events and sets flags based on the keys pressed.
 */
public class KeyHandler implements KeyListener {

    public boolean moveLeft = false;
    public boolean moveRight = false;
    public boolean rotateLeft = false;
    public boolean rotateRight = false;

    /**
     * Handles the event when a key is pressed.
     *
     * Requires: The KeyEvent 'e' must not be null.
     * Modifies: this.moveLeft, this.moveRight, this.rotateLeft, this.rotateRight
     * Effects: Sets moveLeft to true if the left arrow key (KeyEvent.VK_LEFT) is pressed.
     *          Sets moveRight to true if the right arrow key (KeyEvent.VK_RIGHT) is pressed.
     *          Sets rotateLeft to true if the 'A' key (KeyEvent.VK_A) is pressed.
     *          Sets rotateRight to true if the 'D' key (KeyEvent.VK_D) is pressed.
     *          No flags are modified if any other key is pressed.
     *
     * @param e the key event that triggered this method
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e == null) throw new IllegalArgumentException("KeyEvent cannot be null");
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
        // Not used in this example
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this example
    }
}
