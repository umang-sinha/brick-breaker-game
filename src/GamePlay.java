import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;

    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXdirection = -1;
    private int ballYdirection = -2;

    private MapGenerator map;

    public GamePlay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){

        //background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(683, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("comic sans", Font.BOLD, 25));
        g.drawString("Score: " + score, 550, 30);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);

        if(ballPositionY > 570){
            play = false;
            ballXdirection = 0;
            ballYdirection = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("comic sans", Font.BOLD, 25));
            g.drawString("GAME OVER",190, 300);
            g.setColor(Color.white);
            g.setFont(new Font("comic sans", Font.BOLD, 25));
            g.drawString("Your Score: "+ score,190, 330);
            g.setColor(Color.white);
            g.setFont(new Font("comic sans", Font.BOLD, 25));
            g.drawString("Press Enter to Restart",190, 360);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){

            if(new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdirection = -ballYdirection;
            }

            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++){
                    if (map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks = totalBricks - 1;
                            score = score + 5;

                            if (ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width) {
                                ballXdirection = -ballXdirection;
                            } else {
                                ballYdirection = -ballYdirection;
                            }
                            break A;
                        }
                    }
                }
            }

            ballPositionX = ballPositionX + ballXdirection;
            ballPositionY = ballPositionY + ballYdirection;
            if (ballPositionX < 0){
                ballXdirection = -ballXdirection;
            }
            if (ballPositionY < 0){
                ballYdirection = -ballYdirection;
            }
            if (ballPositionX > 670){
                ballXdirection = -ballXdirection;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 0){
                playerX = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXdirection = -1;
                ballYdirection = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
        }
    }

    public void moveRight(){
        play = true;
        playerX = playerX + 20;
    }

    public void moveLeft(){
        play = true;
        playerX = playerX - 20;
    }
}
