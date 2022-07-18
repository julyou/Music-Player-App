package model;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

// Represents a song having a song title and filename
public class Song {
    private String operation;
    private String songTitle;
    private String artist;
    private URL filePath;


    public Song(String title, String artist, String path) {
        operation = "";
        this.songTitle = title;
        this.artist = artist;
        try {
            filePath = new URL("file:" + path);
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }
        Applet.newAudioClip(filePath).play();
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

    // setters
    public void setOperation(String operation) {
        this.operation = operation;
    }

}
