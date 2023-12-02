package org.codeforall.ooptimus;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Game {
    Game() {
        this.elements = new Elements();
        this.startBackground = elements.getStartBackground();
        this.eventHandlerPlayer1 = new EventHandlerPlayer1(Elements.player1, Elements.player2, Elements.lifePlayer1, Elements.background, Elements.backgroundPlayer1, this);
        this.eventHandlerPlayer2 = new EventHandlerPlayer2(Elements.player2, Elements.player1, Elements.lifePlayer2, Elements.background, Elements.backgroundPlayer2, this);
        this.gameIsStarted = false;
    }

    private Elements elements;
    private Picture startBackground;
    private EventHandlerPlayer1 eventHandlerPlayer1;
    private EventHandlerPlayer2 eventHandlerPlayer2;
    private boolean gameIsStarted;


    public void init() {
        elements.getBackground();
        elements.getPlayer2();
        elements.getPlayer1();
        elements.getLife();
        eventHandlerPlayer1.init();
        eventHandlerPlayer2.init();
    }

    public void startMenu() {
        Sound.startSound.play();
        startBackground.draw();
    }

    public void closeMenu() {
        startBackground.delete();
        gameIsStarted = true;
    }

    public boolean getGameIsStarted() {
        return gameIsStarted;
    }
}
