package model;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Test class for Song
class SongTest {
    private Song song;
    private Song song2;
    static final String FILE_NAME = "Martin Garrix, Dean Lewis - Used To Love";
    static final Media FILE_AUDIO = new Media(
            new File("data/songs/" + FILE_NAME + ".mp3").toURI().toString());

    @BeforeEach
    void setup() {
        song = new Song(FILE_NAME, 119, "C# Maj");
        song2 = new Song(FILE_NAME);
    }

    @Test
    void testConstructorKeyBPM() {
        String[] artists = new String[2];
        artists[0] = "Martin Garrix";
        artists[1] = "Dean Lewis";
        assertEquals("Used To Love", song.getName());
        assertEquals(119, song.getBPM());
        assertEquals("C# Maj", song.getKey());
        assertEquals(song.getAudio().getMetadata(), FILE_AUDIO.getMetadata());
        assertEquals(236.8936767578125, song.getLength());
        assertEquals("EDM", song.getGenre());
        assertEquals(artists[0], song.getArtists()[0]);
        assertEquals(artists[1], song.getArtists()[1]);
    }

    @Test
    void testConstructor() {
        String[] artists = new String[2];
        artists[0] = "Martin Garrix";
        artists[1] = "Dean Lewis";
        assertEquals("Used To Love", song2.getName());
        assertEquals(0, song2.getBPM());
        assertEquals("Unknown", song2.getKey());
        assertEquals(song.getAudio().getMetadata(), FILE_AUDIO.getMetadata());
        assertEquals(236.8936767578125, song2.getLength());
        assertEquals("EDM", song2.getGenre());
        assertEquals(artists[0], song2.getArtists()[0]);
        assertEquals(artists[1], song2.getArtists()[1]);
    }

}