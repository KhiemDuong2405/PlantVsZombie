package org.example.Zombie;

import javax.swing.*;
import java.awt.*;
import org.example.Plant.Plant;

public abstract class Zombie {
    protected int x, y;
    protected int health;
    protected ImageIcon imageIcon;
    protected int width, height;
    protected boolean attacking;
    private Timer moveTimer;
    private int speed;

    public Zombie(int x, int y, int health, String imagePath, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.imageIcon = new ImageIcon(getClass().getResource(imagePath));
        this.width = width;
        this.height = height;
        this.attacking = false;
        this.speed = speed;
    }

    public void move() {
        if (!attacking) {
            x -= speed;
        }
    }

    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, width, height, component);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void attack(Plant plant) {
        plant.takeDamage(10);
    }

    public boolean isTouchingPlant(Plant plant) {
        return this.getBounds().intersects(new Rectangle(plant.getX(), plant.getY(), plant.getWidth(), plant.getHeight()));
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Timer getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(Timer moveTimer) {
        this.moveTimer = moveTimer;
    }

    public void stopMoving() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }
}