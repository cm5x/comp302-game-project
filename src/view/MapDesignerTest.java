package view;

import static org.junit.Assert.*;
import org.junit.*;

public class MapDesignerTest {

    private MapDesigner mapDesigner;

    @Before
    public void setUp() {
        mapDesigner = new MapDesigner();
    }

    @Test
    public void testPlacingBarriers() {

        mapDesigner.randomButton.doClick();
        assertEquals(1, mapDesigner.mapPanel.blocks.size());


        mapDesigner.randomButton.doClick();
        assertEquals(2, mapDesigner.mapPanel.blocks.size());


        mapDesigner.randomButton.doClick();
        assertEquals(3, mapDesigner.mapPanel.blocks.size());

        mapDesigner.randomButton.doClick();
        assertEquals(4, mapDesigner.mapPanel.blocks.size());
    }

    @Test
    public void testBarrierLimits() {

        for (int i = 0; i < 200; i++) {
            mapDesigner.randomButton.doClick();
        }
        assertTrue(mapDesigner.mapPanel.blocks.size() <= 150);
    }

    @Test
    public void testSaveLoadMap() {
        mapDesigner.randomButton.doClick();
        mapDesigner.randomButton.doClick();
        mapDesigner.randomButton.doClick();
        mapDesigner.randomButton.doClick();

 
        mapDesigner.mapPanel.saveMap();

        // Load the map
        mapDesigner.mapPanel.loadMap();

        assertEquals(4, mapDesigner.mapPanel.blocks.size());
    }

    @Test
    public void testInvalidInput() {
        // Test providing invalid input
        mapDesigner.redinput.setText("invalid");
        mapDesigner.greeninput.setText("invalid");
        mapDesigner.blueinput.setText("invalid");
        mapDesigner.rewardingb.setText("invalid");
        mapDesigner.randomButton.doClick();
        assertEquals(0, mapDesigner.mapPanel.blocks.size());
    }

    @Test
    public void testRepOk() {
  
        mapDesigner.rednum = 100;
        mapDesigner.greennum = 20;
        mapDesigner.bluenum = 10;
        mapDesigner.rewnum = 20;
        mapDesigner.limitcounter = 100;
        assertTrue(mapDesigner.repOk());


        mapDesigner.rednum = 100;
        mapDesigner.greennum = 20;
        mapDesigner.bluenum = 10;
        mapDesigner.rewnum = 20;
        mapDesigner.limitcounter = 160;
        assertFalse(mapDesigner.repOk());

        
        mapDesigner.rednum = 50;  // Below the lower limit
        mapDesigner.greennum = 150; // Above the upper limit
        mapDesigner.bluenum = 8; // Below the lower limit
        mapDesigner.rewnum = 45; // Above the upper limit
        mapDesigner.limitcounter = 100;
        assertFalse(mapDesigner.repOk());
    }
}
