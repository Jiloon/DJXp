package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

// Test class for Set
public class SetTest {
    private Set set0;
    private Set set1;
    private Set set2;
    private Set set3;
    private Song song0;
    private Song song1;
    private Song song2;
    private ArrayList<Song> songList;

    @BeforeEach
    void setup() {
        song0 = new Song("Martin Garrix, Dean Lewis - Used To Love");
        song1 = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)");
        song2 = new Song("Judah and the Lion - Why did you run (JILOON Remix)");
        songList = new ArrayList<>();
        songList.add(song0);
        songList.add(song1);
        set0 = new Set();
        set1 = new Set("MySet");
        set2 = new Set("MySet", "EDM");
        set3 = new Set("MySet", "EDM", songList);
    }

    @Test
    void testConstructorNoParams() {
        assertTrue();
    }
}
