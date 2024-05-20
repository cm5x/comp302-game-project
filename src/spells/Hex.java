package spells;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import view.RunningMode.MapPanel;
import view.RunningMode;

public class Hex extends Spell {
    public boolean hexCanonsActive;
    public Point leftCanon;
    public Point rightCanon;
    public List<Projectile> projectiles = new ArrayList<>();
    private Timer fireHexesTimer;
    
    public Hex(RunningMode runningMode) {
        super("Hex Spell", new ImageIcon("path/to/icon.png"), runningMode);
        this.hexCanonsActive = false;
        this.fireHexesTimer = new Timer();
    }

    @Override
    protected void performAction() {
        this.hexCanonsActive = true;
        updateCanonPositions();
        fireHexesTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fireHexes();
            }
        }, 0, 1000); // Schedule to run every 1 second
        runningMode.getMapPanel().repaint();
    }

    @Override
    protected void undoAction() {
        this.hexCanonsActive = false;
        fireHexesTimer.cancel(); // Stop the timer
        fireHexesTimer = new Timer(); // Create a new Timer instance for the next activation
        runningMode.getMapPanel().repaint();
    }

    @Override
    protected int getDuration() {
        return 30; // 30 seconds
    }

    private void fireHexes() {
        if (hexCanonsActive) {
            fireHex(leftCanon);
            fireHex(rightCanon);
        }
    }

    private void fireHex(Point canonPosition) {
        projectiles.add(new Projectile(canonPosition.x, canonPosition.y, -5, 5));
    }

    public void updateCanonPositions() {
        double angleRadians = Math.toRadians(runningMode.getStaff().getRotationAngle());

        leftCanon = new Point(
            (int) (runningMode.getStaff().getXPos() - 10 * Math.cos(angleRadians)),
            (int) (runningMode.getStaff().getYPos() - 10 * Math.sin(angleRadians))
        );

        rightCanon = new Point(
            (int) (runningMode.getStaff().getXPos() + runningMode.getStaff().getLength() + 10 * Math.cos(angleRadians)),
            (int) (runningMode.getStaff().getYPos() - 10 * Math.sin(angleRadians))
        );
    }
/**
 * Requires: projectiles is not null and contains only non-null Projectile objects.
 * Modifies: this.projectiles, runningMode.getMapPanel().getBlocks()
 * Effects: Moves each projectile in projectiles upwards by its speedY.
 *          Removes any projectile that moves off the screen (y < 0).
 *          Removes any projectile that intersects with a block in runningMode.getMapPanel().getBlocks().
 *          Removes any block that is hit by a projectile.
 */
    public void moveProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile projectile = it.next();
            projectile.y -= projectile.speedY;
            if (projectile.y < 0) {
                it.remove();
                continue;
            }

            Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, 5, 10);
            Iterator<MapPanel.ColoredBlock> blockIt = runningMode.getMapPanel().getBlocks().iterator();
            while (blockIt.hasNext()) {
                MapPanel.ColoredBlock block = blockIt.next();
                if (projectileRect.intersects(block.getRectangle())) {
                    blockIt.remove();
                    it.remove();
                    break;
                }
            }
        }
    }

    public class Projectile {
        public int x, y;
        int speedY;

        Projectile(int x, int y, int speedX, int speedY) {
            this.x = x;
            this.y = y;
            this.speedY = speedY;
        }
    }
}
