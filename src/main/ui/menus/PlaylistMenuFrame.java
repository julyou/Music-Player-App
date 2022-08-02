package ui.menus;

import model.Playlist;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaylistMenuFrame extends javax.swing.JFrame implements ActionListener, ListSelectionListener {
    JButton button1;
    JButton button2;
    JButton button3;

    JPanel sidePanel;
    JButton openButton;

    ArrayList<Playlist> playlists;

    JScrollPane scrollPanel;

    MusicApp app;

    String text;

    private static final int WIDTH = 700;
    private static final int HEIGHT = 450;
    private static final String addPlaylistString = "Add Playlist";
    private static final String deletePlaylistString = "Delete Playlist";

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem saveMenu;
    private final JMenuItem loadMenu;
    private final JMenuItem mainMenu;

    JButton saveButton;
    JButton loadButton;

    private final JFrame frame;
    JPanel bottomPanel;
    JPanel mainPanel;
    JButton addButton;
    JButton deleteButton;
    private JTextField inputPlaylistNameForm;


    private final JList<String> list;
    private final DefaultListModel<String> listModel;

    // TODO: checkstyle fix
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    PlaylistMenuFrame(MusicApp app) {
        frame = new JFrame();
        this.app = app;
        frame.setTitle("Playlists");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(true);

//        topMainPanel.setLayout(new GridLayout(3, 3, 0, 0));

        menuBar = new JMenuBar();
//        menuBar.setOpaque(true);
//        menuBar.setBackground(new Color(201, 181, 144));

        file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 16));
//        file.setBackground(new Color(201, 181, 144));
        saveMenu = new JMenuItem("Save");
        loadMenu = new JMenuItem("Load");
        mainMenu = new JMenuItem("Main menu");
        saveMenu.addActionListener(this);
        loadMenu.addActionListener(this);
        mainMenu.addActionListener(this);

        menuBar.add(file);
        file.add(saveMenu);
        file.add(loadMenu);
        file.add(mainMenu);
        frame.setJMenuBar(menuBar);

        listModel = new DefaultListModel<>();
        for (Playlist p : app.getAllPlaylists().getPlaylists()) {
            listModel.addElement(p.getPlaylistName());
        }
//        for (int i = 0; i < app.getAllPlaylists().getPlaylistsSize(); i++) {
//            Playlist title = app.getAllPlaylists().getPlaylist(i);
////            String title = app.getAllPlaylists().getPlaylist(i).getPlaylistName();
//            listModel.addElement(title);
//        }

        // Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFont(new Font("Serif", Font.PLAIN, 14));

        scrollPanel = new JScrollPane(list);
        scrollPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.77), (int) (HEIGHT * 0.7)));

//        frame.add(list);

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

        openButton = new JButton("View playlist");
        openButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .32)));
        openButton.setFont(new Font("Serif", Font.PLAIN, 14));
        openButton.addActionListener(new ViewListener());
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton = new JButton("Save playlists");
        saveButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        saveButton.setFont(new Font("Serif", Font.PLAIN, 14));
        saveButton.addActionListener(this);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadButton = new JButton("Load playlists");
        loadButton.setPreferredSize(new Dimension(WIDTH / 5, (int) (HEIGHT * .18)));
        loadButton.setFont(new Font("Serif", Font.PLAIN, 14));
        loadButton.addActionListener(this);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPlaylistNameForm = new JTextField("", 27);
        inputPlaylistNameForm.setFont(new Font("Serif", Font.PLAIN, 14));
        inputPlaylistNameForm.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .09)));
        inputPlaylistNameForm.getDocument().addDocumentListener(addButtonListener);
        inputPlaylistNameForm.addActionListener(addButtonListener);

        sidePanel = new JPanel();
//        sidePanel.setLayout(new BoxLayout());
        sidePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.2), (int) (HEIGHT * 0.82)));
        sidePanel.add(openButton);
        sidePanel.add(saveButton);
        sidePanel.add(loadButton);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .15)));
        bottomPanel.add(inputPlaylistNameForm);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.76), (int) (HEIGHT * .82)));
        mainPanel.add(scrollPanel, BorderLayout.WEST);

        JPanel topMainPanel = new JPanel();
        topMainPanel.setPreferredSize(new Dimension((int) (WIDTH), (int) (HEIGHT * .85)));
        topMainPanel.add(mainPanel, BorderLayout.WEST);
        topMainPanel.add(sidePanel, BorderLayout.EAST);

        frame.add(topMainPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    // based on ListDemoProject
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);

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
        private ArrayList<Playlist> playlistList;

        public AddButtonListener(JButton button, ArrayList<Playlist> playlistList) {
            this.button = button;
            this.playlistList = playlistList;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = inputPlaylistNameForm.getText();
            Playlist playlist = new Playlist(name);
            listModel.addElement(playlist.getPlaylistName());
            playlistList.add(playlist);


            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                inputPlaylistNameForm.requestFocusInWindow();
                inputPlaylistNameForm.selectAll();
                return;
            }

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

    // Listens for "View playlist" button press
    class ViewListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: on button press, change frame to song menu frame
        public void actionPerformed(ActionEvent e) {
            String playlistName = list.getSelectedValue();
            SongMenuFrame songMenuFrame = new SongMenuFrame(app, new Playlist(playlistName));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        } else if (e.getSource() == loadButton) {
            app.loadPlaylists();
            frame.dispose();
            PlaylistMenuFrame playlistMenuFrame = new PlaylistMenuFrame(app);
            System.out.println("load playlists");
        } else if (e.getSource() == saveButton) {
            app.savePlaylists();
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
