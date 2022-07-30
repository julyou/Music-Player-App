package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistsTest {

    Playlists testPlaylists;
    Playlist testPlaylist;

    @BeforeEach
    public void setUp() {
        testPlaylists = new Playlists();
        testPlaylist = new Playlist("playlist");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testPlaylists.getPlaylistsSize());
    }

    @Test
    public void testAddPlaylist() {
        testPlaylists.addPlaylist(testPlaylist);
        assertEquals(1, testPlaylists.getPlaylistsSize());
    }

    @Test
    public void testGetPlaylist() {
        testPlaylists.addPlaylist(testPlaylist);
        assertEquals(testPlaylist, testPlaylists.getPlaylist(0));
    }
}
