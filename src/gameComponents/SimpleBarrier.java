package gameComponents;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class SimpleBarrier extends JComponent implements Barrier {

    private int health;
    private int x1Coordinate;
    private int y1Coordinate;
    private int direction;
    private boolean isMoving;
    //private BufferedImage barrierImage;
    private String imgpath = "assets/images/200iconbluegem.png";
    private static final Random random = new Random();

    private boolean frozen;

    public SimpleBarrier(int startX, int startY) {
       
        this.health = 1; // Simple barriers can be destroyed in one hit
        this.x1Coordinate = startX;
        this.y1Coordinate = startY;

        // Determine if the barrier will be moving
        double probability = 0.8; // 80% chance of being stationary
        isMoving = random.nextDouble() >= probability;
        
        //determine if the barrier is frozen
        this.frozen = false;

        // Set initial movement direction
        this.direction = isMoving ? 1 : 0; // 1 for right, -1 for left (if moving)        
        

    }

    // to display barriers
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the barrier image at the specified coordinates
        //g.drawImage(img, 0, 0, imgPanel);
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
        if (!frozen) {
            health -= hitDamage;
        }
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
        return new int[]{x1Coordinate, y1Coordinate};
    }

    @Override
    public int getXCoordinate() {
        return x1Coordinate;
    }

    @Override
    public int getYCoordinate() {
        return y1Coordinate;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void updatePosition(int newX1, int newX2, int newY1, int newY2) {
        this.x1Coordinate = newX1;
        this.y1Coordinate = newY1;
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

    public JPanel getJPanel(){
        BarrierPanel panel = new BarrierPanel(imgpath,x1Coordinate,y1Coordinate);
        return panel;
    }

    @Override
    public void freeze() {
        frozen = true;
    }

    @Override
    public void unfreeze() {
        frozen = false;
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }
    
    
}