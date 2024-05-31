package spells;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import view.RunningMode;

public class DoubleAccel extends Spell{

    private static final ImageIcon icon = new ImageIcon("path/to/icon.png");

    public DoubleAccel(String name, RunningMode runningMode) {
        super(name, icon, runningMode);      
    }

    @Override
    protected void performAction() {
        runningMode.getMapPanel().setBallSpeedX(runningMode.getMapPanel().getBallSpeedX()/2); 
        runningMode.getMapPanel().setBallSpeedY(runningMode.getMapPanel().getBallSpeedY()/2);
    }

    @Override
    protected void undoAction() {
        runningMode.getMapPanel().setBallSpeedX(runningMode.getMapPanel().getBallSpeedX()*2); 
        runningMode.getMapPanel().setBallSpeedY(runningMode.getMapPanel().getBallSpeedY()*2);
    }

    @Override
    protected int getDuration() {
        return 15; // Duration in seconds
    }
    
}