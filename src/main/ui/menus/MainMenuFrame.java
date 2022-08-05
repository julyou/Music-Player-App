package ui.menus;

import model.Song;
import ui.MusicApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenuFrame extends JFrame implements ActionListener {
    JFrame frame = new JFrame();
    JButton songsButton;
    JButton playlistsButton;
    JButton playButton;
    JButton pauseButton;

    JPanel bottomMainPanel;
    JPanel playPausePanel;

    MusicApp app;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    private static final int FONT_SIZE = 16;

    public MainMenuFrame(MusicApp app) {
        this.app = app;

        playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initPauseButton());

        bottomMainPanel = new JPanel();
        bottomMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        bottomMainPanel.add(playPausePanel);

        JPanel mainPanel = new JPanel();
        mainPanel.add(initSongsButton(), BorderLayout.WEST);
        mainPanel.add(initPlaylistsButton(), BorderLayout.EAST);
        mainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .8)));

        frame.setTitle("Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bottomMainPanel, BorderLayout.SOUTH);
    }


    private JButton initSongsButton() {
        songsButton = new JButton();
        songsButton.addActionListener(this);
        songsButton.setPreferredSize(new Dimension((int) (WIDTH / 2.05), (int) (HEIGHT * .8)));
        songsButton.setText("Browse Songs");
        songsButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return songsButton;
    }

    private JButton initPlaylistsButton() {
        playlistsButton = new JButton();
        playlistsButton.addActionListener(this);
        playlistsButton.setPreferredSize(new Dimension((int) (WIDTH / 2.05), (int) (HEIGHT * .8)));
        playlistsButton.setText("Browse Playlists");
        playlistsButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return playlistsButton;
    }

    private JButton initPlayButton() {
        playButton = new JButton();
        playButton.addActionListener(this);
        playButton.setPreferredSize(new Dimension((int) (WIDTH / 2.5), (int) (HEIGHT * .12)));
        playButton.setText("Play");
        playButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return playButton;
    }

    private JButton initPauseButton() {
        pauseButton = new JButton();
        pauseButton.addActionListener(this);
        pauseButton.setPreferredSize(new Dimension((int) (WIDTH / 2.5), (int) (HEIGHT * .12)));
        pauseButton.setText("Stop");
        pauseButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return pauseButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == songsButton) {
            frame.dispose();
            AllSongsMenuFrame allSongsMenuFrame = new AllSongsMenuFrame(app);
            System.out.println("songs");
        } else if (e.getSource() == playlistsButton) {
            frame.dispose();
            AllPlaylistsMenuFrame allPlaylistsMenuFrame = new AllPlaylistsMenuFrame(app);
            System.out.println("playlists");
        } else if (e.getSource() == playButton) {
            app.getSongThread().startPlaying(app.getAllSongs());

        } else if (e.getSource() == pauseButton) {
            app.getSongThread().stopPlaying();
        }
    }
}
