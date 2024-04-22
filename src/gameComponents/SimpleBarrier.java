package gameComponents;

import java.util.Random;

public class SimpleBarrier implements Barrier {

    private int lenght;
    private int speed;
    private int health;
    private int x1Coordinate;
    private int x2Coordinate;
    private int y1Coordinate;
    private int y2Coordinate;
    private int direction;
    private int coordinateArray[];
    private Boolean isMoving;
    

    private static Random random = new Random();
    
    public static boolean getProbability(double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1 inclusive.");
        }
        return random.nextDouble() >= probability;
    }

    public SimpleBarrier() {

        double probability = 0.8;
        boolean result = getProbability(probability);
        isMoving = result;
        speed = lenght / 4;

        health = 1;
        direction = 1;
        coordinateArray[0] = x1Coordinate;
        coordinateArray[1] = x2Coordinate;
        coordinateArray[2] = y1Coordinate;
        coordinateArray[3] = y2Coordinate;

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public Boolean isDestroyed() {

        if (health == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void hit(int hitDamage) {
        
        health = health - hitDamage;
    }

    @Override
    public void explode() {
        
    }

    @Override
    public Boolean isRewarding() {

        return false;
    }

    @Override
    public int calculateScore(long currentTime, long gameStartingTime) {

        return 0;
    }



    @Override
    public int[] getCoordinates() {
        
        return coordinateArray;
    }

    @Override
    public int getDirection() {
        
        return direction;
    }

    @Override
    public void updatePosition(int newX1, int newX2, int newY1, int newY2) {
        
        x1Coordinate = newX1;
        x2Coordinate = newX2;
        y1Coordinate = newY1;
        y2Coordinate = newY2;
        
    }

    @Override
    public void changeDirection() {
        
        if(direction == -1) {
            direction = 1;
        } else if (direction == 1) {
            direction = -1;
        }
    }

    @Override
    public void handleCollision(Barrier otherBarrier) {
        
        this.changeDirection();
        otherBarrier.changeDirection();
    }

    @Override
    public Boolean checkIfMoving() {

        return isMoving;
    }

}
