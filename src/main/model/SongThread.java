package model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongThread extends Thread {
    private static String status = "stopped";
    List<Song> songs = new LinkedList<>();

    public void startPlaying(List<Song> songs) {
        if (status.equals("playing")) {
            stopPlaying();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            System.out.println(s.getSongTitle() + " is playing");
            int i = 0;
            while (status.equals("playing") && i < s.getSongDuration()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
            if (!status.equals("playing")) {
                s.pauseSong();
                System.out.println(s.getSongTitle() + " stopped playing");
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
                    throw new RuntimeException(e);
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
