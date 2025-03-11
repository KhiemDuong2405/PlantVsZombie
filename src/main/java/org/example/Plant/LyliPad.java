package org.example.Plant;

import org.example.GamePanel3;

import javax.swing.*;
import java.awt.*;

public class LyliPad extends Plant {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SUN_COST = 25;
    private static final int LyliPad_HEALTH = 80;


    public LyliPad(int x, int y) {
        super(x, y, "/Image/plants/lylipad.png", LyliPad_HEALTH);
    }

    public static int getSunCost() {
        return SUN_COST;
    }
    @Override
    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, WIDTH, HEIGHT, component);
    }
}
