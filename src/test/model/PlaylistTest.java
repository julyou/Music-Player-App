package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    Playlist testPlaylist1;
    Playlist testPlaylist2;
    Song testSong1;
    Song testSong2;


    @BeforeEach
    public void setUp() {
        testPlaylist1 = new Playlist("playlist1");
        testPlaylist2 = new Playlist("playlist2");
        testSong1 = new Song("song1", "unknown", "song1.wav", 34);
        testSong2 = new Song("song2", "unknown", "song2.wav", 44);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testPlaylist1.getSongsInPlaylist().size());
    }

    @Test
    public void testAddSong() {
        testPlaylist1.addSong(testSong1);
        assertEquals(1, testPlaylist1.getSongsInPlaylist().size());
        assertTrue(testPlaylist1.getSongsInPlaylist().contains(testSong1.getSongTitle()));
    }

    @Test
    public void testAddTwoSameSongs() {
        testPlaylist1.addSong(testSong1);
        testPlaylist1.addSong(testSong1);
        assertEquals(2, testPlaylist1.getSongsInPlaylist().size());
        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsInPlaylist().get(0));
        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsInPlaylist().get(1));
    }

    @Test
    public void testAddTwoDifferentSongs() {
        testPlaylist1.addSong(testSong1);
        testPlaylist1.addSong(testSong2);

        assertEquals(2, testPlaylist1.getSongsInPlaylist().size());

        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsInPlaylist().get(0));
        assertEquals(testSong2.getSongTitle(), testPlaylist1.getSongsInPlaylist().get(1));
    }

    @Test
    public void testAddOneSongToTwoPlaylists() {
        testPlaylist1.addSong(testSong1);
        testPlaylist2.addSong(testSong1);
        assertEquals(1, testPlaylist1.getSongsInPlaylist().size());
        assertEquals(1, testPlaylist2.getSongsInPlaylist().size());
        assertEquals(testSong1.getSongTitle(), testPlaylist1.getSongsInPlaylist().get(0));
        assertEquals(testSong1.getSongTitle(), testPlaylist2.getSongsInPlaylist().get(0));
    }

    @Test
    public void testRemoveSongFromPlaylist() {
        testPlaylist1.addSong(testSong1);
        assertEquals(1, testPlaylist1.getSongsInPlaylist().size());
        testPlaylist1.removeSong(testSong1);
        assertEquals(0, testPlaylist1.getSongsInPlaylist().size());
        testPlaylist1.removeSong(testSong1);
        assertEquals(0, testPlaylist1.getSongsInPlaylist().size());
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
        assertEquals(2, testPlaylist1.getSongsInPlaylist().size());
        testPlaylist1.clearPlaylist();
        assertEquals(0, testPlaylist1.getSongsInPlaylist().size());
    }
}
