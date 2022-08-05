package ui.menus;

import model.Song;
import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllSongsMenuFrame extends JPanel implements ActionListener, ListSelectionListener {
    JFrame frame = new JFrame();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem mainMenu;

    private final MusicApp app;

    private static final int FONT_SIZE = 16;

    public AllSongsMenuFrame(MusicApp app) {
        super(new GridLayout(1, 0));

        this.app = app;

        frame.setTitle("Your Songs");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setJMenuBar(initMenuBar());

        JScrollPane scrollPane = new JScrollPane(initSongTable());
        frame.add(scrollPane);
    }

    public JTable initSongTable() {
        String[] columnNames = {"Song", "Artist", "Duration (seconds)"};

        Object[][] data = {
                {app.getAllSongs().get(0).getSongTitle(), app.getAllSongs().get(0).getArtist(),
                        app.getAllSongs().get(0).getSongDuration()},
                {app.getAllSongs().get(1).getSongTitle(), app.getAllSongs().get(1).getArtist(),
                        app.getAllSongs().get(1).getSongDuration()},
                {app.getAllSongs().get(2).getSongTitle(), app.getAllSongs().get(2).getArtist(),
                        app.getAllSongs().get(2).getSongDuration()},
                {app.getAllSongs().get(3).getSongTitle(), app.getAllSongs().get(3).getArtist(),
                        app.getAllSongs().get(3).getSongDuration()},
                {app.getAllSongs().get(4).getSongTitle(), app.getAllSongs().get(4).getArtist(),
                        app.getAllSongs().get(4).getSongDuration()},
                {app.getAllSongs().get(5).getSongTitle(), app.getAllSongs().get(5).getArtist(),
                        app.getAllSongs().get(5).getSongDuration()},
                {app.getAllSongs().get(6).getSongTitle(), app.getAllSongs().get(6).getArtist(),
                        app.getAllSongs().get(6).getSongDuration()}
        };

        final JTable table = new JTable(data, columnNames);
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 18));
        table.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return table;
    }

    public JMenuBar initMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setFont(new Font("Serif", Font.PLAIN, 18));
        mainMenu = new JMenuItem("Main menu");
        mainMenu.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
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
