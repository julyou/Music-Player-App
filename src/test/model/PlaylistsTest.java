package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistsTest {

    Playlists testPlaylists;
    Playlist testPlaylist1;
    Playlist testPlaylist2;

    @BeforeEach
    public void setUp() {
        testPlaylists = new Playlists();
        testPlaylist1 = new Playlist("playlist1");
        testPlaylist2 = new Playlist("playlist2");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testPlaylists.getPlaylistsSize());
    }

    @Test
    public void testAddPlaylist() {
        testPlaylists.addPlaylist(testPlaylist1);
        testPlaylists.addPlaylist(testPlaylist2);
        assertEquals(2, testPlaylists.getPlaylistsSize());
        assertEquals("playlist1", testPlaylists.getPlaylistsNames().get(0));
        assertEquals("playlist2", testPlaylists.getPlaylistsNames().get(1));
    }

    @Test
    public void testRemovePlaylist() {
        testPlaylists.addPlaylist(testPlaylist1);
        testPlaylists.removePlaylist(0);
        assertEquals(0, testPlaylists.getPlaylistsSize());
    }

    @Test
    public void testGetPlaylist() {
        testPlaylists.addPlaylist(testPlaylist1);
        assertEquals(testPlaylist1, testPlaylists.getPlaylist(0));
    }
}
