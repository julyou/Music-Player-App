package ui.menus;

import model.Song;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllSongsMenuFrame extends javax.swing.JFrame implements ActionListener, ListSelectionListener {
    JFrame frame = new JFrame();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem mainMenu;

    private final JList<String> list;
    private final DefaultListModel<String> listModel;
    private final MusicApp app;

    // TODO: fix checkstyle error
    AllSongsMenuFrame(MusicApp app) {
        this.app = app;

        frame.setTitle("Your Songs");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);


        frame.setJMenuBar(initMenuBar());

        listModel = new DefaultListModel<>();

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
        list.setFont(new Font("Serif", Font.PLAIN, 16));

        frame.add(list);
    }

    public JMenuBar initMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 18));
        mainMenu = new JMenuItem("Main menu");
        mainMenu.addActionListener(this);
        menuBar.add(file);
        file.add(mainMenu);

        return menuBar;
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
