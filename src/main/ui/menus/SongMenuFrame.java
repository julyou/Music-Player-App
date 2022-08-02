package ui.menus;

import model.Playlist;
import model.Playlists;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SongMenuFrame extends JFrame implements ActionListener, ListSelectionListener {
    JFrame frame = new JFrame();

    private static final int WIDTH = 700;
    private static final int HEIGHT = 450;

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem mainMenu;
    private final JMenuItem back;

    private final JList<String> list;
    private final DefaultListModel<String> listModel;
    private final MusicApp app;


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    SongMenuFrame(MusicApp app, Playlist playlist) {
        this.app = app;

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
        menuBar.add(file);
        file.add(mainMenu);
        file.add(back);

        frame.setJMenuBar(menuBar);


        listModel = new DefaultListModel<>();

        //TODO: connect with playlist
        for (Song s : app.getAllSongs()) {
            String title = s.getSongTitle();
            listModel.addElement(title);
        }

        //Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);

        frame.add(list);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        } else if (e.getSource() == back) {
            frame.dispose();
            PlaylistMenuFrame playlistMenuFrame = new PlaylistMenuFrame(app);
            System.out.println("playlist menu");
        }
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

