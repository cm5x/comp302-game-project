package spells;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import view.RunningMode;

public class DoubleAccel extends Spell{

    public DoubleAccel(String name, RunningMode runningMode) {
        super(name, new ImageIcon("path/to/icon.png"), runningMode); //add path to icon
        //TODO Auto-generated constructor stub
    }

    @Override
    public void performAction() {
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