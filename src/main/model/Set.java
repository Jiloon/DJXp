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

    // EFFECTS: Constructs a new set obj with an empty list of songs, and a given name and given genre
    public Set(String givenName, String givenGenre) {
        this();
        name = givenName;
        genre = givenGenre;
    }

    // MODIFIES: this
    // EFFECTS: adds a new song object according to its respective file name to the songs in this set
    public void addSongToSet(String fileName) {
        songs.add(new Song(fileName));
    }

    // MODIFIES: this
    // EFFECTS: adds a new song object according to its respective file name with specified bpm and key to the songs
    //          in the set
    public void addSongToSet(String fileName, int givenBPM, String givenKey) {
        songs.add(new Song(fileName, givenBPM, givenKey));
    }
}
