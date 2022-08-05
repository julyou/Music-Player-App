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

    private MusicApp app;
    private Playlists playlists;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    private final JFrame frame;
    private JPanel bottomPanel;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel topMainPanel;

    private JScrollPane scrollPanel;

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem mainMenu;

    private JButton addButton;
    private JButton deleteButton;
    private JTextField inputPlaylistNameForm;

    private JButton openButton;
    private JButton saveButton;
    private JButton loadButton;
    private JLabel saveLoadLabel;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;
    private static final String addPlaylistString = "Add Playlist";
    private static final String deletePlaylistString = "Delete Playlist";
    private static final String JSON_STORE = "data/playlists.json";


    // TODO: checkstyle fix
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public AllPlaylistsMenuFrame(MusicApp app) {
        this.app = app;

        frame = new JFrame("Playlists");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(true);

        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 16));
        mainMenu = new JMenuItem("Main menu");
        mainMenu.addActionListener(this);
        menuBar.add(file);
        file.add(mainMenu);

        listModel = new DefaultListModel<>();
        for (Playlist p : app.getAllPlaylists().getPlaylists()) {
            listModel.addElement(p.getPlaylistName());
        }

        playlists = new Playlists();
        for (Playlist p : app.getAllPlaylists().getPlaylists()) {
            playlists.addPlaylist(p);
        }

        // Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFont(new Font("Serif", Font.PLAIN, 14));
        scrollPanel = new JScrollPane(list);
        scrollPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.77), (int) (HEIGHT * 0.7)));

        addButton = new JButton(addPlaylistString);
        AddButtonListener addButtonListener = new AddButtonListener(addButton, playlists);
        addButton.setActionCommand(addPlaylistString);
        addButton.addActionListener(addButtonListener);
        addButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        addButton.setFont(new Font("Serif", Font.PLAIN, 14));
        addButton.setEnabled(false);

        deleteButton = new JButton(deletePlaylistString);
        deleteButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, 14));
        deleteButton.setActionCommand(deletePlaylistString);
        deleteButton.addActionListener(new DeleteButtonListener());

        inputPlaylistNameForm = new JTextField("", 27);
        inputPlaylistNameForm.setFont(new Font("Serif", Font.PLAIN, 14));
        inputPlaylistNameForm.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .1)));
        inputPlaylistNameForm.addActionListener(addButtonListener);
        inputPlaylistNameForm.getDocument().addDocumentListener(addButtonListener);

        openButton = new JButton("View playlist");
        openButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .25)));
        openButton.setFont(new Font("Serif", Font.PLAIN, 14));
        openButton.addActionListener(new ViewListener());
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton = new JButton("Save playlists");
        saveButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        saveButton.setFont(new Font("Serif", Font.PLAIN, 14));
        saveButton.addActionListener(new PlaylistsSaveListener());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadButton = new JButton("Load playlists");
        loadButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        loadButton.setFont(new Font("Serif", Font.PLAIN, 14));
        loadButton.addActionListener(new PlaylistsLoadListener());
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveLoadLabel = new JLabel("");
        saveLoadLabel.setPreferredSize(new Dimension(WIDTH / 6, (int) (HEIGHT * .05)));
        saveLoadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * 0.75)));
        sidePanel.add(openButton);
        sidePanel.add(saveButton);
        sidePanel.add(loadButton);
        sidePanel.add(saveLoadLabel);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .14)));
        bottomPanel.add(inputPlaylistNameForm);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.76), (int) (HEIGHT * .7)));
        mainPanel.add(scrollPanel, BorderLayout.WEST);

        topMainPanel = new JPanel();
        topMainPanel.setPreferredSize(new Dimension((int) (WIDTH), (int) (HEIGHT * .75)));
        topMainPanel.add(mainPanel, BorderLayout.WEST);
        topMainPanel.add(sidePanel, BorderLayout.EAST);

        frame.setJMenuBar(menuBar);
        frame.add(topMainPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            saveLoadLabel.setText("");

            int size = listModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);

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
            String name = inputPlaylistNameForm.getText();
            Playlist playlist = new Playlist(name);
            listModel.addElement(playlist.getPlaylistName());
            playlistList.addPlaylist(playlist);
            saveLoadLabel.setText("");

            //User didn't type in a unique name...
//            if (name.equals("") || alreadyInList(name)) {
//                Toolkit.getDefaultToolkit().beep();
//                inputPlaylistNameForm.requestFocusInWindow();
//                inputPlaylistNameForm.selectAll();
//                return;
//            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            //Reset the text field.
            inputPlaylistNameForm.requestFocusInWindow();
            inputPlaylistNameForm.setText("");

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

    // Listens for "View playlist" button press
    class ViewListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: on button press, change frame to song menu frame of respective playlist
        public void actionPerformed(ActionEvent e) {
            String playlistName = list.getSelectedValue();
            frame.dispose();
            if (list.getSelectedValue().equals("Star Wars Soundtrack")) {
                SongMenuFrame songMenuFrame = new SongMenuFrame(app, app.getAllPlaylists().getPlaylist(0));
            } else if (list.getSelectedValue().equals("Instrumental")) {
                SongMenuFrame songMenuFrame = new SongMenuFrame(app, app.getAllPlaylists().getPlaylist(1));
            } else if (list.getSelectedValue().equals("Film scores")) {
                SongMenuFrame songMenuFrame = new SongMenuFrame(app, app.getAllPlaylists().getPlaylist(2));
            } else {
                SongMenuFrame songMenuFrame = new SongMenuFrame(app, new Playlist(playlistName));
            }
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
                    list = new JList<>(listModel);
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
