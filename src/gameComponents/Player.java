package gameComponents;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Player {
    private int playerChance = 3;
    public String username;
    private String password;
    private ArrayList<Integer> spellInventory; //Index 0: magicalstaffexpansion, 1:hexSpell, 2:FelixFelicis, 3:overWhelmingBall

    public Player(String username, String password){
        this.username = username;
        this.password = password;
        this.spellInventory = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            this.spellInventory.add(1);
        }
    }

public void setChances(int set){
    this.playerChance = set;
}

public int getChances(){
    return this.playerChance;
}

public void setName(String newname){
    this.username = newname;
}

public String getName(){
    return this.username;
}

public void setpass(String newpass){
    this.password = newpass;
}

public String getPass(){
    return this.password;
}

public void decChance(JPanel chancePanel, ArrayList<JLabel> labels){
    this.playerChance = playerChance - 1;
    if (!labels.isEmpty()) {
            JLabel label = labels.remove(labels.size() - 1);
            chancePanel.remove(label);
            chancePanel.revalidate();
            chancePanel.repaint();

            }
    
}

public void incChance(JPanel chancePanel, ArrayList<JLabel> labels, ImageIcon heartimg){
    this.playerChance = playerChance + 1;
    JLabel labelnew = new JLabel(heartimg);
    chancePanel.add(labelnew);
    labels.add(labelnew);
    chancePanel.revalidate();
    chancePanel.repaint();  
}   

public ArrayList<Integer> getSpellInventory() {
    return this.spellInventory;
}

public void setSpellInventory(int index, int value) {
    if (index >= 0 && index < this.spellInventory.size()) {
        this.spellInventory.set(index, value);
    }
}

public void increaseSpellInventory(int index){
    int current_val=this.spellInventory.get(index);
    this.spellInventory.set(index, current_val+1);
}

public void decreaseSpellInventory(int index){
    int current_val=this.spellInventory.get(index);
    this.spellInventory.set(index, current_val-1);
}

}

