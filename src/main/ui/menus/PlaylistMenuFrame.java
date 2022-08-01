package ui.menus;

import model.Playlist;
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

public class PlaylistMenuFrame extends javax.swing.JFrame implements ActionListener, ListSelectionListener {
    JButton button1;
    JButton button2;
    JButton button3;

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

    private final JFrame frame;
    JPanel bottomMainPanel;
    JButton addButton;
    JButton deleteButton;
    private JTextField playlistName;


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
//        frame.getContentPane().setBackground(new Color(234, 231, 226));
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

        for (int i = 0; i < app.getAllPlaylists().getPlaylistsSize(); i++) {
            String title = app.getAllPlaylists().getPlaylist(i).getPlaylistName();
            listModel.addElement(title);
        }

        // Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        list.setFont(new Font("Serif", Font.PLAIN, 14));

        frame.add(list);

        addButton = new JButton(addPlaylistString);
        AddButtonListener addButtonListener = new AddButtonListener(addButton);
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

        playlistName = new JTextField("name your playlist...",24);
        playlistName.setFont(new Font("Serif", Font.PLAIN, 14));
        playlistName.setPreferredSize(new Dimension(WIDTH / 2, (int) (HEIGHT * .08)));
        playlistName.addActionListener(addButtonListener);
        playlistName.getDocument().addDocumentListener(addButtonListener);

        bottomMainPanel = new JPanel();
        bottomMainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * .15)));
        bottomMainPanel.add(playlistName);
        bottomMainPanel.add(addButton);
        bottomMainPanel.add(deleteButton);

//        frame.add(topMainPanel, BorderLayout.NORTH);
        frame.add(bottomMainPanel, BorderLayout.SOUTH);
        // TODO: implement delete playlist
        // TODO: implement add playlist
        // TODO: implement open playlist
    }

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

    //This listener is shared by the text field and the hire button.
    class AddButtonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddButtonListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = playlistName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                playlistName.requestFocusInWindow();
                playlistName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.addElement(playlistName.getText());

            //Reset the text field.
            playlistName.requestFocusInWindow();
            playlistName.setText("");

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        } else if (e.getSource() == loadMenu) {
            app.loadPlaylists();
            System.out.println("load playlists");
        } else if (e.getSource() == saveMenu) {
            app.savePlaylists();
        } else if (e.getSource() == button1) {
            System.out.println("songs");
        } else if (e.getSource() == button2) {
            System.out.println("playlists");
        } else if (e.getSource() == button3) {
            if (text.equals("Play")) {
                System.out.println("play");
                button3.setText("Paused");
                text = "Paused";
            } else {
                System.out.println("pause");
                button3.setText("Play");
                text = "Play";
            }
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
