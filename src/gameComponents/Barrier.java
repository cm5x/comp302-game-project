package gameComponents;

import java.awt.Graphics;

public interface Barrier {

    public int getHealth();
    public boolean isDestroyed(); 
    public void hit(int hitDamage);
    public void explode();
    public boolean isRewarding();
    public int calculateScore(long currentTime, long gameStartingTime);
    public void updatePosition(int newX1, int newX2, int newY1, int newY2);
    public void changeDirection();
    public void handleCollision(Barrier otherBarrier);
    public int getDirection();
    public int[] getCoordinates();
    public boolean checkIfMoving();
    public void paintComponent(Graphics g);
    public int getXCoordinate();
    public int getYCoordinate();
    public void freeze();
    public void unfreeze(); 
    public boolean isFrozen();

   
}
