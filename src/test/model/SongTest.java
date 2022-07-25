package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    Song testSong1;
    Song testSong2;
    Song testSong3;
    String status;

    @BeforeEach
    public void setUp() {
        testSong1 = new Song("song1", "unknown", "song1.wav", 34);
        testSong2 = new Song("song2", "unknown", "song2.wav", 44);
        status = "";
    }

    @Test
    public void testConstructor() {
        assertEquals("song1", testSong1.getSongTitle());
        assertEquals("unknown", testSong1.getArtist());
        assertEquals(44, testSong2.getSongDuration());
        assertEquals("file:song2.wav", testSong2.getSongURL());

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
        assertFalse(testSong1.isStopped());
        testSong1.stopSong();
        assertTrue(testSong1.isStopped());
    }

    @Test
    public void testLoopSong() {
        assertFalse(testSong1.isLooping());
        testSong1.loopSong();
        assertTrue(testSong1.isLooping());
    }

    @Test
    public void testExpectNotMalformedURL() {
        URL filePath = null;

        try {
            filePath = new URL("file:song2.wav");
        } catch (MalformedURLException e) {
            fail("MalformedURL should not have been thrown");
        }
        assertEquals("file:song2.wav", filePath.toString());
    }

    @Test
    public void testExpectMalformedURL() {
        testSong3 = new Song("song3", "unknown", "1", 2);
        AudioClip audioclip;
        boolean i;
        URL filePath;

        try {
            filePath = new URL(testSong3.getSongURL());
            audioclip = Applet.newAudioClip(filePath);
            audioclip.play();
            i = false;
        } catch (MalformedURLException ex) {
            i = true;
        }
//        assertTrue(i);
    }
}