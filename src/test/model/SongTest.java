package model;

import javafx.scene.media.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Song
class SongTest {
    private Song song;
    private Song song2;
    private Song song3;
    private String[] artists = new String[2];
    static final String FILE_NAME = "Martin Garrix, Dean Lewis - Used To Love";
    static final Media FILE_AUDIO = new Media(
            new File("data/songs/" + FILE_NAME + ".mp3").toURI().toString());

    @BeforeEach
    void setup() {
        artists[0] = "Martin Garrix";
        artists[1] = "Dean Lewis";
        song = new Song(FILE_NAME, 119, "C# Maj");
        song2 = new Song(FILE_NAME);
        song3 = new Song(FILE_NAME, 119, "C# Maj", 236.8936767578125, "EDM",
                "Used To Love", artists);
    }

    @Test
    void testConstructorKeyBPM() {
        assertEquals("Used To Love", song.getName());
        assertEquals(119, song.getBPM());
        assertEquals("C# Maj", song.getKey());
        assertEquals(song.getAudio().getMetadata(), FILE_AUDIO.getMetadata());
        assertEquals(236.8936767578125, song.getLength());
        assertEquals("EDM", song.getGenre());
        assertEquals(artists[0], song.getArtists()[0]);
        assertEquals(artists[1], song.getArtists()[1]);
        assertEquals(FILE_NAME, song.getFileName());
    }

    @Test
    void testConstructor() {
        assertEquals("Used To Love", song2.getName());
        assertEquals(0, song2.getBPM());
        assertEquals("Unknown", song2.getKey());
        assertEquals(song2.getAudio().getMetadata(), FILE_AUDIO.getMetadata());
        assertEquals(236.8936767578125, song2.getLength());
        assertEquals("EDM", song2.getGenre());
        assertEquals(artists[0], song2.getArtists()[0]);
        assertEquals(artists[1], song2.getArtists()[1]);
        assertEquals(FILE_NAME, song2.getFileName());
    }

    @Test
    void testConstructorAllInput() {
        assertEquals("Used To Love", song3.getName());
        assertEquals(119, song3.getBPM());
        assertEquals("C# Maj", song3.getKey());
        assertEquals(236.8936767578125, song3.getLength());
        assertEquals("EDM", song3.getGenre());
        assertEquals(artists[0], song3.getArtists()[0]);
        assertEquals(artists[1], song3.getArtists()[1]);
        assertEquals(FILE_NAME, song3.getFileName());
    }

    @Test
    void testConstructorFileMIA() {
        Song song3 = new Song("OBviously not a real file");
        assertNull(song3.getName());
        assertEquals(0, song3.getBPM());
        assertEquals("Unknown", song3.getKey());
        assertNull(song3.getAudio());
        assertEquals(0, song3.getLength());
        assertNull(song3.getGenre());
        assertNull(song3.getArtists());
    }

}