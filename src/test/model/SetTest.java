package model;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

// Test class for Set
public class SetTest {
    private Set set;
    private Set set1;
    private Set set2;
    private Set set3;
    private Song song0;
    private Song song1;
    private Song song2;
    private ArrayList<Song> songList;

    @BeforeEach
    void setup() {
        songList = new ArrayList<>();
        song0 = new Song("Martin Garrix, Dean Lewis - Used To Love");
        song1 = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)");
        song2 = new Song("Judah and the Lion - Why did you run (JILOON Remix)");
        songList.add(song0);
        songList.add(song1);
        set = new Set("MySet");
        set1 = new Set("MySet", "EDM");
        set2 = new Set("MySet", "EDM", songList);
        set3 = new Set();
    }
}
