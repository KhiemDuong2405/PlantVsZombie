package org.example.Zombie;

import javax.swing.*;

public class PoolZombie extends Zombie {
    private static final int POOL_HEALTH = 100;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SPEED = 1;
    private static final String NORMAL_IMAGE = "/Image/zombies/zombie_pool.png";
    private static final String WATER_IMAGE = "/Image/zombies/ongtho.png";
    private static final String ATTACK_IMAGE = "/Image/zombies/zombie_lan.png";

    private boolean diving;

    public PoolZombie(int x, int y) {
        super(x, y, POOL_HEALTH, NORMAL_IMAGE, WIDTH, HEIGHT, SPEED);
        this.diving = false;
    }

    public void updateImageBasedOnPosition(boolean isInWater, boolean isAttacking) {
        if (isAttacking) {
            this.imageIcon = new ImageIcon(getClass().getResource(ATTACK_IMAGE));
            this.diving = false;
        } else if (isInWater) {
            this.imageIcon = new ImageIcon(getClass().getResource(WATER_IMAGE));
            this.diving = true;
        } else {
            this.imageIcon = new ImageIcon(getClass().getResource(NORMAL_IMAGE));
            this.diving = false;
        }
    }
    public boolean isDiving() {
        return diving;
    }
}