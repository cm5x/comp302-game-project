package gameComponents;
import  org.junit.*;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class MagicalStaffTest {

    private MagicalStaff staff;
    private Dimension screenSize;

    @Before
    public void setUp() {
        screenSize = new Dimension(800, 600);
        staff = new MagicalStaff(screenSize);
    }

    @Test
    public void testInitialPositionAndState() {
        assertEquals((int) (screenSize.getWidth() / 2), staff.getXPos(), 0);
        assertEquals((int) screenSize.getHeight() - 200, staff.getYPos(), 0);
        assertEquals(0, staff.getRotationAngle(), 0.01);
        assertFalse(staff.rotateLeft);
        assertFalse(staff.rotateRight);
        assertFalse(staff.moveLeft);
        assertFalse(staff.moveRight);
    }

    @Test
    public void testMoveLeft() {
        int initialXPos = staff.getXPos();
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_LEFT));
        staff.update();
        assertTrue(staff.getXPos() < initialXPos);
    }

    @Test
    public void testMoveRight() {
        int initialXPos = staff.getXPos();
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_RIGHT));
        staff.update();
        assertTrue(staff.getXPos() > initialXPos);
    }

    @Test
    public void testRotateLeft() {
        double initialAngle = staff.getRotationAngle();
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_A));
        staff.update();
        assertTrue(staff.getRotationAngle() < initialAngle);
    }

    @Test
    public void testRotateRight() {
        double initialAngle = staff.getRotationAngle();
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_D));
        staff.update();
        assertTrue(staff.getRotationAngle() > initialAngle);
    }

    @Test
    public void testMovementBoundary() {
        staff.setXPos(0);
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_LEFT));
        staff.update();
        assertEquals(0, staff.getXPos(), 0);

        staff.setXPos(screenSize.width - staff.getLength());
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_RIGHT));
        staff.update();
        assertEquals(screenSize.width - staff.getLength(), staff.getXPos(), 0);
    }

    @Test
    public void testRotationBoundary() {
        staff.setRotationAngle(-MagicalStaff.ROTATION_LIMIT);
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_A));
        staff.update();
        assertEquals(-MagicalStaff.ROTATION_LIMIT, staff.getRotationAngle(), 0.01);

        staff.setRotationAngle(MagicalStaff.ROTATION_LIMIT);
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_D));
        staff.update();
        assertEquals(MagicalStaff.ROTATION_LIMIT, staff.getRotationAngle(), 0.01);
    }
}
