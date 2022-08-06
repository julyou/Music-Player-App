package ui.menus;

import model.Playlist;
import model.Playlists;
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

public class AllPlaylistsMenuFrame implements ActionListener, ListSelectionListener {

    private final MusicApp app;
    private Playlists playlists;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    private final JFrame frame;

    private JMenuItem mainMenu;

    private JButton addButton;
    private final JTextField inputPlaylistNameForm;

    private final JLabel saveLoadLabel;

    AddButtonListener addButtonListener;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;
    private static final String addPlaylistString = "Add Playlist";
    private static final String deletePlaylistString = "Delete Playlist";
    private static final String JSON_STORE = "data/playlists.json";
    private static final int FONT_SIZE = 16;


    public AllPlaylistsMenuFrame(MusicApp app, Playlists playlists) {
        this.app = app;

        frame = new JFrame("Playlists");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(true);

        this.playlists = new Playlists();
        for (Playlist p : playlists.getPlaylists()) {
            this.playlists.addPlaylist(p);
        }

        saveLoadLabel = new JLabel("");
        saveLoadLabel.setPreferredSize(new Dimension(WIDTH / 6, (int) (HEIGHT * .05)));
        saveLoadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addButtonListener = new AddButtonListener(addButton);

        inputPlaylistNameForm = new JTextField("", 27);
        inputPlaylistNameForm.addActionListener(addButtonListener);
        inputPlaylistNameForm.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        inputPlaylistNameForm.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .1)));

        frame.setJMenuBar(initMenuBar());
        frame.add(initMainPanel(), BorderLayout.NORTH);
        frame.add(initBottomPanel(), BorderLayout.SOUTH);
    }

    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        mainMenu = new JMenuItem("Main menu");
        file.setFont(new Font("Serif", Font.PLAIN, 18));
        mainMenu.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        mainMenu.addActionListener(this);
        menuBar.add(file);
        file.add(mainMenu);
        return menuBar;
    }

    private JPanel initMainPanel() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .03)));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.76), (int) (HEIGHT * .7)));
        mainPanel.add(initPlaylistScrollPane(), BorderLayout.WEST);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * 0.75)));
        sidePanel.add(emptyPanel);
        sidePanel.add(initOpenPlaylistButton());
        sidePanel.add(initSaveButton());
        sidePanel.add(initLoadButton());
        sidePanel.add(saveLoadLabel);

        JPanel topMainPanel = new JPanel();
        topMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .75)));
        topMainPanel.add(mainPanel, BorderLayout.WEST);
        topMainPanel.add(sidePanel, BorderLayout.EAST);

        return topMainPanel;
    }

    public JPanel initBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .14)));
        bottomPanel.add(inputPlaylistNameForm);
        bottomPanel.add(initAddButton());
        bottomPanel.add(initDeleteButton());
        return bottomPanel;
    }

    public JScrollPane initPlaylistScrollPane() {
        listModel = new DefaultListModel<>();
        for (Playlist p : playlists.getPlaylists()) {
            listModel.addElement(p.getPlaylistName());
        }

        // Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        JScrollPane scrollPanel = new JScrollPane(list);
        scrollPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.77), (int) (HEIGHT * 0.7)));

        return scrollPanel;
    }

    public JButton initAddButton() {
        addButton = new JButton(addPlaylistString);
        addButton.setActionCommand(addPlaylistString);
        addButton.addActionListener(addButtonListener);
        addButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        addButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        addButton.setEnabled(true);

        return addButton;
    }


    public JButton initDeleteButton() {
        JButton deleteButton = new JButton(deletePlaylistString);
        deleteButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        deleteButton.setActionCommand(deletePlaylistString);
        deleteButton.addActionListener(new DeleteButtonListener());

        return deleteButton;
    }

    public JButton initOpenPlaylistButton() {
        JButton openButton = new JButton("View playlist");
        openButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .25)));
        openButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        openButton.addActionListener(new ViewListener());
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return openButton;
    }

    public JButton initSaveButton() {
        JButton saveButton = new JButton("Save playlists");
        saveButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        saveButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        saveButton.addActionListener(new PlaylistsSaveListener());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return saveButton;
    }

    public JButton initLoadButton() {
        JButton loadButton = new JButton("Load playlists");
        loadButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        loadButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        loadButton.addActionListener(new PlaylistsLoadListener());
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return loadButton;
    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    class DeleteButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            listModel.removeElement(index);
            saveLoadLabel.setText("");
            playlists.removePlaylist(index);
        }
    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    //This listener is shared by the text field and the hire button.
    class AddButtonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private final JButton button;
//        private final Playlists playlistList;

        public AddButtonListener(JButton button) {
            this.button = button;
        }

//        public AddButtonListener(JButton button, Playlists playlistList) {
//            this.button = button;
//            this.playlistList = playlistList;
//        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = inputPlaylistNameForm.getText();
            Playlist playlist = new Playlist(name);
            listModel.addElement(playlist.getPlaylistName());
//            playlistList.addPlaylist(playlist);
            playlists.addPlaylist(playlist);
            saveLoadLabel.setText("");

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            // Reset the text field.
            inputPlaylistNameForm.requestFocusInWindow();
            inputPlaylistNameForm.setText("");

            // Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

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

    // Listens for "View playlist" button press
    class ViewListener implements ActionListener {
        private AllPlaylistsMenuFrame allPlaylistsMenuFrame;

        // MODIFIES: this
        // EFFECTS: on button press, change frame to song menu frame of respective playlist
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedValue() != null) {
                String playlistName = list.getSelectedValue();
                frame.dispose();

//                if (playlistName.equals("Star Wars Soundtrack")) {
//                    SongMenuFrame songMenuFrame = new SongMenuFrame(app, app.getAllPlaylists().getPlaylist(0),
//                    playlists);
//                } else if (playlistName.equals("Instrumental")) {
//                    SongMenuFrame songMenuFrame = new SongMenuFrame(app, app.getAllPlaylists().getPlaylist(1),
//                    playlists);
//                } else if (playlistName.equals("Film scores")) {
//                    SongMenuFrame songMenuFrame = new SongMenuFrame(app, app.getAllPlaylists().getPlaylist(2),
//                    playlists);
//                } else {
                int index = 0;
                for (String s : playlists.getPlaylistsNames()) {
                    if (s.equals(playlistName)) {
                        SongMenuFrame songMenuFrame = new SongMenuFrame(app, playlists.getPlaylist(index), playlists);
                    }
                    index++;
                }
            }
//            }

            System.out.println("null");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        }
    }

    // EFFECTS: listens for save button click
    private class PlaylistsSaveListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: saves playlist list to file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
                jsonWriter.open();
                jsonWriter.writePlaylists(playlists);
                jsonWriter.close();
                saveLoadLabel.setText("Saved!");
            } catch (FileNotFoundException f) {
                saveLoadLabel.setText("Could not save...");

            }
        }
    }

    // EFFECTS: listens for load button click
    private class PlaylistsLoadListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: loads playlist list from file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                listModel.removeAllElements();
                JsonReader jsonReader = new JsonReader(JSON_STORE);
                playlists = jsonReader.readPlaylists();
                for (Playlist p : playlists.getPlaylists()) {
                    listModel.addElement(p.getPlaylistName());
                }
                saveLoadLabel.setText("Loaded!");
            } catch (Exception ex) {
                saveLoadLabel.setText("Could not load...");
            }

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
