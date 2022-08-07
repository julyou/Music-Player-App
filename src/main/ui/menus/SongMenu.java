package ui.menus;

import model.Playlist;
import model.Playlists;
import model.Song;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a song menu showing songs in a playlist
public class SongMenu extends JFrame implements ActionListener, ListSelectionListener {
    private final JFrame frame = new JFrame();

    private JButton playButton;
    private JButton stopButton;
    private JButton addButton;

    private JMenuItem mainMenu;
    private JMenuItem back;

    private JList<String> mainSongList;
    private DefaultListModel<String> mainSongListModel;

    private JList<String> songsToCopyList;
    private DefaultListModel<String> songsToCopyListModel;

    private final MusicApp app;
    private final Playlist playlist;
    private final Playlists playlists;

    private static final String addSongString = "Add Song";
    private static final String deleteSongString = "Delete Song";
    private static final int FONT_SIZE = 16;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    // EFFECTS: creates song menu layout and components
    SongMenu(MusicApp app, Playlist playlist, Playlists previousPlaylists) {
        this.app = app;
        this.playlist = playlist;
        this.playlists = new Playlists();

        for (Playlist p : previousPlaylists.getPlaylists()) {
            this.playlists.addPlaylist(p);
        }

        frame.setTitle(playlist.getPlaylistName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setJMenuBar(initMenuBar());
        frame.add(initBottomPanel(), BorderLayout.SOUTH);
        frame.add(initMainPanel(), BorderLayout.CENTER);
        frame.add(initSidePanel(), BorderLayout.EAST);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(Color.PINK);
    }

    // MODIFIES: this
    // EFFECTS: creates side panel with add song and delete song buttons
    private JPanel initSidePanel() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.1), (int) (HEIGHT * .04)));

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.16), (int) (HEIGHT * .7)));
        sidePanel.add(emptyPanel);
        sidePanel.add(initAddButton());
        sidePanel.add(initDeleteButton());
        sidePanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        return sidePanel;
    }

    // MODIFIES: this
    // EFFECTS: creates menu bar with main menu and back options
    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenu view = new JMenu("View");
        view.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenu help = new JMenu("Help");
        help.setFont(new Font("Serif", Font.PLAIN, 18));
        mainMenu = new JMenuItem("Main menu");
        mainMenu.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        back = new JMenuItem("Back");
        back.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        mainMenu.addActionListener(this);
        back.addActionListener(this);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(help);
        file.add(mainMenu);
        file.add(back);
        return menuBar;
    }

    // MODIFIES: this
    // EFFECTS: creates two panels displaying songs in playlist and song options to add into playlist
    private JPanel initMainPanel() {
        JLabel songsAdded = new JLabel("Songs in your playlist: ");
        songsAdded.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, FONT_SIZE));

        JPanel subMainPanelLeft = new JPanel();
        subMainPanelLeft.setPreferredSize(new Dimension((int) (WIDTH * 0.52), (int) (HEIGHT * .7)));
        subMainPanelLeft.setAlignmentY(Component.CENTER_ALIGNMENT);
        subMainPanelLeft.add(songsAdded, BorderLayout.NORTH);
        subMainPanelLeft.add(initAddedSongsScrollPanel(), BorderLayout.SOUTH);

        JLabel songsNotAdded = new JLabel("Choose songs to add: ");
        songsNotAdded.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, FONT_SIZE));

        JPanel subMainPanelRight = new JPanel();
        subMainPanelRight.setPreferredSize(new Dimension((int) (WIDTH * 0.32), (int) (HEIGHT * .7)));
        subMainPanelRight.setAlignmentY(Component.CENTER_ALIGNMENT);
        subMainPanelRight.add(songsNotAdded, BorderLayout.NORTH);
        subMainPanelRight.add(initAllSongsScrollPanel(), BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel();
        mainPanel.add(subMainPanelLeft, BorderLayout.WEST);
        mainPanel.add(subMainPanelRight, BorderLayout.EAST);
        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates panel containing play and stop buttons
    private JPanel initBottomPanel() {
        JPanel playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initStopButton());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        bottomPanel.add(playPausePanel);

        return bottomPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates button allowing user to add a song into the playlist
    private JButton initAddButton() {
        addButton = new JButton(addSongString);
        addButton.setActionCommand(addSongString);
        addButton.setPreferredSize(new Dimension((int) (WIDTH * 0.155), (int) (HEIGHT * .32)));
        addButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new AddButtonListener());
        addButton.setEnabled(true);
        return addButton;
    }

    // MODIFIES: this
    // EFFECTS: creates button allowing user to delete a song from the playlist
    private JButton initDeleteButton() {
        JButton deleteButton = new JButton(deleteSongString);
        deleteButton.setActionCommand(deleteSongString);
        deleteButton.addActionListener(new DeleteButtonListener());
        deleteButton.setPreferredSize(new Dimension((int) (WIDTH * 0.155), (int) (HEIGHT * .32)));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return deleteButton;
    }

    // MODIFIES: this
    // EFFECTS: creates play button that starts playing songs in playlist on click
    private JButton initPlayButton() {
        playButton = new JButton();
        playButton.addActionListener(this);
        playButton.setPreferredSize(new Dimension((int) (WIDTH / 2.2), (int) (HEIGHT * .12)));
        playButton.setText("Play");
        playButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return playButton;
    }

    // MODIFIES: this
    // EFFECTS: creates stop button that stops playing songs in playlist on click
    private JButton initStopButton() {
        stopButton = new JButton();
        stopButton.addActionListener(this);
        stopButton.setPreferredSize(new Dimension((int) (WIDTH / 2.2), (int) (HEIGHT * .12)));
        stopButton.setText("Stop");
        stopButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return stopButton;
    }

    // MODIFIES: this
    // EFFECTS: adds list of songs already in playlist into a scroll pane
    private JScrollPane initAddedSongsScrollPanel() {
        mainSongListModel = new DefaultListModel<>();
        for (Song s : playlist.getSongsInPlaylist()) {
            String title = s.getSongTitle();
            mainSongListModel.addElement(title);
        }

        mainSongList = new JList<>(mainSongListModel);
        mainSongList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        mainSongList.setSelectedIndex(0);
        mainSongList.addListSelectionListener(this);
        mainSongList.setVisibleRowCount(5);
        mainSongList.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));

        JScrollPane scrollPanelLeft = new JScrollPane(mainSongList);
        scrollPanelLeft.setPreferredSize(new Dimension((int) (WIDTH * 0.52), (int) (HEIGHT * 0.7)));
        return scrollPanelLeft;
    }

    // MODIFIES: this
    // EFFECTS: adds list of songs that can be added into a scroll pane
    public JScrollPane initAllSongsScrollPanel() {
        songsToCopyListModel = new DefaultListModel<>();

        for (Song s : app.getAllSongs()) {
            songsToCopyListModel.addElement(s.getSongTitle());
        }

        songsToCopyList = new JList<>(songsToCopyListModel);
        songsToCopyList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        songsToCopyList.setSelectedIndex(0);
        songsToCopyList.addListSelectionListener(this);
        songsToCopyList.setVisibleRowCount(5);
        songsToCopyList.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));

        JScrollPane scrollPanelRight = new JScrollPane(songsToCopyList);
        scrollPanelRight.setPreferredSize(new Dimension((int) (WIDTH * 0.32), (int) (HEIGHT * 0.7)));
        return scrollPanelRight;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    // MODIFIES: this
    // EFFECTS: listens for "delete song" button click and removes selected song from "songs in your playlist"
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = mainSongList.getSelectedIndex();
            String element = mainSongList.getSelectedValue();
            mainSongListModel.remove(index);

            for (Song s : app.getAllSongs()) {
                if (element.equals(s.getSongTitle())) {
                    playlist.removeSong(s);
                }
            }

            System.out.println(playlist.getSongsTitlesInPlaylist());

            int size = mainSongListModel.getSize();

            if (size != 0) {
                if (index == mainSongListModel.getSize()) {
                    index--;
                }
                mainSongList.setSelectedIndex(index);
                mainSongList.ensureIndexIsVisible(index);
            }
        }
    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    // MODIFIES: this
    // EFFECTS: listens for "add song" button click and adds selected song into "songs in your playlist"
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String element = songsToCopyList.getSelectedValue();

            int index = mainSongList.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            mainSongListModel.insertElementAt(element, index);

            for (Song s : app.getAllSongs()) {
                if (element.equals(s.getSongTitle())) {
                    playlist.addSongAtIndex(index, s);
                }
            }
            System.out.println(playlist.getSongsTitlesInPlaylist());

            int size = songsToCopyListModel.getSize();

            if (size == 0) {
                addButton.setEnabled(false);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes action commands of "main menu" and "back" menu items, and "play" and "stop" buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(app);
        } else if (e.getSource() == back) {
            frame.dispose();
            AllPlaylistsMenu allPlaylistsMenu = new AllPlaylistsMenu(app, playlists);
        } else if (e.getSource() == playButton) {
            app.getSongThread().startPlaying(playlist.getSongsInPlaylist());
        } else if (e.getSource() == stopButton) {
            app.getSongThread().stopPlaying();
        }
    }
}