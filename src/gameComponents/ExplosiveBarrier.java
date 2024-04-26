package gameComponents;

public class ExplosiveBarrier implements Barrier {

    private boolean exploded; // Indicates if the barrier has exploded
    private int[] coordinates; // Coordinates of the barrier

    public ExplosiveBarrier(int startX, int startY) {
        this.exploded = false;
        this.coordinates = new int[]{startX, startX + 30, startY, startY + 30}; // Assuming fixed size for explosive barriers
    }

    @Override
    public int getHealth() {
        return 1; // Explosive barriers are destroyed immediately upon explosion
    }

    @Override
    public boolean isDestroyed() {
        return exploded;
    }

    @Override
    public void hit(int hitDamage) {
        if (!exploded) {
            explode(); // Explode when hit
        }
    }

    @Override
    public void explode() {
        this.exploded = true;
        // Implement explosion logic (e.g., trigger effects, drop remains)
    }

    @Override
    public boolean isRewarding() {
        return false; // Explosive barriers do not drop rewards
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
        // Explosive barriers do not change direction
    }

    @Override
    public void handleCollision(Barrier otherBarrier) {
        // Explosive barriers do not handle collisions
    }

    @Override
    public int getDirection() {
        return 0; // Explosive barriers do not move
    }

    @Override
    public int[] getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean checkIfMoving() {
        return false; // Explosive barriers do not move
    }
}
