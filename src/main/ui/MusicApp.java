package ui;

import model.Playlist;
import model.Song;
import model.SongThread;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// based on Teller app and FitLifeGymChain; links below
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git

public class MusicApp {
    private static final String PLAY_COMMAND = "play";
    private static final String STOP_COMMAND = "stop";
    private static final String SONGS_COMMAND = "songs";
    private static final String PLAYLISTS_COMMAND = "playlists";
    private static final String MAIN_MENU_COMMAND = "main";
    private static final String GO_BACK_COMMAND = "back";
    private static final String ADD_SONG_COMMAND = "add";
    private static final String DELETE_SONG_COMMAND = "del";
    private static final String QUIT_COMMAND = "quit";

    private Scanner input;
    private SongThread songthread = new SongThread();
    private Thread thread = new Thread();

    List<Song> songs = new LinkedList<>();
    List<Playlist> playlists = new LinkedList<>();

    Playlist playlist1 = new Playlist("Star Wars Soundtrack");
    Playlist playlist2 = new Playlist("Instrumental");
    Playlist playlist3 = new Playlist("Film scores");

    Song song1 = new Song("song1", "unknown", "song1.wav", 34);
    Song song2 = new Song("song2", "unknown", "song2.wav", 44);
    Song song3 = new Song("Pink Panther", "Henry Mancini", "song3.wav", 30);
    Song song4 = new Song("Imperial March", "John Williams", "song4.wav", 60);
    Song song5 = new Song("Cantina Band", "John Williams", "song5.wav", 60);
    Song song6 = new Song("Dhol Drums", "unknown", "song6.wav", 18);
    Song song7 = new Song("Main Title", "John Williams", "song7.wav", 60);

    private boolean keepGoing;
    private String command;

    // EFFECTS: runs the music player application
    public MusicApp() throws InterruptedException {
        runMusicApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMusicApp() throws InterruptedException {
        keepGoing = true;
        init();
        handleUserInput();
    }

    public void handleUserInput() throws InterruptedException {
        displayMainMenu();
        String command;

        while (keepGoing) {
            command = getUserInputString();
            processCommand(command);
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tsongs -> browse songs");
        System.out.println("\tplaylists -> browse playlists");
        System.out.println("\tplay -> start playing");
        System.out.println("\tstop -> stop song");
        System.out.println("\tquit -> quit");
    }
//        System.out.println("type 'main' for main menu!");

    //EFFECTS: formats user input
    private String getUserInputString() {
        String command = "";
        if (input.hasNext()) {
            command = input.nextLine();
            command = command.toLowerCase();
            command = command.trim();
            command = command.replaceAll("\"|'", "");
            return command;
        }
        return command;
    }

//            if(command.equals("main"))
//
//    {
//        displayMainMenu();
//    } else if(command.equals("songs"))
//
//    {
//        allSongs();
//        displayMenuActions();
//    } else if(command.equals("playlists"))
//
//    {
//        displayMenuPlaylist();
//    } else if(command.equals("s")|command.equals("i")|command.equals("f"))
//
//    {
//        displayMenuPlaylistActions();
//    } else if(command.equals("quit"))
//
//    {
//        keepGoing = false;
//        for (Song s : songs) {
//            s.pauseSong();
//        }
//    }
//
//    //            processCommandPlaylist(command);
//    processCommand(command);
//}
//
//    }


    // EFFECTS: prints menu options and info depending on user input
    private void processCommand(String command) throws InterruptedException {
        if (command.length() > 0) {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case PLAY_COMMAND:
                    songthread.setSongs(songs);
                    songthread.start();
                    break;
                case STOP_COMMAND:
                    songthread.stopPlaying();
                    break;
                case SONGS_COMMAND:
                    getAllSongs(songs);
                    break;
                case PLAYLISTS_COMMAND:
                    getAllPlaylists(playlists);
                    break;
                case QUIT_COMMAND:
                    keepGoing = false;
                    endProgram();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    displayMainMenu();
                    break;
            }
        }
    }

    private void getAllSongs(List<Song> songs) {
        List<String> songNames = new LinkedList<>();
        for (Song s : songs) {
            songNames.add(s.getSongTitle() + " by " + s.getArtist());
        }
        System.out.println("Here are all the songs in your library: ");
        int position = 1;
        for (String s : songNames) {
            System.out.println(position + ". " + s);
            position++;
        }
    }

    public void getAllPlaylists(List<Playlist> playlists) {
        List<String> playlistNames = new LinkedList<>();
        for (Playlist p : playlists) {
            playlistNames.add(p.getPlaylistName());
        }
        System.out.println("Here are all the playlists in your library: ");
        int position = 1;
        for (String s : playlistNames) {
            System.out.println(position + ". " + s);
            position++;
        }
    }

    //EFFECTS: stops playing songs and stops receiving user input
    public void endProgram() throws InterruptedException {
        songthread.stopPlaying();
        thread.sleep(800);
        System.out.println("Thanks for listening. Goodbye!");
        input.close();
    }

//        } else if (command.equals("create")) {
//            System.out.println("What would you like to name your new playlist? ");
//} else if(command.equals("s")){
//        System.out.println("This playlist contains: ");
//        System.out.println(playlist1.getSongsInPlaylist());
//        displayMenuPlaylistActions();
//        }else if(command.equals("remove")){
//        System.out.println("which song would you like to remove?");
//        playlist1.removeSong(Integer.parseInt(command));
////        } else if (command.equals("add")) {
////            System.out.println("which song would you like to add?");
////            playlist1.addSong(command);
//        }else if(command.equals("i")){
//        System.out.println("This playlist contains: ");
//        System.out.println(playlist2.getSongsInPlaylist());
//        displayMenuPlaylistActions();
//        }else if(command.equals("f")){
//        System.out.println("This playlist contains: ");
//        System.out.println(playlist3.getSongsInPlaylist());
//        displayMenuPlaylistActions();
//        }else if(command.equals("new")){
//        Playlist newPlaylist=new Playlist("new playlist");
//        playlists.add(newPlaylist);
//        }else if(command.equals("quit")){
//        System.out.println("\nThanks for listening!");
//        }else{
//        System.out.println("Selection not valid...");
//        }
//
//        }


// MODIFIES: this
// EFFECTS: processes user command for songs
//    private void processCommandPlaylist(String command) {
//        if (command.equals("s")) {
//            playlist1.getSongsInPlaylist();
//        } else if (command.equals("i")) {
//            playlist2.getSongsInPlaylist();
//        } else if (command.equals("f")) {
//            playlist3.getSongsInPlaylist();
//        } else if (command.equals("new")) {
//            new Playlist("new playlist");
//        } else if (command.equals("main")) {
//            displayMenu();
//        }
//    }

//    // EFFECTS: prompts user to select chequing or savings account and returns it
//    private Playlist selectPlaylist() {
//        String selection = "";  // force entry into loop
//
//        while (!(selection.equals(int) || selection.equals("s"))) {
//            System.out.println("What playlist would you like to select? (Please type the number)");
//            selection = input.next();
//            selection = selection.toLowerCase();
//        }
//
//        if (selection.equals("c")) {
//            return cheq;
//        } else {
//            return sav;
//        }
//    }

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
        songs.add(song4);
        songs.add(song5);
        songs.add(song6);
        songs.add(song7);
//        songs.add(song8);
//        songs.add(song9);
//        songs.add(song10);

        playlists.add(playlist1);
        playlists.add(playlist2);
        playlists.add(playlist3);

        playlist1.addSong(song4);
        playlist1.addSong(song5);
        playlist1.addSong(song7);

        playlist2.addSong(song2);
        playlist2.addSong(song6);

        playlist3.addSong(song3);
        playlist3.addSong(song4);
        playlist3.addSong(song5);
        playlist3.addSong(song7);


        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    private void displayMenuPlaylist() {
        System.out.println("\nSelect a playlist or create your own:");
        System.out.println("\nCurrent playlists:");
        System.out.println("\ts -> Star Wars Soundtrack");
        System.out.println("\ti -> Instrumental");
        System.out.println("\tf -> Film Scores");
        System.out.println("\n");
        System.out.println("\tnew -> Create new playlist");
        System.out.println("\tmain -> Main menu");
    }

//    private void displayMenuSongs() {
//        allSongs();
//        System.out.println("\nActions:");
//        System.out.println("\tplay -> play song");
//        System.out.println("\tpause -> pause song");
//        System.out.println("\tloop -> loop song");
//        System.out.println("\tmain -> Main menu");
//    }

    private void displayMenuActions() {
        System.out.println("\nActions:");
        System.out.println("\tplay -> play song");
        System.out.println("\tstop -> stop song");
        System.out.println("\tmain -> Main menu");
    }

    private void displayMenuPlaylistActions() {
        System.out.println("\nActions:");
        System.out.println("\tremove -> remove song");
        System.out.println("\tadd -> add song");
        System.out.println("\tplay -> play song");
        System.out.println("\tstop -> stop song");
        System.out.println("\tmain -> Main menu");
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
