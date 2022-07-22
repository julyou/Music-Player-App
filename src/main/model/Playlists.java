package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Playlists {
    private List<Playlist> playlists;
    private Playlist playlist1;
    private Playlist playlist2;

    public Playlists() {
        playlist1 = new Playlist("playlist1");
        playlist2 = new Playlist("playlist2");
        playlists = new LinkedList<>();
    }

    public void addPlaylist(Playlist p) {
        playlists.add(p);
    }

    public void removePlaylist(Playlist p) {
        playlists.remove(p);
    }

    public void clearPlaylists() {
        playlists.clear();
    }

}
