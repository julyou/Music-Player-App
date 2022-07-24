package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongThreadTest {
    SongThread testSongThread;
    String status;
    List<Song> songs;

    @BeforeEach
    public void setUp() {
        testSongThread = new SongThread();
        status = "stopped";
        songs = new LinkedList<>();
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
//
//    }
//
//    @Test
//    public void testRun() {
//
//    }
}
