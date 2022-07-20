package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongTest {
    Song testSong1;
    Song testSong2;


    @BeforeEach
    public void setUp() {
        testSong1 = new Song("song1", "unknown", "song1.wav");
        testSong2 = new Song("song2", "unknown", "song2.wav");
    }

    @Test
    public void testConstructor() {

    }

    @Test
    public void testPlaySong() {
        assertFalse(testSong1.isPlaying());
        testSong1.playSong();
        assertTrue(testSong1.isPlaying());

    }

    @Test
    public void testPauseSong() {
        testSong1.playSong();
        assertFalse(testSong1.isPaused());
        testSong1.pauseSong();
        assertTrue(testSong1.isPaused());
    }

    @Test
    public void testLoopSong() {
        assertFalse(testSong1.isLooping());
        testSong1.loopSong();
        assertTrue(testSong1.isLooping());
    }


}