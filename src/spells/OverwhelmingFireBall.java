package spells;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import view.RunningMode;

public class OverwhelmingFireBall extends Spell {

    public OverwhelmingFireBall(RunningMode runningMode) {
        super("OverwhelmingFireBall", new ImageIcon("path/to/icon.png"), runningMode);
    }

    @Override
    protected void performAction() {
        
    }

    @Override
    protected void undoAction() {

    }

    @Override
    protected int getDuration() {
        return 30; // permanent
    }
}
