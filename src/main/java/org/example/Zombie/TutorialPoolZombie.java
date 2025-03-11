package org.example.Zombie;

import javax.swing.*;

public class TutorialPoolZombie extends Zombie{
    private static final int TutorialPool_HEALTH = 100;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SPEED = 1;
    private static final String NORMAL_IMAGE = "/Image/zombies/zombie_tutorial_pool.png";
    private static final String WATER_IMAGE = "/Image/zombies/zombie_phao.png";

    public TutorialPoolZombie(int x, int y) {
        super(x, y, TutorialPool_HEALTH, NORMAL_IMAGE, WIDTH, HEIGHT, SPEED);
    }

    public void updateImageBasedOnPosition(boolean isInWater) {
        if (isInWater) {
            this.imageIcon = new ImageIcon(getClass().getResource(WATER_IMAGE));
        } else {
            this.imageIcon = new ImageIcon(getClass().getResource(NORMAL_IMAGE));
        }
    }
}
