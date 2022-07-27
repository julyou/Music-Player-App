package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a main playlist having a collection of playlists
public class Playlists implements Writable {
    private List<Playlist> playlists;

    // EFFECTS: constructs workroom with a name and empty list of thingies
    public Playlists() {
        playlists = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds playlist to this playlists
    public void addPlaylist(Playlist pl) {
        playlists.add(pl);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("", playlistsToJson());
        return json;
    }

    // EFFECTS: returns the playlists in this playlists as a JSON array
    private JSONArray playlistsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Playlist pl : playlists) {
            jsonArray.put(pl.toJson());
        }
        return jsonArray;
    }

    // getters
    public int getPlaylistSize() {
        return playlists.size();
    }

    public Playlist getPlaylist(int i) {
        return playlists.get(i);
    }

    // EFFECTS: returns an unmodifiable list of thingies in this workroom
    public List<Playlist> getPlaylists() {
        return playlists;
    }
}

