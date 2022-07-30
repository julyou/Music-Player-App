package persistence;

import model.Playlist;
import model.Playlists;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/invalidFile1.json");
        try {
            Playlists pl = reader.readPlaylists();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlaylists() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlaylists.json");
        try {
            Playlists pl = reader.readPlaylists();
            assertEquals(0, pl.getPlaylistsSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlaylists() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylists.json");
        try {
            Playlists pl = reader.readPlaylists();
            List<Playlist> p = pl.getPlaylists();
            assertEquals(2, p.size());
            checkPlaylist("Instrumental", p.get(0));
            checkPlaylist("Film scores", p.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylists.json");
        try {
            Playlists pl = reader.readPlaylists();
            List<Playlist> p = pl.getPlaylists();
            Playlist p1 = p.get(0);
            Playlist p2 = p.get(1);
            List<String> titles1 = p1.getSongsTitlesInPlaylist();
            List<String> titles2 = p2.getSongsTitlesInPlaylist();
            assertEquals("Dhol Drums", titles1.get(0));
            assertEquals("Cantina Band", titles2.get(0));
            assertEquals("Main Title", titles2.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPlaylistToJson() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylists.json");
        try {
            Playlists pl = reader.readPlaylists();
            List<Playlist> ps = pl.getPlaylists();
            Playlist p1 = ps.get(0);
            Playlist p2 = ps.get(1);
            assertEquals(1, p1.getSongsInPlaylist().size());
            assertEquals(2, p2.getSongsInPlaylist().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSongsToJson() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylists.json");
        try {
            Playlists pl = reader.readPlaylists();
            List<Playlist> ps = pl.getPlaylists();
            Playlist p = ps.get(0);
            Song s = p.getSongsInPlaylist().get(0);
            assertEquals("Dhol Drums", s.getSongTitle());
            assertEquals("unknown", s.getArtist());
            assertEquals("file:data/song6.wav", s.getSongURL());
            assertEquals(18, s.getSongDuration());
            assertEquals("", s.getSongStatus());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}