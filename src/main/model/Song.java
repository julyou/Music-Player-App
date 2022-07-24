package model;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

// Represents a song that has a song title, artist, and file location
public class Song {
    private String songTitle;
    private String artist;
    private int duration;
    private URL filePath;
    private String status;
    private AudioClip audioclip;

    // EFFECTS: creates a song with title, artist, file location, and status
    public Song(String title, String artist, String src, int duration) {
        this.songTitle = title;
        this.artist = artist;
        this.status = "";
        this.duration = duration;

        try {
            this.filePath = new URL("file:" + src);
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }
        audioclip = Applet.newAudioClip(this.filePath);
    }

    // EFFECTS: plays given song
    public void playSong() {
        audioclip.play();
        status = "playing";
    }

    // EFFECTS: pauses given song
    public void pauseSong() {
        audioclip.stop();
        status = "paused";
    }

    // EFFECTS: loops given song
    public void loopSong() {
        audioclip.loop();
        status = "looping";
    }

    public boolean isPlaying() {
        return status.equals("playing");
    }

    public boolean isPaused() {
        return status.equals("paused");
    }

    public boolean isLooping() {
        return status.equals("looping");

    }

    // getters
    public String getSongTitle() {
        return songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongStatus() {
        return status;
    }

    public int getSongDuration() {
        return duration;
    }



}