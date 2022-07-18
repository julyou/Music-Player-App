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
    private String operation;
    private Scanner scanner;

    // EFFECTS: creates a song with title and artist
    public Song(String title, String artist, String filePath) {
        this.songTitle = title;
        this.artist = artist;

    }

    public void processOperations(String filePath) {
        scanner = new Scanner(System.in);
        String operation = "";

        while (true) {
            System.out.println("Please select an option (play, pause, or quit):");
            operation = scanner.nextLine();
            System.out.println("you selected: " + operation);

            if (operation.equals("quit")) {
                break;
            }

            if (operation.equals("play")) {
                try {
                    this.filePath = new URL("file:" + filePath);
                } catch (MalformedURLException ex) {
                    System.err.println(ex);
                }
                Applet.newAudioClip(this.filePath).play();
            } else if (operation.equals("pause")) {
                try {
                    this.filePath = new URL("file:" + filePath);
                } catch (MalformedURLException ex) {
                    System.err.println(ex);
                }
                Applet.newAudioClip(this.filePath).stop();
            }
        }
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
