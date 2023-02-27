package model;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

/*
  Represents an audio player object that handles the playback of songs in sets. Contains the list of sets available to
  play, the current playback speed and volume, and the next song's playback speed and volume, as well as an indicator
  to the current set being played, and the media player itself. Also holds a JFXPanel to initialize the MediaPlayer as
  well as a sound logo audio file to play at program start up
*/
public class Playback {
    //only initialized to allow for MediaPlayer. Will give warning; ignore. JavaFX loaded from class-path instead of
    //module-path, but nothing actually breaks. I'm too lazy to go fix it, so ignore.
    public static final JFXPanel JFX_PANEL = new JFXPanel();
    public static final Media SOUND_LOGO = new Media(new File("data/soundLogo.mp3").toURI().toString());
    public static final String SET_NOT_FOUND = "That set doesn't exist";
    public static final String SONG_NOT_FOUND = "That song isn't in the set";

    private MediaPlayer audioPlayer;
    private ArrayList<Set> listOfSets;
    private double currentSpeed;
    private double currentVolume;
    private double nextSpeed;
    private double nextVolume;
    private int currentSet;
    private int currentSong;
    private ArrayList<Song> poolOfSongs;
    private boolean stopped;

    // EFFECTS: Creates a new playback obj with no sets, a current song speed of 1.0, a current song volume of 0,
    //          a next song speed of 1.0, and a next song volume of 0, and loads and plays a startup sound logo
    public Playback() {
        listOfSets = new ArrayList<>();
        poolOfSongs = new ArrayList<>();
        currentSpeed = 1.0;
        currentVolume = 1.0;
        nextSpeed = 1.0;
        nextVolume = 1.0;
        currentSet = 0;
        currentSong = 0;
        stopped = true;

        audioPlayer = new MediaPlayer(SOUND_LOGO);
        audioPlayer.play();
    }

    // MODIFIES: this
    // EFFECTS: renames specified set through name, with new name
    public void setSetName(String setName, String newName) throws NullPointerException {
        boolean found = false;
        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                set.setName(newName);
                found = true;
            }
        }

        if (!found) {
            throw new NullPointerException(SET_NOT_FOUND);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets specified set through name's genre
    public void setGenreName(String setName, String setGenre) throws NullPointerException {
        boolean found = false;
        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                set.setGenre(setGenre);
                found = true;
            }
        }

        if (!found) {
            throw new NullPointerException(SET_NOT_FOUND);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new song object through given file name, bpm, and key, and adds it to the song pool
    public void addSong(String fileName, int givenBPM, String givenKey) {
        poolOfSongs.add(new Song(fileName, givenBPM, givenKey));
    }

    // EFFECTS: returns all initialized songs
    public ArrayList<Song> getSongs() {
        return poolOfSongs;
    }

    // REQUIRES: a set and song to have already been initialized
    // EFFECTS: returns remaining time left in the set
    public double getRemainingSetTime() {
        double result = 0;
        for (Song song : getCurrentSetSongs()) {
            if (getCurrentSetSongs().indexOf(song) > currentSong) {
                result += song.getLength();
            }
        }

        result += audioPlayer.getTotalDuration().toSeconds() - audioPlayer.getCurrentTime().toSeconds();

        return result;
    }

    // EFFECTS: returns remaining # of songs left in the set, excluding the one currently playing/selected
    public int getRemainingSetSongs() {
        return getCurrentSetSongs().size() - (currentSong + 1);
    }

    /*
    // MODIFIES: this
    // EFFECTS: produces a new set object with given name and adds it to the list of sets
    public void makeNewSet(String givenName) {
        listOfSets.add(new Set(givenName));
    }
     */

    // MODIFIES: this
    // EFFECTS: produces new set obj with given name and genre and adds it to the list of sets
    public void makeNewSet(String givenName, String givenGenre) {
        listOfSets.add(new Set(givenName, givenGenre));
    }

    /*
    // MODIFIES: this
    // EFFECTS: produces new set with given name, genre, and list of songs and adds it to the list of sets
    public void makeNewSet(String givenName, String givenGenre, ArrayList<Song> givenSongs) {
        listOfSets.add(new Set(givenName, givenGenre, givenSongs));
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: adds set to list of sets
    public void makeNewSet(Set givenSet) {
        listOfSets.add(givenSet);
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: removes set from the list of sets
    public void removeSet(Set givenSet) throws NullPointerException {
        listOfSets.remove(givenSet);
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: removes set through set ID from list of sets
    public void removeSet(int setID) throws ArrayIndexOutOfBoundsException {
        listOfSets.remove(setID);
    }
     */

    // MODIFIES: this
    // EFFECTS: removes set through set name from list of sets
    public void removeSet(String setName) throws NullPointerException {
        boolean found = false;

        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                listOfSets.remove(set);
                found = true;
            }
        }

        if (!found) {
            throw new NullPointerException(SET_NOT_FOUND);
        }
    }

    /*
    // MODIFIES: this
    // EFFECTS: removes specified song from specified set
    public void removeSongFromSet(Song givenSong, Set set) throws NullPointerException {
        set.removeSongFromSet(givenSong);
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: removes specified song from specified set through set name
    public void removeSongFromSet(Song givenSong, String setName) throws NullPointerException {
        boolean found = false;

        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                set.removeSongFromSet(givenSong);
                found = true;
            }
        }

        if (!found) {
            throw new NullPointerException(SONG_NOT_FOUND);
        }
    }
     */

    // MODIFIES: this
    // EFFECTS: removes specified song from specified set through song name and set name
    public void removeSongFromSet(String songName, String setName) throws NullPointerException {
        boolean found = false;

        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                for (Song song : poolOfSongs) {
                    if (song.getName().equals(songName)) {
                        set.removeSongFromSet(song);
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            throw new NullPointerException(SONG_NOT_FOUND);
        }
    }

    /*
    // MODIFIES: this
    // EFFECTS: removes specified song from specified set through set id
    public void removeSongFromSet(Song givenSong, int setID) throws ArrayIndexOutOfBoundsException {
        if (setID < listOfSets.size() && setID >= 0) {
            listOfSets.get(setID).removeSongFromSet(givenSong);
        } else {
            throw new ArrayIndexOutOfBoundsException(SET_NOT_FOUND);
        }
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: adds specified song to specified set
    public void addSongToSet(Song givenSong, Set set) throws NullPointerException {
        set.addSongToSet(givenSong);
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: adds specified song to specified set through set name
    public void addSongToSet(Song givenSong, String setName) throws NullPointerException {
        boolean found = false;

        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                set.addSongToSet(givenSong);
                found = true;
            }
        }

        if (!found) {
            throw new NullPointerException(SET_NOT_FOUND);
        }
    }
     */

    // MODIFIES: this
    // EFFECTS: adds specified song to specified set through song name and set name
    public void addSongToSet(String songName, String setName) throws NullPointerException {
        boolean found = false;

        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                for (Song song : poolOfSongs) {
                    if (song.getName().equals(songName)) {
                        set.addSongToSet(song);
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            throw new NullPointerException(SET_NOT_FOUND);
        }
    }

    /*
    // MODIFIES: this
    // EFFECTS: adds specified song to specified set through set ID
    public void addSongToSet(Song givenSong, int setID) throws ArrayIndexOutOfBoundsException {
        if (setID < listOfSets.size() && setID >= 0) {
            listOfSets.get(setID).addSongToSet(givenSong);
        } else {
            throw new ArrayIndexOutOfBoundsException(SET_NOT_FOUND);
        }
    }
     */

    // MODIFIES: this
    // EFFECTS: removes specified song from specified set and adds it at a new position through set ID
    public void positionSong(Song givenSong, int setID, int newPosition) throws NullPointerException {
        if (listOfSets.get(setID).getSongs().contains(givenSong)) {
            listOfSets.get(setID).removeSongFromSet(givenSong);
            listOfSets.get(setID).getSongs().add(newPosition - 1, givenSong);
        } else {
            throw new NullPointerException(SONG_NOT_FOUND);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes specified song from specified set and adds it at new position through song name and set name
    public void positionSong(String songName, String setName, int newPosition) throws NullPointerException {
        for (Set set : listOfSets) {
            if (set.getName().equals(setName)) {
                for (Song song : set.getSongs()) {
                    if (song.getName().equals(songName)) {
                        positionSong(song, listOfSets.indexOf(set), newPosition);
                    }
                }
            }
        }
    }

    // EFFECTS: returns the currently selected set
    private Set getCurrentSet() {
        return listOfSets.get(currentSet);
    }

    // EFFECTS: returns the name of the currently selected set
    public String getCurrentSetTitle() {
        return getCurrentSet().getName();
    }

    // EFFECTS: returns genre of currently selected set
    public String getCurrentSetGenre() {
        return getCurrentSet().getGenre();
    }

    // EFFECTS: returns list of songs of currently selected set
    public ArrayList<Song> getCurrentSetSongs() {
        return getCurrentSet().getSongs();
    }

    // EFFECTS: returns currently selected song in currently selected set
    private Song getCurrentSong() {
        return getCurrentSetSongs().get(currentSong);
    }

    // EFFECTS: returns next song in queue in currently selected set
    private Song getNextSong() {
        if (currentSong + 1 < listOfSets.get(currentSet).getSongs().size()) {
            return getCurrentSetSongs().get(currentSong + 1);
        } else {
            return getCurrentSetSongs().get(0);
        }
    }

    // EFFECTS: returns name of next song in queue in currently selected set
    public String getNextSongName() {
        return getNextSong().getName();
    }

    // EFFECTS: returns artists of next song in queue in currently selected set
    public String[] getNextSongArtists() {
        return getNextSong().getArtists();
    }

    // EFFECTS: returns song duration of next song in queue in currently selected set
    public double getNextSongLength() {
        return getCurrentSong().getLength();
    }

    // EFFECTS: returns song bpm of next song in queue in currently selected set
    public int getNextSongBPM() {
        return getNextSong().getBPM();
    }

    // EFFECTS: returns key of next song in queue in currently selected set
    public String getNextSongKey() {
        return getNextSong().getKey();
    }

    // EFFECTS: returns genre of next song in queue of currently selected set
    public String getNextSongGenre() {
        return getNextSong().getGenre();
    }

    // EFFECTS: returns name of currently selected song in currently selected set
    public String getCurrentSongName() {
        return getCurrentSong().getName();
    }

    // EFFECTS: returns artists of currently selected song in currently selected set
    public String[] getCurrentSongArtists() {
        return getCurrentSong().getArtists();
    }

    // EFFECTS: returns duration of currently selected song in currently selected set
    public double getCurrentSongLength() {
        return getCurrentSong().getLength();
    }

    // EFFECTS: returns bpm of currently selected song in currently selected set
    public int getCurrentSongBPM() {
        return getCurrentSong().getBPM();
    }

    // EFFECTS: returns key of currently selected song in currently selected set
    public String getCurrentSongKey() {
        return getCurrentSong().getKey();
    }

    // EFFECTS: returns genre of currently selected song in currently selected set
    public String getCurrentSongGenre() {
        return getCurrentSong().getGenre();
    }

    // EFFECTS: returns true if the song finishes playing on its own without intervention, false otherwise
    public boolean isEnd() {
        if (!stopped) {
            return (audioPlayer.getCurrentTime().greaterThanOrEqualTo(audioPlayer.getCycleDuration()));
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: toggles playback (play -> pause, pause -> play)
    public void togglePlay() {
        if (audioPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            this.pause();
        } else {
            this.play();
        }
    }

    // MODIFIES: this
    // EFFECTS: handles switching songs; resets the playback position in current set, stops audio if finishing last
    //          song, else, reloads audio player with next song in queue and starts its play. Carries next song
    //          playback values to current song and resets next song playback values
    public void handleSongEnd() {
        if (currentSong + 1 >= getCurrentSetSongs().size()) {
            currentSong = 0;
            audioPlayer.stop();
            audioPlayer = new MediaPlayer(getCurrentSong().getAudio());
            stopped = true;
        } else {
            audioPlayer.stop();
            currentSong += 1;
            audioPlayer = new MediaPlayer(getCurrentSong().getAudio());
            audioPlayer.play();
        }

        currentSpeed = nextSpeed;
        currentVolume = nextVolume;
        nextSpeed = 1.0;
        nextVolume = 1.0;
    }

    /*
    // MODIFIES: this
    // EFFECTS: resets playback values and sets the current set to specification
    public void selectSet(int givenSet) throws ArrayIndexOutOfBoundsException {
        if (givenSet < listOfSets.size() && givenSet >= 0) {
            currentSet = givenSet;
            currentSong = 0;
            currentSpeed = 1.0;
            currentVolume = 1.0;
            nextSpeed = 1.0;
            nextVolume = 1.0;
            audioPlayer = new MediaPlayer(getCurrentSong().getAudio());
            audioPlayer.stop();
        } else {
            throw new ArrayIndexOutOfBoundsException(SET_NOT_FOUND);
        }
    }
     */

    // MODIFIES: this
    // EFFECTS: resets playback values and sets the current set to specification through set name
    public void selectSet(String givenSetName) throws NullPointerException {
        boolean found = false;
        for (Set set : listOfSets) {
            if (set.getName().equals(givenSetName)) {
                found = true;
                currentSet = listOfSets.indexOf(set);
                currentSong = 0;
                currentSpeed = 1.0;
                currentVolume = 1.0;
                nextSpeed = 1.0;
                nextVolume = 1.0;
                audioPlayer = new MediaPlayer(getCurrentSong().getAudio());
                audioPlayer.stop();
            }
        }

        if (!found) {
            throw new NullPointerException(SET_NOT_FOUND);
        }
    }

    // MODIFIES: this
    // EFFECTS: plays loaded songs
    public void play() throws NullPointerException {
        if (audioPlayer.getMedia().equals(SOUND_LOGO)) {
            throw new NullPointerException("Nothing selected");
        } else {
            audioPlayer.play();
            stopped = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: pauses audio
    public void pause() {
        audioPlayer.pause();
    }

    /*
    // MODIFIES: this
    // EFFECTS: skips to specified time in song in milliseconds
    public void seek(int time) {
        audioPlayer.seek(Duration.millis(time));
    }
     */

    /*
    // MODIFIES: this
    // EFFECTS: skips to specified time in song
    public void seek(Duration time) {
        audioPlayer.seek(time);
    }
     */

    // MODIFIES: this
    // EFFECTS: sets playback speed to specification
    public void setSpeed(double speed) {
        currentSpeed = speed;
        audioPlayer.setRate(currentSpeed);
    }

    // MODIFIES: this
    // EFFECTS: sets playback volume to specification
    public void setVolume(double volume) {
        currentVolume = volume;
        audioPlayer.setVolume(currentVolume);
    }

    // MODIFIES: this
    // EFFECTS: sets next playback speed to specification
    public void setNextSpeed(double speed) {
        nextSpeed = speed;
    }

    // MODIFIES: this
    // EFFECTS: sets next playback volume to specification
    public void setNextVolume(double volume) {
        nextVolume = volume;
    }

    // EFFECTS: returns the current playback speed
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    // EFFECTS: returns the current playback volume
    public double getCurrentVolume() {
        return currentVolume;
    }

    // EFFECTS: returns the next playback speed
    public double getNextSpeed() {
        return nextSpeed;
    }

    // EFFECTS: returns the next playback volume
    public double getNextVolume() {
        return nextVolume;
    }
}
