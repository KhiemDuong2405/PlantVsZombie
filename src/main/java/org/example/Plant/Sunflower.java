package org.example.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.GamePanel;
import org.example.GamePanel2;
import org.example.GamePanel3;
import org.example.Sun;

public class Sunflower extends Plant {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SUN_COST = 50;
    private static final int SUNFLOWER_HEALTH = 80;
    private static final int SUN_PRODUCE_TIME = 12000;

    private Timer sunProduceTimer;
    private Object gamePanel;

    public Sunflower(int x, int y, GamePanel gamePanel) {
        super(x, y, "/Image/plants/sunflower.gif", SUNFLOWER_HEALTH);
        this.gamePanel = gamePanel;
        startSunProduceTimer();
    }

    public Sunflower(int x, int y, GamePanel2 gamePanel2) {
        super(x, y, "/Image/plants/sunflower.gif", SUNFLOWER_HEALTH);
        this.gamePanel = gamePanel2;
        startSunProduceTimer();
    }

    public Sunflower(int x, int y, GamePanel3 gamePanel3) {
        super(x, y, "/Image/plants/sunflower.gif", SUNFLOWER_HEALTH);
        this.gamePanel = gamePanel3;
        startSunProduceTimer();
    }

    public Sunflower(int x, int y) {
        super(x, y, "/Image/plants/sunflower.gif", SUNFLOWER_HEALTH);
    }

    private void startSunProduceTimer() {
        sunProduceTimer = new Timer(SUN_PRODUCE_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                produceSun();
            }
        });
        sunProduceTimer.start();
    }

    private void produceSun() {
        Sun sun = new Sun(x + WIDTH / 2 - 15, y + HEIGHT / 2 - 15, 1, gamePanel);
        if (gamePanel instanceof GamePanel) {
            ((GamePanel) gamePanel).addSun(sun);
        } else if (gamePanel instanceof GamePanel2) {
            ((GamePanel2) gamePanel).addSun(sun);
        } else if (gamePanel instanceof GamePanel3) {
            ((GamePanel3) gamePanel).addSun(sun);
        }
    }

    @Override
    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, WIDTH, HEIGHT, component);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (isDead()) {
            sunProduceTimer.stop();
        }
    }

    public static int getSunCost() {
        return SUN_COST;
    }
}