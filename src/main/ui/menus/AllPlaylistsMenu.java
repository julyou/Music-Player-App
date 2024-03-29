package ui.menus;

import model.Event;
import model.EventLog;
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
import java.awt.event.*;
import java.io.FileNotFoundException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

// represents list of all playlists in app with save and load options
public class AllPlaylistsMenu implements ActionListener, ListSelectionListener {

    private final MusicApp app;
    private Playlists playlists;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    private JFrame frame;
    private JMenuItem mainMenu;
    private JButton addButton;
    private final JTextField inputPlaylistNameForm;
    private final JLabel saveLoadLabel;

    private final AddButtonListener addButtonListener;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;
    private static final String addPlaylistString = "Add Playlist";
    private static final String deletePlaylistString = "Delete Playlist";
    private static final String JSON_STORE = "data/playlists.json";
    private static final int FONT_SIZE = 16;

    // EFFECTS: creates playlist menu
    public AllPlaylistsMenu(MusicApp app, Playlists playlists) {
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
        inputPlaylistNameForm = new JTextField("Input playlist name...", 27);
        inputPlaylistNameForm.addActionListener(addButtonListener);
        inputPlaylistNameForm.setForeground(Color.GRAY);
        inputPlaylistNameForm.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        inputPlaylistNameForm.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .1)));
        inputPlaylistNameListener();

        initJFrame();
    }

    // EFFECTS: grays out default placeholder text until user types in the field, thus changing text
    //          to black until cursor moves outside of text field
    public void inputPlaylistNameListener() {
        inputPlaylistNameForm.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputPlaylistNameForm.getText().trim().equals("Input playlist name...")) {
                    inputPlaylistNameForm.setText("");
                    inputPlaylistNameForm.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputPlaylistNameForm.getText().trim().equals("")) {
                    inputPlaylistNameForm.setText("Input playlist name...");
                    inputPlaylistNameForm.setForeground(Color.GRAY);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates frame with layout and components that opens in center of screen
    private JFrame initJFrame() {
        frame = new JFrame("Playlists");
//        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                printLog();
                System.exit(0);
            }
        });
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setJMenuBar(initMenuBar());
        frame.add(initMainPanel(), BorderLayout.NORTH);
        frame.add(initBottomPanel(), BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.setBackground(Color.PINK);
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

        JLabel playlistTitle = new JLabel("Your playlists: ");
        playlistTitle.setPreferredSize(new Dimension((int) (WIDTH / 1.32), (int) (HEIGHT * 0.035)));
        playlistTitle.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 17));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.76), (int) (HEIGHT * .7)));
        mainPanel.add(playlistTitle, BorderLayout.NORTH);
        mainPanel.add(initPlaylistScrollPane(), BorderLayout.SOUTH);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * 0.7)));
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

        list.addKeyListener(new KeyDeleteListener());
        JScrollPane scrollPanel = new JScrollPane(list);
        scrollPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.77), (int) (HEIGHT * 0.7)));

        return scrollPanel;
    }

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

    // EFFECTS: creates delete playlist button
    public JButton initDeleteButton() {
        JButton deleteButton = new JButton(deletePlaylistString);
        deleteButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .1)));
        deleteButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        deleteButton.setActionCommand(deletePlaylistString);
        deleteButton.addActionListener(new DeleteButtonListener());

        return deleteButton;
    }

    // EFFECTS: creates view playlist button
    public JButton initViewPlaylistButton() {
        JButton openButton = new JButton("View playlist");
        openButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .25)));
        openButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        openButton.addActionListener(new ViewListener());
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return openButton;
    }

    // EFFECTS: creates save playlists button
    public JButton initSaveButton() {
        JButton saveButton = new JButton("Save playlists");
        saveButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        saveButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        saveButton.addActionListener(new PlaylistsSaveListener());
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return saveButton;
    }

    // EFFECTS: creates load playlists button
    public JButton initLoadButton() {
        JButton loadButton = new JButton("Load playlists");
        loadButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        loadButton.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        loadButton.addActionListener(new PlaylistsLoadListener());
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return loadButton;
    }

    // based on ListDemoProject linked below
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

    // based on ListDemoProject linked below
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    // MODIFIES: this
    // EFFECTS: listens for delete playlist button click and removes playlist from displayed list and playlists
    class AddButtonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private final JButton button;

        public AddButtonListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = inputPlaylistNameForm.getText();
            Playlist playlist = new Playlist(name);
            playlists.addPlaylist(playlist);
            saveLoadLabel.setText("");

            // beeps if entered name is not unique
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

    // EFFECTS: listens for "view playlist" button click and opens song menu window of chosen playlist
    class ViewListener implements ActionListener {
        private AllPlaylistsMenu allPlaylistsMenu;

        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedValue() != null) {
                String playlistName = list.getSelectedValue();
                frame.dispose();
                int index = 0;
                for (String s : playlists.getPlaylistsNames()) {
                    if (s.equals(playlistName)) {
                        SongMenu songMenu = new SongMenu(app, playlists.getPlaylist(index), playlists);
                    }
                    index++;
                }
            }
        }
    }

    @Override
    // EFFECTS: enables delete button
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            initDeleteButton().setEnabled(list.getSelectedIndex() != -1);
        }
    }

    // MODIFIES: this
    // EFFECTS: listens for backspace key press and deletes playlist, beeps if invalid key is pressed
    class KeyDeleteListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                int index = list.getSelectedIndex();
                listModel.remove(index);
                listModel.removeElement(index);
                saveLoadLabel.setText("");
                playlists.removePlaylist(index);
            } else if (e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    // EFFECTS: listens for "main menu" click and returns user to main menu
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(app);
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

    // EFFECTS: prints events
    public void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e);
        }
    }
}