package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Test class for Playback
public class PlaybackTest {
    private Playback playback;

    @BeforeEach
    void setup() {
        playback = new Playback();
    }

    @Test
    void testConstructor() {
        assertTrue(playback.getSongs().isEmpty());
        assertEquals(1.0, playback.getCurrentSpeed());
        assertEquals(1.0, playback.getCurrentVolume());
        assertEquals(1.0, playback.getNextSpeed());
        assertEquals(1.0, playback.getNextVolume());
    }
}
