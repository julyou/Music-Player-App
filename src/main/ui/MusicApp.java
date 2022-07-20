package ui;

import model.Playlist;
import model.Song;

public class MusicApp {
    // EFFECTS: runs the teller application
    public MusicApp() {
        runMusicApp();
    }

    private Song song1 =  new Song("song1", "unknown", "song1.wav", "");

    private void runMusicApp() {
        song1.playSong();
    }
}
