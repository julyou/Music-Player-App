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

// based on JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads playlists from JSON data stored in file
public class JsonReader {
    private final String src;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String src) {
        this.src = src;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String src) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(src), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: read playlists from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Playlists readPlaylists() throws IOException {
        String jsonData = readFile(src);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlaylists(jsonObject);
    }

    // MODIFIES: pl
    // EFFECTS: parses playlist from JSON object and adds them to playlists
    private Playlists parsePlaylists(JSONObject jsonObject) throws MalformedURLException {
        Playlists pl = new Playlists();
        JSONArray jsonArray = jsonObject.getJSONArray("playlists");
        for (Object json : jsonArray) {
            JSONObject nextPlaylist = (JSONObject) json;
            pl.addPlaylist(parsePlaylist(nextPlaylist));
        }
        return pl;
    }

    // EFFECTS: parses playlist from JSON object and returns it
    private Playlist parsePlaylist(JSONObject jsonObject) throws MalformedURLException {
        String name = jsonObject.getString("playlist name");
        Playlist p = new Playlist(name);
        addSongs(p, jsonObject);
        return p;
    }

    // MODIFIES: p
    // EFFECTS: parses songs from JSON object and adds them to playlist
    private void addSongs(Playlist p, JSONObject jsonObject) throws MalformedURLException {
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(p, nextSong);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses song from JSON object and adds it to playlist
    //          throws MalformedURLException if URL cannot be parsed
    private void addSong(Playlist p, JSONObject jsonObject) throws MalformedURLException {
        String name = jsonObject.getString("song name");
        String artist = jsonObject.getString("artist");
        String source = jsonObject.getString("source");
        int duration = jsonObject.getInt("duration");

        Song song = new Song(name, artist, source, duration);
        p.addSong(song);
    }
}