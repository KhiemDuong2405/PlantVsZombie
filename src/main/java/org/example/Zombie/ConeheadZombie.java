package org.example.Zombie;

public class ConeheadZombie extends Zombie {
    private static final int CONEHEAD_HEALTH = 200;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 80;
    private static final int SPEED = 1;

    public ConeheadZombie(int x, int y) {
        super(x, y, CONEHEAD_HEALTH, "/Image/zombies/zombie_tutorial_cone.png", WIDTH, HEIGHT,SPEED);
    }
}