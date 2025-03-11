package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sun extends JLabel {
    private int x, y;
    private int speed;
    private boolean collected;
    private Timer fallTimer;
    private Object gamePanel;

    public Sun(int x, int y, int speed, Object gamePanel) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.collected = false;
        this.gamePanel = gamePanel;
        setIcon(new ImageIcon(getClass().getResource("/Image/sun.png")));
        setBounds(x, y, getIcon().getIconWidth(), getIcon().getIconHeight());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!collected) {
                    collect();
                }
            }
        });
    }

    public void fall() {
        if (!collected) {
            y += speed;
            setLocation(x, y);
        }
    }

    public void collect() {
        collected = true;
        setVisible(false);

        if (gamePanel instanceof GamePanel) {
            ((GamePanel) gamePanel).increaseSunScore(25);
        } else if (gamePanel instanceof GamePanel2) {
            ((GamePanel2) gamePanel).increaseSunScore(25);
        } else if (gamePanel instanceof GamePanel3) {
            ((GamePanel3) gamePanel).increaseSunScore(25);
        }
    }

    public boolean isCollected() {
        return collected;
    }

    public void stopFalling() {
        if (fallTimer != null) {
            fallTimer.stop();
        }
    }

    public void draw(Graphics g, JLayeredPane panel) {
        if (!collected) {
            g.drawImage(((ImageIcon) getIcon()).getImage(), x, y, panel);
        }
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public Timer getFallTimer() {
        return fallTimer;
    }

    public void setFallTimer(Timer fallTimer) {
        this.fallTimer = fallTimer;
    }
}