package model;

import java.util.ArrayList;

// Represents a dj set, holding an ordered list of songs, having a title, and a genre
public class Set {
    private ArrayList<Song> songs;
    private String name;
    private String genre;

    // EFFECTS: Constructs a new set obj with an empty list of songs, no name, and no genre
    public Set() {
        songs = new ArrayList<>();
        name = "";
        genre = "";
    }

    // EFFECTS: Constructs a new set obj with a given list of songs, a name, and genre
    public Set(String givenName, String givenGenre, ArrayList<Song> givenSongs) {
        songs = givenSongs;
        name = givenName;
        genre = givenGenre;
    }

    // EFFECTS: Constructs a new set obj with an empty list of songs, and a given name and given genre
    public Set(String givenName, String givenGenre) {
        this();
        name = givenName;
        genre = givenGenre;
    }

    // EFFECTS: Constructs a new set obj with an empty list of songs, and a given name and no genre
    public Set(String givenName) {
        this();
        name = givenName;
    }

    // MODIFIES: this
    // EFFECTS: adds a new song object according to its respective file name to the songs in this set
    public void addSongToSet(String fileName) throws NullPointerException {
        songs.add(new Song(fileName));
    }

    // MODIFIES: this
    // EFFECTS: adds a new song object according to its respective file name with specified bpm and key to the songs
    //          in the set
    public void addSongToSet(String fileName, int givenBPM, String givenKey) throws NullPointerException {
        songs.add(new Song(fileName, givenBPM, givenKey));
    }

    // MODIFIES: this
    // EFFECTS: adds a song obj to the list of songs in the set
    public void addSongToSet(Song song) {
        songs.add(song);
    }

    // REQUIRES: the song to be removed exists in the set
    // MODIFIES: this
    // EFFECTS: removes specified song obj from the list of songs in the set
    public void removeSongFromSet(Song song) {
        songs.remove(song);
    }

    public void setGenre(String givenGenre) {
        genre = givenGenre;
    }

    public void setName(String givenName) {
        name = givenName;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
