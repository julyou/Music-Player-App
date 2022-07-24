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
    Song testSong1;
    Song testSong2;

    @BeforeEach
    public void setUp() {
        testSongThread = new SongThread();
        status = "stopped";
        songs = new LinkedList<>();
        testSong1 = new Song("song1", "unknown", "song1.wav", 34);
        testSong2 = new Song("song2", "unknown", "song2.wav", 44);
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

}
