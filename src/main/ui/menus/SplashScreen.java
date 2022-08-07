package ui.menus;

import ui.MusicApp;

import javax.swing.*;
import java.awt.*;

// Creates splash screen upon loading the music app
public class SplashScreen {
    MusicApp app;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    // EFFECTS: constructs splash screen and displays for 4 seconds before loading application
    public SplashScreen(MusicApp app) {
        this.app = app;
        JFrame frame = new JFrame("Music Player");
        frame.setFont(new Font("Serif", Font.BOLD, 18));
        frame.getContentPane().add(new JLabel(new ImageIcon("data/images/loadingScreen.gif")));
        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(Color.PINK);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        frame.setVisible(false);
        MainMenu mainMenuFrame = new MainMenu(app);
        frame.dispose();
    }
}