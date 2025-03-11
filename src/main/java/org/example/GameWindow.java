package org.example;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;

public class GameWindow extends JFrame {
    static GameWindow gw;

    public GameWindow() {
        setTitle("Plants vs. Zombies");
        setSize(894, 506);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        URL iconURL = getClass().getResource("/Image/logo.png");
        ImageIcon icon = new ImageIcon(iconURL);


        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            try {
                Class<?> appClass = Class.forName("com.apple.eawt.Application");
                Object app = appClass.getDeclaredConstructor().newInstance();
                appClass.getMethod("setDockIconImage", Image.class).invoke(app, icon.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Menu menu = new Menu(this);
        setContentPane(menu);
        setVisible(true);
    }

    public GameWindow(boolean showLevel) {
        setTitle("Plants vs. Zombies");
        setSize(894, 506);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        if (showLevel) {
            Level levelSelection = new Level();
            setContentPane(levelSelection);
        }
        setVisible(true);
    }

    public GameWindow(int level) {
        setTitle("Plants vs. Zombies");
        setSize(894, 506);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JLayeredPane gamePanel;
        switch (level) {
            case 1:
                gamePanel = new GamePanel();
                break;
            case 2:
                gamePanel = new GamePanel2();
                break;
            case 3:
                gamePanel = new GamePanel3();
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
        setContentPane(gamePanel);
        setVisible(true);
    }

    public static void begin(int level) {
        if (gw != null) {
            gw.dispose();
        }
        gw = new GameWindow(level);
    }

    public static void showLevelSelection() {
        if (gw != null) {
            gw.dispose();
        }
        gw = new GameWindow(true);
    }

    public static void showMenu() {
        if (gw != null) {
            gw.dispose();
        }
        gw = new GameWindow();
    }

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Plants vs. Zombies");
        gw = new GameWindow();
        gw.setVisible(true);
    }
}