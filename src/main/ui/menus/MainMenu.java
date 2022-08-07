package ui.menus;

import ui.MusicApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a main menu
public class MainMenu extends JFrame implements ActionListener {
    private final JFrame frame = new JFrame();
    private JButton songsButton;
    private JButton playlistsButton;
    private JButton playButton;
    private JButton stopButton;

    private final MusicApp app;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 555;
    private static final int FONT_SIZE = 16;

    // EFFECTS: creates main menu with layout and components
    public MainMenu(MusicApp app) {
        this.app = app;

        JPanel bottomMainPanel = new JPanel();
        bottomMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.17)));
        bottomMainPanel.add(initPlayPausePanel());

        JPanel mainPanel = new JPanel();
        mainPanel.add(initSongsButton(), BorderLayout.WEST);
        mainPanel.add(initPlaylistsButton(), BorderLayout.EAST);
        mainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .70)));

        frame.setTitle("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(initMenuBar(), BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bottomMainPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(Color.PINK);
    }

    // MODIFIES: this
    // EFFECTS: creates panel with play and pause buttons
    private JPanel initPlayPausePanel() {
        JPanel playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initStopButton());

        return playPausePanel;
    }

    // MODIFIES: this
    // EFFECTS: creates menu bar with "main menu" submenu
    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.05)));
        menuBar.setBorderPainted(true);
        JMenu file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenu view = new JMenu("View");
        view.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenu help = new JMenu("Help");
        help.setFont(new Font("Serif", Font.PLAIN, 18));
        JMenuItem mainMenu = new JMenuItem("Main menu");
        mainMenu.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        mainMenu.addActionListener(this);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(help);
        file.add(mainMenu);
        return menuBar;
    }

    // MODIFIES: this
    // EFFECTS: creates "browse songs" button
    private JButton initSongsButton() {
        songsButton = new JButton();
        songsButton.addActionListener(this);
        songsButton.setPreferredSize(new Dimension((int) (WIDTH / 2.05), (int) (HEIGHT * .68)));
        songsButton.setText("Browse Songs");
        songsButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return songsButton;
    }

    // MODIFIES: this
    // EFFECTS: creates "browse playlists" button
    private JButton initPlaylistsButton() {
        playlistsButton = new JButton();
        playlistsButton.addActionListener(this);
        playlistsButton.setPreferredSize(new Dimension((int) (WIDTH / 2.05), (int) (HEIGHT * .68)));
        playlistsButton.setText("Browse Playlists");
        playlistsButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return playlistsButton;
    }

    // MODIFIES: this
    // EFFECTS: creates "play" button
    private JButton initPlayButton() {
        playButton = new JButton();
        playButton.addActionListener(this);
        playButton.setPreferredSize(new Dimension((int) (WIDTH / 2.5), (int) (HEIGHT * .13)));
        playButton.setText("Play");
        playButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return playButton;
    }

    // MODIFIES: this
    // EFFECTS: creates "stop" button
    private JButton initStopButton() {
        stopButton = new JButton();
        stopButton.addActionListener(this);
        stopButton.setPreferredSize(new Dimension((int) (WIDTH / 2.5), (int) (HEIGHT * .13)));
        stopButton.setText("Stop");
        stopButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return stopButton;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: listens for button clicks and processes resulting action of the respective button
    //          "browse songs" brings user to all songs menu, "browse playlists" brings user playlists menu
    //          "play" starts playing songs, "stop" stops playing songs
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == songsButton) {
            frame.dispose();
            AllSongsMenu allSongsMenu = new AllSongsMenu(app);
        } else if (e.getSource() == playlistsButton) {
            frame.dispose();
            AllPlaylistsMenu allPlaylistsMenu = new AllPlaylistsMenu(app, app.getAllPlaylists());
        } else if (e.getSource() == playButton) {
            app.getSongThread().startPlaying(app.getAllSongs());
        } else if (e.getSource() == stopButton) {
            app.getSongThread().stopPlaying();
        }
    }
}