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
    private JScrollPane scrollPanel;

    private JPanel bottomMainPanel;
    private JPanel playPausePanel;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;


    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem mainMenu;
    private final JMenuItem back;

    private final JList<String> list;
    private final DefaultListModel<String> listModel;
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

        listModel = new DefaultListModel<>();

        for (Song s : playlist.getSongsInPlaylist()) {
            String title = s.getSongTitle();
            listModel.addElement(title);
        }

        //Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        list.setFont(new Font("Serif", Font.PLAIN, 14));
        scrollPanel = new JScrollPane(list);
        scrollPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.77), (int) (HEIGHT * 0.7)));

        playPausePanel = new JPanel();
        playPausePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        playPausePanel.add(initPlayButton());
        playPausePanel.add(initPauseButton());

        bottomMainPanel = new JPanel();
        bottomMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .17)));
        bottomMainPanel.add(playPausePanel);

        frame.add(bottomMainPanel, BorderLayout.SOUTH);
        frame.add(scrollPanel, BorderLayout.NORTH);
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
            int index = list.getSelectedIndex();
            listModel.remove(index);
//            saveLoadLabel.setText("");

            int size = listModel.getSize();

            if (size == 0) {
//                deleteButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
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


            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            //Reset the text field.
//            inputPlaylistNameForm.requestFocusInWindow();
//            inputPlaylistNameForm.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
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

