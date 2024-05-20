package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class HollowPurpleBarrier extends JComponent implements Barrier {

    private int hitsNeeded = 1; // Number of hits required to destroy the barrier
    private boolean destroyed; // Indicates if the barrier is destroyed
    private int[] coordinates; // Coordinates of the barrier
    private BufferedImage barrierImage;
    private String imgpath = "assets/images/iconHollowPurple.png"; // Assuming the image path is correct
    private int x1Coordinate;
    private int y1Coordinate;

    public HollowPurpleBarrier(int startX, int startY) {
        this.destroyed = false;
        this.coordinates = new int[]{startX, startX + 40, startY, startY + 20}; // Assuming fixed size for reinforced barriers
        this.x1Coordinate = startX;
        this.y1Coordinate = startY;

        // Change appearance of the barrier
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/GameResources/iconHollowPurple.png");
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

    // To display barriers
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the barrier image at the specified coordinates
        g.drawImage(barrierImage, coordinates[0] + 25, coordinates[2] + 10, this);
    }

    @Override
    public int getHealth() {
        return hitsNeeded; // Barriers are destroyed immediately upon hit
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
        return false; // Hollow purple barriers do not drop rewards
    }

    @Override
    public int calculateScore(long currentTime, long gameStartingTime) {
        return 0; // Hollow purple barriers do not contribute to score calculation
    }

    @Override
    public void updatePosition(int newX1, int newX2, int newY1, int newY2) {
        this.coordinates = new int[]{newX1, newX2, newY1, newY2};
    }

    @Override
    public void changeDirection() {
        // Hollow purple barriers do not change direction
    }

    @Override
    public void handleCollision(Barrier otherBarrier) {
        // Hollow purple barriers do not handle collisions
    }

    @Override
    public int getDirection() {
        return 0; // Hollow purple barriers do not move
    }

    @Override
    public int[] getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean checkIfMoving() {
        return false; // Hollow purple barriers do not move
    }

    public JPanel getJPanel() {
        BarrierPanel panel = new BarrierPanel(imgpath, x1Coordinate, y1Coordinate);
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

    @Override
    public void freeze() {
        // Hollow purple barriers do not freeze
    }

    @Override
    public void unfreeze() {
        // Hollow purple barriers do not freeze
    }

}

