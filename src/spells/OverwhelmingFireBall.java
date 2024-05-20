package spells;
import javax.swing.ImageIcon;

import spells.Spell;
import view.RunningMode;

public class OverwhelmingFireBall extends Spell {
    
    public OverwhelmingFireBall(RunningMode runningMode) {
        super("Overwhelming Fire Ball", new ImageIcon("path/to/icon.png"), runningMode);
    }

    public void deactivate(){

    }

    public void activate(){
        
    }

    @Override
    protected void performAction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }

    @Override
    protected void undoAction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'undoAction'");
    }

    @Override
    protected int getDuration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDuration'");
    }

}
