package ui.menus;

import ui.MusicApp;

import javax.swing.*;

// Creates splash screen upon loading the music app
public class SplashScreenOpen {
    MusicApp app;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 550;

    // EFFECTS: constructs splash screen and displays for 4 seconds before loading application
    public SplashScreenOpen(MusicApp app) {
        this.app = app;
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel(new ImageIcon("data/images/loadingScreen.gif")));
        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setVisible(true);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        frame.setVisible(false);
        MainMenuFrame mainMenuFrame = new MainMenuFrame(app);
        frame.dispose();
    }
}