package spells;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import gameComponents.HollowPurpleBarrier;
import gameComponents.Player;
import gameMechanics.MapPanel.ColoredBlock;
import spells.HollowPurple;
import view.RunningMode;

public class HollowPurpleTest {
    private HollowPurple spell;
    private RunningMode runningMode;

    @Before
    public void setUp() {
        runningMode = new RunningMode(1, new Player("uname", "pass"));
        spell = new HollowPurple("HollowPurple", runningMode);
        System.out.println("Setup completed. RunningMode and HollowPurple instances are initialized.");
    }

    @Test
    // Black-Box Test: Verify barriers are added correctly.
    public void testPerformActionAddsBarriers() {
        System.out.println("Running testPerformActionAddsBarriers...");
        spell.performAction();

        ColoredBlock[] blocks = runningMode.getMapPanel().getBlocks();
        long hollowPurpleCount = Arrays.stream(blocks).filter(b -> b.color.equals("hollowpurple")).count();
        assertEquals("There should be 8 HollowPurple barriers added to the map.", 8, hollowPurpleCount);
        System.out.println("Tested that 8 HollowPurple barriers are added to the map.");
    }

    @Test
    // Glass-Box Test: Verify the mapPanel is modified correctly.
    public void testPerformActionModifiesMapPanel() {
        System.out.println("Running testPerformActionModifiesMapPanel...");
        int initialBlockCount = runningMode.getMapPanel().getBlockCount("hollowpurple");
        spell.performAction();
        int finalBlockCount = runningMode.getMapPanel().getBlockCount("hollowpurple");
        assertEquals("Map panel should have 8 more hollow purple blocks.", 8, finalBlockCount - initialBlockCount);
        System.out.println("Tested that the mapPanel is modified correctly by adding 8 new hollow purple blocks.");
    }

    @Test
    // Representation Invariant Test: Check the repOk method.
    public void testRepOkAfterPerformAction() {
        System.out.println("Running testRepOkAfterPerformAction...");
        spell.performAction();
        assertTrue(spell.repOk(), "repOk should return true after performAction.");
        System.out.println("Tested that repOk returns true after performAction.");
    }

    @Test
// Black-Box Test: Verify that barriers are added at valid positions.
    public void testBarriersAddedAtValidPositions() {
        System.out.println("Running testBarriersAddedAtValidPositions...");
        spell.performAction();

        ColoredBlock[] blocks = runningMode.getMapPanel().getBlocks();
        boolean allValidPositions = Arrays.stream(blocks)
                .filter(b -> b.color.equals("hollowpurple"))
                .allMatch(b -> isValidBarrierPosition(b.getRectangle().x, b.getRectangle().y));
        assertTrue("All barriers should be added at valid positions.", allValidPositions);
        System.out.println("Tested that barriers are added at valid positions.");
    }

    // Helper method to check if a barrier is added at a valid position
    private boolean isValidBarrierPosition(int x, int y) {
        // Check if the position is within the bounds of the map
        return x >= 0 && x < runningMode.getMapPanel().getWidth() &&
            y >= 0 && y < runningMode.getMapPanel().getHeight();
    }

    @Test
    // Glass-Box Test: Verify that new barriers are added to the newbarriers array.
    public void testNewBarriersArrayUpdated() {
        System.out.println("Running testNewBarriersArrayUpdated...");
        spell.performAction();

        HollowPurpleBarrier[] newBarriers = spell.getNewBarriers();
        assertNotNull("The newBarriers array should not be null.", newBarriers);
        assertEquals("The newBarriers array should have 8 elements.", 8, newBarriers.length);
        long nullCount = Arrays.stream(newBarriers).filter(b -> b == null).count();
        assertEquals("All elements in newBarriers array should be initialized.", 0, nullCount);
        System.out.println("Tested that new barriers are added to the newbarriers array.");
    }

}
