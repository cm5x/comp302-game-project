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

    private int length;
    private int health;
    private int x1Coordinate;
    private int x2Coordinate;
    private int y1Coordinate;
    private int y2Coordinate;
    private int direction;
    private boolean isMoving;
    //private BufferedImage barrierImage;
    private String imgpath = "assets/images/200iconbluegem.png";
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

        // Setting barrier view
        /* 
        try {
            InputStream inputStream = getClass().getResourceAsStream("BlueGem.png");
            if (inputStream != null) {
                barrierImage = ImageIO.read(inputStream);
                inputStream.close();
            } else {
                throw new IOException("Resource not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle image loading error
        }
        */
        
        

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

    public JPanel getJPanel(){
        BarrierPanel panel = new BarrierPanel(imgpath,x1Coordinate,y1Coordinate);
        return panel;
    }

    
    
}