package gameComponents;

import java.util.Random;

public class SimpleBarrier implements Barrier {

    private int length;
    private int health;
    private int x1Coordinate;
    private int x2Coordinate;
    private int y1Coordinate;
    private int y2Coordinate;
    private int direction;
    private boolean isMoving;

    private static final Random random = new Random();

    public SimpleBarrier(int length, int startX, int startY) {
        this.length = length;
        this.health = 1; // Simple barriers can be destroyed in one hit
        this.x1Coordinate = startX;
        this.x2Coordinate = startX + length;
        this.y1Coordinate = startY;
        this.y2Coordinate = startY + 20; // Assuming height of 20px for all barriers

        // Determine if the barrier will be moving
        double probability = 0.8; // 80% chance of being stationary
        isMoving = random.nextDouble() >= probability;

        // Set initial movement direction
        this.direction = isMoving ? 1 : 0; // 1 for right, -1 for left (if moving)
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }

    @Override
    public void hit(int hitDamage) {
        health -= hitDamage;
    }

    @Override
    public void explode() {
        // Simple barriers do not explode
    }

    @Override
    public boolean isRewarding() {
        return false; // Simple barriers do not drop rewards
    }

    @Override
    public int calculateScore(long currentTime, long gameStartingTime) {
        if (currentTime <= gameStartingTime) {
            return 0;
        }
        return 300 / (int) (currentTime - gameStartingTime); // Calculate score based on time
    }

    @Override
    public int[] getCoordinates() {
        return new int[]{x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate};
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void updatePosition(int newX1, int newX2, int newY1, int newY2) {
        this.x1Coordinate = newX1;
        this.x2Coordinate = newX2;
        this.y1Coordinate = newY1;
        this.y2Coordinate = newY2;
    }

    @Override
    public void changeDirection() {
        direction *= -1; // Reverse the movement direction
    }

    @Override
    public void handleCollision(Barrier otherBarrier) {
        this.changeDirection();
        otherBarrier.changeDirection();
    }

    @Override
    public boolean checkIfMoving() {
        return isMoving;
    }
}