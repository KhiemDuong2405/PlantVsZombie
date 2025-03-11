package org.example.Plant;

import org.example.Collider;
import org.example.GamePanel3;

import javax.swing.*;
import java.awt.*;

public class Plant {
    protected int x, y;
    protected ImageIcon imageIcon;
    protected int health;
    protected static final int WIDTH = 70;
    protected static final int HEIGHT = 80;
    private Collider collider;

    public Plant(int x, int y, String imagePath, int health) {
        this.x = x;
        this.y = y;
        this.imageIcon = new ImageIcon(getClass().getResource(imagePath));
        this.health = health;
    }

    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, WIDTH, HEIGHT, component);
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

    public void takeDamage(int damage) {
        this.health -= damage;
        if (isDead()) {
            notifyCollider();
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Collider getCollider() {
        return this.collider;
    }

    public void notifyCollider() {
        if (collider != null) {
            collider.removePlant();
        }
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}