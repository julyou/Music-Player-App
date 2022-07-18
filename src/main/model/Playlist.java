package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private List<Song> songs;

    public Playlist() {
        songs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds song to the playlist
    public void addSong(Song s) {
        songs.add(s);
    }

    // MODIFIES: this
    // EFFECTS: removes song to the playlist and returns true, false otherwise
    public boolean removeSong(Song s) {
        if (songs.contains(s)) {
            songs.remove(s);
            return true;
        }
        return false;
    }
}
