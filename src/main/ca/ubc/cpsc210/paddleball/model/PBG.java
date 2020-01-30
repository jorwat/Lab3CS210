package ca.ubc.cpsc210.paddleball.model;

import java.awt.event.KeyEvent;
import java.util.Random;

/*
 * Represents a paddle ball game.
 */
public class PBG {
    public static final int DIMENSION1 = 800;
    public static final int DIMENSION2 = 600;
    private static final Random RND = new Random();

    private Ball balls;
    private Puddle puddles;
    private boolean stop;

    // Constructs a Paddle Ball Game
    // EFFECTS:  creates ball at random location at top of screen
    public PBG() {
        setUp();
    }

    public Puddle getPaddle() {
        return puddles;
    }

    public Ball getBall() {
        return balls;
    }

    // Is game over?
    // EFFECTS: returns true if game is over, false otherwise
    public boolean isOver() {
        return stop;
    }

    // Updates the game on clock tick
    // MODIFIES: this
    // EFFECTS:  updates ball, paddle and game over status
    public void update() {
        balls.move();
        puddles.move();

        checkHitSomething();
        checkstyle();
    }

    // Responds to key press codes
    // MODIFIES: this
    // EFFECTS:  turns paddle and resets game (if game over) in response to
    //           given key pressed code
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_R && stop) {
            setUp();
        } else if (keyCode == KeyEvent.VK_X) {
            System.exit(0);
        } else {
            paddleControl(keyCode);
        }
    }

    // Sets / resets the game
    // MODIFIES: this
    // EFFECTS:  clears list of missiles and invaders, initializes tank
    private void setUp() {
        balls = new Ball(RND.nextInt(PBG.DIMENSION1), Ball.SIZE / 2);
        puddles = new Puddle(DIMENSION1 / 2);
        stop = false;
    }

    // Controls the paddle
    // MODIFIES: this
    // EFFECTS: changes direction of paddle in response to key code
    private void paddleControl(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            puddles.moveLeft();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            puddles.moveRight();
        }
    }

    // Checks for collision between ball and paddle
    // MODIFIES: this
    // EFFECTS:  bounces ball if it collides with paddle
    private void checkHitSomething() {
        if (balls.doSomething(puddles)) {
            balls.bounceOffPaddle();
        }
    }

    // Is game over? (Has ball hit ground?)
    // MODIFIES: this
    // EFFECTS:  if ball has hit ground, game is marked as over
    private void checkstyle() {
        if (balls.getPosY() > DIMENSION2) {
            stop = true;
        }
    }
}
