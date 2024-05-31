package spells;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import view.RunningMode;
import gameComponents.Barrier;

public class InfiniteVoid extends Spell {
    private List<Barrier> barriers;
    private static final ImageIcon icon = new ImageIcon("path/to/icon.png");

    public InfiniteVoid(String name, RunningMode runningMode) {
        super(name, icon, runningMode);
        this.barriers = runningMode.bArrayList;
    }

    @Override
    protected void performAction() {
        Random random = new Random();
        int barrierCount = barriers.size();
        int numBarriersToFreeze = Math.min(barrierCount, 8);

        for (int i = 0; i < numBarriersToFreeze; i++) {
            int randomIndex = random.nextInt(barrierCount);
            Barrier barrier = barriers.get(randomIndex);
            if (!barrier.isFrozen()) {
                barrier.freeze();
            }
        }

        // Start a timer to unfreeze barriers after 15 seconds
        Timer timer = new Timer(getDuration() * 1000, e -> undoAction());
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void undoAction() {
        for (Barrier barrier : barriers) {
            if (barrier.isFrozen()) {
                barrier.unfreeze();
            }
        }
    }

    @Override
    protected int getDuration() {
        return 15; // Duration in seconds
    }
}


