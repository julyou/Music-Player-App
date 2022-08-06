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

// represents list of all playlists in app with save and load options
public class AllPlaylistsMenuFrame implements ActionListener, ListSelectionListener {

    private final MusicApp app;
    private Playlists playlists;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    private JFrame frame;
    private JMenuItem mainMenu;
    private JButton addButton;
    private final JTextField inputPlaylistNameForm;
    private final JLabel saveLoadLabel;

    private AddButtonListener addButtonListener;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;
    private static final String addPlaylistString = "Add Playlist";
    private static final String deletePlaylistString = "Delete Playlist";
    private static final String JSON_STORE = "data/playlists.json";
    private static final int FONT_SIZE = 16;

    // EFFECTS: creates playlist menu
    public AllPlaylistsMenuFrame(MusicApp app, Playlists playlists) {
        this.app = app;

        this.playlists = new Playlists();
        for (Playlist p : playlists.getPlaylists()) {
            this.playlists.addPlaylist(p);
        }

        saveLoadLabel = new JLabel("");
        saveLoadLabel.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        saveLoadLabel.setPreferredSize(new Dimension(WIDTH / 6, (int) (HEIGHT * .04)));
        saveLoadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addButtonListener = new AddButtonListener(addButton);
        inputPlaylistNameForm = new JTextField("", 27);
        inputPlaylistNameForm.addActionListener(addButtonListener);
        inputPlaylistNameForm.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        inputPlaylistNameForm.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .1)));
        initJFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates frame with layout and components that opens in center of screen
    private JFrame initJFrame() {
        frame = new JFrame("Playlists");
        frame.setFont(new Font("Serif", Font.PLAIN, 18));
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setJMenuBar(initMenuBar());
        frame.add(initMainPanel(), BorderLayout.NORTH);
        frame.add(initBottomPanel(), BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(Color.orange);
        return frame;
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
        mainMenu = new JMenuItem("Main menu");
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
    // EFFECTS: creates panel with list of playlists on the left and view, save, and load buttons on the right
    private JPanel initMainPanel() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.17), (int) (HEIGHT * .015)));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.76), (int) (HEIGHT * .7)));
        mainPanel.add(initPlaylistScrollPane(), BorderLayout.WEST);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * 0.75)));
        sidePanel.add(emptyPanel);
        sidePanel.add(initViewPlaylistButton());
        sidePanel.add(initSaveButton());
        sidePanel.add(initLoadButton());
        sidePanel.add(saveLoadLabel);

        JPanel topMainPanel = new JPanel();
        topMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .75)));
        topMainPanel.add(mainPanel, BorderLayout.WEST);
        topMainPanel.add(sidePanel, BorderLayout.EAST);

        return topMainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates panel with text field allowing user to name a new playlist, and add and delete playlist buttons
    public JPanel initBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .14)));
        bottomPanel.add(inputPlaylistNameForm);
        bottomPanel.add(initAddButton());
        bottomPanel.add(initDeleteButton());
        return bottomPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds playlists into a scroll pane
    public JScrollPane initPlaylistScrollPane() {
        listModel = new DefaultListModel<>();
        for (Playlist p : playlists.getPlaylists()) {
            listModel.addElement(p.getPlaylistName());
        }

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        JScrollPane scrollPanel = new JScrollPane(list);
        scrollPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.77), (int) (HEIGHT * 0.7)));

        return scrollPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates add playlist button
    public JButton initAddButton() {
        addButton = new JButton(addPlaylistString);
        addButton.setActionCommand(addPlaylistString);
        addButton.addActionListener(addButtonListener);
        addButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        addButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        addButton.setEnabled(true);

        return addButton;
    }

    // MODIFIES: this
    // EFFECTS: creates delete playlist button
    public JButton initDeleteButton() {
        JButton deleteButton = new JButton(deletePlaylistString);
        deleteButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        deleteButton.setActionCommand(deletePlaylistString);
        deleteButton.addActionListener(new DeleteButtonListener());

        return deleteButton;
    }

    // MODIFIES: this
    // EFFECTS: creates view playlist button
    public JButton initViewPlaylistButton() {
        JButton openButton = new JButton("View playlist");
        openButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .25)));
        openButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        openButton.addActionListener(new ViewListener());
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return openButton;
    }

    // MODIFIES: this
    // EFFECTS: creates save playlists button
    public JButton initSaveButton() {
        JButton saveButton = new JButton("Save playlists");
        saveButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        saveButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        saveButton.addActionListener(new PlaylistsSaveListener());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return saveButton;
    }

    // MODIFIES: this
    // EFFECTS: creates load playlists button
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
    // MODIFIES: this
    // EFFECTS: listens for delete playlist button click and removes playlist from displayed list and playlists
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
    // MODIFIES: this
    // EFFECTS: listens for delete playlist button click and removes playlist from displayed list and playlists
    class AddButtonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddButtonListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = inputPlaylistNameForm.getText();
            Playlist playlist = new Playlist(name);
            playlists.addPlaylist(playlist);
            saveLoadLabel.setText("");


            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                inputPlaylistNameForm.requestFocusInWindow();
                inputPlaylistNameForm.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listModel.addElement(inputPlaylistNameForm.getText());

            // Reset the text field.
            inputPlaylistNameForm.requestFocusInWindow();
            inputPlaylistNameForm.setText("");

            // Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
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

    // MODIFIES: this
    // EFFECTS: listens for "view playlist" button click and opens song menu window with songs in playlist
    class ViewListener implements ActionListener {
        private AllPlaylistsMenuFrame allPlaylistsMenuFrame;

        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedValue() != null) {
                String playlistName = list.getSelectedValue();
                frame.dispose();
                int index = 0;
                for (String s : playlists.getPlaylistsNames()) {
                    if (s.equals(playlistName)) {
                        SongMenuFrame songMenuFrame = new SongMenuFrame(app, playlists.getPlaylist(index), playlists);
                    }
                    index++;
                }
            }
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: listens for "main menu" click and returns user to main menu
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: listens for "save playlists" button click and saves playlists and songs to JSON file
    private class PlaylistsSaveListener implements ActionListener {

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

    // MODIFIES: this
    // EFFECTS: listens for "load playlists" button click and loads playlists and songs to display
    private class PlaylistsLoadListener implements ActionListener {

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
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                initDeleteButton().setEnabled(false);
            } else {
                initDeleteButton().setEnabled(true);
            }
        }
    }
}