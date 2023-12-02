package org.codeforall.ooptimus;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventHandlerPlayer2 implements KeyboardHandler {
    private Picture player1;
    private Picture player2;
    private Picture background;
    private Picture backgroundPlayer1;
    private Picture ball;
    private static final int PADDING = 10;
    private boolean falling = false;
    private boolean launch = false;
    private Timer timer;
    private int verticalVelocity = 0;
    private List<Picture> life;
    private int remainingLife;
    private boolean rising = false;
    Game game;

    public EventHandlerPlayer2(Picture player1, Picture player2, List<Picture> life, Picture background, Picture backgroundMario, Game game) {
        this.player1 = player1;
        this.player2 = player2;
        this.life = life;
        remainingLife = life.size() - 1;
        this.background = background;
        this.backgroundPlayer1 = backgroundMario;
        this.game = game;
    }

    public void init() {
        Keyboard marioKb = new Keyboard(this);

        KeyboardEvent arrowRight = new KeyboardEvent();
        arrowRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        arrowRight.setKey(KeyboardEvent.KEY_RIGHT);
        marioKb.addEventListener(arrowRight);

        KeyboardEvent arrowLeft = new KeyboardEvent();
        arrowLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        arrowLeft.setKey(KeyboardEvent.KEY_LEFT);
        marioKb.addEventListener(arrowLeft);

        KeyboardEvent kJump = new KeyboardEvent();
        kJump.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        kJump.setKey(KeyboardEvent.KEY_UP);
        marioKb.addEventListener(kJump);

        KeyboardEvent kLand = new KeyboardEvent();
        kLand.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        kLand.setKey(KeyboardEvent.KEY_UP);
        marioKb.addEventListener(kLand);

        KeyboardEvent pShoot = new KeyboardEvent();
        pShoot.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        pShoot.setKey(KeyboardEvent.KEY_P);
        marioKb.addEventListener(pShoot);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                if (player1.getX() > background.getMaxX() - (background.getMaxX() / 2)) {
                    player1.translate(-50, 0);
                }
                break;
            case KeyboardEvent.KEY_RIGHT:
                if (player1.getMaxX() < background.getMaxX() - PADDING - 30) {
                    player1.translate(50, 0);
                }
                break;
            case KeyboardEvent.KEY_UP:
                if (!falling) {
                    System.out.println(player1.getMaxY());
                    slowRisePlayer1();
                }
                break;
            case KeyboardEvent.KEY_P: {
                if (!launch && !isDead() && game.getGameIsStarted()) {
                    launch = true;
                    Sound.carolShotSound.play();
                    ball = Elements.getPlayer2Throw();
                    ball.grow(-20, -10);
                    ball.draw();

                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (ball.getMaxX() <= PADDING) {
                                ball.delete();
                                launch = false;
                                timer.stop();
                            } else if ((ball.getMaxX() > player2.getX() && ball.getX() < player2.getMaxX()) &&
                                    (ball.getMaxY() > player2.getY() && ball.getY() < player2.getMaxY())) {
                                ball.delete();
                                life.get(remainingLife).delete();
                                remainingLife--;
                                isDead();
                                launch = false;
                                timer.stop();
                            } else {
                                ball.translate(-10, 0);
                            }
                        }
                    });
                    timer.start();
                }
            }
            break;
        }
    }

    public boolean isDead() {
        if (remainingLife > -1) {
            return false;
        }
        backgroundPlayer1.draw();
        Sound.carolWinnerSound.play();
        player2.delete();
        player1.delete();
        ball.delete();
        return true;
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

    public void slowFallPlayer1() {
        falling = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (falling) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player1.getMaxY() <= 670) {
                    player1.translate(0, verticalVelocity);
                    verticalVelocity++;
                } else {
                    falling = false;
                    verticalVelocity = 0;
                    System.out.println(player1.getMaxY());
                    break;
                }
            }
        });
        fallThread.start();

    }

    private void slowRisePlayer1() {

        rising = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (rising) {
                try {
                    Thread.sleep(13);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player1.getY() > 75) {
                    verticalVelocity++;
                    player1.translate(0, -verticalVelocity);
                    if (player1.getY() < 75) {
                        slowFallPlayer1();
                        break;
                    }
                } else {
                    rising = false;
                    verticalVelocity = 0;
                    break;
                }
            }
        });
        fallThread.start();
    }
}
