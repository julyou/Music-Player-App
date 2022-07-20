package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// represents a playlist with songs
public class Playlist {

    private List<Song> playlist;

    public Playlist() {
        playlist = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds song to the playlist to end of playlist
    public void addSong(Song s) {
        playlist.add(s);
    }

    // MODIFIES: this
    // EFFECTS: removes song to the playlist if present and returns true, false otherwise
    public boolean removeSong(Song s) {
        if (playlist.contains(s)) {
            playlist.remove(s);
            return true;
        }
        return false;
    }

    // getters
    public List<Song> getSongsInPlaylist() {
        List<Song> songs = new LinkedList<>();
        for (Song s : playlist) {
            songs.add(s);
        }
        return songs;
    }


}
