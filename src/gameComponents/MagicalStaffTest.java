package gameComponents;
import org.junit.*;
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
    public void testInitialRotationState() {
        assertEquals(0, staff.getRotationAngle(), 0.01);
    }

    @Test
    public void testRotateLeft() {
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_A));
        staff.updateRotation();
        assertTrue(staff.getRotationAngle() < 0);
    }

    @Test
    public void testRotateRight() {
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_D));
        staff.updateRotation();
        assertTrue(staff.getRotationAngle() > 0);
    }

    @Test
    public void testRotateLeftBoundary() {
        staff.setRotationAngle(-MagicalStaff.ROTATION_LIMIT);
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_A));
        staff.updateRotation();
        assertEquals(-MagicalStaff.ROTATION_LIMIT, staff.getRotationAngle(), 0.01);
    }

    @Test
    public void testRotateRightBoundary() {
        staff.setRotationAngle(MagicalStaff.ROTATION_LIMIT);
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_D));
        staff.updateRotation();
        assertEquals(MagicalStaff.ROTATION_LIMIT, staff.getRotationAngle(), 0.01);
    }

    @Test
    public void testRotationStepSize() {
        double initialAngle = staff.getRotationAngle();
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_A));
        staff.updateRotation();
        assertEquals(initialAngle - staff.rotationRate * 0.02, staff.getRotationAngle(), 0.01);

        initialAngle = staff.getRotationAngle();
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_D));
        staff.updateRotation();
        assertEquals(initialAngle + staff.rotationRate * 0.02, staff.getRotationAngle(), 0.01);
    }

    @Test
    public void testRotationDirectionFlags() {
        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_A));
        assertTrue(staff.rotateLeft);
        assertFalse(staff.rotateRight);

        staff.keyPressed(new KeyEvent(new java.awt.Component(){}, 0, 0, 0, KeyEvent.VK_D));
        assertTrue(staff.rotateRight);
        assertFalse(staff.rotateLeft);
    }

    // Glass-Box 
    @Test
    public void testUpdateRotationInternalLogic() {
        // Test rotating left
        staff.rotateLeft = true;
        staff.updateRotation();
        double expectedAngleLeft = -staff.rotationRate * 0.02;
        assertEquals(expectedAngleLeft, staff.getRotationAngle(), 0.01);

        // Reset angle
        staff.setRotationAngle(0);

        // Test rotating right
        staff.rotateRight = true;
        staff.updateRotation();
        double expectedAngleRight = staff.rotationRate * 0.02;
        assertEquals(expectedAngleRight, staff.getRotationAngle(), 0.01);

        // Test no rotation when both flags are false
        staff.rotateLeft = false;
        staff.rotateRight = false;
        double initialAngle = staff.getRotationAngle();
        staff.updateRotation();
        assertEquals(initialAngle, staff.getRotationAngle(), 0.01);
    }
}
