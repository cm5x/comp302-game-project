package gameComponents;

import spells.InfiniteVoid;
import spells.DoubleAccel;
import spells.HollowPurple;
import spells.Spell;
import view.RunningMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Ymir {
    private static final int COIN_FLIP_INTERVAL = 30000; // 30 seconds
    private static final double COIN_FLIP_PROBABILITY = 0.5;

    private List<Spell> abilities;
    private List<Spell> lastTwoAbilities;
    private Random random;
    private Timer timer;
    private RunningMode runningMode;
    private ArrayList<Barrier> barriers;
    private FireBall fireball;

    public Ymir(RunningMode runningMode, List<Barrier> barriers, FireBall fireball) {
        this.runningMode = runningMode;
        this.barriers = runningMode.bArrayList;
        this.fireball = fireball;

        abilities = new ArrayList<>();
        abilities.add(new InfiniteVoid("InfiniteVoid", runningMode));
        //abilities.add(new DoubleAccel(null, runningMode, fireball));
        abilities.add(new HollowPurple("HollowPurple", runningMode));

        lastTwoAbilities = new ArrayList<>();
        random = new Random();
        timer = new Timer();

        // Initialize last two abilities with random abilities
        lastTwoAbilities.add(getRandomAbility());
        lastTwoAbilities.add(getRandomAbility());
    }

    public void startCoinFlipping() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (random.nextDouble() < COIN_FLIP_PROBABILITY) {
                    showCoinFlipResult(true);
                    Spell ability = chooseAbility();
                    ability.activate();
                    updateLastTwoAbilities(ability);
                } else {
                    showCoinFlipResult(false);
                }
            }
        }, 0, COIN_FLIP_INTERVAL);
    }

    private Spell getRandomAbility() {
        return abilities.get(random.nextInt(abilities.size()));
    }

    private Spell chooseAbility() {
        Spell chosenAbility;
        do {
            chosenAbility = getRandomAbility();
        } while (isRepeatedThrice(chosenAbility));
        return chosenAbility;
    }

    private boolean isRepeatedThrice(Spell ability) {
        return lastTwoAbilities.size() >= 2 && lastTwoAbilities.get(0).equals(lastTwoAbilities.get(1)) && lastTwoAbilities.get(0).equals(ability);
    }

    private void updateLastTwoAbilities(Spell ability) {
        if (lastTwoAbilities.size() >= 2) {
            lastTwoAbilities.remove(0);
        }
        lastTwoAbilities.add(ability);
    }

    private void showCoinFlipResult(boolean success) {
        String message = success ? "Coin Flip Success! An ability will be activated." : "Coin Flip Failure! No ability activated.";
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Coin Flip Result");
        dialog.setModal(false);
        dialog.setVisible(true);

        // Hide the dialog after 2 seconds
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.setVisible(false);
                dialog.dispose();
            }
        }, 2000); // 2000 milliseconds = 2 seconds
    }

    public void stop() {
        timer.cancel();
    }
}

