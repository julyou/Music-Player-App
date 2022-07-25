package model;

import java.util.LinkedList;
import java.util.List;

// represents a playlist with songs
public class Playlist {

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

    // MODIFIES: this;
    // EFFECTS: clears songs in playlist
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

}
