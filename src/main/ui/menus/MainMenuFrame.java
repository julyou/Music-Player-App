package ui.menus;

import ui.MusicApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame implements ActionListener {
    JButton songsButton;
    JButton playlistsButton;
    JButton playButton;
    JButton pauseButton;

    JPanel bottomMainPanel;
    JPanel playPausePanel;

    MusicApp app;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    public MainMenuFrame(MusicApp app) {
        this.app = app;
        this.setLayout(new BorderLayout());
        this.setTitle("Music Player");
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);

        playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initPauseButton());

        bottomMainPanel = new JPanel();
        bottomMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        bottomMainPanel.add(playPausePanel);

        this.add(initSongsButton(), BorderLayout.WEST);
        this.add(initPlaylistsButton(), BorderLayout.EAST);
        this.add(bottomMainPanel, BorderLayout.SOUTH);
    }


    private JButton initSongsButton() {
        songsButton = new JButton();
        songsButton.addActionListener(this);
        songsButton.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .75)));
        songsButton.setText("Browse Songs");
        songsButton.setFont(new Font("Serif", Font.PLAIN, 14));
        return songsButton;
    }

    private JButton initPlaylistsButton() {
        playlistsButton = new JButton();
        playlistsButton.addActionListener(this);
        playlistsButton.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .75)));
        playlistsButton.setText("Browse Playlists");
        playlistsButton.setFont(new Font("Serif", Font.PLAIN, 14));
        return playlistsButton;
    }

    private JButton initPlayButton() {
        playButton = new JButton();
        playButton.addActionListener(this);
        playButton.setPreferredSize(new Dimension(WIDTH / 4, (int) (HEIGHT * .12)));
        playButton.setText("Play");
        playButton.setFont(new Font("Serif", Font.PLAIN, 14));
        return playButton;
    }

    private JButton initPauseButton() {
        pauseButton = new JButton();
        pauseButton.addActionListener(this);
        pauseButton.setPreferredSize(new Dimension(WIDTH / 4, (int) (HEIGHT * .12)));
        pauseButton.setText("Stop");
        pauseButton.setFont(new Font("Serif", Font.PLAIN, 14));
        return pauseButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == songsButton) {
            this.dispose();
            AllSongsMenuFrame allSongsMenuFrame = new AllSongsMenuFrame(app);
            System.out.println("songs");
        } else if (e.getSource() == playlistsButton) {
            this.dispose();
            AllPlaylistsMenuFrame allPlaylistsMenuFrame = new AllPlaylistsMenuFrame(app);
            System.out.println("playlists");
        } else if (e.getSource() == playButton) {
            app.getSongThread().startPlaying(app.getAllSongs());

        } else if (e.getSource() == pauseButton) {
            app.getSongThread().stopPlaying();
        }
    }
}
