package org.example.Zombie;

public class NormalZombie extends Zombie {
    private static final int NORMAL_HEALTH = 100;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SPEED = 1;

    public NormalZombie(int x, int y) {
        super(x, y, NORMAL_HEALTH, "/Image/zombies/zombie_tutorial.png", WIDTH, HEIGHT,SPEED);
    }

}