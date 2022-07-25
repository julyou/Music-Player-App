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

// Music player application
public class MusicApp {
    private static final String PLAY_COMMAND = "play";
    private static final String STOP_COMMAND = "stop";
    private static final String SONGS_COMMAND = "songs";
    private static final String PLAYLISTS_COMMAND = "playlists";
    private static final String MAIN_MENU_COMMAND = "main";
    private static final String GO_BACK_COMMAND = "back";
    private static final String ADD_SONG_COMMAND = "add";
    private static final String DELETE_SONG_COMMAND = "dels";
    private static final String CREATE_PLAYLIST_COMMAND = "new";
    private static final String DELETE_PLAYLIST_COMMAND = "delp";
    private static final String QUIT_COMMAND = "quit";

    private Scanner input;
    private final SongThread songthread = new SongThread();

    List<Song> songs = new LinkedList<>();
    List<Playlist> playlists = new LinkedList<>();

    Playlist playlist1 = new Playlist("Star Wars Soundtrack");
    Playlist playlist2 = new Playlist("Instrumental");
    Playlist playlist3 = new Playlist("Film scores");

    Song song1 = new Song("song1", "unknown", "data/song1.wav", 34);
    Song song2 = new Song("song2", "unknown", "data/song2.wav", 44);
    Song song3 = new Song("Pink Panther", "Henry Mancini", "data/song3.wav", 30);
    Song song4 = new Song("Imperial March", "John Williams", "data/song4.wav", 60);
    Song song5 = new Song("Cantina Band", "John Williams", "data/song5.wav", 60);
    Song song6 = new Song("Dhol Drums", "unknown", "data/song6.wav", 18);
    Song song7 = new Song("Main Title", "John Williams", "data/song7.wav", 60);

    private boolean keepGoing;

    // EFFECTS: runs the music player application
    public MusicApp() {
        songthread.start();
        runMusicApp();
        songthread.end();
    }

    // EFFECTS: sets up the music application
    private void runMusicApp() {
        keepGoing = true;
        init();
        handleUserInput();
        endProgram();
    }

    // EFFECTS: processes user input
    public void handleUserInput() {
        displayMainMenu();
        String cmd;

        while (keepGoing) {
            cmd = getUserInputString();
            processMainMenuCMD(cmd);
        }
    }

    // EFFECTS: displays main menu to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tsongs -> browse songs");
        System.out.println("\tplaylists -> browse playlists");
        System.out.println("\tplay -> start playing");
        System.out.println("\tstop -> stop song");
        System.out.println("\tquit -> quit");
    }

    // EFFECTS: processes main menu commands
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
                    break;
                case QUIT_COMMAND:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    displayMainMenu();
                    break;
            }
        }
    }

    // EFFECTS: displays playlist menu options to user
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

    // EFFECTS: processes playlist menu commands
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

    // EFFECTS: Prompts user to select which playlist to delete and returns playlists in library
    private void displayDeletePlaylistMenu(List<Playlist> playlists) throws InterruptedException {
        System.out.println("\nWhich playlist would you like to delete? (type the number)");
        printAllPlaylists(playlists);
        processDeletePlaylistMenuCMD(playlists);
    }

    // EFFECTS: processes delete playlist menu commands
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
        } else if ((Integer.parseInt(command) <= 3) && (Integer.parseInt(command) >= 1)) {
            System.out.println("cannot delete default playlist");
            displayPlaylistMenu(playlists);
        } else {
            System.out.println("That is an invalid command. Please try again.");
            displayDeletePlaylistMenu(pl);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes playlist from playlist if it exists and prints success message, otherwise prints error message
    public void deletePlaylist(String s, List<Playlist> pl) {
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

    // EFFECTS: prompts user to name their new playlist
    private void displayNewPlaylistMenu(List<Playlist> playlists) {
        System.out.println("\nWhat would you like to name your new playlist?");
        processNewPlaylistMenuCMD(playlists);
    }

    // MODIFIES: this
    // EFFECTS: creates new playlist with given name and returns all playlists in library
    private void processNewPlaylistMenuCMD(List<Playlist> playlists) {
        String command = getUserInputString();

        Playlist playlist = new Playlist(command);
        if (command.length() > 0) {
            playlists.add(playlist);
        }
        printAllPlaylists(playlists);
        displayPlaylistMenu(playlists);
    }

    // EFFECTS: displays menu options of actions users can do inside a playlist (add/delete, play/stop songs)
    private void displayPlaylistSongsMenu(Playlist playlist) throws InterruptedException {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add song");
        System.out.println("\tdels -> remove song");
        System.out.println("\tplay -> play song");
        System.out.println("\tstop -> stop song");
        System.out.println("\tmain -> main menu");
        System.out.println("\tback -> back");
        System.out.println("\tquit -> quit");

        processPlaylistSongsMenuCMD(playlist);
    }

    // EFFECTS: process playlist songs menu commands
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processPlaylistSongsMenuCMD(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();

        if (command.length() > 0) {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case ADD_SONG_COMMAND:
                    displayAddSongMenu(playlist);
                    break;
                case DELETE_SONG_COMMAND:
                    displayDeleteSongMenu(playlist);
                    break;
                case PLAY_COMMAND:
                    songthread.startPlaying(playlist.getSongsInPlaylist());
                    displayPlaylistSongsMenu(playlist);
                    break;
                case STOP_COMMAND:
                    songthread.stopPlaying();
                    displayPlaylistSongsMenu(playlist);
                    break;
                case GO_BACK_COMMAND:
                    displayPlaylistMenu(playlists);
                    break;
                case QUIT_COMMAND:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    processPlaylistSongsMenuCMD(playlist);
            }
        }
    }

    // EFFECTS: prints songs in given playlist
    public void printSongsInPlaylist(String s) throws InterruptedException {
        int i = Integer.parseInt(s);
        Playlist p = playlists.get(i - 1);
        System.out.println(p.getPlaylistName() + " contains: ");
        System.out.println(p.getSongsTitlesInPlaylist());
        displayPlaylistSongsMenu(p);
    }

    // EFFECTS: prompts user to choose song to add
    private void displayAddSongMenu(Playlist p) throws InterruptedException {
        System.out.println("\nWhat song would you like to add? (type the number)");
        printAllSongs(songs);
        processAddSongMenuCMD(p);
    }

    // EFFECTS: process adding songs into playlist commands
    private void processAddSongMenuCMD(Playlist playlist) throws InterruptedException {
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
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    displayAddSongMenu(playlist);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds song into playlist if not already there and returns songs in playlist. Prints message signalling
    //          add is successful or unsuccessful
    public void checkCanAddSong(String s, Playlist playlist) {
        Song song = songs.get(Integer.parseInt(s) - 1);
        if (!playlist.getSongsTitlesInPlaylist().contains(song.getSongTitle())) {
            playlist.addSong(song);
            System.out.println(song.getSongTitle() + " successfully added to " + playlist.getPlaylistName());

            System.out.println(playlist.getPlaylistName() + " contains: ");
            System.out.println(playlist.getSongsTitlesInPlaylist());
        } else {
            System.out.println(song.getSongTitle() + " is already in the playlist");
        }
    }

    // EFFECTS: prompts user to choose which song to delete from playlist
    private void displayDeleteSongMenu(Playlist p) throws InterruptedException {
        System.out.println("\nWhat song would you like to delete? (type the number)");
        printAllSongs(songs);
        processDeleteSongMenuCMD(p);
    }

    // EFFECTS: processes delete song commands
    private void processDeleteSongMenuCMD(Playlist playlist) throws InterruptedException {
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
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    processDeleteSongMenuCMD(playlist);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes song from playlist if it exists and returns songs in playlist. Prints message signalling
    //          deletion is successful or unsuccessful
    public void checkCanDeleteSong(String s, Playlist playlist) {
        Song song = songs.get(Integer.parseInt(s) - 1);
        if (playlist.getSongsTitlesInPlaylist().contains(song.getSongTitle())) {
            playlist.removeSong(song);
            System.out.println(song.getSongTitle() + " successfully deleted form " + playlist.getPlaylistName());

            System.out.println(playlist.getPlaylistName() + " contains: ");
            System.out.println(playlist.getSongsTitlesInPlaylist());
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
