package ui.menus;

import ui.MusicApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame implements ActionListener {
    JButton songsButton;
    JButton playlistsButton;
    JButton playPauseButton;
    ImageIcon playIcon;
    ImageIcon pauseIcon;

    String text;

    MusicApp app;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public MainMenuFrame(MusicApp app) {
        this.app = app;
        songsButton = new JButton();
        songsButton.addActionListener(this);
        songsButton.setBounds(0, 0, WIDTH / 2, (int) (HEIGHT * 0.75));
        songsButton.setText("Browse Songs");
        songsButton.setFocusable(false);

        playlistsButton = new JButton();
        playlistsButton.addActionListener(this);
        playlistsButton.setBounds(WIDTH / 2, 0, WIDTH / 2, (int) (HEIGHT * 0.75));
        playlistsButton.setText("Browse Playlists");
        playlistsButton.setFocusable(false);

        playIcon = new ImageIcon("data/images/Play.gif");
        pauseIcon = new ImageIcon("data/images/Pause.png");
        playPauseButton = new JButton();
        playPauseButton.addActionListener(this);
        playPauseButton.setBounds(0, (int) (HEIGHT * 0.75), WIDTH, (int) (HEIGHT * 0.25));
        playPauseButton.setText("Play");
        text = "Play";
        playPauseButton.setFocusable(false);
        playPauseButton.setBackground(new Color(255, 255, 255, 0));


        this.setTitle("Music Player");
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(234, 231, 226));

        this.add(songsButton);
        this.add(playlistsButton);
        this.add(playPauseButton);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == songsButton) {
            this.dispose();
            AllSongsMenuFrame allSongsMenuFrame = new AllSongsMenuFrame(app);
            System.out.println("songs");
        } else if (e.getSource() == playlistsButton) {
            this.dispose();
            PlaylistMenuFrame playlistMenuFrame = new PlaylistMenuFrame(app);
            System.out.println("playlists");
        } else if (e.getSource() == playPauseButton) {
            if (text.equals("Play")) {
                System.out.println("play");
                playPauseButton.setText("Paused");
                text = "Paused";
            } else {
                System.out.println("pause");
                playPauseButton.setText("Play");
                text = "Play";
            }
        } else {
            return;
        }
    }
}
