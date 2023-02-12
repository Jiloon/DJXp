package model;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

public class Playback {
    //only initialized to allow for MediaPlayer. Will give warning; ignore. JavaFX loaded from class-path instead of
    //module-path, but nothing actually breaks. I'm too lazy to go fix it so ignore.
    final JFXPanel jfxPanel = new JFXPanel();
    final Media soundLogo = new Media(new File("data/soundLogo.mp3").toURI().toString());

    private MediaPlayer audioPlayer;
    private ArrayList<Set> listOfSets;
    private double currentSpeed;
    private int currentVolume;
    private double nextSpeed;
    private int nextVolume;

    // EFFECTS: Creates a new playback obj with no sets, a current song speed of 1.0, a current song volume of 0,
    //          a next song speed of 1.0, and a next song volume of 0, and loads and plays a startup sound logo
    public Playback() {
        listOfSets = new ArrayList<>();
        currentSpeed = 1.0;
        currentVolume = 0;
        nextSpeed = 1.0;
        nextVolume = 0;

        //Song utl = new Song("Martin Garrix, Dean Lewis - Used To Love", 119, "E Maj");
        //Song wyr = new Song("Judah and the Lion - Why did you run (JILOON Remix)", 120, "G Maj");
        //Song tay = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)", 128, "A Min");
        audioPlayer = new MediaPlayer(soundLogo);
        audioPlayer.play();
    }

    public void adjustSpeed(double adjustByPc) {
        currentSpeed *= adjustByPc / 100;
    }

    public void adjustVolume(int volume) {
        currentVolume = volume;
    }

    public void setNextSpeed(double speed) {
        nextSpeed = speed;
    }

    public void setNextVolume(int volume) {
        nextVolume = volume;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public double getNextSpeed() {
        return nextSpeed;
    }

    public int getNextVolume() {
        return nextVolume;
    }
}
