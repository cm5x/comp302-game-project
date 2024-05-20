import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gameComponents.ExplosiveBarrier;
import gameComponents.ReinforcedBarrier;
import gameComponents.RewardingBarrier;
import gameComponents.SimpleBarrier;

public class BarrierTest {
    //setting up barriers
    SimpleBarrier barrier = new SimpleBarrier(100, 200);
    ReinforcedBarrier barrier2 = new ReinforcedBarrier(3, 10, 20);
    ExplosiveBarrier barrier3 = new ExplosiveBarrier(0, 0);
    RewardingBarrier barrier4 = new RewardingBarrier(500, 100);

    @Test
    public void healthTest(){
        //tests to see if health is displayed correctly
        //blackbox test
        assertEquals(1, barrier.getHealth());
        assertEquals(3, barrier2.getHealth());
        assertEquals(1, barrier3.getHealth());
        assertEquals(1, barrier4.getHealth());
    
    }
    @Test
    public void testHitReducesHealth() {
        //tests to see health is reduced properly
        //blackbox test
        barrier.hit(1);
        barrier2.hit(2);
        barrier3.hit(1);
        barrier4.hit(0);
        assertEquals(0, barrier.getHealth());
        assertEquals(1, barrier2.getHealth());
        assertEquals(0, barrier3.getHealth());
        assertEquals(1, barrier4.getHealth());

    }

    @Test
    public void testExploded(){
        //test to see that explosive barrier is exploded
        //glassbox test since from implementation I know that the barrier explodes after 1 hit 
        barrier3.hit(1);
        assertTrue(barrier3.isexploded());
    }

    @Test
    public void testIsDestroyed() {
        //test to see that barriers are exploded
        //glassbox test since from implementation I know that which barrier explodes after different amounts of hits
        barrier.hit(1);
        barrier2.hit(2);
        barrier3.hit(1);
        barrier4.hit(0);
        assertTrue(barrier.isDestroyed());
        assertTrue(barrier3.isDestroyed());
        assertFalse(barrier2.isDestroyed());
        assertFalse(barrier4.isDestroyed());
    }

    @Test
    public void testIsRewarding() {
        //test to see only rewarding barrier is rewarding
        //blackbox test
        assertFalse(barrier.isRewarding());
        assertFalse(barrier2.isRewarding());
        assertTrue(barrier4.isRewarding());
        assertFalse(barrier3.isRewarding());
    }

    
    public boolean repOK(){
        //repok for simpleBarrier (same for other barriers)
        if (barrier.getHealth() < 0){
            return false;
        }

        if (barrier.getXCoordinate() > 1200 || barrier.getYCoordinate() > 800){
            return false;
        }

        if (barrier.getXCoordinate() < 0 || barrier.getYCoordinate() < 0) {
            return false;
        }

        if (barrier.getDirection() != -1 && barrier.getDirection() != 1) {
            return false;
        }
        

       
        
        

        return true;
    }


   

    



}
