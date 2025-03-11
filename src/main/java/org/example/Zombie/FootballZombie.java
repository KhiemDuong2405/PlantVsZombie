package org.example.Zombie;

public class FootballZombie extends Zombie{
    private static final int Football_HEALTH = 100;
    private static final int WIDTH = 70;  // Đặt chiều rộng mong muốn
    private static final int HEIGHT = 80;// Đặt chiều cao mong muốn
    private static final int SPEED = 2;

    public FootballZombie(int x, int y) {
        super(x, y, Football_HEALTH, "/Image/zombies/zombie_football.gif", WIDTH, HEIGHT, SPEED);
    }
}
