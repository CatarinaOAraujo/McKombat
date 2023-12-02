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

public class EventHandlerPlayer1 implements KeyboardHandler {
    private Picture player2;
    private Picture player1;
    private Picture player1Throw;
    private Picture background;
    private Picture backgroundPlayer2;
    private static final int PADDING = 10;
    private boolean falling = false;
    private boolean throwing = false;
    private int verticalVelocity = 0;

    private int horizontalVelocity = 0;
    private Timer timer;
    private List<Picture> life;
    private int remainingLife;
    private boolean rising = false;
    private Game game;


    public EventHandlerPlayer1(Picture player1, Picture player2, List<Picture> life, Picture background, Picture backgroundBob, Game game) {
        this.player2 = player1;
        this.player1 = player2;
        this.life = life;
        remainingLife = life.size() - 1;
        this.background = background;
        this.backgroundPlayer2 = backgroundBob;
        this.game = game;
    }

    public void init() {
        Keyboard bobKb = new Keyboard(this);

        KeyboardEvent dRight = new KeyboardEvent();
        dRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        dRight.setKey(KeyboardEvent.KEY_D);
        bobKb.addEventListener(dRight);

        KeyboardEvent aLeft = new KeyboardEvent();
        aLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        aLeft.setKey(KeyboardEvent.KEY_A);
        bobKb.addEventListener(aLeft);

        KeyboardEvent shiftJump = new KeyboardEvent();
        shiftJump.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        shiftJump.setKey(KeyboardEvent.KEY_W);
        bobKb.addEventListener(shiftJump);

        KeyboardEvent shiftLand = new KeyboardEvent();
        shiftLand.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        shiftLand.setKey(KeyboardEvent.KEY_W);
        bobKb.addEventListener(shiftLand);

        KeyboardEvent sShoot = new KeyboardEvent();
        sShoot.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        sShoot.setKey(KeyboardEvent.KEY_SHIFT);
        bobKb.addEventListener(sShoot);


        KeyboardEvent startgame = new KeyboardEvent();
        startgame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        startgame.setKey(KeyboardEvent.KEY_ENTER);
        bobKb.addEventListener(startgame);
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_A:
                if (player2.getX() > background.getX() + PADDING + 30) {
                    player2.translate(-50, 0);
                }
                break;
            case KeyboardEvent.KEY_D:
                if (player2.getMaxX() < (background.getMaxX() - (background.getMaxX() / 2)) - 20) {
                    player2.translate(50, 0);
                }
                break;
            case KeyboardEvent.KEY_W:
                if (!falling) {
                    slowRisePlayer2();
                }
                break;
            case KeyboardEvent.KEY_SHIFT: {
                if (!throwing && !isDead() && game.getGameIsStarted()) {
                    throwing = true;
                    Sound.gustavoShotSound.play();
                    player1Throw = Elements.getPlayer1Throw();
                    player1Throw.grow(-20, -10);
                    player1Throw.draw();

                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (player1Throw.getMaxX() > 1600) {
                                player1Throw.delete();
                                throwing = false;
                                timer.stop();
                            } else if ((player1Throw.getMaxX() > player1.getX() && player1Throw.getX() < player1.getMaxX()) &&
                                    (player1Throw.getMaxY() > player1.getY() && player1Throw.getY() < player1.getMaxY())) {
                                player1Throw.delete();
                                life.get(remainingLife).delete();
                                remainingLife--;
                                isDead();
                                throwing = false;
                                timer.stop();

                            } else {
                                player1Throw.translate(10, 0);
                            }
                        }
                    });
                    timer.start();
                }
            }
            break;
            case KeyboardEvent.KEY_ENTER:
                game.closeMenu();
                Sound.startSound.stop();
                break;
        }
    }

    public boolean isDead() {
        if (remainingLife > -1) {
            return false;
        }
        backgroundPlayer2.draw();
        Sound.gustavoWinnerSound.play();
        player2.delete();
        player1.delete();
        player1Throw.delete();
        return true;
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }

    public void slowFallPlayer2() {
        falling = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (falling) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player2.getMaxY() <= 670) {
                    player2.translate(0, verticalVelocity);
                    verticalVelocity++;
                } else {
                    falling = false;
                    verticalVelocity = 0;
                    System.out.println(player2.getMaxY());
                    break;
                }
            }
        });
        fallThread.start();
    }


    private void slowRisePlayer2() {
        rising = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (rising) {
                try {
                    Thread.sleep(13);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player2.getY() > 75) {
                    verticalVelocity++;
                    player2.translate(0, -verticalVelocity);
                    if (player2.getY() < 75) {
                        slowFallPlayer2();
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
