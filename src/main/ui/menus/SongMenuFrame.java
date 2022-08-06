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

public class SongMenuFrame extends JFrame implements ActionListener, ListSelectionListener {
    private final JFrame frame = new JFrame();

    private JButton playButton;
    private JButton pauseButton;
    private JButton addButton;
    private JButton deleteButton;
    JMenuItem mainMenu;
    JMenuItem back;

    private JPanel mainPanel;
    private final JPanel sidePanel;
    private JPanel bottomPanel;
    private JPanel playPausePanel;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    private JList<String> mainSongList;
    private DefaultListModel<String> mainSongListModel;

    private JList<String> songsToCopyList;
    private DefaultListModel<String> songsToCopyListModel;

    private JScrollPane scrollPanelLeft;
    private JScrollPane scrollPanelRight;

    private final MusicApp app;
    private final Playlist playlist;
    private final Playlists playlists;


    private static final String addSongString = "Add Song";
    private static final String deleteSongString = "Delete Song";
    private static final int FONT_SIZE = 16;

    SongMenuFrame(MusicApp app, Playlist playlist, Playlists previousPlaylists) {
        this.app = app;
        this.playlist = playlist;
        this.playlists = new Playlists();

        for (Playlist p : previousPlaylists.getPlaylists()) {
            this.playlists.addPlaylist(p);
        }

        frame.setTitle("Songs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .04)));

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * .7)));
        sidePanel.add(emptyPanel);
        sidePanel.add(initAddButton());
        sidePanel.add(initDeleteButton());
        sidePanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        frame.setJMenuBar(initMenuBar());
        frame.add(initBottomPanel(), BorderLayout.SOUTH);
        frame.add(initMainPanel(), BorderLayout.WEST);
        frame.add(sidePanel, BorderLayout.EAST);
    }

    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 18));
        mainMenu = new JMenuItem("Main menu");
        mainMenu.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        back = new JMenuItem("Back");
        back.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        mainMenu.addActionListener(this);
        back.addActionListener(this);
        menuBar.add(file);
        file.add(mainMenu);
        file.add(back);
        return menuBar;
    }

    private JPanel initMainPanel() {
        JLabel songsAdded = new JLabel("Songs in your playlist");
        songsAdded.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        JLabel songsNotAdded = new JLabel("Song list");
        songsNotAdded.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));

        JPanel subMainPanelLeft = new JPanel();
        subMainPanelLeft.setPreferredSize(new Dimension((int) (WIDTH * 0.47), (int) (HEIGHT * .7)));
        subMainPanelLeft.setAlignmentY(Component.CENTER_ALIGNMENT);
        subMainPanelLeft.add(songsAdded, BorderLayout.NORTH);
        subMainPanelLeft.add(initAddedSongsScrollPanel(), BorderLayout.SOUTH);

        JPanel subMainPanelRight = new JPanel();
        subMainPanelRight.setPreferredSize(new Dimension((int) (WIDTH * 0.33), (int) (HEIGHT * .7)));
        subMainPanelRight.setAlignmentY(Component.CENTER_ALIGNMENT);
        subMainPanelRight.add(songsNotAdded, BorderLayout.NORTH);
        subMainPanelRight.add(initAllSongsScrollPanel(), BorderLayout.SOUTH);

        mainPanel = new JPanel();
        mainPanel.add(subMainPanelLeft, BorderLayout.WEST);
        mainPanel.add(subMainPanelRight, BorderLayout.EAST);
        return mainPanel;
    }

    private JPanel initBottomPanel() {
        playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initPauseButton());

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        bottomPanel.add(playPausePanel);

        return bottomPanel;
    }

    private JButton initAddButton() {
        addButton = new JButton(addSongString);
        addButton.setActionCommand(addSongString);
        addButton.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .32)));
        addButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new AddButtonListener());
        return addButton;
    }

    private JButton initDeleteButton() {
        deleteButton = new JButton(deleteSongString);
        deleteButton.setActionCommand(deleteSongString);
        deleteButton.addActionListener(new DeleteButtonListener());
        deleteButton.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .32)));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return deleteButton;
    }

    private JButton initPlayButton() {
        playButton = new JButton();
        playButton.addActionListener(this);
        playButton.setPreferredSize(new Dimension((int) (WIDTH / 2.2), (int) (HEIGHT * .12)));
        playButton.setText("Play");
        playButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return playButton;
    }

    private JButton initPauseButton() {
        pauseButton = new JButton();
        pauseButton.addActionListener(this);
        pauseButton.setPreferredSize(new Dimension((int) (WIDTH / 2.2), (int) (HEIGHT * .12)));
        pauseButton.setText("Stop");
        pauseButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return pauseButton;
    }

    private JScrollPane initAddedSongsScrollPanel() {
        mainSongListModel = new DefaultListModel<>();
        for (Song s : playlist.getSongsInPlaylist()) {
            String title = s.getSongTitle();
            mainSongListModel.addElement(title);
        }

        //Create the list and put it in a scroll pane.
        mainSongList = new JList<>(mainSongListModel);
        mainSongList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        mainSongList.setSelectedIndex(0);
        mainSongList.addListSelectionListener(this);
        mainSongList.setVisibleRowCount(5);
        mainSongList.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        scrollPanelLeft = new JScrollPane(mainSongList);
        scrollPanelLeft.setPreferredSize(new Dimension((int) (WIDTH * 0.47), (int) (HEIGHT * 0.7)));
        return scrollPanelLeft;
    }

    public JScrollPane initAllSongsScrollPanel() {
        songsToCopyListModel = new DefaultListModel<>();

        for (Song s : app.getAllSongs()) {
            songsToCopyListModel.addElement(s.getSongTitle());
        }

        //Create the list and put it in a scroll pane.
        songsToCopyList = new JList<>(songsToCopyListModel);
        songsToCopyList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        songsToCopyList.setSelectedIndex(0);
        songsToCopyList.addListSelectionListener(this);
        songsToCopyList.setVisibleRowCount(5);
        songsToCopyList.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        scrollPanelRight = new JScrollPane(songsToCopyList);
        scrollPanelRight.setPreferredSize(new Dimension((int) (WIDTH * 0.33), (int) (HEIGHT * 0.7)));
        return scrollPanelRight;
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = mainSongList.getSelectedIndex();
            mainSongListModel.remove(index);
            String element = songsToCopyList.getSelectedValue();
            for (Song s : app.getAllSongs()) {
                if (element.equals(s.getSongTitle())) {
                    playlist.removeSong(s);
                }
            }
            int size = mainSongListModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);

            } else {
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
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String element = songsToCopyList.getSelectedValue();
            mainSongListModel.addElement(element);
            for (Song s : app.getAllSongs()) {
                if (element.equals(s.getSongTitle())) {
                    playlist.addSong(s);
                }
            }

            int size = songsToCopyListModel.getSize();

            if (size == 0) {
                addButton.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
        } else if (e.getSource() == back) {
            frame.dispose();
            AllPlaylistsMenuFrame allPlaylistsMenuFrame = new AllPlaylistsMenuFrame(app, playlists);
        } else if (e.getSource() == playButton) {
            app.getSongThread().startPlaying(playlist.getSongsInPlaylist());
        } else if (e.getSource() == pauseButton) {
            app.getSongThread().stopPlaying();
        }
    }
}


