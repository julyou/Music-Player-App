package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    Playlist testPlaylist1;
    Playlist testPlaylist2;
    Song testSong1;
    Song testSong2;


    @BeforeEach
    public void setUp() throws MalformedURLException {
        testPlaylist1 = new Playlist("playlist1");
        testPlaylist2 = new Playlist("playlist2");
        testSong1 = new Song("song1", "unknown", "song1.wav", 34);
        testSong2 = new Song("song2", "unknown", "song2.wav", 44);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testPlaylist1.getSongsTitlesInPlaylist().size());
        assertEquals(0, testPlaylist1.getSongsInPlaylist().size());
    }

    @Test
    public void testAddSong() {
        testPlaylist1.addSong(testSong1);
        assertEquals(1, testPlaylist1.getSongsTitlesInPlaylist().size());
        assertTrue(testPlaylist1.getSongsTitlesInPlaylist().contains(testSong1.getSongTitle()));
        assertTrue(testPlaylist1.getSongsInPlaylist().contains(testSong1));
    }

    @Test
    public void testAddSongAtIndex() {
        testPlaylist1.addSongAtIndex(0, testSong1);
        assertEquals(testSong1, testPlaylist1.getSongsInPlaylist().get(0));
        testPlaylist1.addSongAtIndex(0, testSong2);
        assertEquals(testSong2, testPlaylist1.getSongsInPlaylist().get(0));
        assertEquals(testSong1, testPlaylist1.getSongsInPlaylist().get(1));
    }

    @Test
    public void testAddTwoSameSongs() {
        testPlaylist1.addSong(testSong1);
        testPlaylist1.addSong(testSong1);
        assertEquals(2, testPlaylist1.getSongsTitlesInPlaylist().size());
        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsTitlesInPlaylist().get(0));
        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsTitlesInPlaylist().get(1));
    }

    @Test
    public void testAddTwoDifferentSongs() {
        testPlaylist1.addSong(testSong1);
        testPlaylist1.addSong(testSong2);

        assertEquals(2, testPlaylist1.getSongsTitlesInPlaylist().size());

        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsTitlesInPlaylist().get(0));
        assertEquals(testSong2.getSongTitle(), testPlaylist1.getSongsTitlesInPlaylist().get(1));
    }

    @Test
    public void testAddOneSongToTwoPlaylists() {
        testPlaylist1.addSong(testSong1);
        testPlaylist2.addSong(testSong1);
        assertEquals(1, testPlaylist1.getSongsTitlesInPlaylist().size());
        assertEquals(1, testPlaylist2.getSongsTitlesInPlaylist().size());
        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsTitlesInPlaylist().get(0));
        assertEquals(testSong1.getSongTitle(), testPlaylist2.getSongsTitlesInPlaylist().get(0));
    }

    @Test
    public void testRemoveSongFromPlaylist() {
        testPlaylist1.addSong(testSong1);
        assertEquals(1, testPlaylist1.getSongsTitlesInPlaylist().size());
        testPlaylist1.removeSong(testSong1);
        assertEquals(0, testPlaylist1.getSongsTitlesInPlaylist().size());
        testPlaylist1.removeSong(testSong1);
        assertEquals(0, testPlaylist1.getSongsTitlesInPlaylist().size());
    }

    @Test
    public void testRenamePlaylist() {
        assertEquals("playlist1", testPlaylist1.getPlaylistName());
        testPlaylist1.renamePlaylist("renamed playlist");
        assertEquals("renamed playlist", testPlaylist1.getPlaylistName());
    }

    @Test
    public void testClearPlaylist() {
        testPlaylist1.addSong(testSong1);
        testPlaylist1.addSong(testSong2);
        assertEquals(2, testPlaylist1.getSongsTitlesInPlaylist().size());
        testPlaylist1.clearPlaylist();
        assertEquals(0, testPlaylist1.getSongsTitlesInPlaylist().size());
    }

    @Test
    public void testSongsToJson() {
        testPlaylist1.addSong(testSong1);
        testPlaylist1.addSong(testSong2);
        testPlaylist1.toJson();

        assertEquals("song1", testSong1.toJson().getString("song name"));
        assertEquals("unknown", testSong1.toJson().getString("artist"));
        assertEquals(34, testSong1.toJson().getInt("duration"));
        assertEquals("song1.wav", String.valueOf(testSong1.toJson().getString("source")));
        assertEquals("", testSong1.toJson().getString("status"));

        assertEquals("song2", testSong2.toJson().getString("song name"));
        assertEquals("unknown", testSong2.toJson().getString("artist"));
        assertEquals(44, testSong2.toJson().getInt("duration"));
        assertEquals("song2.wav", String.valueOf(testSong2.toJson().getString("source")));
        assertEquals("", testSong2.toJson().getString("status"));
    }
}
