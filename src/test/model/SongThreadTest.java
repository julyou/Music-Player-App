package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class SongThreadTest {
    SongThread testSongThread;
    List<Song> songs;
    Song testSong1;

    @BeforeEach
    public void setUp() {
        testSongThread = new SongThread();
        songs = new LinkedList<>();
        testSong1 = new Song("test", "unknown", "data/test.wav", 3);
        songs.add(testSong1);
    }

    @Test
    public void testConstructor() {
        assertEquals("stopped", testSongThread.getStatus());
        assertEquals(1, songs.size());
        assertEquals(3, testSong1.getSongDuration());
    }

    @Test
    public void testRun() throws InterruptedException {
        testSongThread.start();
        assertEquals("stopped", testSongThread.getStatus());

        testSongThread.startPlaying(songs);
        TimeUnit.SECONDS.sleep(1);
        assertEquals("playing", testSongThread.getStatus());

        testSongThread.stopPlaying();
        assertEquals("stopped", testSongThread.getStatus());

        testSongThread.startPlaying(songs);
        TimeUnit.SECONDS.sleep(1);
        assertEquals("playing", testSongThread.getStatus());
        TimeUnit.SECONDS.sleep(testSong1.getSongDuration() + 1);
        assertEquals("stopped", testSongThread.getStatus());

        testSongThread.end();
        assertEquals("end", testSongThread.getStatus());

        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());
    }

    @Test
    public void testStartPlaying() {
        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());
    }


    @Test
    public void testStartPlayingTwice() throws InterruptedException {
        testSongThread.start();

        testSongThread.startPlaying(songs);
        TimeUnit.SECONDS.sleep(2);
        testSongThread.startPlaying(songs);
        TimeUnit.SECONDS.sleep(2);

        assertEquals("stopped", testSongThread.getStatus());
        testSongThread.end();
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

    @Test
    public void testExpectExceptionPlaying() throws InterruptedException {
        testSongThread.start();
        testSongThread.startPlaying(songs);

            TimeUnit.SECONDS.sleep(1);

        testSongThread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testExpectExceptionRun() throws InterruptedException {
        testSongThread.start();
        TimeUnit.SECONDS.sleep(1);

        testSongThread.interrupt();
        TimeUnit.SECONDS.sleep(1);

        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testExpectExceptionStartPlaying() throws InterruptedException {
        testSongThread.start();

        testSongThread.startPlaying(songs);
        TimeUnit.SECONDS.sleep(2);

        testSongThread.startPlaying(songs);
        testSongThread.interrupt();
        TimeUnit.SECONDS.sleep(1);

        assertEquals("end", testSongThread.getStatus());
        testSongThread.end();
    }
}










