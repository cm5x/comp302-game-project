package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class RewardingBarrier extends JComponent implements Barrier {

    private boolean destroyed; // Indicates if the barrier is destroyed
    private int[] coordinates; // Coordinates of the barrier
    private BufferedImage barrierImage;
    private String imgpath = "assets/images/200icongreengem.png";
    private int x1Coordinate;
    private int y1Coordinate;
    private int health;
    private int spellIndex;
    
    public int getSpellIndex() {
        return spellIndex;
    }

    public void setSpellIndex(int spellIndex) {
        this.spellIndex = spellIndex;
    }

    public RewardingBarrier(int startX, int startY) {
        this.destroyed = false;
        this.coordinates = new int[]{startX, startX + 20, startY, startY + 20}; // Assuming fixed size for rewarding barriers
        this.x1Coordinate = startX;
        this.y1Coordinate = startY;
        this.health = 1;
        // Change appereance of barrier
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/GameResources/GreenGem.png");
            if (inputStream != null) {
            barrierImage = ImageIO.read(inputStream);
            inputStream.close();
            } else {
                throw new IOException("Resource not found.");
            }
            } catch (IOException e) {
                e.printStackTrace();
                
        }
    }

    // to display barriers
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the barrier image at the specified coordinates
        g.drawImage(barrierImage, coordinates[0]+25,coordinates[2]+10 , this);
    }

    
    @Override
    public int getHealth() {
        return health; // Rewarding barriers are destroyed immediately upon hitting
    }

    @Override
    public boolean isDestroyed() {
        return health<=0;
    }

    @Override
    public void hit(int hitDamage) {
        this.health = health - hitDamage;
        if (!destroyed) {
            destroyed = true;
        }
    }

    @Override
    public void explode() {
        // Rewarding barriers do not explode
    }

    @Override
    public boolean isRewarding() {
        return true; // Rewarding barriers drop rewards
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
        // Rewarding barriers do not change direction
    }

    @Override
    public void handleCollision(Barrier otherBarrier) {
        // Rewarding barriers do not handle collisions
    }

    @Override
    public int getDirection() {
        return 0; // Rewarding barriers do not move
    }

    @Override
    public int[] getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean checkIfMoving() {
        return false; // Rewarding barriers do not move
    }

    public JPanel getJPanel(){
        BarrierPanel panel = new BarrierPanel(imgpath,x1Coordinate,y1Coordinate);
        return panel;
    }

    @Override
    public int getXCoordinate() {
        return x1Coordinate;
    }

    @Override
    public int getYCoordinate() {
        return y1Coordinate;
    }
}
