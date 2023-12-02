package org.codeforall.ooptimus;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import java.util.ArrayList;
import java.util.List;

public class Elements extends Picture {
    private static final int PADDING = 10;
    public static Picture startBackground  = new Picture(PADDING, PADDING, "Start_Background.png");
    public static Picture background = new Picture(PADDING, PADDING, "Background.jpg");
    public static Picture backgroundPlayer1 = new Picture(PADDING, PADDING, "Player1_winner.png");
    public static Picture backgroundPlayer2= new Picture(PADDING, PADDING, "Player2_winner.png");
    public static Picture player1 = new Picture(170, 400, "Player1.png");
    public static Picture player2 = new Picture(1300, 400, "Player2.png");
    public static List<Picture> lifePlayer2 = new ArrayList<>();
    public static List<Picture> lifePlayer1 = new ArrayList<>();


    public static Picture getPlayer1Throw() {

        return new Picture(Elements.player1.getX(), Elements.player1.getY(), "Player1_throw.png");
    }

    public static Picture getPlayer2Throw() {
        return new Picture(Elements.player2.getX(), Elements.player2.getY(), "Player2_throw.png");
    }

    public void getBackground() {
        background.draw();
    }

    public static Picture getStartBackground() {
        return startBackground;
    }

    public void getPlayer2() {
        player1.grow(-10,-20);
        player1.draw();
    }

    public void getPlayer1() {
        player2.grow(-10,-20);
        player2.draw();
    }

    public void getLife() {
        for (int x = 0; x < 4; x++) {
            lifePlayer2.add(new Picture(35 + 50 * x, 40, "Life_icon.png"));
            lifePlayer2.get(x).draw();
        }
        for (int x = 0; x < 4; x++) {
            lifePlayer1.add(new Picture(1500 - 50 * x, 40, "Life_icon.png"));
            lifePlayer1.get(x).draw();
        }


    }
}
