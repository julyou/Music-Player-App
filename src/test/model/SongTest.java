package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    Song testSong1;
    Song testSong2;
    String status;


    @BeforeEach
    public void setUp() {
        testSong1 = new Song("song1", "unknown", "song1.wav",34);
        testSong2 = new Song("song2", "unknown", "song2.wav", 44);
        status = "";
    }

    @Test
    public void testConstructor() {
        assertEquals("song1", testSong1.getSongTitle());
    }

    @Test
    public void testStatus() {
        assertEquals("", testSong1.getSongStatus());
        testSong1.playSong();
        assertEquals("playing", testSong1.getSongStatus());
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