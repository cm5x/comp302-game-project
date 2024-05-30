package spells;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import view.RunningMode;

public class FelixFelicis extends Spell {

    public FelixFelicis(RunningMode runningMode) {
        super("Felix Felicis", new ImageIcon("path/to/icon.png"), runningMode);
    }

    @Override
    protected void performAction() {
        runningMode.getPlayer().incChance(runningMode.getChancePanel(), runningMode.getLabels(), runningMode.getHeartimg());;
    }

    @Override
    protected void undoAction() {

    }

    @Override
    protected int getDuration() {
        return 0; // permanent
    }
}
