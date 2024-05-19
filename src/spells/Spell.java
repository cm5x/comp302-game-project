package spells;

import javax.swing.*;

import view.RunningMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Spell {
    protected String name;
    protected Icon icon;
    protected boolean isActive;
    protected RunningMode runningMode;
    protected int counter;


    public Spell(String name, Icon icon,RunningMode runningMode) {
        this.name = name;
        this.icon = icon;
        this.runningMode = runningMode;
        this.isActive = false;
        this.counter=0;
    }
      
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
        performAction();
        Timer timer = new Timer(getDuration() * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivate();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    public void deactivate() {
        isActive = false;
        undoAction();
    }

    protected abstract void performAction();
    protected abstract void undoAction();
    protected abstract int getDuration();

}