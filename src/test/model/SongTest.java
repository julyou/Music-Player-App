package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    JSONObject json;

    Song testSong1;
    Song testSong2;
    String status;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        json = new JSONObject();
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
        assertEquals("song1.wav", testSong1.getSongSrc());
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
    public void testToJson() {
        assertEquals("song1", testSong1.toJson().getString("song name"));
        assertEquals("unknown", testSong1.toJson().getString("artist"));
        assertEquals(34, testSong1.toJson().getInt("duration"));
        assertEquals("song1.wav", String.valueOf(testSong1.toJson().getString("source")));
        assertEquals("", testSong1.toJson().getString("status"));
    }

}