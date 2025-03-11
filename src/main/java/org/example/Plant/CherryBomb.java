package org.example.Plant;

import org.example.GamePanel;
import org.example.GamePanel2;
import org.example.GamePanel3;
import org.example.Zombie.Zombie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CherryBomb extends Plant {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SUN_COST = 150;
    private static final int CHERRYBOMB_HEALTH = 9999;
    private Timer bomNoTimer;
    private GamePanel gamePanel;
    private GamePanel2 gamePanel2;
    private GamePanel3 gamePanel3;

    public CherryBomb(int x, int y, GamePanel gamePanel) {
        super(x, y, "/Image/plants/cherryboom.png", CHERRYBOMB_HEALTH);
        this.gamePanel = gamePanel;
        batDauNo();
    }
    public CherryBomb(int x, int y, GamePanel2 gamePanel2) {
        super(x, y, "/Image/plants/cherryboom.png", CHERRYBOMB_HEALTH);
        this.gamePanel2 = gamePanel2;
        batDauNo();
    }
    public CherryBomb(int x, int y, GamePanel3 gamePanel3) {
        super(x, y, "/Image/plants/cherryboom.png", CHERRYBOMB_HEALTH);
        this.gamePanel3 = gamePanel3;
        batDauNo();
    }

    public CherryBomb(int x, int y) {
        super(x, y, "/Image/plants/cherryboom.png", CHERRYBOMB_HEALTH);
    }

    public static int getSunCost() {
        return SUN_COST;
    }

    private void batDauNo() {
        bomNoTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                no();
            }
        });
        bomNoTimer.setRepeats(false);
        bomNoTimer.start();
    }

    private void no() {
        ArrayList<Zombie> zombiesToRemove = new ArrayList<>();

        if (gamePanel != null) {
            for (int hang = 0; hang < GamePanel.ROWS; hang++) {
                for (Zombie zombie : gamePanel.getLaneZombies(hang)) {
                    if (PhamViNo(zombie)) {
                        zombie.takeDamage(999);
                        zombiesToRemove.add(zombie);
                    }
                }
            }

            for (Zombie zombie : zombiesToRemove) {
                gamePanel.removeZombie(zombie);
            }

            setHealth(0);
            notifyCollider();
            gamePanel.removePlant(this);
            gamePanel.repaint();

        } else if (gamePanel2 != null) {
            for (int hang = 0; hang < GamePanel2.ROWS; hang++) {
                for (Zombie zombie : gamePanel2.getLaneZombies(hang)) {
                    if (PhamViNo(zombie)) {
                        zombie.takeDamage(999);
                        zombiesToRemove.add(zombie);
                    }
                }
            }

            for (Zombie zombie : zombiesToRemove) {
                gamePanel2.removeZombie(zombie);
            }

            setHealth(0);
            notifyCollider();
            gamePanel2.removePlant(this);
            gamePanel2.repaint();

        } else if (gamePanel3 != null) {
            for (int hang = 0; hang < GamePanel3.ROWS; hang++) {
                for (Zombie zombie : gamePanel3.getLaneZombies(hang)) {
                    if (PhamViNo(zombie)) {
                        zombie.takeDamage(999);
                        zombiesToRemove.add(zombie);
                    }
                }
            }

            for (Zombie zombie : zombiesToRemove) {
                gamePanel3.removeZombie(zombie);
            }

            setHealth(0);
            notifyCollider();
            gamePanel3.removePlant(this);
            gamePanel3.repaint();
        }
    }

    private boolean PhamViNo(Zombie zombie) {
        int xZombie = zombie.getX();
        int yZombie = zombie.getY();

        double leftBound = x - WIDTH * 1.5;
        double rightBound = x + WIDTH * 1.5 ;
        double upperBound = y - HEIGHT * 1.5 ;
        double lowerBound = y + HEIGHT * 1.5;

        return xZombie >= leftBound && xZombie < rightBound && yZombie >= upperBound && yZombie < lowerBound;
    }

    @Override
    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, WIDTH, HEIGHT, component);
    }
}