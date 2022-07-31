package ui.menus;

import model.Song;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongMenuFrame extends JFrame implements ActionListener, ListSelectionListener {
    JFrame frame = new JFrame();
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem mainMenu;

    private final JList<String> list;
    private final DefaultListModel<String> listModel;
    private final MusicApp app;

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    SongMenuFrame(MusicApp app) {
        this.app = app;

        frame.setTitle("Songs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(234, 231, 226));

        menuBar = new JMenuBar();

        file = new JMenu("File");
        mainMenu = new JMenuItem("Main menu");

        menuBar.add(file);
        file.add(mainMenu);

        frame.setJMenuBar(menuBar);

        mainMenu.addActionListener(this);

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
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
