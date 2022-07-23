package model;

import model.Song;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongThread extends Thread {
    private static boolean flag = true;
    List<Song> songs = new LinkedList<>();

    public void setSongs(List<Song> songsIn) {
        songs = songsIn;
    }

    public void stopPlaying() {
        flag = false;
    }

    public void run() {
        flag = true;
        for (Song s : songs) {
            s.playSong();
            System.out.println(s.getSongTitle() + " is playing");
            int i = 0;

            while (flag && i < s.getSongDuration()) {

                try {
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
            if (!flag) {
                s.pauseSong();
                System.out.println(s.getSongTitle() + " stopped playing");
                break;
            }
        }

    }
}
