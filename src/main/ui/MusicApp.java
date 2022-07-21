package ui;

import com.sun.xml.internal.bind.v2.TODO;
import model.Playlist;
import model.Song;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MusicApp {

    private Scanner input;

    // EFFECTS: runs the music player application
    public MusicApp() throws InterruptedException {
        runMusicApp();
    }

    List<Song> songs = new LinkedList<>();
    Playlist playlist = new Playlist("");
    Song song1 = new Song("song1", "unknown", "song1.wav", 34);
    Song song2 = new Song("song2", "unknown", "song2.wav", 44);
    Song song3 = new Song("song2", "unknown", "song2.wav", 44);


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMusicApp() throws InterruptedException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
                for (Song s : songs) {
                    s.pauseSong();
                }
            } else if (command.equals("browse")) {
                List<String> songNames = new LinkedList<>();
                for (Song s : songs) {
                    songNames.add(s.getSongTitle());
                }
                System.out.println("Here are all the songs you can play!" + songNames);

            } else if (command.equals("create")) {
                System.out.println("Please name your new playlist ");
                List<String> songNames = new LinkedList<>();
                for (Song s : songs) {
                    songNames.add(s.getSongTitle());
                }
                System.out.println("Here are all the songs you can play!" + songNames);
                displayMenu();
            } else {
                System.out.println("invalid");
            }
            processCommand(command);
        }

        System.out.println("\nThanks for listening!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for songs
    private void processCommand(String command) throws InterruptedException {
        if (command.equals("play")) {
            for (Song s : songs) {
                s.playSong();
                System.out.println("Playing " + s.getSongTitle() + " by " + s.getArtist());
                // Thread.sleep(s.getSongDuration() * 1000);
            }
        } else if (command.equals("pause")) {
            for (Song s : songs) {
                if (s.isPlaying() | s.isLooping()) {
                    s.pauseSong();
                    System.out.println("Paused " + s.getSongTitle() + " by " + s.getArtist());
                }
            }
        } else if (command.equals("loop")) {
            for (Song s : songs) {
                if (!s.isLooping()) {
                    s.loopSong();
                    System.out.println("Looping " + s.getSongTitle() + " by " + s.getArtist());
                }
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

//    // MODIFIES: this
//    // EFFECTS: processes user command for songs
//    // TODO: fix this
//    private void processCommandPlaylist(String command) {
//        if (command.equals("rename")) {
//            playlist.renamePlaylist("");
//        } else if (command.equals("new")) {
//            Playlist playlist = new Playlist("");
////        } else if (command.equals("delete")) {
////            // TODO: delete
////            playlist.removeSong("");
////            }
////        } else {
////            System.out.println("Selection not valid...");
//        }
//    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tcreate -> create new playlist");
        System.out.println("\tbrowse -> browse songs");
        System.out.println("\tplay -> start playing");
        System.out.println("\tpause -> pause song");
        System.out.println("\tloop  -> loop song");
        System.out.println("\tquit -> quit");
    }

//    // MODIFIES: this
//    // EFFECTS: conducts a deposit transaction
//    private void doDeposit() {
//        Account selected = selectAccount();
//        System.out.print("Enter amount to deposit: $");
//        double amount = input.nextDouble();
//
//        if (amount >= 0.0) {
//            selected.deposit(amount);
//        } else {
//            System.out.println("Cannot deposit negative amount...\n");
//        }
//
//        printBalance(selected);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: conducts a withdraw transaction
//    private void doWithdrawal() {
//        Account selected = selectAccount();
//        System.out.print("Enter amount to withdraw: $");
//        double amount = input.nextDouble();
//
//        if (amount < 0.0) {
//            System.out.println("Cannot withdraw negative amount...\n");
//        } else if (selected.getBalance() < amount) {
//            System.out.println("Insufficient balance on account...\n");
//        } else {
//            selected.withdraw(amount);
//        }
//
//        printBalance(selected);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: conducts a transfer transaction
//    private void doTransfer() {
//        System.out.println("\nTransfer from?");
//        Account source = selectAccount();
//        System.out.println("Transfer to?");
//        Account destination = selectAccount();
//
//        System.out.print("Enter amount to transfer: $");
//        double amount = input.nextDouble();
//
//        if (amount < 0.0) {
//            System.out.println("Cannot transfer negative amount...\n");
//        } else if (source.getBalance() < amount) {
//            System.out.println("Insufficient balance on source account...\n");
//        } else {
//            source.withdraw(amount);
//            destination.deposit(amount);
//        }
//
//        System.out.print("Source ");
//        printBalance(source);
//        System.out.print("Destination ");
//        printBalance(destination);
//    }
//
//    // EFFECTS: prompts user to select playlist and returns songs in it
//    private Playlist selectPlaylist() {
//        String selection = "";  // force entry into loop
//
//        while (!(selection.equals("s") || selection.equals("p"))) {
//            System.out.println("s for all songs");
//            System.out.println("p for playlists");
//            selection = input.next();
//            selection = selection.toLowerCase();
//        }
//
//        if (selection.equals("s")) {
//            return songs;
//        } else {
//            return playlists;
//        }
//    }
//
//    // EFFECTS: prints balance of account to the screen
//    private void printBalance(Account selected) {
//        System.out.printf("Balance: $%.2f\n", selected.getBalance());
//    }
}
