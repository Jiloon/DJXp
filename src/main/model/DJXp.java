package model;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class DJXp {
    final JFXPanel jfxPanel = new JFXPanel();

    private MediaPlayer audioPlayer;
    private ArrayList<Set> listOfSets;
    private double currentSpeed;
    private int currentVolume;
    private double nextSpeed;
    private int nextVolume;

    public DJXp() {
        listOfSets = new ArrayList<>();
        currentSpeed = 1.0;
        currentVolume = 0;
        nextSpeed = 1.0;
        nextVolume = 0;

        Song utl = new Song("Martin Garrix, Dean Lewis - Used To Love", 119, "E Maj");
        Song har = new Song("Mike Williams - Harmony (feat. Xillions)");
        Song hme = new Song("Robert Grace - Hate Me");
        Song enf = new Song("Stromae - L'enfer");
        Song dfy = new Song("VALORANT, Grabbitz - Die For You");
        audioPlayer = new MediaPlayer(har.getAudio());
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
