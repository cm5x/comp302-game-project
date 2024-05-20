package gameComponents;
import org.junit.*;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Toolkit;

public class TestFireball {
    @Test
    public static void main(String[] args) throws InterruptedException {

        // create a fireball object
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        FireBall ball = new FireBall(screenSize.width/2, screenSize.height-70);

        // Test 1, overwhelming effect is not activated
        ball.deactivateOverwhelmed();
        Assert.assertFalse(ball.isOverwhelmed());
        Assert.assertTrue(ball.isOverwhelmendDurationElapsed(5));


        ball = new FireBall(screenSize.width/2, screenSize.height-70); // Reset fireball
        // Test 2, check if the time elapsed is equal to the duration
        ball.activateOverwhelming();
        Thread.sleep(5000); // sleep for 5 seconds
        assertTrue(ball.isOverwhelmed());
        assertTrue(ball.isOverwhelmendDurationElapsed(5));
        


        ball = new FireBall(screenSize.width/2, screenSize.height-70); // Reset fireball
        // Test 3, check if the time elapsed is less than the duration
        ball.activateOverwhelming();
        Thread.sleep(1000); // sleep for 5 seconds
        assertTrue(ball.isOverwhelmed());
        Assert.assertFalse(ball.isOverwhelmendDurationElapsed(5));


        ball = new FireBall(screenSize.width/2, screenSize.height-70); // Reset fireball
        // Test 4, check if the time elapsed is more than the duration
        ball.activateOverwhelming();
        Thread.sleep(10000); // sleep for 5 seconds
        assertTrue(ball.isOverwhelmed());
        assertTrue(ball.isOverwhelmendDurationElapsed(5));


        ball = new FireBall(screenSize.width/2, screenSize.height-70); // Reset fireball
        // Test 5, test what happens when the duration is set to 0
        ball.activateOverwhelming();
        assertTrue(ball.isOverwhelmed());
        assertTrue(ball.isOverwhelmendDurationElapsed(0));
    }

}
