package org.example.Plant;

import org.example.GamePanel;
import org.example.GamePanel2;
import org.example.GamePanel3;
import org.example.Zombie.PoolZombie;
import org.example.Zombie.Zombie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pea extends JLabel {
    private int x, y;
    private static final int SPEED = 5;
    private static final int DAMAGE = 20;
    private ImageIcon imageIcon;
    private Timer moveTimer;

    public Pea(int x, int y) {
        this.x = x + 40;
        this.y = y ;
        this.imageIcon = new ImageIcon(getClass().getResource("/Image/pea.png"));
        setIcon(imageIcon);
        setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        startMoving();
    }

    private void startMoving() {
        moveTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                checkCollision();
            }
        });
        moveTimer.start();
    }

    public void move() {
        x += SPEED;
        setLocation(x, y);
    }

    public void checkCollision() {
        Container parent = getParent();

        if (parent instanceof GamePanel) {
            GamePanel gamePanel = (GamePanel) parent;
            for (Zombie zombie : gamePanel.getLaneZombies(y / Peashooter.HEIGHT)) {
                if (isColliding(zombie)) {
                    zombie.takeDamage(DAMAGE);
                    stopMoving();
                    return;
                }
            }
        } else if (parent instanceof GamePanel2) {
            GamePanel2 gamePanel2 = (GamePanel2) parent;
            for (Zombie zombie : gamePanel2.getLaneZombies(y / Peashooter.HEIGHT)) {
                if (isColliding(zombie)) {
                    zombie.takeDamage(DAMAGE);
                    stopMoving();
                    return;
                }
            }
        } else if (parent instanceof GamePanel3) {
            GamePanel3 gamePanel3 = (GamePanel3) parent;
            for (Zombie zombie : gamePanel3.getLaneZombies(y / Peashooter.HEIGHT)) {
                if (isColliding(zombie)) {
                    if (!(zombie instanceof PoolZombie && ((PoolZombie) zombie).isDiving())) {
                        zombie.takeDamage(DAMAGE);
                        stopMoving();
                        return;
                    }
                }
            }
        }
    }

    public boolean isColliding(Zombie zombie) {
        Rectangle peaBounds = new Rectangle(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        Rectangle zombieBounds = new Rectangle(zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getHeight());
        return peaBounds.intersects(zombieBounds);
    }

    public void stopMoving() {
        moveTimer.stop();
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.repaint();
        }
    }

    public void draw(Graphics g, JComponent component) {
        g.drawImage(imageIcon.getImage(), x, y, component);
    }
}