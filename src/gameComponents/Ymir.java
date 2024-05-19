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

    private List<String> abilities;
    private List<String> lastTwoAbilities;
    private Random random;
    private Timer timer;

    public Ymir() {
        abilities = new ArrayList<>();
        abilities.add("Infinite Void");
        abilities.add("Double Accel");
        abilities.add("Hollow Purple");

        lastTwoAbilities = new ArrayList<>();
        random = new Random();
        timer = new Timer();

        // Initialize last two abilities with random abilities
        lastTwoAbilities.add(getRandomAbility());
        lastTwoAbilities.add(getRandomAbility());

        startCoinFlipping();
    }

    private void startCoinFlipping() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (random.nextDouble() < COIN_FLIP_PROBABILITY) {
                    showCoinFlipResult(true);
                    String ability = chooseAbility();
                    applyAbility(ability);
                } else {
                    showCoinFlipResult(false);
                }
            }
        }, 0, COIN_FLIP_INTERVAL);
    }

    private String getRandomAbility() {
        return abilities.get(random.nextInt(abilities.size()));
    }

    private String chooseAbility() {
        String chosenAbility;
        do {
            chosenAbility = getRandomAbility();
        } while (isRepeatedThrice(chosenAbility));
        updateLastTwoAbilities(chosenAbility);
        return chosenAbility;
    }

    private boolean isRepeatedThrice(String ability) {
        return lastTwoAbilities.size() >= 2 && lastTwoAbilities.get(0).equals(lastTwoAbilities.get(1)) && lastTwoAbilities.get(0).equals(ability);
    }

    private void updateLastTwoAbilities(String ability) {
        if (lastTwoAbilities.size() >= 2) {
            lastTwoAbilities.remove(0);
        }
        lastTwoAbilities.add(ability);
    }

    private void applyAbility(String ability) {
        System.out.println("Ymir activates: " + ability);
        // Apply the effects of the ability here
        switch (ability) {
            case "Infinite Void":
                applyInfiniteVoid();
                break;
            case "Double Accel":
                applyDoubleAccel();
                break;
            case "Hollow Purple":
                applyHollowPurple();
                break;
        }
    }

    private void applyInfiniteVoid() {
        // Implement the Infinite Void
    }

    private void applyDoubleAccel() {
        // Implement the Double Accel
    }

    private void applyHollowPurple() {
        // Implement the Hollow Purple
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

    public static void main(String[] args) {
        Ymir ymir = new Ymir();
    }
}
