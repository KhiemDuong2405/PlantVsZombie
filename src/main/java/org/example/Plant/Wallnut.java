package org.example.Plant;

import javax.swing.*;
import java.awt.*;

public class Wallnut extends Plant {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SUN_COST = 50;
    private static final int WALLNUT_HEALTH = 300;

    public Wallnut(int x, int y) {
        super(x, y, "/Image/plants/Wallnut.gif", WALLNUT_HEALTH);
    }

    public static int getSunCost() {
        return SUN_COST;
    }

    @Override
    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, WIDTH, HEIGHT, component);
    }
}