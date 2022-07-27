package ui;

import model.Playlist;
import model.Playlists;
import model.Song;
import model.SongThread;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
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
    private static final String SAVE_PLAYLIST_COMMAND = "save";
    private static final String LOAD_PLAYLIST_COMMAND = "load";
    private static final String QUIT_COMMAND = "quit";

    private Scanner input;
    private final SongThread songthread = new SongThread();
    private boolean keepGoing;

    private List<Song> songs = new LinkedList<>();
    private Playlists playlists;
    private static final String JSON_STORE = "./data/playlists.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Playlist playlist1 = new Playlist("Star Wars Soundtrack");
    private Playlist playlist2 = new Playlist("Instrumental");
    private Playlist playlist3 = new Playlist("Film scores");

    private Song song1 = new Song("song1", "unknown", "data/song1.wav", 34);
    private Song song2 = new Song("song2", "unknown", "data/song2.wav", 44);
    private Song song3 = new Song("Pink Panther", "Henry Mancini", "data/song3.wav", 30);
    private Song song4 = new Song("Imperial March", "John Williams", "data/song4.wav", 60);
    private Song song5 = new Song("Cantina Band", "John Williams", "data/song5.wav", 60);
    private Song song6 = new Song("Dhol Drums", "unknown", "data/song6.wav", 18);
    private Song song7 = new Song("Main Title", "John Williams", "data/song7.wav", 60);


    // EFFECTS: runs the music player application
    public MusicApp() throws MalformedURLException {
        playlists = new Playlists("Main");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

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
    private void displayPlaylistMenu(Playlists playlists) {
        System.out.println("\nSelect from:");
        int index = 1;
        for (Playlist p : playlists.getPlaylists()) {
            System.out.println("\t" + index + " -> " + p.getPlaylistName() + " playlist");
            index++;
        }
        System.out.println("\tnew -> create new playlist");
        System.out.println("\tdelp -> delete playlist");
        System.out.println("\tsave -> save playlists");
        System.out.println("\tload -> load playlist");
        System.out.println("\tmain -> main menu");
        System.out.println("\tquit -> quit");

        processPlaylistMenuCMD(playlists);
    }

    // EFFECTS: processes playlist menu commands
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processPlaylistMenuCMD(Playlists playlists) {
        String command = getUserInputString();
        int size = playlists.getPlaylists().size();
        if (command.length() > 0) {
            try {
                if (command.equals(MAIN_MENU_COMMAND)) {
                    displayMainMenu();
                } else if (command.equals(CREATE_PLAYLIST_COMMAND)) {
                    displayNewPlaylistMenu(playlists);
                } else if (command.equals(DELETE_PLAYLIST_COMMAND)) {
                    displayDeletePlaylistMenu(playlists);
                } else if (command.equals(SAVE_PLAYLIST_COMMAND)) {
                    savePlaylists();
                } else if (command.equals(LOAD_PLAYLIST_COMMAND)) {
                    loadPlaylists();
                } else if (command.equals(QUIT_COMMAND)) {
                    keepGoing = false;
                } else if (Integer.parseInt(command) <= size && (Integer.parseInt(command) > 0)) {
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
    private void displayDeletePlaylistMenu(Playlists playlists) throws InterruptedException {
        System.out.println("\nWhich playlist would you like to delete? (type the number)");
        printAllPlaylists(playlists);
        processDeletePlaylistMenuCMD(playlists);
    }

    // EFFECTS: processes delete playlist menu commands
    private void processDeletePlaylistMenuCMD(Playlists pl) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= pl.getPlaylistSize() && 4 <= commandInt) {
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
    public void deletePlaylist(String s, Playlists pl) {
        Playlist playlist = playlists.getPlaylist(Integer.parseInt(s) - 1);
        if (pl.getPlaylists().contains(playlist)) {
            pl.getPlaylists().remove(playlist);
            System.out.println(playlist.getPlaylistName() + " successfully deleted from library");
            printAllPlaylists(pl);
            displayPlaylistMenu(playlists);
        } else {
            System.out.println("That playlist doesn't exist");
        }
    }

    // EFFECTS: prompts user to name their new playlist
    private void displayNewPlaylistMenu(Playlists playlists) {
        System.out.println("\nWhat would you like to name your new playlist?");
        processNewPlaylistMenuCMD(playlists);
    }

    // MODIFIES: this
    // EFFECTS: creates new playlist with given name and returns all playlists in library
    private void processNewPlaylistMenuCMD(Playlists playlists) {
        String command = getUserInputString();

        Playlist playlist = new Playlist(command);
        if (command.length() > 0) {
            this.playlists.addPlaylist(playlist);
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
        Playlist p = playlists.getPlaylist(i - 1);
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
        loadAllSongs();
        loadAllPlaylists();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: creates list of all songs
    private void loadAllSongs() {
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
    private void loadAllPlaylists() {
        playlists.addPlaylist(playlist1);
        playlists.addPlaylist(playlist2);
        playlists.addPlaylist(playlist3);

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
    public void printAllPlaylists(Playlists playlists) {
        List<String> playlistNames = new LinkedList<>();
        for (Playlist p : playlists.getPlaylists()) {
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

    // EFFECTS: saves playlists to file
    private void savePlaylists() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlists);
            jsonWriter.close();
            System.out.println("Saved " + playlists.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads playlists from file
    private void loadPlaylists() {
        try {
            playlists = jsonReader.read();
            System.out.println("Loaded" + playlists.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
