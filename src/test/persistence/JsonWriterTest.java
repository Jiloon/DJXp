package persistence;

import model.Set;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    private ArrayList<Set> sets;
    private Set set1;
    private Set set2;
    private Song usedToLove;
    private Song whyDidYouRun;
    private Song thinkAboutYouNobody;

    @BeforeEach
    void setup() {
        sets = new ArrayList<>();
        set1 = new Set("MySet", "EDM");
        set2 = new Set("OtherSet", "Chill");
        usedToLove = new Song("Martin Garrix, Dean Lewis - Used To Love", 119, "C# Maj");
        whyDidYouRun = new Song("Judah and the Lion - Why did you run (JILOON Remix)", 128,
                "C Maj");
        thinkAboutYouNobody = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)", 120,
                "C Maj");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("my\0illegal:fileName");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterSetsEmpty() {
        try {
            ArrayList<Song> testSongPool;
            JsonWriter writer = new JsonWriter("testWriterSetsEmpty");
            writer.open();
            writer.write(sets);
            writer.close();

            JsonReader reader = new JsonReader("testWriterSetsEmpty");
            testSongPool = reader.getSongsFromFile();
            sets = reader.getSetsFromFile(testSongPool);
            assertEquals(0, sets.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSetsGeneral() {
        try {
            sets.add(set1);
            sets.add(set2);
            set1.addSongToSet(usedToLove);
            set1.addSongToSet(whyDidYouRun);
            ArrayList<Song> testSongPool;
            JsonWriter writer = new JsonWriter("testWriterSetsGeneral");
            writer.open();
            writer.write(sets);
            writer.close();

            JsonReader reader = new JsonReader("testWriterSetsGeneral");
            testSongPool = reader.getSongsFromFile();
            sets = reader.getSetsFromFile(testSongPool);
            assertEquals(2, sets.size());
            assertEquals("MySet", sets.get(0).getName());
            assertEquals("OtherSet", sets.get(1).getName());
            assertEquals("EDM", sets.get(0).getGenre());
            assertEquals("Chill", sets.get(1).getGenre());
            assertEquals(2, sets.get(0).getSongs().size());
            assertEquals(0, sets.get(1).getSongs().size());
            assertEquals(testSongPool.get(0), sets.get(0).getSongs().get(0));
            assertEquals(testSongPool.get(1), sets.get(0).getSongs().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSongsGeneral() {
        try {
            ArrayList<Song> testSongPool;
            JsonReader reader = new JsonReader("testWriterSetsGeneral");
            testSongPool = reader.getSongsFromFile();
            ArrayList<String> oldFileNames = new ArrayList<>();
            ArrayList<String> fileNames = new ArrayList<>();

            for (int i = 0; i < testSongPool.size(); i++) {
                 oldFileNames.add(testSongPool.get(0).getFileName());
            }

            JsonWriter writer = new JsonWriter("testWriterSetsGeneral");
            writer.open();
            writer.writePool(testSongPool);
            writer.close();

            testSongPool = reader.getSongsFromFile();

            for (int i = 0; i < testSongPool.size(); i++) {
                fileNames.add(testSongPool.get(0).getFileName());
            }

            assertEquals(oldFileNames, fileNames);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
