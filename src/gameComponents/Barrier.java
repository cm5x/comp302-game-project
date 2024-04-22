package gameComponents;

public interface Barrier {

    public int getHealth();
    public Boolean isDestroyed(); 
    public void hit(int hitDamage);
    public void explode();
    public Boolean isRewarding();
    public int calculateScore(long currentTime, long gameStartingTime);
    public void updatePosition(int newX1, int newX2, int newY1, int newY2);
    public void changeDirection();
    public void handleCollision(Barrier otherBarrier);
    public int getDirection();
    public int[] getCoordinates();
    public Boolean checkIfMoving();

}
