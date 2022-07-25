package model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// represents a song thread that can be played and stopped
public class SongThread extends Thread {
    private static String status;
    List<Song> songs;

    // EFFECTS: creates an empty song thread with initial status "stopped"
    public SongThread() {
        status = "stopped";
        songs = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: Starts playing songs. Stops song thread first if already playing
    public void startPlaying(List<Song> songs) {
        if (status.equals("playing")) {
            stopPlaying();
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                status = "end";
//                return;
//            }
        }
        this.songs = songs;
        status = "playing";
    }

    // MODIFIES: this
    // EFFECTS: stops playing songs in thread
    public void stopPlaying() {
        status = "stopped";
    }

    // MODIFIES: this
    // EFFECTS: terminates song thread
    public void end() {
        status = "end";
    }

    // MODIFIES: this
    // EFFECTS: plays songs in thread one after the other until thread is stopped or ended
    private void playing() {
        for (Song s : songs) {
            s.playSong();
            int i = 0;
            while (status.equals("playing") && i < s.getSongDuration()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    status = "end";
                    return;
                }
                i++;
            }
            if (!status.equals("playing")) {
                s.stopSong();
                break;
            }
        }
        if (status.equals("playing")) {
            status = "stopped";
        }
    }

    // MODIFIES: this
    // EFFECTS: runs the song thread and processes song thread methods
    public void run() {
        while (!status.equals("end")) {
            if (status.equals("stopped")) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    status = "end";
                }
            } else if (status.equals("playing")) {
                playing();
            }
        }
    }

    // getters
    public String getStatus() {
        return status;
    }
}













