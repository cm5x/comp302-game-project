package gameComponents;

public class ReinforcedBarrier implements Barrier {

    private int hitsNeeded; // Number of hits required to destroy the barrier
    private boolean destroyed; // Indicates if the barrier is destroyed
    private int[] coordinates; // Coordinates of the barrier

    public ReinforcedBarrier(int hitsNeeded, int startX, int startY) {
        this.hitsNeeded = hitsNeeded;
        this.destroyed = false;
        this.coordinates = new int[]{startX, startX + 40, startY, startY + 20}; // Assuming fixed size for reinforced barriers
    }

    @Override
    public int getHealth() {
        return hitsNeeded;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void hit(int hitDamage) {
        if (!destroyed) {
            hitsNeeded -= hitDamage;
            if (hitsNeeded <= 0) {
                destroyed = true;
            }
        }
    }

    @Override
    public void explode() {
        // Reinforced barriers do not explode
    }

    @Override
    public boolean isRewarding() {
        return false; // Reinforced barriers do not drop rewards
    }

    @Override
    public int calculateScore(long currentTime, long gameStartingTime) {
        if (currentTime <= gameStartingTime) {
            return 0;
        }
        return 300 / (int) (currentTime - gameStartingTime); // Calculate score based on time
    }

    @Override
    public void updatePosition(int newX1, int newX2, int newY1, int newY2) {
        this.coordinates = new int[]{newX1, newX2, newY1, newY2};
    }

    @Override
    public void changeDirection() {
        // Reinforced barriers do not change direction
    }

    @Override
    public void handleCollision(Barrier otherBarrier) {
        // Reinforced barriers do not handle collisions
    }

    @Override
    public int getDirection() {
        return 0; // Reinforced barriers do not move
    }

    @Override
    public int[] getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean checkIfMoving() {
        return false; // Reinforced barriers do not move
    }
}
