package utilities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import gameComponents.ExplosiveBarrier;
import gameComponents.Player;
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;
import view.RunningMode;
import view.RunningMode.MapPanel;

public class MapLoadTest {

    private RunningMode game;
    private MapPanel mapPanel;

    @Before
    public void setUp() {
        Player p = new Player("uname", "pass");
        game = new RunningMode(1,p);
        mapPanel = game.mapPanel;
    }

    @Test
    public void testLoadMapValidFile() throws Exception {
        ArrayList<int[]> barriers = new ArrayList<>();
        barriers.add(new int[]{0, 0, 1});
        barriers.add(new int[]{1, 1, 2});
        barriers.add(new int[]{2, 2, 3});

        String filePath = "testMap.dat";
        saveTestMap(filePath, barriers);

        mapPanel.loadMap(filePath);

        assertEquals(3, mapPanel.getBarrierIndexList().size());
        assertEquals(SimpleBarrier.class, mapPanel.getBArrayList().get(0).getClass());
        assertEquals(ReinforcedBarrier.class, mapPanel.getBArrayList().get(1).getClass());
        assertEquals(ExplosiveBarrier.class, mapPanel.getBArrayList().get(2).getClass());
    }

    @Test
    public void testLoadMapInvalidFile() {
        String filePath = "invalidMap.dat";
        assertThrows(ClassNotFoundException.class, () -> mapPanel.loadMap(filePath));
    }

    @Test
    public void testLoadMapEmptyFile() throws IOException {
        String filePath = "emptyMap.dat";
        new File(filePath).createNewFile();
        assertThrows(ClassNotFoundException.class, () -> mapPanel.loadMap(filePath));
    }

    @Test
    public void testLoadMapIncorrectDataFormat() throws IOException {
        String filePath = "incorrectDataMap.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject("This is not an ArrayList<int[]>");
        }
        assertThrows(ClassCastException.class, () -> mapPanel.loadMap(filePath));
    }

    @Test
    public void testLoadMapWithMixedBarrierTypes() throws Exception {
        ArrayList<int[]> barriers = new ArrayList<>();
        barriers.add(new int[]{0, 0, 1});
        barriers.add(new int[]{1, 1, 2});
        barriers.add(new int[]{2, 2, 3});
        barriers.add(new int[]{3, 3, 4});

        String filePath = "mixedBarrierMap.dat";
        saveTestMap(filePath, barriers);

        mapPanel.loadMap(filePath);

        assertEquals(4, mapPanel.getBarrierIndexList().size());
        assertEquals(4, mapPanel.getBArrayList().size());
        assertEquals(SimpleBarrier.class, mapPanel.getBArrayList().get(0).getClass());
        assertEquals(ReinforcedBarrier.class, mapPanel.getBArrayList().get(1).getClass());
        assertEquals(ExplosiveBarrier.class, mapPanel.getBArrayList().get(2).getClass());
        assertEquals(RewardingBarrier.class, mapPanel.getBArrayList().get(3).getClass());
    }

    private void saveTestMap(String filePath, ArrayList<int[]> barriers) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(barriers);
        }
    }
}
