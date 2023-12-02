package org.codeforall.ooptimus;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    private final AudioClip clip;

    public static final Sound startSound = new Sound("/Starting_theme.wav");
    public static final Sound gustavoShotSound = new Sound("/Player1_throw.wav");
    public static final Sound carolShotSound = new Sound("/Player2_throw.wav");
    public static final Sound carolWinnerSound = new Sound("/Player2_winner.wav");
    public static final Sound gustavoWinnerSound = new Sound("/Player1_winner.wav");

    private Sound(String name) {
        clip = Applet.newAudioClip(Sound.class.getResource(name));
    }

    public void play() {
        clip.play();
    }
    public void loop() {
        clip.loop();
    }
    public void stop() {
        clip.stop();
    }
}
