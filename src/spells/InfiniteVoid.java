package spells;

import java.util.List;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import gameComponents.Barrier;
import view.RunningMode;

public class InfiniteVoid extends Spell{

    private List<Barrier> barriers;

    public InfiniteVoid(String name, RunningMode runningMode) {
        super(name, new ImageIcon("path/to/icon.png"), runningMode); //add path to icon
        this.barriers = barriers;
    }

    @Override
    protected void performAction() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void undoAction() {
        //
    }

    @Override
    protected int getDuration() {
        return 15;
    }
    
}
