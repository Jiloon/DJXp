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
        songList = new ArrayList<>();
        song0 = new Song("Martin Garrix, Dean Lewis - Used To Love");
        song1 = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)");
        song2 = new Song("Judah and the Lion - Why did you run (JILOON Remix)");
        songList.add(song0);
        songList.add(song1);
        set0 = new Set("MySet");
        set1 = new Set("MySet", "EDM");
        set2 = new Set("MySet", "EDM", songList);
        set3 = new Set();
    }

    @Test
    void testConstructorNoParams() {
        assertTrue(set3.getSongs().isEmpty());
        assertEquals("", set3.getName());
        assertEquals("", set3.getGenre());
    }

    @Test
    void testConstructorName() {
        assertTrue(set0.getSongs().isEmpty());
        assertEquals("MySet", set3.getName());
        assertEquals("", set3.getGenre());
    }

    @Test
    void testConstructorNameGenre() {
        assertTrue(set1.getSongs().isEmpty());
        assertEquals("MySet", set3.getName());
        assertEquals("EDM", set3.getGenre());
    }

    @Test
    void testConstructorNameGenreSongs() {
        assertEquals(set2.getSongs(), songList);
        assertEquals("MySet", set3.getName());
        assertEquals("EDM", set3.getGenre());
    }
}
