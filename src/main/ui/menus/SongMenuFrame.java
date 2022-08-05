package ui.menus;

import model.Playlist;
import model.Playlists;
import model.Song;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongMenuFrame extends JFrame implements ActionListener, ListSelectionListener {
    private JFrame frame = new JFrame();

    private JButton playButton;
    private JButton pauseButton;
    private JButton addSongButton;
    private JButton deleteSongButton;

    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel bottomMainPanel;
    private JPanel playPausePanel;



    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem mainMenu;
    private final JMenuItem back;

    private final JList<String> mainSongList;
    private final DefaultListModel<String> mainSongListModel;

    private final JList<String> songsToCopyList;
    private final DefaultListModel<String> songsToCopyListModel;

    private JScrollPane scrollPanelLeft;
    private JScrollPane scrollPanelRight;

    private final MusicApp app;
    private final Playlist playlist;


    SongMenuFrame(MusicApp app, Playlist playlist) {
        this.app = app;
        this.playlist = playlist;

        frame.setTitle("Songs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);

        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 16));
        mainMenu = new JMenuItem("Main menu");
        back = new JMenuItem("Back");
        mainMenu.addActionListener(this);
        back.addActionListener(this);
        menuBar.add(file);
        file.add(mainMenu);
        file.add(back);

        frame.setJMenuBar(menuBar);

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
        mainSongList.setFont(new Font("Serif", Font.PLAIN, 14));
        scrollPanelLeft = new JScrollPane(mainSongList);
        scrollPanelLeft.setPreferredSize(new Dimension((int) (WIDTH * 0.47), (int) (HEIGHT * 0.7)));

        songsToCopyListModel = new DefaultListModel<>();
        for (Song s : app.getAllSongs()) {
            if (!playlist.getSongsInPlaylist().contains(s.getSongTitle())) {
                songsToCopyListModel.addElement(s.getSongTitle());
            }
        }

        //Create the list and put it in a scroll pane.
        songsToCopyList = new JList<>(songsToCopyListModel);
        songsToCopyList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        songsToCopyList.setSelectedIndex(0);
        songsToCopyList.addListSelectionListener(this);
        songsToCopyList.setVisibleRowCount(5);
        songsToCopyList.setFont(new Font("Serif", Font.PLAIN, 14));
        scrollPanelRight = new JScrollPane(songsToCopyList);
        scrollPanelRight.setPreferredSize(new Dimension((int) (WIDTH * 0.33), (int) (HEIGHT * 0.7)));

        JLabel songsAdded = new JLabel("Songs in your playlist");
        songsAdded.setFont(new Font("Serif", Font.PLAIN, 14));
        JLabel songsNotAdded = new JLabel("Choose songs to add");
        songsNotAdded.setFont(new Font("Serif", Font.PLAIN, 14));


        addSongButton = new JButton("Add songs");
        addSongButton.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .32)));
        addSongButton.setFont(new Font("Serif", Font.PLAIN, 14));
        addSongButton.addActionListener(this);
        addSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteSongButton = new JButton("Delete songs");
        deleteSongButton.setPreferredSize(new Dimension((int) (WIDTH * .17), (int) (HEIGHT * .32)));
        deleteSongButton.setFont(new Font("Serif", Font.PLAIN, 14));
        deleteSongButton.addActionListener(this);
        deleteSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .06)));

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * .7)));
        sidePanel.add(emptyPanel);
        sidePanel.add(addSongButton);
        sidePanel.add(deleteSongButton);
        sidePanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel subMainPanelLeft = new JPanel();
        subMainPanelLeft.setPreferredSize(new Dimension((int) (WIDTH * 0.47), (int) (HEIGHT * .7)));
        subMainPanelLeft.setAlignmentY(Component.CENTER_ALIGNMENT);
        subMainPanelLeft.add(songsAdded, BorderLayout.NORTH);
        subMainPanelLeft.add(scrollPanelLeft, BorderLayout.SOUTH);

        JPanel subMainPanelRight = new JPanel();
        subMainPanelRight.setPreferredSize(new Dimension((int) (WIDTH * 0.33), (int) (HEIGHT * .7)));
        subMainPanelRight.setAlignmentY(Component.CENTER_ALIGNMENT);
        subMainPanelRight.add(songsNotAdded, BorderLayout.NORTH);
        subMainPanelRight.add(scrollPanelRight, BorderLayout.SOUTH);

        mainPanel = new JPanel();
        mainPanel.add(subMainPanelLeft, BorderLayout.WEST);
        mainPanel.add(subMainPanelRight, BorderLayout.EAST);
//        mainPanel.add(songsNotAdded, BorderLayout.NORTH);
//        mainPanel.add(scrollPanelRight, BorderLayout.EAST);


        playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initPauseButton());

        bottomMainPanel = new JPanel();
        bottomMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        bottomMainPanel.add(playPausePanel);

        frame.add(bottomMainPanel, BorderLayout.SOUTH);
        frame.add(mainPanel, BorderLayout.WEST);
        frame.add(sidePanel, BorderLayout.EAST);
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
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        } else if (e.getSource() == back) {
            frame.dispose();
            AllPlaylistsMenuFrame allPlaylistsMenuFrame = new AllPlaylistsMenuFrame(app);
            System.out.println("playlist menu");
        }
        if (e.getSource() == playButton) {
            app.getSongThread().startPlaying(playlist.getSongsInPlaylist());
        } else if (e.getSource() == pauseButton) {
            app.getSongThread().stopPlaying();
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public MusicApp getApp() {
        return app;
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
//            saveLoadLabel.setText("");

            int size = mainSongListModel.getSize();

            if (size == 0) {
//                deleteButton.setEnabled(false);

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
    //This listener is shared by the text field and the hire button.
    class AddButtonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        private Playlists playlistList;

        public AddButtonListener(JButton button, Playlists playlistList) {
            this.button = button;
            this.playlistList = playlistList;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
//            String name = inputPlaylistNameForm.getText();
//            Playlist playlist = new Playlist(name);
//            listModel.addElement(playlist.getPlaylistName());
//            playlistList.addPlaylist(playlist);
//            saveLoadLabel.setText("");


            int index = mainSongList.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            //Reset the text field.
//            inputPlaylistNameForm.requestFocusInWindow();
//            inputPlaylistNameForm.setText("");

            //Select the new item and make it visible.
            mainSongList.setSelectedIndex(index);
            mainSongList.ensureIndexIsVisible(index);
        }

//        protected boolean alreadyInList(String name) {
//            return listModel.contains(name);
//        }

        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


}

