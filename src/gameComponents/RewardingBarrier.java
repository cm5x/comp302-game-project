package gameComponents;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
public class RewardingBarrier implements Barrier {

    private boolean destroyed; // Indicates if the barrier is destroyed
    private int[] coordinates; // Coordinates of the barrier
    private BufferedImage barrierImage;

    public RewardingBarrier(int startX, int startY) {
        this.destroyed = false;
        this.coordinates = new int[]{startX, startX + 20, startY, startY + 20}; // Assuming fixed size for rewarding barriers

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

    @Override
    public int getHealth() {
        return 1; // Rewarding barriers are destroyed immediately upon hitting
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void hit(int hitDamage) {
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
}
