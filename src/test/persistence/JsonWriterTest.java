package persistence;


import model.Playlist;
import model.Playlists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Playlists pl = new Playlists();
            JsonWriter writer = new JsonWriter("./data/invalidFile.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Playlists pl = new Playlists();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlaylists.json");
            writer.open();
            writer.writePlaylists(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlaylists.json");
            pl = reader.readPlaylists();
            assertEquals(0, pl.toJson().length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Playlists pl = new Playlists();
            pl.addPlaylist(new Playlist("Instrumental"));
            pl.addPlaylist(new Playlist("Film"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlaylists.json");
            writer.open();
            writer.writePlaylists(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlaylists.json");
            pl = reader.readPlaylists();
            assertEquals(0, pl.toJson().length());
            List<Playlist> playlists = pl.getPlaylists();
            assertEquals(2, playlists.size());
            checkPlaylist("Instrumental", playlists.get(0));
            checkPlaylist("Film", playlists.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}