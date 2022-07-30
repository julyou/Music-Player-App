package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// represents a playlist with songs
public class Playlist implements Writable {

    private final List<Song> playlist;
    private String playlistName;

    // EFFECTS: creates a playlist with a playlist name
    public Playlist(String playlistName) {
        playlist = new LinkedList<>();
        this.playlistName = playlistName;
    }

    // MODIFIES: this
    // EFFECTS: adds song to the playlist to end of playlist
    public void addSong(Song s) {
        playlist.add(s);
    }

    // REQUIRES: song exists in playlist
    // MODIFIES: this
    // EFFECTS: removes song from the playlist
    public void removeSong(Song s) {
        playlist.remove(s);
    }

    // MODIFIES: this
    // EFFECTS: renames playlist
    public String renamePlaylist(String s) {
        playlistName = s;
        return s;
    }

    // REQUIRES: playlist is not empty
    // MODIFIES: this
    // EFFECTS: removes all songs in playlist
    public void clearPlaylist() {
        playlist.clear();
    }

    // getters
    public List<String> getSongsTitlesInPlaylist() {
        List<String> songNames = new LinkedList<>();
        for (Song s : playlist) {
            songNames.add(s.getSongTitle());
        }
        return songNames;
    }

    public List<Song> getSongsInPlaylist() {
        List<Song> songs = new LinkedList<>();
        for (Song s : playlist) {
            songs.add(s);
        }
        return songs;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("songs", songsToJson());
        json.put("playlist name", playlistName);
        return json;
    }

    // EFFECTS: returns things in this playlist as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Song s : playlist) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }
}
