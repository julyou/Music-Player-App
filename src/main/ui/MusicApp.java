package ui;

import model.Playlist;
import model.Song;

public class MusicApp {
    // EFFECTS: runs the teller application
    public MusicApp() {
        runMusicApp();
    }

    Song song1 = new Song("song1", "unknown", "song1.wav");
    Song song2 = new Song("song2", "unknown", "song2.wav");

    private void runMusicApp() {
        try {
            song2.pauseSong();
            System.out.println("Song is paused");

            Thread.sleep(7000);
            song2.playSong();
            System.out.println("Song is playing");

            Thread.sleep(10000);
            song1.playSong();
            System.out.println("Another song is playing");

            Thread.sleep(7000);
            song1.pauseSong();
            System.out.println("Second song is paused");

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
