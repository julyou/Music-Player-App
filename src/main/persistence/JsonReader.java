package persistence;

import model.Playlist;

import model.Playlists;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

// TODO: Represents a reader that reads playlists from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ??? from file and returns it;
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

    // EFFECTS: parses workroom from JSON object and returns it
    private Playlists parsePlaylists(JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
        Playlists pl = new Playlists();
        addPlaylist(pl, jsonObject);
        return pl;
    }

//    // MODIFIES: wr
//    // EFFECTS: parses thingies from JSON object and adds them to workroom
//    private void addThingies(Playlists pl, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
//        for (Object json : jsonArray) {
//            JSONObject nextThingy = (JSONObject) json;
//            addPlaylist(pl, nextThingy);
//        }
//    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPlaylist(Playlists pl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Playlist p = new Playlist(name);
        pl.addPlaylist(p);
    }
}
