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
    //    private static final String PLAYLIST1_COMMAND = "1";
//    private static final String PLAYLIST2_COMMAND = "2";
//    private static final String PLAYLIST3_COMMAND = "3";
//    private static final String PLAYLIST4_COMMAND = "4";
//    private static final String PLAYLIST5_COMMAND = "5";
//    private static final String PLAYLIST6_COMMAND = "6";
    private static final String MAIN_MENU_COMMAND = "main";
    private static final String GO_BACK_COMMAND = "back";
    private static final String ADD_SONG_COMMAND = "add";
    private static final String DELETE_SONG_COMMAND = "dels";
    private static final String CREATE_PLAYLIST_COMMAND = "new";
    private static final String DELETE_PLAYLIST_COMMAND = "delp";
    private static final String QUIT_COMMAND = "quit";

    private Scanner input;
    private SongThread songthread = new SongThread();
//    private Thread thread = new Thread();

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

    // EFFECTS: runs the music player application
    public MusicApp() throws InterruptedException {
//        System.out.println("\nsongThread starting");
        songthread.start();

//        System.out.println("\nrunMusicApp started");
        runMusicApp();

//        System.out.println("\nsongThread ending...")
        songthread.end();

//        System.out.println("\nsongThread join....");
//        songthread.join();
//        System.out.println("\nsongThread join ended");


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
        String cmd;

        while (keepGoing) {
            cmd = getUserInputString();
            processMainMenuCMD(cmd);
        }
    }
//    public String processPlaylistMenu() throws InterruptedException {
//        String command;
//
//        do {
//            getAllPlaylists(playlists);
//            command = playlistExtraInfoOptions(playlists);
//        } while (!command.equals("back") && !command.equals("quit"));
//        return command;
//    }

    // EFFECTS: displays menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tsongs -> browse songs");
        System.out.println("\tplaylists -> browse playlists");
        System.out.println("\tplay -> start playing");
        System.out.println("\tstop -> stop song");
        System.out.println("\tquit -> quit");
    }

    // EFFECTS: prints menu options and info depending on user input
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processMainMenuCMD(String command) {
        if (command.length() > 0) {
            switch (command) {
                case PLAY_COMMAND:
                    songthread.startPlaying(songs);
                    break;
                case STOP_COMMAND:
                    songthread.stopPlaying();
                    break;
                case SONGS_COMMAND:
                    printAllSongs(songs);
                    displayMainMenu();
                    break;
                case PLAYLISTS_COMMAND:
                    printAllPlaylists(playlists);
                    displayPlaylistMenu(playlists);
//                    String cmd;
//                    cmd = processPlaylistMenu();
//                    if (cmd.equals("previous")) {
//                        displayMainMenu();
//                    } else {
//                        keepGoing = false;
//                        endProgram();
//                    }
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

    private void displayPlaylistMenu(List<Playlist> playlists) {
        System.out.println("\nSelect from:");
        int index = 1;
        for (Playlist p : playlists) {
            System.out.println("\t" + index + " -> " + p.getPlaylistName() + " playlist");
            index++;
        }
        System.out.println("\tnew -> create new playlist");
        System.out.println("\tdelp -> delete playlist");
        System.out.println("\tmain -> main menu");
        System.out.println("\tquit -> quit");

        processPlaylistMenuCMD(playlists);
    }

    private void processPlaylistMenuCMD(List<Playlist> playlists) {
        String command = getUserInputString();
        if (command.length() > 0) {
            try {
                if (command.equals(MAIN_MENU_COMMAND)) {
                    displayMainMenu();
                } else if (command.equals(CREATE_PLAYLIST_COMMAND)) {
                    displayNewPlaylistMenu(playlists);
                } else if (command.equals(DELETE_PLAYLIST_COMMAND)) {
                    displayDeletePlaylistMenu(playlists);
                } else if (command.equals(QUIT_COMMAND)) {
                    keepGoing = false;
                    endProgram();
                } else if ((Integer.parseInt(command) <= playlists.size()) && (Integer.parseInt(command) > 0)) {
                    printSongsInPlaylist(command);
                } else {
                    System.out.println("That is an invalid command. Please try again.");
                    processPlaylistMenuCMD(playlists);
                }
            } catch (Exception e) {
                System.out.println("That is an invalid command. Please try again.");
                processPlaylistMenuCMD(playlists);
            }
        }
    }

    private void displayDeletePlaylistMenu(List<Playlist> playlists) throws InterruptedException {
        System.out.println("\nWhich playlist would you like to delete? (type the number)");
        printAllPlaylists(playlists);
        processDeletePlaylistMenuCMD(playlists);
    }

    private void processDeletePlaylistMenuCMD(List<Playlist> pl) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= pl.size() && 4 <= commandInt) {
            deletePlaylist(command, pl);
        }

        if (command.equals(MAIN_MENU_COMMAND)) {
            displayMainMenu();
        } else if (command.equals(QUIT_COMMAND)) {
            keepGoing = false;
            endProgram();
        } else if ((Integer.parseInt(command) <= 3) && (Integer.parseInt(command) >= 1)) {
            System.out.println("cannot delete default playlist");
            displayPlaylistMenu(playlists);
        } else {
            System.out.println("That is an invalid command. Please try again.");
            displayDeletePlaylistMenu(pl);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes playlist from playlist if it exists and print success message, otherwise print error message
    public void deletePlaylist(String s, List<Playlist> pl) throws InterruptedException {
        Playlist playlist = playlists.get(Integer.parseInt(s) - 1);
        if (pl.contains(playlist)) {
            pl.remove(playlist);
            System.out.println(playlist.getPlaylistName() + " successfully deleted from library");
            printAllPlaylists(pl);
            displayPlaylistMenu(playlists);
        } else {
            System.out.println("That playlist doesn't exist");
        }
    }

    private void displayNewPlaylistMenu(List<Playlist> playlists) throws InterruptedException {
        System.out.println("\nWhat would you like to name your new playlist?");
        processNewPlaylistMenuCMD(playlists);
    }

    private void processNewPlaylistMenuCMD(List<Playlist> playlists) throws InterruptedException {
        String command = getUserInputString();

        Playlist playlist = new Playlist(command);
        if (command.length() > 0) {
            playlists.add(playlist);
            printAllPlaylists(playlists);
            displayPlaylistMenu(playlists);
        }
    }

    private void displayPlaylistSongsMenu(Playlist playlist) throws InterruptedException {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add song");
        System.out.println("\tdels -> remove songs");
        System.out.println("\tmain -> main menu");
        System.out.println("\tback -> back");
        System.out.println("\tquit -> quit");

        processPlaylistSongsMenuCMD(playlist);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processPlaylistSongsMenuCMD(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();

        if (command.length() > 0) {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case ADD_SONG_COMMAND:
                    displayAddSong(playlist);
                    processPlaylistSongsMenuCMD(playlist);
                    break;
                case DELETE_SONG_COMMAND:
                    displayDeleteSong(playlist);
                    processPlaylistSongsMenuCMD(playlist);
                    break;
                case GO_BACK_COMMAND:
                    displayPlaylistMenu(playlists);
                    break;
                case QUIT_COMMAND:
                    keepGoing = false;
                    endProgram();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    processPlaylistSongsMenuCMD(playlist);
            }

        }
    }

    // EFFECTS: prints songs in playlist
    public void printSongsInPlaylist(String s) throws InterruptedException {
        int i = Integer.parseInt(s);
        for (Playlist p : playlists) {
            p = playlists.get(i - 1);
            System.out.println(p.getPlaylistName() + " contains: ");
            System.out.println(p.getSongsInPlaylist());
            displayPlaylistSongsMenu(p);
        }
    }

    // EFFECTS: asks for user input to choose song to add
    private void displayAddSong(Playlist p) throws InterruptedException {
        System.out.println("\nWhat song would you like to add? (type the number)");
        printAllSongs(songs);
        processAddSongCMD(p);
    }

    private void processAddSongCMD(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= songs.size() && 1 <= commandInt) {
            checkCanAddSong(command, playlist);
            displayPlaylistSongsMenu(playlist);
        } else {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case QUIT_COMMAND:
                    keepGoing = false;
                    endProgram();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    displayAddSong(playlist);
            }
        }
    }

    public void checkCanAddSong(String s, Playlist playlist) {
        Song song = songs.get(Integer.parseInt(s) - 1);
        if (!playlist.getSongsInPlaylist().contains(song.getSongTitle())) {
            playlist.addSong(song);
            System.out.println(song.getSongTitle() + " successfully added to " + playlist.getPlaylistName());

            System.out.println(playlist.getPlaylistName() + " contains: ");
            System.out.println(playlist.getSongsInPlaylist());
        } else {
            System.out.println(song.getSongTitle() + " is already in the playlist");
        }
    }

    private void displayDeleteSong(Playlist p) throws InterruptedException {
        System.out.println("\nWhat song would you like to delete? (type the number)");
        printAllSongs(songs);
        processDeleteSongCMD(p);
    }

    private void processDeleteSongCMD(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= 7 && 1 <= commandInt) {
            checkCanDeleteSong(command, playlist);
            displayPlaylistSongsMenu(playlist);
        } else {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case QUIT_COMMAND:
                    keepGoing = false;
                    endProgram();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    displayDeleteSong(playlist);
            }
        }
    }

    public void checkCanDeleteSong(String s, Playlist playlist) {
        Song song = songs.get(Integer.parseInt(s) - 1);
        if (playlist.getSongsInPlaylist().contains(song.getSongTitle())) {
            playlist.removeSong(song);
            System.out.println(song.getSongTitle() + " successfully deleted form " + playlist.getPlaylistName());

            System.out.println(playlist.getPlaylistName() + " contains: ");
            System.out.println(playlist.getSongsInPlaylist());
        } else {
            System.out.println(song.getSongTitle() + " doesn't exist in the playlist");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        loadSongs();
        loadPlaylists();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: creates list of all songs
    private void loadSongs() {
        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);
        songs.add(song6);
        songs.add(song7);
    }

    // MODIFIES: this
    // EFFECTS: creates list of playlists
    private void loadPlaylists() {
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
    }

    // MODIFIES: this
    //EFFECTS: formats user input
    private String getUserInputString() {
        String cmd = "";
        if (input.hasNext()) {
            cmd = input.nextLine();
            cmd = cmd.toLowerCase();
            cmd = cmd.trim();
            cmd = cmd.replaceAll("\"|'", "");
            return cmd;
        }
        return cmd;
    }

    // EFFECTS: prints all songs in library
    private void printAllSongs(List<Song> songs) {
        List<String> songNames = new LinkedList<>();
        for (Song s : songs) {
            songNames.add(s.getSongTitle() + " by " + s.getArtist());
        }
        System.out.println("Here are all the songs in your library: ");
        int position = 1;
        for (String s : songNames) {
            System.out.println("\t" + position + ". " + s);
            position++;
        }
    }

    // EFFECTS: prints all playlists in library
    public void printAllPlaylists(List<Playlist> playlists) {
        List<String> playlistNames = new LinkedList<>();
        for (Playlist p : playlists) {
            playlistNames.add(p.getPlaylistName());
        }
        System.out.println("Here are all the playlists in your library: ");
        int position = 1;
        for (String s : playlistNames) {
            System.out.println("\t" + position + ". " + s);
            position++;
        }

    }

    //EFFECTS: stops playing songs and stops receiving user input
    public void endProgram() {
        System.out.println("Thanks for listening. Goodbye!");
        input.close();
    }
}
