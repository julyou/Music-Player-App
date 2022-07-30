package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a main playlist having a collection of playlists
public class Playlists implements Writable {
    private List<Playlist> playlists;
//    private String name;

    // EFFECTS: constructs workroom with a name and empty list of thingies
//    public Playlists(String name)
    public Playlists() {
//        this.name = name;
        playlists = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds playlist to this playlists
    public void addPlaylist(Playlist p) {
        playlists.add(p);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playlists", playlistsToJson());
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
    public int getPlaylistsSize() {
        return playlists.size();
    }

    public Playlist getPlaylist(int i) {
        return playlists.get(i);
    }

    // EFFECTS: returns  list playlists in this workroom
    public List<Playlist> getPlaylists() {
        return playlists;
    }

//    // EFFECTS: returns an unmodifiable list of thingies in this workroom
//    public List<Playlist> getUnmodifiablePlaylists() {
//        return Collections.unmodifiableList(playlists);
//    }

//    public String getName() {
//        return name;
//        return null;
//}
}

