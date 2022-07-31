package ui;

import ui.menus.MainMenuFrame;

import java.net.MalformedURLException;

// Creates a Music player application
public class Main {
    // Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    public static void main(String[] args) throws MalformedURLException {
        AppLabel label = new AppLabel();
        label.setText("Welcome!");
        label.setBounds(0, 0, 250, 250);

        new MusicApp();
    }
}
