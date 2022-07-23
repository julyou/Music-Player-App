package model;

import java.util.LinkedList;
import java.util.List;

public class Playlists {

    private List<Playlist> playlists;
    private List<Song> songs;

    public Playlists() {
        playlists = new LinkedList<>();
        songs = new LinkedList<>();
    }

    // getters
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<Song> getSongs() {
        return songs;
    }

    // REQUIRES: playlist != null
    // MODIFIES: this
    // EFFECTS: adds playlist to this chain
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    // REQUIRES: playlist != null
    // MODIFIES: this
    // EFFECTS: removes playlist from this chain
    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }


}
