package ui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import model.Song;

// A basic stripped DJ application
public class DJXp {
    final JFXPanel jfxPanel = new JFXPanel();
    private MediaPlayer audioPlayer;

    DJXp() {
        Song usedToLove = new Song("Martin Garrix, Dean Lewis - Used To Love");
        audioPlayer = new MediaPlayer(usedToLove.getAudio());
        audioPlayer.play();
    }
}
