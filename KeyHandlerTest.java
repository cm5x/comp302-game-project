package deneme;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.KeyEvent;

public class KeyHandlerTest {

    private KeyHandler keyHandler;

    @Before
    public void setUp() {
        keyHandler = new KeyHandler();
    }

    @Test
    public void testLeftArrowKeyPressed() {
        KeyEvent leftArrow = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        keyHandler.keyPressed(leftArrow);
        assertTrue(keyHandler.moveLeft);
        assertFalse(keyHandler.moveRight);
        assertFalse(keyHandler.rotateLeft);
        assertFalse(keyHandler.rotateRight);
    }

    @Test
    public void testRightArrowKeyPressed() {
        KeyEvent rightArrow = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        keyHandler.keyPressed(rightArrow);
        assertTrue(keyHandler.moveRight);
        assertFalse(keyHandler.moveLeft);
        assertFalse(keyHandler.rotateLeft);
        assertFalse(keyHandler.rotateRight);
    }

    @Test
    public void testAKeyPressed() {
        KeyEvent aKey = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        keyHandler.keyPressed(aKey);
        assertTrue(keyHandler.rotateLeft);
        assertFalse(keyHandler.moveLeft);
        assertFalse(keyHandler.moveRight);
        assertFalse(keyHandler.rotateRight);
    }

    @Test
    public void testDKeyPressed() {
        KeyEvent dKey = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        keyHandler.keyPressed(dKey);
        assertTrue(keyHandler.rotateRight);
        assertFalse(keyHandler.moveLeft);
        assertFalse(keyHandler.moveRight);
        assertFalse(keyHandler.rotateLeft);
    }

    @Test
    public void testNonRelevantKeyPressed() {
        KeyEvent otherKey = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        keyHandler.keyPressed(otherKey);
        assertFalse(keyHandler.moveLeft);
        assertFalse(keyHandler.moveRight);
        assertFalse(keyHandler.rotateLeft);
        assertFalse(keyHandler.rotateRight);
    }

    // Additional tests to cover more edge cases

    @Test(expected = IllegalArgumentException.class)
    public void testNullKeyEvent() {
        keyHandler.keyPressed(null);
    }

    @Test
    public void testMultipleKeysPressed() {
        KeyEvent leftArrow = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent rightArrow = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        keyHandler.keyPressed(leftArrow);
        keyHandler.keyPressed(rightArrow);
        assertTrue(keyHandler.moveLeft);
        assertTrue(keyHandler.moveRight);
    }
}
