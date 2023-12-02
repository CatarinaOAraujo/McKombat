package org.codeforall.ooptimus;

public class Main {
    public static void main(String[] args) {

        Elements elements = new Elements();
        Game game = new Game();

        elements.getBackground();
        elements.getPlayer2();
        elements.getLife();
        elements.getPlayer1();

        EventHandlerPlayer1 eventHandlerPlayer1 = new EventHandlerPlayer1(Elements.player1, Elements.player2,Elements.lifePlayer1, Elements.background, Elements.backgroundPlayer1,game);
        eventHandlerPlayer1.init();

        EventHandlerPlayer2 eventHandlerPlayer2 = new EventHandlerPlayer2(Elements.player2,Elements.player1,Elements.lifePlayer2,Elements.background,Elements.backgroundPlayer2,game);
        game.startMenu();
        eventHandlerPlayer2.init();
    }
}