package model;

import org.json.JSONObject;
import persistence.Writable;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

// Represents a song that has a song title, artist, file location, and duration
public class Song implements Writable {
    private final String songTitle;
    private final String artist;
    private final int duration;
    private final URL filePath;
    private String status;
    private final AudioClip audioclip;

    // EFFECTS: creates a song with title, artist, file location, and status
    public Song(String title, String artist, String src, int duration) throws MalformedURLException {
        this.songTitle = title;
        this.artist = artist;
        this.status = "";
        this.duration = duration;

        this.filePath = new URL("file:" + src);
        audioclip = Applet.newAudioClip(this.filePath);
    }

    // EFFECTS: plays song
    public void playSong() {
        audioclip.play();
        status = "playing";
    }

    // EFFECTS: pauses song
    public void stopSong() {
        audioclip.stop();
        status = "stopped";
    }

    // EFFECTS: loops song
    public void loopSong() {
        audioclip.loop();
        status = "looping";
    }

    // EFFECTS: returns true if song is playing, false otherwise
    public boolean isPlaying() {
        return status.equals("playing");
    }

    // EFFECTS: returns true if song is stopped, false otherwise
    public boolean isStopped() {
        return status.equals("stopped");
    }

    // EFFECTS: returns true if song is looping, false otherwise
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

    public String getSongURL() {
        return String.valueOf(filePath);
    }

    public AudioClip getSongAudioclip() {
        return audioclip;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("song name", songTitle);
        json.put("artist", artist);
        json.put("duration", duration);
        json.put("url", filePath);
        json.put("status", status);
        json.put("audioclip", audioclip);
        return json;
    }
}