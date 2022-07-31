package ui.menus;

import model.Playlist;
import ui.MusicApp;

import javax.swing.*;
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

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem saveMenu;
    private final JMenuItem loadMenu;
    private final JMenuItem mainMenu;
    private final JFrame frame;

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
        frame.getContentPane().setBackground(new Color(234, 231, 226));
        frame.setResizable(true);
        frame.setLayout(new GridLayout(3, 3, 0, 0));

        menuBar = new JMenuBar();

        file = new JMenu("File");
        saveMenu = new JMenuItem("Save");
        loadMenu = new JMenuItem("Load");
        mainMenu = new JMenuItem("Main menu");

        menuBar.add(file);

        file.add(saveMenu);
        file.add(loadMenu);
        file.add(mainMenu);

        frame.setJMenuBar(menuBar);

        saveMenu.addActionListener(this);
        loadMenu.addActionListener(this);
        mainMenu.addActionListener(this);

//        this.add(new JButton("1"));
//        this.add(new JButton("2"));
//        this.add(new JButton("3"));
//        this.add(new JButton("4"));
//        this.add(new JButton("5"));
//        this.add(new JButton("6"));

        listModel = new DefaultListModel<>();

        for (int i = 0; i < app.getAllPlaylists().getPlaylistsSize(); i++) {
            String title = app.getAllPlaylists().getPlaylist(i).getPlaylistName();
            listModel.addElement(title);
        }

        //Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);

        frame.add(list);

        // TODO: implement delete playlist
        // TODO: implement add playlist
        // TODO: implement open playlist
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
            System.out.println("main menu");
        } else if (e.getSource() == loadMenu) {
            System.out.println("load playlists");
        } else if (e.getSource() == saveMenu) {
            System.out.println("save playlists");
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
