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

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){

            if(new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdirection = -ballYdirection;
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
