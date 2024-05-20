package spells;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import gameComponents.HollowPurpleBarrier;
import view.RunningMode;

public class HollowPurple extends Spell {

    private HollowPurpleBarrier[] barriers;
    private static final int NUM_BARRIERS = 8;

    public HollowPurple(String name, RunningMode runningMode) {
        super(name, new ImageIcon("path/to/icon.png"), runningMode);  //add path to icon
        this.barriers = new HollowPurpleBarrier[NUM_BARRIERS];
    }

    @Override
    protected void performAction() {
        Random rand = new Random();

        for (int i = 0; i < NUM_BARRIERS; i++) {
            int x = rand.nextInt(runningMode.mapPanel.getWidth());
            int y = rand.nextInt(runningMode.mapPanel.getHeight());
            barriers[i] = new HollowPurpleBarrier(x, y);
            runningMode.add(barriers[i]);
        }
    }

    @Override
    protected void undoAction() {
        for (HollowPurpleBarrier barrier : barriers) {
            if (barrier != null) {
                runningMode.remove(barrier);
            }
        }
    }

    @Override
    protected int getDuration() {
        throw new UnsupportedOperationException("Unimplemented method 'getDuration'");
    }
    

}

