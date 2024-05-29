package spells;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import gameComponents.HollowPurpleBarrier;
import view.RunningMode;
import view.RunningMode.MapPanel;

public class HollowPurple extends Spell {

    private HollowPurpleBarrier[] newbarriers;
    private static final int NUM_BARRIERS = 8;

    public HollowPurple(String name, RunningMode runningMode) {
        super(name, new ImageIcon("path/to/icon.png"), runningMode);  //add path to icon
        this.newbarriers = new HollowPurpleBarrier[NUM_BARRIERS];
    }

    @Override
    protected void performAction() {
        Random rand = new Random();

        for (int i = 0; i < NUM_BARRIERS; i++) {
            int x = rand.nextInt(runningMode.mapPanel.getWidth());
            int y = rand.nextInt(runningMode.mapPanel.getHeight() - 200);
            
            // Ensure x and y are aligned to the block grid
            x -= x % MapPanel.BLOCK_WIDTH;
            y -= y % MapPanel.BLOCK_HEIGHT;
            
            HollowPurpleBarrier barrier = new HollowPurpleBarrier(x, y);
            newbarriers[i] = barrier;
            boolean isplaced = false;
            while (!isplaced){

                isplaced = runningMode.mapPanel.addBlock(x, y, "hollowpurple"); // Add the block to the map

                x = rand.nextInt(runningMode.mapPanel.getWidth());
                y = rand.nextInt(runningMode.mapPanel.getHeight());
               
            }
           
        }
    }

    @Override
    protected void undoAction() {
        for (HollowPurpleBarrier barrier : newbarriers) {
            if (barrier != null) {
                runningMode.mapPanel.removeBlock(barrier.getX(), barrier.getY());
            }
        }
    }

    @Override
    protected int getDuration() {
        throw new UnsupportedOperationException("Unimplemented method 'getDuration'");
    }

    

}

