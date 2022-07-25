package model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// represents a thread of songs that can be run to play, pause, and end
public class SongThread extends Thread {
    private static String status;
    List<Song> songs;

    public SongThread() {
        status = "stopped";
        songs = new LinkedList<>();
    }

    public void startPlaying(List<Song> songs) {
        if (status.equals("playing")) {
            stopPlaying();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                return;
            }
        }
        this.songs = songs;
        status = "playing";
    }

    public void stopPlaying() {
        status = "stopped";
    }

    public void end() {
        status = "end";
    }

    private void playing() {
        for (Song s : songs) {
            s.playSong();
            int i = 0;
            while (status.equals("playing") && i < s.getSongDuration()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    continue;
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

    public String getStatus() {
        return status;
    }
}













