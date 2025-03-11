package org.example.Zombie;

public class BucketheadZombie extends Zombie {
    private static final int BUCKETHEAD_HEALTH = 300;
    private static final int WIDTH = 70;  
    private static final int HEIGHT = 80;
    private static final int SPEED = 1;

    public BucketheadZombie(int x, int y) {
        super(x, y, BUCKETHEAD_HEALTH, "/Image/zombies/zombie_tutorial_bucket.png", WIDTH, HEIGHT, SPEED);
    }
}