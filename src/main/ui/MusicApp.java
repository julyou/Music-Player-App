package ui;

import model.Playlist;
import model.Playlists;
import model.Song;
import model.SongThread;

import javax.sound.sampled.DataLine;
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
    private static final String PLAYLIST1_COMMAND = "1";
    private static final String PLAYLIST2_COMMAND = "2";
    private static final String PLAYLIST3_COMMAND = "3";
    private static final String MAIN_MENU_COMMAND = "main";
    private static final String GO_BACK_COMMAND = "back";
    private static final String ADD_SONG_COMMAND = "add";
    private static final String DELETE_SONG_COMMAND = "dels";
    private static final String CREATE_PLAYLIST_COMMAND = "new";
    private static final String DELETE_PLAYLIST_COMMAND = "delp";
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

    // EFFECTS: prints menu options and info depending on user input
    private void processCommand(String command) throws InterruptedException {
        if (command.length() > 0) {
            switch (command) {
                case PLAY_COMMAND:
                    songthread.setSongs(songs);
                    songthread.start();
                    break;
                case STOP_COMMAND:
                    songthread.stopPlaying();
                    break;
                case SONGS_COMMAND:
                    getAllSongs(songs);
                    displayMainMenu();
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
            System.out.println("\t" + position + ". " + s);
            position++;
        }
    }

    public void getAllPlaylists(List<Playlist> playlists) throws InterruptedException {
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
        playlistExtraInfoOptions(playlists);
    }

    private void playlistExtraInfoOptions(List<Playlist> playlists) throws InterruptedException {
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

        processPlaylistCommands(playlists);
    }

    private void processPlaylistCommands(List<Playlist> playlists) throws InterruptedException {
        String command = getUserInputString();

        if (command.length() > 0) {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case PLAYLIST1_COMMAND:
                    System.out.println("This playlist contains: ");
                    System.out.println(playlist1.getSongsInPlaylist());
                    playlistSongsExtraInfoOptions(playlist1);
                    break;
                case PLAYLIST2_COMMAND:
                    System.out.println("This playlist contains: ");
                    System.out.println(playlist2.getSongsInPlaylist());
                    playlistSongsExtraInfoOptions(playlist2);
                    break;
                case PLAYLIST3_COMMAND:
                    System.out.println("This playlist contains: ");
                    System.out.println(playlist3.getSongsInPlaylist());
                    playlistSongsExtraInfoOptions(playlist3);
                    break;
                case CREATE_PLAYLIST_COMMAND:
                    playlistNameExtraInfoOptions(playlists);
                    break;
                case DELETE_PLAYLIST_COMMAND:
                    choosePlaylistToDelete(playlists);
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    processPlaylistCommands(playlists);
            }
        }
    }

    private void choosePlaylistToDelete(List<Playlist> pl) throws InterruptedException {
        System.out.println("Which playlist would you like to delete? (type the number)");
        getAllPlaylists(playlists);
        processPlaylistToDelete(pl);
    }

    private void processPlaylistToDelete(List<Playlist> pl) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= pl.size() && 1 <= commandInt) {
            checkCanDeletePlaylist(command, pl);
//            playlistExtraInfoOptions(pl);
        } else {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    choosePlaylistToDelete(pl);
            }
        }
    }

    public void checkCanDeletePlaylist(String s, List<Playlist> pl) throws InterruptedException {
        Playlist playlist = playlists.get(Integer.parseInt(s) - 1);
        if (pl.contains(playlist)) {
            pl.remove(playlist);
            System.out.println(playlist.getPlaylistName() + " successfully deleted from library");

            getAllPlaylists(pl);
        } else {
            System.out.println("That playlist doesn't exist");
        }
    }


    private void playlistNameExtraInfoOptions(List<Playlist> playlists) throws InterruptedException {
        System.out.println("\nWhat would you like to name your new playlist?");
        processPlaylistNameCommand(playlists);
    }

    private void processPlaylistNameCommand(List<Playlist> playlists) throws InterruptedException {
        String command = getUserInputString();
        System.out.println("What would you like to name your playlist?");

        Playlist playlist = new Playlist(command);
        if (command.length() > 0) {
            playlists.add(playlist);
            getAllPlaylists(playlists);
        }

        playlistSongsExtraInfoOptions(playlist);
    }

    private void playlistSongsExtraInfoOptions(Playlist playlist) throws InterruptedException {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add song");
        System.out.println("\tdels -> remove songs");
        System.out.println("\tmain -> main menu");
        System.out.println("\tback -> back");
        System.out.println("\tquit -> quit");

        processPlaylistSongCommands(playlist);
    }

    private void processPlaylistSongCommands(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();

        if (command.length() > 0) {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                case ADD_SONG_COMMAND:
                    chooseSongToAdd(playlist);
                    processPlaylistSongCommands(playlist);
                    break;
                case DELETE_SONG_COMMAND:
                    chooseSongToDelete(playlist);
                    processPlaylistSongCommands(playlist);
                    break;
                case GO_BACK_COMMAND:
                    playlistExtraInfoOptions(playlists);
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    processPlaylistSongCommands(playlist);
            }

        }
    }


    private void chooseSongToAdd(Playlist p) throws InterruptedException {
        System.out.println("\nWhat song would you like to add? (type the number)");
        getAllSongs(songs);
        processSongToAdd(p);

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

    private void processSongToAdd(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= songs.size() && 1 <= commandInt) {
            checkCanAddSong(command, playlist);
            playlistSongsExtraInfoOptions(playlist);
        } else {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    chooseSongToAdd(playlist);
            }
        }
    }

    private void chooseSongToDelete(Playlist p) throws InterruptedException {
        System.out.println("\nWhat song would you like to delete? (type the number)");
        getAllSongs(songs);
        processSongToDelete(p);
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

    private void processSongToDelete(Playlist playlist) throws InterruptedException {
        String command = getUserInputString();
        int commandInt = Integer.parseInt(command);
        if (command.length() > 0 && commandInt <= 7 && 1 <= commandInt) {
            checkCanDeleteSong(command, playlist);
            playlistSongsExtraInfoOptions(playlist);
        } else {
            switch (command) {
                case MAIN_MENU_COMMAND:
                    displayMainMenu();
                    break;
                default:
                    System.out.println("That is an invalid command. Please try again.");
                    chooseSongToDelete(playlist);
            }
        }
    }

//    private void choosePlaylistToDelete(Playlists pl) throws InterruptedException {
//        System.out.println("\nWhat playlist would you like to delete? (type the number)");
//        getAllPlaylists((List<Playlist>) pl);
//        processPlaylistToDelete(pl);
//    }
//
//    public void checkCanDeletePlaylist(String s, Playlists playlists) throws InterruptedException {
//        Playlist playlist = this.playlists.get(Integer.parseInt(s) - 1);
//        if (playlists.getPlaylists().contains(playlist.getPlaylistName())) {
//            playlists.removePlaylist(playlist);
//            System.out.println(playlist.getPlaylistName() + " successfully deleted form " + playlists.getPlaylists());
//
//            System.out.println("Your library contains: ");
//            getAllPlaylists(this.playlists);
//        } else {
//            System.out.println(playlist.getPlaylistName() + " doesn't exist in the playlist");
//        }
//    }
//
//    private void processPlaylistToDelete(Playlists playlists) throws InterruptedException {
//        String command = getUserInputString();
//        int commandInt = Integer.parseInt(command);
//        if (command.length() > 0 && commandInt <= this.playlists.size() && 1 <= commandInt) {
//            checkCanDeletePlaylist(command, playlists);
//            playlistExtraInfoOptions((List<Playlist>) playlists);
//        } else {
//            switch (command) {
//                case MAIN_MENU_COMMAND:
//                    displayMainMenu();
//                    break;
//                default:
//                    System.out.println("That is an invalid command. Please try again.");
//                    choosePlaylistToDelete(playlists);
//            }
//        }
//    }

    //EFFECTS: stops playing songs and stops receiving user input
    public void endProgram() throws InterruptedException {
        songthread.stopPlaying();
        thread.sleep(800);
        System.out.println("Thanks for listening. Goodbye!");
        input.close();
    }

    // MODIFIES: this
// EFFECTS: initializes accounts
    private void init() {
        loadSongs();
        loadPlaylists();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

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


}
