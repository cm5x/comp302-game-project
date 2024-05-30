package spells;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import view.RunningMode;

public class MagicalStaffExpansion extends Spell {

    public MagicalStaffExpansion(RunningMode runningMode) {
        super("Magical Staff Expansion", new ImageIcon("path/to/icon.png"), runningMode);
    }

    @Override
    protected void performAction() {
        //runningMode.expandPaddle();
        runningMode.getStaff().setLength(runningMode.getStaff().getLength()*2);
        runningMode.getMapPanel().repaint();
    }

    @Override
    protected void undoAction() {
        //runningMode.resetPaddle();
        runningMode.getStaff().setLength(runningMode.getMapPanel().getOriginalPaddleWidth());
        runningMode.getMapPanel().repaint();
    }

    @Override
    protected int getDuration() {
        return 30; // 30 seconds
    }
}
