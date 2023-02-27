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
        assertTrue(set0.getSongs().isEmpty());
        assertEquals("", set0.getName());
        assertEquals("", set0.getGenre());
    }

    @Test
    void testConstructorName() {
        assertTrue(set1.getSongs().isEmpty());
        assertEquals("MySet", set1.getName());
        assertEquals("", set1.getGenre());
    }

    @Test
    void testConstructorNameGenre() {
        assertTrue(set2.getSongs().isEmpty());
        assertEquals("MySet", set2.getName());
        assertEquals("EDM", set2.getGenre());
    }

    @Test
    void testConstructorNameGenreSongs() {
        assertEquals(songList, set3.getSongs());
        assertEquals("MySet", set3.getName());
        assertEquals("EDM", set3.getGenre());
    }

    @Test
    void testAddSongToEmptySet() {
        ArrayList<Song> tempList = new ArrayList<>();
        tempList.add(song2);
        set0.addSongToSet(song2);
        assertEquals(tempList, set0.getSongs());
    }

    @Test
    void testAddSongToFilledSet() {
        songList.add(song2);
        set3.addSongToSet(song2);
        assertEquals(songList, set3.getSongs());
    }

    @Test
    void testRemoveSongFromSet() {
        songList.remove(song1);
        set3.removeSongFromSet(song1);
        assertEquals(songList, set3.getSongs());
    }

    @Test
    void testEditGenre() {
        set3.setGenre("Future House");
        assertEquals("Future House", set3.getGenre());
    }

    @Test
    void testEditName() {
        set3.setName("Set Two");
        assertEquals("Set Two", set3.getName());
    }
}
