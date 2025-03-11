package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Menu extends JPanel {
    private JPanel adventurePanel;
    private JTextField nameField;
    private Image backgroundImage;
    private JPanel settingsButton;
    private JPanel settingsPanel;
    private CustomButton startMusic;
    private JSlider volumeSlider;
    private JLayeredPane layeredPane;

    private static final String USER_NAME = "username.txt";
    private static final String USER_LEVEL = "userlevel.txt";
    private static final String USER_VOLUME = "uservolume.txt";

    private String username;
    private int volumegame;
    private String fileImgPlay;
    private boolean isPlay;
    private Sound sound;

    public Menu(JFrame parentFrame) {
        URL imageUrl = getClass().getResource("/Image/menuBG1.png");
        backgroundImage = new ImageIcon(imageUrl).getImage();
        setLayout(null);

        sound = new Sound();
        try {
            loadVolumeGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        layeredPane = parentFrame.getLayeredPane();

        adventurePanel = new JPanel();
        adventurePanel.setBounds(450, 130, 180, 50);
        adventurePanel.setOpaque(false);

        adventurePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                URL imageUrl = getClass().getResource("/Image/menuBG2.png");
                backgroundImage = new ImageIcon(imageUrl).getImage();
                repaint();

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GameWindow.showLevelSelection();
                    }
                }, 1000);
            }
        });
        add(adventurePanel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameField.setForeground(Color.WHITE);
        nameField.setBounds(22, 30, 210, 30);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setOpaque(false);
        nameField.setBorder(null);
        DefaultCaret caret = new DefaultCaret() {
            @Override
            public void setVisible(boolean e) {
                super.setVisible(false);
            }
        };
        nameField.setCaret(caret);

        try {
            loadUsername();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nameField.addActionListener(e -> {
            username = nameField.getText();
            try {
                saveUsername();
                saveGameLevel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(nameField);

        settingsButton = new JPanel();
        settingsButton.setBounds(25, 390, 60, 60);
        settingsButton.setOpaque(false);
        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingsPanel.setVisible(true);
            }
        });
        add(settingsButton);

        settingsPanel = new CustomPanel();
        settingsPanel.setBounds(280, 100, 336, 286);
        settingsPanel.setLayout(null);
        settingsPanel.setOpaque(false);
        settingsPanel.setVisible(false);

        JPanel backButton = new JPanel();
        backButton.setBounds(292, 4, 40, 40);
        backButton.setOpaque(false);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingsPanel.setVisible(false);
            }
        });
        settingsPanel.add(backButton);

        volumeSlider = new JSlider(0, 100,  volumegame);
        volumeSlider.setBounds(120, 80, 180, 50);
        volumeSlider.setOpaque(false);
        volumeSlider.setFocusable(false);
        volumeSlider.setUI(new CustomSliderUI(volumeSlider));
        volumeSlider.addChangeListener(e -> {
            volumegame = volumeSlider.getValue();
            try {
                saveVolumeGame(isPlay,volumegame);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            sound.setVolume(volumegame);
        });
        settingsPanel.add(volumeSlider);

        fileImgPlay = isPlay ? "/Image/pause.png" : "/Image/play.png";
        startMusic = new CustomButton(fileImgPlay);
        if (isPlay)
        {
            startMusic.setBounds(56, 82, 40, 40);
            sound.playBackgroundMusic();
            sound.setVolume(volumegame);
        }
        else
            startMusic.setBounds(60, 82, 40, 40);
        startMusic.setOpaque(false);
        startMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPlay)
                {
                    startMusic.setBounds(60, 82, 40, 40);
                    try {
                        saveVolumeGame(false,volumegame);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    sound.stopBackgroundMusic();
                }
                else
                {
                    startMusic.setBounds(56, 82, 40, 40);
                    try {
                        saveVolumeGame(true,volumegame);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    sound.playBackgroundMusic();
                    sound.setVolume(volumegame);
                }
                isPlay = !isPlay;
                fileImgPlay = isPlay ? "/Image/pause.png" : "/Image/play.png";
                startMusic.setImage(fileImgPlay);
                startMusic.repaint();
            }
        });
        settingsPanel.add(startMusic);

        layeredPane.add(settingsPanel, JLayeredPane.MODAL_LAYER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void saveUsername() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(USER_NAME));
        writer.write(username);
        writer.close();
    }

    private void loadUsername() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(USER_NAME));
        username = reader.readLine();
        if (username != null)
            nameField.setText(username);
        reader.close();
    }

    private void saveGameLevel() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(USER_LEVEL));
        writer.write("1");
        writer.close();
    }

    private void saveVolumeGame(boolean isPlaying, int volume) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_VOLUME))) {
            writer.write(String.valueOf(isPlaying));
            writer.newLine();
            writer.write(String.valueOf(volume));
        }
    }

    private void loadVolumeGame() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_VOLUME))) {
            String state = reader.readLine();
            String temp = reader.readLine();
            if (state != null && temp != null) {
                isPlay = Boolean.parseBoolean(state);
                volumegame = Integer.parseInt(temp);
            } else {
                isPlay = false;
            }
        }
    }

    private class CustomPanel extends JPanel {
        private Image backgroundImage;

        public CustomPanel() {
            URL imageUrl = getClass().getResource("/Image/SettingMenu.png");
            backgroundImage = new ImageIcon(imageUrl).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class CustomSliderUI extends BasicSliderUI {
        private Image thumbImage;
        private Image trackImage;

        public CustomSliderUI(JSlider slider) {
            super(slider);
            thumbImage = new ImageIcon(getClass().getResource("/Image/thumb.png")).getImage();
            trackImage = new ImageIcon(getClass().getResource("/Image/track.png")).getImage();
        }

        @Override
        public void paintThumb(Graphics g) {
            g.drawImage(thumbImage, thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, null);
        }

        @Override
        public void paintTrack(Graphics g) {
            g.drawImage(trackImage, trackRect.x, trackRect.y, trackRect.width, trackRect.height, null);
        }
        @Override
        protected Dimension getThumbSize() {
            return new Dimension(30, 30);
        }
    }

    private class CustomButton extends JPanel {
        private Image backgroundImage;

        public CustomButton(String imagePath) {
            setImage(imagePath);
        }

        public void setImage(String imagePath) {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
