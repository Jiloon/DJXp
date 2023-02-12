package model;

import java.util.ArrayList;

// Represents a dj set, holding an ordered list of songs, having a title, and a genre
public class Set {
    private ArrayList<Song> songs;
    private String name;
    private String genre;

    Set() {
        songs = new ArrayList<>();
        name = "";
        genre = "";
    }

    Set(String givenName, String givenGenre) {
        this();
        name = givenName;
        genre = givenGenre;
    }

    public void addSongToSet(String fileName) {
        songs.add(new Song(fileName));
    }

    public void addSongToSet(String fileName, int givenBPM, String givenKey) {
        songs.add(new Song(fileName, givenBPM, givenKey));
    }
}
