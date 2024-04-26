package view;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Helppage extends JFrame {
    public Helppage() {
        setTitle("Help Page");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // game instructions for the game
        String instructions = "Lance of Destiny is an easy and competitive game to play. It combines fun and challenge. Two brave warriors are racing to obtain a unique item which is called the Lance of Fate that enables its holder to rule the world with its power. Its creator, Fistandantalus, the arch-magician, has created several barriers to protect the lance in a way that only the worthiest soul can reach it.\n\n"
                + "In this game, two players will contend representing a warrior, and try to reach the Lance of Power before the other. There are several types of barriers that they shall face. The warrior can obtain spells during their quest. Some of the spells can be used to enhance one’s attacking power and status (Benevolent or good Spells), and others can be used to obstruct the other player from reaching the Lance of Power fast (Malevolent or evil Spells, to be discussed in phase 2). The warrior who finishes all the barriers first, is deemed the winner and the one worthy of the treasure.\n\n"
                + "Each warrior is represented by their Magical Staff and Fire Ball. A Magical Staff is a paddle-like object that is used to deflect the Fire Ball from falling to the ground. The Fire Ball is the object that is sent around to destroy barriers, but it is affected by gravity, therefore the Magical Staff is used to set its track towards the target barriers to destroy them. If the Fire Ball falls below the Magical Staff, the warrior loses a chance. Each warrior has 3 chances only. Once the warrior runs out of chances, they are considered unworthy and therefore lose the game. If a player is deemed unworthy, then the other player automatically wins. In this phase, you shall focus on the single player case. In the next phase, the 2 player-mode shall be added by means of networking. The player has control over the Magical Staff (paddle), he/she should use the Magical Staff (paddle) to direct the Fire Ball to destroy as many barriers as possible and at the same time protect the Fire Ball from falling. The details about the Fire Ball and the Magical Staff movements will be provided in the following sections. The Magical Staff can basically be moved horizontally. To control the movement of the Magical Staff, the player needs to use arrow-left and arrow-right buttons. The Magical Staff ( can also be rotated temporarily by up to 45- or 135-degrees using A and D keys respectively. This rotation might help to direct the Fire Ball (ball) to hit more important areas of the wall. On the game start, the Fire Ball will be on the top of that Magical Staff, to make the first shot the player can click the mouse left button or the letter W. During the game the player can gather some spells, some of these (Hex, Magical Staff Expansion, ...etc.) can be kept for later use. These spells will appear as icons on a specified place of the game view. The player might choose to activate a spell directly or keep them for the future. To activate the spell, the player can either click on its icon on the screen using the mouse left button or type the first letter of the name of spell. Controlled by the player.\n\n"
                + "Moves horizontally, can be rotated by up to 45 or 135 degrees."
                + "The Magical Staff thickness T is 20px.\n"
                + "Simple Barrier (Ancient Wall)\n"
                + "Can be broken in one hit."
                + "Reinforced Barrier (Triple Rashōmon)\n"
                + "These barriers are more difficult to destroy. Each one contains a number written on it (not necessarily just three as the name implies), which corresponds to the number of hits it requires to be destroyed. After every hit it receives, the number decreases by 1, and the barrier disappears once the number reaches zero.\n"
                + "Explosive Barrier (Volatile Fence)\n"
                + "This barrier has a circular shape and it explodes once it is hit. Once exploded, its remains fall downwards towards the Magical Staff. If the remains touch the Magical Staff, the player loses a chance.\n"
                + "Rewarding Barrier (Wish Endower)\n"
                + "This barrier can be destroyed in one hit like the simple one. Once destroyed, it drops a box downwards towards the Magical Staff. If the Magical Staff touches the box, then the box opens and rewards the warrior with a spell that can be either used to support the warrior, or to create more challenges and barriers for the other player.\n"
                + "All barriers except explosive barriers are rectangles with dimensions L/5 and 20px.\n"
                + "Spell types:\n"
                + "Felix Felicis\n"
                + "This ability increases the player’s chances by 1.\n"
                + "Magical Staff Expansion\n"
                + "This ability doubles the length of the Magical Staff. It is not necessarily activated once it is received. The player can choose to activate it whenever they want by either pressing the button T, or pressing its icon on the screen. Once activated, it lasts for only 30 seconds, after which the Magical Staff returns to its original state.\n"
                + "Hex\n"
                + "This ability equips the Magical Staff with two magical cannons on both of its ends. The cannons should point upwards and they rotate as the Magical Staff rotates. They can fire magical hexes that can hit the barriers. A hex hit has the same effect as the hit of a Fire Ball. It does not activate immediately, but can be activated by pressing H or pressing its icon on the screen. Once activated it remains active for only 30 seconds and then disappears afterwards.\n"
                + "Overwhelming Fire Ball\n"
                + "This ability upgrades the Fire Ball and makes it much more powerful, such that if it hits any barriers, it destroys it and passes through it regardless of its type (even for the firm barriers). This upgrade only lasts 30 seconds after it is activated.\n";
        textArea.setText(instructions);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
    }

}
