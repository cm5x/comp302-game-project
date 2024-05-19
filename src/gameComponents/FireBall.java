package gameComponents;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FireBall {
    private boolean overwhelmingActivated;
    private long overwhelmingStartTime;
    private int x,y, xPosition,yPosition;
    private final int SIZE = 16;
    private Image image;

    public FireBall(int x,int y){
        this.overwhelmingActivated = false;
        this.overwhelmingStartTime = 0;
        this.x = x;
        this.x = y;
        this.xPosition = 1;
        this.yPosition = 1;
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResource("/assets/images/200Fireball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, SIZE, SIZE, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void activateOverwhelming(){
        overwhelmingActivated = true;
        overwhelmingStartTime = System.currentTimeMillis();
    }

    public boolean isOverwhelmed(){
        return overwhelmingActivated;
    }

    public boolean isOverwhelmendDurationElapsed(int duration){
        if (!overwhelmingActivated) return true;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - overwhelmingStartTime;
        return elapsedTime >= duration*1000;    
    }

    public void deactivateOverwhelmed(){
        overwhelmingActivated = false;
    }

    public void setY(int num) {
        y = num;
    }

    public void setX(int num) {
        x = num;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
  /*   
    private void moveBall() {

        ballPosition.x += ballSpeedX;
        ballPosition.y += ballSpeedY;
        if (ballPosition.x < 0 || ballPosition.x > getWidth()) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballPosition.y < 0) {
            ballSpeedY = -ballSpeedY;
        }
        if (ballPosition.y > getHeight()) { // Ball goes below the paddle
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.ERROR_MESSAGE);
        }

        // Check collision with the paddle
        if (new Rectangle(ballPosition.x, ballPosition.y, 10, 10).intersects(paddle)) {
            ballSpeedY = -ballSpeedY;
            ballPosition.y = paddle.y - 10; // Adjust ball position to avoid sticking
        }

        // // Check collision with barriers
        // Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
        // while (it.hasNext()) {
        //     MapPanel.ColoredBlock block = it.next();
        //     if (new Rectangle(ballPosition.x, ballPosition.y, 10, 10).intersects(block.rectangle)) {
        //         ballSpeedY = -ballSpeedY; // Reflect the ball

        //         it.remove(); // Remove the barrier on hit
        //         break;
        //     }
        // }

        //ALTERNATIVE

        // Collision with barriers
        Rectangle ballRect = new Rectangle(ballPosition.x, ballPosition.y, 10, 10);
        Iterator<MapPanel.ColoredBlock> it = barriers.iterator();
        while (it.hasNext()) {
            MapPanel.ColoredBlock block = it.next();
            if (ballRect.intersects(block.rectangle)) {
                // Determine the collision direction
                int ballCenterX = ballPosition.x + 5;
                int ballCenterY = ballPosition.y + 5;
                int blockCenterX = block.rectangle.x + block.rectangle.width / 2;
                int blockCenterY = block.rectangle.y + block.rectangle.height / 2;

                int deltaX = ballCenterX - blockCenterX;
                int deltaY = ballCenterY - blockCenterY;

                // Check which side (top, bottom, left, right) of the block the ball has hit
                boolean collisionFromTopOrBottom = Math.abs(deltaY) > Math.abs(deltaX);
                if (collisionFromTopOrBottom) {
                    ballSpeedY = -ballSpeedY; // Vertical bounce
                    if (deltaY > 0) { // Ball is below the block
                        ballPosition.y = block.rectangle.y + block.rectangle.height;
                    } else { // Ball is above the block
                        ballPosition.y = block.rectangle.y - 10;
                    }
                } else {
                    ballSpeedX = -ballSpeedX; // Horizontal bounce
                    if (deltaX > 0) { // Ball is to the right of the block
                        ballPosition.x = block.rectangle.x + block.rectangle.width;
                    } else { // Ball is to the left of the block
                        ballPosition.x = block.rectangle.x - 10;
                    }
                }

                it.remove(); // Remove the barrier on hit
                break; // Assuming only one collision can occur per frame
            }

        }
        
        
        
        repaint();
    }

    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    //     g.fillOval(ballPosition.x, ballPosition.y, 10, 10);
    // }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        g.setColor(Color.RED);
        g.fillOval(ballPosition.x, ballPosition.y, 10, 10);
        for (MapPanel.ColoredBlock block : barriers) {
            g.setColor(Color.GREEN);
            g.fillRect(block.rectangle.x, block.rectangle.y, block.rectangle.width, block.rectangle.height);
        }
        
    }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    //     repaint();
    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode()));  // Debugging line
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         // paddle.x = Math.max(0, paddle.x - 10);
    //         paddle.x = paddle.x-100;
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         // paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //         paddle.x = paddle.x+100;
    //     }
    //     repaint();
    // }
    

    // @Override
    // public void keyTyped(KeyEvent e) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    // }

    private void updateGame() {
        moveBall();
        movePaddle(); // Method to update paddle position
        repaint();
    }

    private void movePaddle() {
        if (paddleMoveDirection != 0) {
            int newPaddleX = paddle.x + (paddleSpeed * paddleMoveDirection);
            // Ensure the paddle does not move out of the panel's bounds
            if (newPaddleX < 0) {
                newPaddleX = 0;
            } else if (newPaddleX + paddle.width > getWidth()) {
                newPaddleX = getWidth() - paddle.width;
            }
            paddle.x = newPaddleX;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddleMoveDirection = -1; // Move left
            frame.appendInfoText("key activation.");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleMoveDirection = 1; // Move right
            frame.appendInfoText("key activation.");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleMoveDirection = 0; // Stop moving when key is released
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method can be left empty if not used
    }

    // //???
    // @Override
    // public void keyReleased(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    // }


    // //???
    // @Override
    // public void keyTyped(KeyEvent e) {
    //     if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    //         paddle.x = Math.max(0, paddle.x - 10);
    //     } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    //         paddle.x = Math.min(getWidth() - paddle.width, paddle.x + 10);
    //     }
    // }

}

}
 */