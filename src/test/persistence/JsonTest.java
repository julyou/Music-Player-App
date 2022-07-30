package persistence;

import model.Playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlaylist(String name, Playlist p) {
        assertEquals(name, p.getPlaylistName());

    }
}
