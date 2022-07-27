package persistence;

import model.Playlist;

import model.Playlists;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads playlists from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: read playlists from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Playlists read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlaylists(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses playlists from JSON object and returns it
    private Playlists parsePlaylists(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Playlists pl = new Playlists(name);
        addPlaylists(pl, jsonObject);
        return pl;
    }

    // MODIFIES: pl
    // EFFECTS: parses the playlists from JSON object and adds them to playlists
    private void addPlaylists(Playlists pl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("playlists");
        for (Object json : jsonArray) {
            JSONObject nextPlaylist = (JSONObject) json;
            addPlaylist(pl, nextPlaylist);
        }
    }

    // MODIFIES: pl
    // EFFECTS: parses playlist from JSON object and adds it to workroom
    private void addPlaylist(Playlists pl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Playlist p = new Playlist(name);
        pl.addPlaylist(p);
    }


//    // EFFECTS: parses songs from JSON object and returns it
//    private Playlist parseSongs(JSONObject jsonObject) throws MalformedURLException {
//        String name = jsonObject.getString("name");
//        Playlist p = new Playlist(name);
//        addSongs(p, jsonObject);
//        return p;
//    }

//    // MODIFIES: p
//    // EFFECTS: parses the songs from JSON object and adds them to playlist
//    private void addSongs(Playlist p, JSONObject jsonObject) throws MalformedURLException {
//        JSONArray jsonArray = jsonObject.getJSONArray("playlist");
//        for (Object json : jsonArray) {
//            JSONObject nextSongs = (JSONObject) json;
//            addSong(p, nextSongs);
//        }
//    }
//
//    // MODIFIES: p
//    // EFFECTS: parses the songs from JSON object and adds them to playlist
//    private void addSong(Playlist p, JSONObject jsonObject) throws MalformedURLException {
//        String name = jsonObject.getString("name");
//        Song s = new Song(name, "artist", "", 0);
//        p.addSong(s);
//    }
}
