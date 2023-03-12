package persistence;

import model.Set;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    private ArrayList<Song> testSongPool;
    private Song usedToLove;
    private Song whyDidYouRun;
    private Song thinkAboutYouNobody;

    @BeforeEach
    void setup() {
        usedToLove = new Song("Martin Garrix, Dean Lewis - Used To Love", 119, "C# Maj");
        whyDidYouRun = new Song("Judah and the Lion - Why did you run (JILOON Remix)", 128,
                "C Maj");
        thinkAboutYouNobody = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)", 120,
                "C Maj");
        testSongPool = new ArrayList<>();
        testSongPool.add(usedToLove);
        testSongPool.add(whyDidYouRun);
        testSongPool.add(thinkAboutYouNobody);
    }

    @Test
    void testReaderSetsNonExistentFile() {
        JsonReader reader = new JsonReader("noSuchFile");
        try {
            ArrayList<Set> sets = reader.getSetsFromFile(testSongPool);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderSetsFirstSets() {
        JsonReader reader = new JsonReader("firstSets");
        try {
            ArrayList<Set> sets = reader.getSetsFromFile(testSongPool);
            assertEquals("MySet", sets.get(0).getName());
            assertEquals("EDM", sets.get(0).getGenre());
            ArrayList<Song> songs = sets.get(0).getSongs();
            assertEquals(2, songs.size());
            assertEquals(usedToLove, songs.get(0));
            assertEquals(whyDidYouRun, songs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSetsEmpty() {
        JsonReader reader = new JsonReader("testReaderSetsEmpty");
        try {
            ArrayList<Set> sets = reader.getSetsFromFile(testSongPool);
            assertEquals(0, sets.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSongsAlternateGivenPath() {
        JsonReader reader = new JsonReader("noSuchFile");
        try {
            ArrayList<Song> songs = reader.getSongsFromFile();
            assertEquals(usedToLove.getFileName(), songs.get(0).getFileName());
            assertEquals(whyDidYouRun.getFileName(), songs.get(1).getFileName());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }

    @Test
    void testReaderSongsNormalPath() {
        JsonReader reader = new JsonReader("firstSets");
        try {
            ArrayList<Song> songs = reader.getSongsFromFile();
            assertEquals(usedToLove.getFileName(), songs.get(0).getFileName());
            assertEquals(whyDidYouRun.getFileName(), songs.get(1).getFileName());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }
}
