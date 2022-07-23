package model;

import java.util.LinkedList;
import java.util.List;

// represents a playlist with songs
public class Playlist {

    private List<Song> playlist;
    private String playlistName;

    public Playlist(String playlistName) {
        playlist = new LinkedList<>();
        this.playlistName = playlistName;
    }

    // MODIFIES: this
    // EFFECTS: adds song to the playlist to end of playlist
    public void addSong(Song s) {
        playlist.add(s);
    }

    // MODIFIES: this
    // EFFECTS: removes song to the playlist if present and returns true, false otherwise
    public void removeSong(Song s) {
        playlist.remove(s);
    }

    // MODIFIES: this;
    // EFFECTS: renames playlist
    public String renamePlaylist(String s) {
        playlistName = s;
        return s;
    }

    public void clearPlaylist() {
        playlist.clear();
    }

    // getters
    public List<String> getSongsInPlaylist() {
        List<String> songs = new LinkedList<>();
        for (Song s : playlist) {
            songs.add(s.getSongTitle());
        }
        return songs;
    }

    public String getPlaylistName() {
        return playlistName;
    }


}
