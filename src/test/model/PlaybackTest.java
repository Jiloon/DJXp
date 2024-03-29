package model;

import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Playback
public class PlaybackTest {
    private Playback playback;
    private Song song0;
    private Song song1;
    private Song song2;
    private String[] artists;
    private String[] nextArtists;

    @BeforeEach
    void setup() {
        playback = new Playback();
        song0 = new Song("Martin Garrix, Dean Lewis - Used To Love");
        song1 = new Song("Kygo x NOTD - Think About You x Nobody (JILOON Remix)");
        song2 = new Song("Judah and the Lion - Why did you run (JILOON Remix)");
        playback.makeNewSet("MySet", "EDM");
        playback.addSong("Martin Garrix, Dean Lewis - Used To Love", 119, "C# Maj");
        playback.addSong("Kygo x NOTD - Think About You x Nobody (JILOON Remix)",
                128, "C Min");
        playback.addSong("Judah and the Lion - Why did you run (JILOON Remix)", 120, "A Min");
        playback.addSongToSet("Used To Love", "MySet");
        playback.selectSet("MySet");
        artists = new String[1];
        nextArtists = new String[2];
        artists[0] = "Judah & the Lion";
        nextArtists[0] = "Martin Garrix";
        nextArtists[1] = "Dean Lewis";
    }

    @Test
    void testSetSongPool() {
        ArrayList<Song> testSongs = new ArrayList<>();
        testSongs.add(song0);
        playback.setPoolOfSongs(testSongs);
        assertEquals(1, playback.getSongs().size());
        assertEquals(song0, playback.getSongs().get(0));
    }

    @Test
    void testSetSetList() {
        ArrayList<Set> testSets = new ArrayList<>();
        testSets.add(new Set("OtherSet"));
        playback.setListOfSets(testSets);
        assertEquals(1, playback.getAllSets().size());
        assertEquals(testSets.get(0), playback.getAllSets().get(0));
    }

    @Test
    void testConstructor() {
        Playback playback1 = new Playback();
        assertTrue(playback1.getSongs().isEmpty());
        assertEquals(1.0, playback1.getCurrentSpeed());
        assertEquals(1.0, playback1.getCurrentVolume());
        assertEquals(1.0, playback1.getNextSpeed());
        assertEquals(1.0, playback1.getNextVolume());
    }

    @Test
    void testIsEndStopped() {
        Playback playback1 = new Playback();
        assertFalse(playback1.isEnd());
    }

    @Test
    void testIsEndNotEnd() {
        playback.togglePlay();
        assertFalse(playback.isEnd());
    }



    @Test
    void testSelectSetNotFound() {
        try {
            playback.setSpeed(2.0);
            playback.selectSet("Rando Name");
            fail("Set should not have been found");
        } catch (NullPointerException e) {
            assertEquals(2.0, playback.getCurrentSpeed());
        }
    }

    @Test
    void testPositionSongNotFound() {
        try {
            playback.positionSong(song1, 0, 5);
            fail("Song should not have been found");
        } catch (NullPointerException e) {
            assertEquals(1, playback.getCurrentSetSongs().size());
        }
    }

    @Test
    void testAddSongToSetNotFound() {
        try {
            playback.addSongToSet("Rando Song", "Rando Set");
            fail("Set should not have been found");
        } catch (NullPointerException e) {
            assertEquals(3, playback.getSongs().size());
        }
    }

    @Test
    void testRemoveSongFromSet() {
        assertEquals(song0.getName(),playback.getCurrentSongName());
        playback.removeSongFromSet("Used To Love", "MySet");
        assertEquals(0, playback.getCurrentSetSongs().size());
    }

    @Test
    void testRemoveSongFromSetNotFound() {
        try {
            assertEquals(1, playback.getCurrentSetSongs().size());
            playback.removeSongFromSet("Rando", "MySet");
            fail("Song should not have been found");
        } catch (NullPointerException e) {
            assertEquals(1, playback.getCurrentSetSongs().size());
        }
    }

    @Test
    void testRemoveSet() {
        assertEquals(1, playback.getAllSets().size());
        playback.removeSet("MySet");
        assertEquals(0, playback.getAllSets().size());
    }

    @Test
    void testRemoveSetNotFound() {
        try {
            assertEquals(1, playback.getAllSets().size());
            playback.removeSet("AWJRYGAWUGR");
            fail("Should not have been found");
        } catch (NullPointerException e) {
            assertEquals(1, playback.getAllSets().size());
        }
    }

    @Test
    void testGetNumRemainingSongsZero() {
        assertEquals(0, playback.getRemainingSetSongs());
    }

    @Test
    void testGetNumRemainingSongsNonZero() {
        playback.addSongToSet(song1.getName(), "MySet");
        assertEquals(1, playback.getRemainingSetSongs());
        playback.handleSongEnd();
        assertEquals(0, playback.getRemainingSetSongs());
    }

    @Test
    void testGetRemainingTime() {
        playback.play();
        playback.getAudioPlayer().setOnReady(() -> {
            assertEquals(Math.round(song0.getLength()), Math.round(playback.getRemainingSetTime()));
        });
    }

    @Test
    void testGetRemainingTimeTwo() {
        playback.addSongToSet(song1.getName(), "MySet");
        playback.getAudioPlayer().setOnReady(() -> {
            assertEquals(Math.round(song0.getLength() + song1.getLength()), Math.round(playback.getRemainingSetTime()));
        });
    }

    @Test
    void testSetGenreNameNotFound() {
        try {
            playback.setGenreName("Rando Set", "Future House");
            fail("Set should not have been found");
        } catch (NullPointerException e) {
            assertEquals("EDM", playback.getCurrentSetGenre());
        }
    }

    @Test
    void testSetSetNameNotFound() {
        try {
            playback.setSetName("Rando Set", "Rando PART TWOOOOO");
            fail("Set should not have been found");
        } catch (NullPointerException e) {
            assertEquals("MySet", playback.getCurrentSetTitle());
        }
    }

    @Test
    void testSetVolumeMin() {
        playback.setVolume(0);
        assertEquals(0, playback.getCurrentVolume());
    }

    @Test
    void testSetVolumeMid() {
        playback.setVolume(.5);
        assertEquals(.5, playback.getCurrentVolume());
    }

    @Test
    void testSetVolumeMax() {
        playback.setVolume(1.0);
        assertEquals(1.0, playback.getCurrentVolume());
    }

    @Test
    void testSetNextVolumeMin() {
        playback.setNextVolume(0);
        assertEquals(0, playback.getNextVolume());
    }

    @Test
    void testSetNextVolumeMid() {
        playback.setNextVolume(.2);
        assertEquals(.2, playback.getNextVolume());
    }

    @Test
    void testSetNextVolumeMax() {
        playback.setNextVolume(1);
        assertEquals(1, playback.getNextVolume());
    }

    @Test
    void testSetSpeedMin() {
        playback.setSpeed(0);
        assertEquals(0, playback.getCurrentSpeed());
    }

    @Test
    void testSetSpeedMid() {
        playback.setSpeed(.8);
        assertEquals(.8, playback.getCurrentSpeed());
    }

    @Test
    void testSetSpeedMax() {
        playback.setSpeed(1.8);
        assertEquals(1.8, playback.getCurrentSpeed());
    }

    @Test
    void testSetNextSpeedMin() {
        playback.setNextSpeed(0);
        assertEquals(0, playback.getNextSpeed());
    }

    @Test
    void testSetNextSpeedMid() {
        playback.setNextSpeed(.9);
        assertEquals(.9, playback.getNextSpeed());
    }

    @Test
    void testSetNextSpeedMax() {
        playback.setNextSpeed(2);
        assertEquals(2, playback.getNextSpeed());
    }

    @Test
    void testTogglePlaybackEmpty() {
        Playback playback1 = new Playback();
        try {
            playback1.togglePlay();
            assert(false);
        } catch (NullPointerException e) {
            assert(true);
        }
    }

    @Test
    void testTogglePlaybackOnce() {
        playback.togglePlay();
        assertEquals("playing", playback.getPlayerStatus());
    }

    @Test
    void testTogglePlaybackTwice() {
        playback.togglePlay();
        playback.togglePlay();
        assertEquals("paused", playback.getPlayerStatus());
    }

    @Test
    void testTogglePlaybackThrice() {
        playback.togglePlay();
        playback.togglePlay();
        playback.togglePlay();
        assertEquals("playing", playback.getPlayerStatus());
    }

    @Test
    void testTogglePlaybackQuad() {
        playback.togglePlay();
        playback.togglePlay();
        playback.togglePlay();
        playback.togglePlay();
        assertEquals("paused", playback.getPlayerStatus());
    }

    @Test
    void testSkip() {
        playback.addSongToSet("Why Did You Run? (JILOON Remix)", "MySet");
        playback.setNextSpeed(2.0);
        playback.setNextVolume(.5);
        playback.handleSongEnd();
        assertEquals("Why Did You Run? (JILOON Remix)", playback.getCurrentSongName());
        assertEquals(Arrays.toString(artists), Arrays.toString(playback.getCurrentSongArtists()));
        assertEquals(215.0531768798828, playback.getCurrentSongLength());
        assertEquals(120, playback.getCurrentSongBPM());
        assertEquals("A Min", playback.getCurrentSongKey());
        assertNull(playback.getCurrentSongGenre());
        assertEquals("Used To Love", playback.getNextSongName());
        assertEquals(Arrays.toString(nextArtists), Arrays.toString(playback.getNextSongArtists()));
        assertEquals(236.8936767578125, playback.getNextSongLength());
        assertEquals(119, playback.getNextSongBPM());
        assertEquals("C# Maj", playback.getNextSongKey());
        assertEquals("EDM", playback.getNextSongGenre());
        assertEquals(2.0, playback.getCurrentSpeed());
        assertEquals(.5, playback.getCurrentVolume());
        assertEquals(1, playback.getNextSpeed());
        assertEquals(1, playback.getNextVolume());
    }

    @Test
    void testEndSkip() {
        playback.addSongToSet("Why Did You Run? (JILOON Remix)", "MySet");
        playback.handleSongEnd();
        playback.setNextSpeed(2.0);
        playback.setNextVolume(.5);
        playback.handleSongEnd();
        assertEquals("Why Did You Run? (JILOON Remix)", playback.getNextSongName());
        assertEquals(Arrays.toString(artists), Arrays.toString(playback.getNextSongArtists()));
        assertEquals(215.0531768798828, playback.getNextSongLength());
        assertEquals(120, playback.getNextSongBPM());
        assertEquals("A Min", playback.getNextSongKey());
        assertNull(playback.getNextSongGenre());
        assertEquals("Used To Love", playback.getCurrentSongName());
        assertEquals(Arrays.toString(nextArtists), Arrays.toString(playback.getCurrentSongArtists()));
        assertEquals(236.8936767578125, playback.getCurrentSongLength());
        assertEquals(119, playback.getCurrentSongBPM());
        assertEquals("C# Maj", playback.getCurrentSongKey());
        assertEquals("EDM", playback.getCurrentSongGenre());
        assertEquals(2.0, playback.getCurrentSpeed());
        assertEquals(.5, playback.getCurrentVolume());
        assertEquals(1, playback.getNextSpeed());
        assertEquals(1, playback.getNextVolume());
    }

    @Test
    void testSetGenre() {
        playback.setGenreName("MySet", "Future Bass");
        assertEquals("Future Bass", playback.getCurrentSetGenre());
    }

    @Test
    void testSetName() {
        playback.setSetName("MySet", "New Set");
        assertEquals("New Set", playback.getCurrentSetTitle());
    }

    @Test
    void testSwitchPosition() {
        ArrayList<Song> los = new ArrayList<>();
        playback.addSongToSet("Why Did You Run? (JILOON Remix)", "MySet");
        playback.positionSong("Used To Love", "MySet", 2);

        los.add(song2);
        los.add(song0);
        assertEquals(los.get(0).getName(), playback.getCurrentSetSongs().get(0).getName());
        assertEquals(los.get(1).getName(), playback.getCurrentSetSongs().get(1).getName());
    }

}
