package org.example.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.example.GamePanel;
import org.example.GamePanel2;
import org.example.GamePanel3;
import org.example.Zombie.PoolZombie;
import org.example.Zombie.Zombie;

public class Peashooter extends Plant {
    public static final int WIDTH = 70;
    public static final int HEIGHT = 80;
    private static final int SUN_COST = 100;
    private static final int PEASHOOTER_HEALTH = 100;
    private static final int SHOOT_INTERVAL = 2000; // 2 giây

    private Timer shootTimer;
    private Object gamePanel;
    private int lane;

    public Peashooter(int x, int y, GamePanel gamePanel) {
        super(x, y, "/Image/plants/peashooter.gif", PEASHOOTER_HEALTH);
        this.gamePanel = gamePanel;
        this.lane = y / HEIGHT;
        startShooting();
    }

    public Peashooter(int x, int y, GamePanel2 gamePanel2) {
        super(x, y, "/Image/plants/peashooter.gif", PEASHOOTER_HEALTH);
        this.gamePanel = gamePanel2;
        this.lane = y / HEIGHT;
        startShooting();
    }

    public Peashooter(int x, int y, GamePanel3 gamePanel3) {
        super(x, y, "/Image/plants/peashooter.gif", PEASHOOTER_HEALTH);
        this.gamePanel = gamePanel3;
        this.lane = y / HEIGHT;
        startShooting();
    }

    public Peashooter(int x, int y) {
        super(x, y, "/Image/plants/peashooter.gif", PEASHOOTER_HEALTH);
    }

    public static int getSunCost() {
        return SUN_COST;
    }

    private void startShooting() {
        shootTimer = new Timer(SHOOT_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gamePanel instanceof GamePanel) {
                    if (!((GamePanel) gamePanel).getLaneZombies(lane).isEmpty()) {
                        shoot();
                    }
                } else if (gamePanel instanceof GamePanel2) {
                    if (!((GamePanel2) gamePanel).getLaneZombies(lane).isEmpty()) {
                        shoot();
                    }
                } else if (gamePanel instanceof GamePanel3) {
                    if (!getNonDivingZombies((GamePanel3) gamePanel).isEmpty()) {
                        shoot();
                    }
                }
            }
        });
        shootTimer.start();
    }

    private void shoot() {
        Pea pea = new Pea(x + WIDTH / 7, y + HEIGHT / 8);
        if (gamePanel instanceof GamePanel) {
            ((GamePanel) gamePanel).addPea(pea);
        } else if (gamePanel instanceof GamePanel2) {
             ((GamePanel2) gamePanel).addPea(pea);
        } else if (gamePanel instanceof GamePanel3) {
            ((GamePanel3) gamePanel).addPea(pea);
        }
    }

    private ArrayList<Zombie> getNonDivingZombies(GamePanel3 gamePanel3) {
        ArrayList<Zombie> nonDivingZombies = new ArrayList<>();
        for (Zombie zombie : gamePanel3.getLaneZombies(lane)) {
            if (!(zombie instanceof PoolZombie && ((PoolZombie) zombie).isDiving())) {
                nonDivingZombies.add(zombie);
            }
        }
        return nonDivingZombies;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (isDead()) {
            shootTimer.stop();
        }
    }

    @Override
    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, WIDTH, HEIGHT, component);
    }
}