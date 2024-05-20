package gameComponents;
import org.junit.*;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Toolkit;

public class TestFireball {

    private FireBall ball;

    @Before
    public void setUp() {
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        ball = new FireBall(screenSize.width/2, screenSize.height-70);
    }

    @Test
    public void test1() {
        // Test 1, overwhelming effect is not activated
        ball.deactivateOverwhelmed();
        Assert.assertFalse(ball.isOverwhelmed());
        Assert.assertTrue(ball.isOverwhelmendDurationElapsed(5));
    }

    @Test
    public void test2() throws InterruptedException{   
        // Test 2, check if the time elapsed is equal to the duration
        ball.activateOverwhelming();
        Thread.sleep(5000); // sleep for 5 seconds
        assertTrue(ball.isOverwhelmed());
        assertTrue(ball.isOverwhelmendDurationElapsed(5));
    }
    
    @Test
    public void test3() throws InterruptedException{
        // Test 3, check if the time elapsed is less than the duration
        ball.activateOverwhelming();
        Thread.sleep(1000); // sleep for 5 seconds
        assertTrue(ball.isOverwhelmed());
        Assert.assertFalse(ball.isOverwhelmendDurationElapsed(5));
    }

    @Test
    public void test4() throws InterruptedException{
        // Test 4, check if the time elapsed is more than the duration
        ball.activateOverwhelming();
        Thread.sleep(10000); // sleep for 5 seconds
        assertTrue(ball.isOverwhelmed());
        assertTrue(ball.isOverwhelmendDurationElapsed(5));
    }

    @Test
    public void test5() throws InterruptedException{
        // Test 5, test what happens when the duration is set to 0
        ball.activateOverwhelming();
        assertTrue(ball.isOverwhelmed());
        assertTrue(ball.isOverwhelmendDurationElapsed(0));
    }

}
