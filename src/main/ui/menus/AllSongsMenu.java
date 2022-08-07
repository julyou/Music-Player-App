package ui.menus;

import ui.MusicApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents menu displaying all songs in the app with song title, artist, and duration
public class AllSongsMenu extends JPanel implements ActionListener, ListSelectionListener {
    private final JFrame frame = new JFrame();
    private JMenuItem mainMenu;
    private final MusicApp app;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;
    private static final int FONT_SIZE = 16;

    // EFFECTS: creates all songs menu in table layout
    public AllSongsMenu(MusicApp app) {
        super(new GridLayout(1, 0));
        this.app = app;

        JScrollPane scrollPane = new JScrollPane(initSongTable());

        frame.setTitle("All Songs");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setJMenuBar(initMenuBar());
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(Color.PINK);
    }

    // MODIFIES: this
    // EFFECTS: fills table with songs information, including song title, artist, and duration
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public JTable initSongTable() {
        String[] columnNames = {"#", "Song", "Artist", "Duration (sec)"};

        Object[][] data = {
                {1, app.getAllSongs().get(0).getSongTitle(), app.getAllSongs().get(0).getArtist(),
                        app.getAllSongs().get(0).getSongDuration()},
                {2, app.getAllSongs().get(1).getSongTitle(), app.getAllSongs().get(1).getArtist(),
                        app.getAllSongs().get(1).getSongDuration()},
                {3, app.getAllSongs().get(2).getSongTitle(), app.getAllSongs().get(2).getArtist(),
                        app.getAllSongs().get(2).getSongDuration()},
                {4, app.getAllSongs().get(3).getSongTitle(), app.getAllSongs().get(3).getArtist(),
                        app.getAllSongs().get(3).getSongDuration()},
                {5, app.getAllSongs().get(4).getSongTitle(), app.getAllSongs().get(4).getArtist(),
                        app.getAllSongs().get(4).getSongDuration()},
                {6, app.getAllSongs().get(5).getSongTitle(), app.getAllSongs().get(5).getArtist(),
                        app.getAllSongs().get(5).getSongDuration()},
                {7, app.getAllSongs().get(6).getSongTitle(), app.getAllSongs().get(6).getArtist(),
                        app.getAllSongs().get(6).getSongDuration()}
        };

        final JTable table = new JTable(data, columnNames);
        table.setRowHeight(26);
        table.getColumnModel().getColumn(0).setMaxWidth((int) (WIDTH * 0.08));
        table.getColumnModel().getColumn(1).setMinWidth((int) (WIDTH * 0.3));
        table.getColumnModel().getColumn(2).setMinWidth((int) (WIDTH * 0.3));
        table.getColumnModel().getColumn(3).setMinWidth((int) (WIDTH * 0.2));
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 18));
        table.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        return table;
    }

    // MODIFIES: this
    // EFFECTS: creates menu bar with main menu submenu
    public JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
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

    @Override
    // MODIFIES: this
    // EFFECTS: processes main menu click and brings user to main menu
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(app);
            System.out.println("main menu");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}