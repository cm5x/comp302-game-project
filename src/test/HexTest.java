package test;


import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gameComponents.Player;
import spells.Hex;
import spells.Hex.Projectile;
import view.RunningMode;
import view.RunningMode.MapPanel;

public class HexTest {
    private Hex hex;
    private RunningMode runningMode;
    private MapPanel mapPanel;

    @Before
    public void setUp() {
        runningMode = new RunningMode(1, new Player("uname", "pass"));
        mapPanel = runningMode.getMapPanel();
        hex = new Hex(runningMode);
        System.out.println("Setup completed. RunningMode and Hex instances are initialized.");
    }

    @Test
    // Test that the projectiles list is empty after moveProjectiles is called with no projectiles.
    public void testEmptyProjectilesList() {
        hex.moveProjectiles();
        assertTrue(hex.projectiles.isEmpty());
    }
    @Test
    public void testProjectileMovement() {
        // Add a projectile and check its new position after moving        
        // Test that the projectile moves correctly.
        Projectile projectile = hex.new Projectile(100, 100, 0, 10);
        hex.projectiles.add(projectile);
        int initialY = projectile.y;
        hex.moveProjectiles(); 
        assertEquals(initialY - 10, projectile.y);
    }
    @Test
    public void testProjectilePassingThroughEmptySpace() {
        // Add a projectile that does not collide with any block and ensure it moves correctly
        // Test that the projectile passing through empty space moves correctly.
        hex.projectiles.add(hex.new Projectile(100, 100, 0, 10));
        hex.moveProjectiles();
        assertFalse(hex.projectiles.isEmpty());
        assertEquals(90, hex.projectiles.get(0).y);
    }
    @Test
    // Test that the projectile moving out of bounds is removed from the list.
    public void testProjectileMovingOutOfBounds() {
        hex.projectiles.add(hex.new Projectile(100, 5, 0, 10));
        hex.moveProjectiles();
        assertTrue(hex.projectiles.isEmpty());
    }

    @Test
    public void testProjectileHittingBlock() {
        // Test that the projectile hitting a block removes the block and the projectile.
        MapPanel.ColoredBlock block = mapPanel.new ColoredBlock(new Rectangle(100, 100, 10, 10), "simple");
        mapPanel.getBlocks().add(block);
        hex.projectiles.add(hex.new Projectile(105, 105, 0, 10));
        hex.moveProjectiles();
        assertTrue(hex.projectiles.isEmpty());
        assertTrue(mapPanel.getBlocks().isEmpty());
    }

    @Test
    public void testProjectileNotHittingAnyBlock() {
        // Test that the projectile not hitting any block moves correctly.
        hex.projectiles.add(hex.new Projectile(100, 100, 0, 10));
        int initialY = hex.projectiles.get(0).y;
        hex.moveProjectiles();
        assertEquals(initialY - 10, hex.projectiles.get(0).y);
    }

    @Test
    public void testMultipleProjectilesAndBlocks() {
        MapPanel.ColoredBlock block1 = mapPanel.new ColoredBlock(new Rectangle(100, 100, 10, 10), "simple");
        MapPanel.ColoredBlock block2 = mapPanel.new ColoredBlock(new Rectangle(200, 200, 10, 10), "simple");
        mapPanel.getBlocks().add(block1);
        mapPanel.getBlocks().add(block2);

        hex.projectiles.add(hex.new Projectile(105, 105, 0, 10));
        hex.projectiles.add(hex.new Projectile(150, 150, 0, 10));
        hex.projectiles.add(hex.new Projectile(205, 205, 0, 10));

        hex.moveProjectiles();
        // Test multiple projectiles and blocks. One projectile and one block should remain.
        assertEquals(1, hex.projectiles.size());
        assertEquals(1, mapPanel.getBlocks().size());
    }
}