package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SongThreadTest {
    SongThread testSongThread;
    List<Song> songs;
    Song testSong1;
    Song testSong2;

    @BeforeEach
    public void setUp() {
        testSongThread = new SongThread();
        songs = new LinkedList<>();
        testSong1 = new Song("song1", "unknown", "song1.wav", 34);
        testSong2 = new Song("song2", "unknown", "song2.wav", 44);
    }

    @Test
    public void testConstructor() {
        assertEquals("stopped", testSongThread.getStatus());
        assertEquals(0, songs.size());
    }

    @Test
    public void testRun() {
        testSongThread.start();
        assertEquals("stopped", testSongThread.getStatus());
        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());
        testSongThread.end();
        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testStartPlaying() {
        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());
    }

    @Test
    public void testStopPlaying() {
        testSongThread.stopPlaying();
        assertEquals("stopped", testSongThread.getStatus());
    }

    @Test
    public void testEnd() {
        testSongThread.end();
        assertEquals("end", testSongThread.getStatus());
    }

//    @Test
//    public void testPlaying() {
//        testSongThread.startPlaying(songs);
//        assertEquals("stopped", testSongThread.getStatus());
//
//        testSongThread.stopPlaying();
//        assertEquals("stopped", testSongThread.getStatus());
//    }



}










