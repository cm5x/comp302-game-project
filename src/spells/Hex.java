package spells;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import view.RunningMode;

public class Hex extends Spell {

    public Hex(RunningMode runningMode) {
        super("Hex Spell", new ImageIcon("path/to/icon.png"), runningMode);
    }

    @Override
    protected void performAction() {
        runningMode.activateHexCanons();
    }

    @Override
    protected void undoAction() {
        runningMode.deactivateHexCanons();
    }

    @Override
    protected int getDuration() {
        return 30; // 30 seconds
    }
}
