package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
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
        testSong1 = new Song("test", "unknown", "test.wav", 3);
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
        assertEquals("playing", testSongThread.getStatus());

        testSongThread.stopPlaying();
        assertEquals("stopped", testSongThread.getStatus());

        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());
        TimeUnit.SECONDS.sleep(testSong1.getSongDuration() + 1);
        assertEquals("stopped", testSongThread.getStatus());

        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());

        testSongThread.end();
        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testStartPlaying() throws InterruptedException {
        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());
        Thread.sleep(1000);
        assertEquals("playing", testSongThread.getStatus());
    }

    @Test
    public void testStartPlayingTwice() throws InterruptedException {
        testSongThread.start();
        testSongThread.startPlaying(songs);
        testSongThread.startPlaying(songs);
        testSongThread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testStopPlaying() {
        testSongThread.start();
        testSongThread.stopPlaying();
        assertEquals("stopped", testSongThread.getStatus());

        testSongThread.startPlaying(songs);
        assertEquals("playing", testSongThread.getStatus());

        testSongThread.end();
    }

    @Test
    public void testEnd() {
        testSongThread.end();
        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testAll() {
        testSongThread.end();
        assertEquals("end", testSongThread.getStatus());
    }

    @Test
    public void testExpectNoException() {
        boolean i;
        try {
            TimeUnit.SECONDS.sleep(1);
            i = true;
        } catch (InterruptedException e) {
            fail("InterruptedException should not have been thrown");
            i = false;
        }
        assertTrue(i);
    }

    @Test
    public void testExpectExceptionStartPlaying() throws InterruptedException {
        testSongThread.startPlaying(songs);
        testSongThread.startPlaying(songs);

        testSongThread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("stopped", testSongThread.getStatus());
    }

    @Test
    public void testExpectExceptionPlaying() throws InterruptedException {
        testSongThread.start();
        testSongThread.startPlaying(songs);
        TimeUnit.SECONDS.sleep(1);
        testSongThread.interrupt();
        assertEquals("playing", testSongThread.getStatus());
    }

    @Test
    public void testExpectExceptionRun() {
        testSongThread.start();
        testSongThread.stopPlaying();
        testSongThread.interrupt();
        assertEquals("stopped", testSongThread.getStatus());
    }

}










