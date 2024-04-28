package gameComponents;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
public class ExplosiveBarrier extends JComponent implements Barrier {

    private boolean exploded; // Indicates if the barrier has exploded
    private int[] coordinates; // Coordinates of the barrier
    private BufferedImage barrierImage;
    private String imgpath = "assets/images/200iconredgem.png";
    private int x1Coordinate;
    private int y1Coordinate;

    public ExplosiveBarrier(int startX, int startY) {
        this.exploded = false;
        this.coordinates = new int[]{startX, startX + 30, startY, startY + 30}; // Assuming fixed size for explosive barriers
        this.x1Coordinate = startX;
        this.y1Coordinate = startY;

        // Change appereance of barrier
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/GameResources/RedGem.png");
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
