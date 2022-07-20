package model;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// Represents a song that has a song title, artist, and file location
public class Song {
    private String songTitle;
    private String artist;
    private URL filePath;
    private String status;

    // EFFECTS: creates a song with title, artist, file location, and status
    public Song(String title, String artist, String filePath, String status) {
        this.songTitle = title;
        this.artist = artist;
        this.status = "";

        try {
            this.filePath = new URL("file:" + filePath);
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }
    }

    // EFFECTS: plays given song
    public void playSong() {
        Applet.newAudioClip(this.filePath).play();
        status = "Playing";
    }

    // EFFECTS: pauses given song
    public void pauseSong() {
        Applet.newAudioClip(this.filePath).stop();
        status = "Paused";
    }

    // EFFECTS: loops given song
    public void loopSong() {
        Applet.newAudioClip(this.filePath).loop();
        status = "Looping";
    }

    public boolean isPlaying() {
        return status.equals("Playing");
    }

    public boolean isPaused() {
        return status.equals("Paused");
    }

    public boolean isLooping() {
        return status.equals("Looping");

    }

    // getters
    public String getSongTitle() {
        return songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public URL getSongURL() {
        return filePath;
    }


}