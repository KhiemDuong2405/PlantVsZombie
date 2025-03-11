package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;

public class Level extends JPanel {
    private JButton adventureButton1, adventureButton2, adventureButton3;
    private JPanel back;
    private Image backgroundImage;
    private static int Lv;
    public Level() {
        URL imageUrl = getClass().getResource("/Image/menulevel.png");
        backgroundImage = new ImageIcon(imageUrl).getImage();

        setLayout(null);

        adventureButton1 = createCustomButton("DAY", 235, 240, 100, 40);
        adventureButton2 = createCustomButton("NIGHT", 380, 257, 140, 40);
        adventureButton3 = createCustomButton("POOL", 570, 220, 100, 40);

        switch (getLv()){
            case 2:
            {
                adventureButton1.setForeground(Color.WHITE);
                adventureButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameWindow.begin(1);
                    }
                });
                adventureButton2.setForeground(Color.WHITE);
                adventureButton2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameWindow.begin(2);
                    }
                });
                break;
            }
            case 3:
            {
                adventureButton1.setForeground(Color.WHITE);
                adventureButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameWindow.begin(1);
                    }
                });
                adventureButton2.setForeground(Color.WHITE);
                adventureButton2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameWindow.begin(2);
                    }
                });
                adventureButton3.setForeground(Color.WHITE);
                adventureButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameWindow.begin(3);
                    }
                });
                break;
            }
            default:
            {
                adventureButton1.setForeground(Color.WHITE);
                adventureButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameWindow.begin(1);
                    }
                });
                break;
            }
        }

        back = new JPanel();
        back.setBounds(5, 410, 55, 55);
        back.setOpaque(false);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameWindow.showMenu();
            }
        });

        add(adventureButton1);
        add(adventureButton2);
        add(adventureButton3);
        add(back);
    }

    private JButton createCustomButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/Font/ReenieBeanie.ttf").getFile())).deriveFont(36f);
            button.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            button.setFont(new Font("Arial", Font.BOLD, 24)); // fallback font
        }
        button.setContentAreaFilled(false); // Làm cho nền trong suốt
        button.setBorderPainted(false); // Tắt vẽ đường viền nút
        button.setOpaque(false); // Làm cho nút hoàn toàn trong suốt
        button.setForeground(Color.BLACK); // Màu chữ
        button.setFocusPainted(false); // Tắt viền focus
        return button;
    }

    public int getLv() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userlevel.txt"));
            String userlevel = reader.readLine();
            Lv = Integer.parseInt(userlevel);
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Lv;
    }

    public static void setLv(int lv) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("userlevel.txt"));
            writer.write(String.valueOf(lv));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}