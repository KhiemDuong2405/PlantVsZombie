package org.example;

import org.example.Plant.*;
import org.example.Zombie.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JLayeredPane {
    private Image backgroundImage;
    private static JLabel sunScoreboard;
    private ArrayList<ArrayList<Plant>> lanePlants;
    private ArrayList<ArrayList<Zombie>> laneZombies;
    private ArrayList<Sun> suns;
    private Plant selectedPlant;
    private Random random;
    private ArrayList<Timer> zombieTimers;
    private Timer sunGeneratorTimer;
    private Timer zombieGeneratorTimer;
    private Collider[][] colliders;
    private int maxZombieCount = 20;
    private int zombieCount = 20;

    private static final int CELL_WIDTH = 70;
    private static final int CELL_HEIGHT = 80;

    public static final int ROWS = 5;
    public static final int COLS = 9;

    public static final int GRID_START_X = 250;
    public static final int GRID_START_Y = 60;



    public GamePanel() {
        URL imageUrl = getClass().getResource("/Image/MapNgay.jpg");
        backgroundImage = new ImageIcon(imageUrl).getImage();
        setLayout(null);

        lanePlants = new ArrayList<>();
        laneZombies = new ArrayList<>();
        suns = new ArrayList<>();
        zombieTimers = new ArrayList<>();
        random = new Random();

        for (int i = 0; i < ROWS; i++) {
            lanePlants.add(new ArrayList<>());
            laneZombies.add(new ArrayList<>());
        }
        colliders = new Collider[ROWS][COLS];

        SunScore();
        addColliders();
        addPlantCards();

        startSunGenerator();
        new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startZombieGenerator();
            }
        }).start();
    }

    private void SunScore() {
        sunScoreboard = new JLabel();
        sunScoreboard.setFont(new Font("Arial", Font.BOLD, 30));
        sunScoreboard.setForeground(Color.BLACK);
        sunScoreboard.setBounds(155, 8, 100, 30);
        add(sunScoreboard);
        setSunScore(200);
    }

    private void setSunScore(int sunScore) {
        sunScoreboard.setText(String.valueOf(sunScore));
    }

    public void increaseSunScore(int amount) {
        int currentScore = Integer.parseInt(sunScoreboard.getText());
        sunScoreboard.setText(String.valueOf(currentScore + amount));
    }

    private int getSunScore() {
        return Integer.parseInt(sunScoreboard.getText());
    }

    private void flashSunScoreboard() {
        Color originalColor = sunScoreboard.getForeground();
        int flashCount = 8;
        int flashInterval = 100;

        Timer timer = new Timer(flashInterval, null);
        timer.addActionListener(new ActionListener() {
            private int count = 0;
            private boolean isRed = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= flashCount) {
                    sunScoreboard.setForeground(originalColor);
                    ((Timer) e.getSource()).stop();
                } else {
                    sunScoreboard.setForeground(isRed ? originalColor : Color.RED);
                    isRed = !isRed;
                    count++;
                }
            }
        });
        timer.start();
    }

    private void addPlantCards() {
        int xOffset = 0;
        int yOffset = 0;

        PlantCard peashooterCard = new PlantCard(new ImageIcon(getClass().getResource("/Image/cards/card_peashooter_true.png")).getImage());
        peashooterCard.setLocation(xOffset, yOffset);
        peashooterCard.setAction(e -> {
            if (getSunScore() >= Peashooter.getSunCost()) {
                System.out.println("Peashooter selected");
                selectedPlant = new Peashooter(0, 0);
            } else {
                flashSunScoreboard();
            }
        });
        add(peashooterCard);

        PlantCard sunflowerCard = new PlantCard(new ImageIcon(getClass().getResource("/Image/cards/card_sunflower_true.png")).getImage());
        sunflowerCard.setLocation(xOffset, yOffset + 60);
        sunflowerCard.setAction(e -> {
            if (getSunScore() >= Sunflower.getSunCost()) {
                System.out.println("Sunflower selected");
                selectedPlant = new Sunflower(0, 0);
            } else {
                flashSunScoreboard();
            }
        });
        add(sunflowerCard);

        PlantCard cherryBombCard = new PlantCard(new ImageIcon(getClass().getResource("/Image/cards/card_cherrybomb_true.png")).getImage());
        cherryBombCard.setLocation(xOffset, yOffset + 120);
        cherryBombCard.setAction(e -> {
            if (getSunScore() >= CherryBomb.getSunCost()) {
                System.out.println("Cherry Bomb selected");
                selectedPlant = new CherryBomb(0, 0);
            } else {
                flashSunScoreboard();
            }
        });
        add(cherryBombCard);

        PlantCard wallnutCard = new PlantCard(new ImageIcon(getClass().getResource("/Image/cards/card_wallnut_true.png")).getImage());
        wallnutCard.setLocation(xOffset, yOffset + 180);
        wallnutCard.setAction(e -> {
            if (getSunScore() >= Wallnut.getSunCost()) {
                System.out.println("Wall-nut selected");
                selectedPlant = new Wallnut(0, 0);
            } else {
                flashSunScoreboard();
            }
        });
        add(wallnutCard);
    }

    private void addColliders() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = GRID_START_X + col * CELL_WIDTH;
                int y = GRID_START_Y + row * CELL_HEIGHT;
                Collider collider = new Collider(CELL_WIDTH, CELL_HEIGHT);
                collider.setLocation(x, y);
                int finalRow = row; // Biến này cần để sử dụng trong lambda
                collider.setAction(e -> {
                    if (selectedPlant != null && !collider.hasPlant()) {
                        Plant plant = createPlant(selectedPlant, x, y);
                        collider.setPlant(plant);
                        plant.setCollider(collider);
                        lanePlants.get(finalRow).add(plant);
                        setSunScore(getSunScore() - getSunCost(plant));
                        selectedPlant = null;
                        revalidate();  // Cập nhật layout
                        repaint();  // Vẽ lại panel
                    }
                });
                add(collider, JLayeredPane.PALETTE_LAYER);
                colliders[row][col] = collider;
            }
        }
    }

    private int getSunCost(Plant plant) {
        if (plant instanceof Peashooter) {
            return Peashooter.getSunCost();
        } else if (plant instanceof Sunflower) {
            return Sunflower.getSunCost();
        } else if (plant instanceof CherryBomb) {
            return CherryBomb.getSunCost();
        } else if (plant instanceof Wallnut) {
            return Wallnut.getSunCost();
        }
        return 0;
    }

    private Plant createPlant(Plant selectedPlant, int x, int y) {
        if (selectedPlant instanceof Peashooter) {
            return new Peashooter(x, y, this);
        } else if (selectedPlant instanceof Sunflower) {
            return new Sunflower(x, y, this);
        } else if (selectedPlant instanceof CherryBomb) {
            return new CherryBomb(x, y, this);
        } else if (selectedPlant instanceof Wallnut) {
            return new Wallnut(x, y);
        }
        return null;
    }

    private void startSunGenerator() {
        sunGeneratorTimer = new Timer(12000, e -> generateSun());
        sunGeneratorTimer.start();
    }

    private void generateSun() {
        int x = GRID_START_X + random.nextInt(getWidth() - GRID_START_X - 50);
        int y = -50;
        int speed = 1;
        Sun sun = new Sun(x, y, speed, this);
        suns.add(sun);
        add(sun, JLayeredPane.DRAG_LAYER);
        Timer sunTimer = new Timer(30, e -> {
            sun.fall();
            if (sun.getY() >= getHeight() - 80) {
                ((Timer) e.getSource()).stop();
            }
            repaint();
        });
        sun.setFallTimer(sunTimer);
        sunTimer.start();
    }

    public void addSun(Sun sun) {
        suns.add(sun);
        add(sun, JLayeredPane.DRAG_LAYER);
    }

    private void startZombieGenerator() {
        int delay = 7000;
        zombieGeneratorTimer =  new Timer(delay, e -> generateZombie());
        zombieGeneratorTimer.start();
    }

    private void generateZombie() {
        if (maxZombieCount > 0) {
            int lane = random.nextInt(ROWS);
            int x = getWidth();
            int y = GRID_START_Y + lane * CELL_HEIGHT;
            Zombie zombie = createRandomZombie(x, y);
            laneZombies.get(lane).add(zombie);
            Timer zombieTimer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!zombie.isDead()) {
                        moveZombie(zombie, lane);
                        checkZombieHealth();
                        repaint();
                    } else {
                        laneZombies.get(lane).remove(zombie);
                        zombieCount--;
                        zombie.stopMoving();
                        repaint();
                        ((Timer) e.getSource()).stop();
                        if(zombieCount<=0)
                            showWinDialog();
                    }
                }
            });
            zombie.setMoveTimer(zombieTimer);
            zombieTimers.add(zombieTimer);
            zombieTimer.start();

            Timer attackTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!zombie.isDead()) {
                        attackPlant(zombie, lane);
                    } else {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            attackTimer.start();
            System.out.println(zombie.getClass().getSimpleName() + " xuất hiện ở lane " + (lane + 1));
            maxZombieCount--;
        }
    }

    private void checkZombieHealth() {
        for (ArrayList<Zombie> lane : laneZombies) {
            lane.removeIf(zombie -> {
                boolean dead = zombie.isDead();
                if (dead) {
                    stopZombieTimer(zombie);
                    zombieCount--;
                }
                if(zombieCount<=0)
                    showWinDialog();
                return dead;
            });
        }
    }

    private void stopZombieTimer(Zombie zombie) {
        Timer moveTimer = zombie.getMoveTimer();
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }

    private void moveZombie(Zombie zombie, int lane) {
        zombie.move();
        boolean foundPlant = false;
        for (Plant plant : lanePlants.get(lane)) {
            if (zombie.isTouchingPlant(plant)) {
                zombie.setAttacking(true);
                foundPlant = true;
                break;
            }
        }
        if (!foundPlant) {
            zombie.setAttacking(false);
        }
        if (zombie.getX() <= 200) {
            stopAllActivities();
            showLoseDialog();
        }
        if (zombie.isDead()) {
            laneZombies.get(lane).remove(zombie);
            repaint();
        }
    }

    private void attackPlant(Zombie zombie, int lane) {
        if (zombie.isAttacking()) {
            for (Plant plant : lanePlants.get(lane)) {
                if (zombie.isTouchingPlant(plant)) {
                    zombie.attack(plant);
                    if (plant.isDead()) {
                        lanePlants.get(lane).remove(plant);
                        zombie.setAttacking(false);
                    }
                    break;
                }
            }
        }
    }

    private Zombie createRandomZombie(int x, int y) {
        int zombieType = random.nextInt(3);
        switch (zombieType) {
            case 0:
                return new NormalZombie(x, y);
            case 1:
                return new ConeheadZombie(x, y);
            case 2:
                return new BucketheadZombie(x, y);
            default:
                return new NormalZombie(x, y);
        }
    }

    private void showLoseDialog() {
        stopAllActivities();

        int option = JOptionPane.showOptionDialog(
                this,
                "Zombie đã ăn não của bạn rồi, chơi lại thôi!",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK"},
                "OK"
        );

        if (option == JOptionPane.OK_OPTION) {
            GameWindow.showLevelSelection();
        }
    }

    private void showWinDialog() {
        stopAllActivities();

        int option = JOptionPane.showOptionDialog(
                this,
                "Điều đáng sợ đang chờ đợi phía sau",
                "Next Level",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK"},
                "OK"
        );

        if (option == JOptionPane.OK_OPTION) {
            Level.setLv(2);
            GameWindow.begin(2);
        }
    }
    private void stopAllActivities() {
        if (zombieGeneratorTimer != null) {
            zombieGeneratorTimer.stop();
        }
        for (Timer timer : zombieTimers) {
            timer.stop();
        }
        if (sunGeneratorTimer != null) {
            sunGeneratorTimer.stop();
        }
        for (Sun sun : suns) {
            sun.stopFalling();
        }
    }
    public void addPea(Pea pea) {
        add(pea, JLayeredPane.DEFAULT_LAYER);
        pea.setBounds(pea.getX(), pea.getY(), pea.getIcon().getIconWidth(), pea.getIcon().getIconHeight());
    }

    public ArrayList<Zombie> getLaneZombies(int lane) {
        return laneZombies.get(lane);
    }


    public void removePlant(Plant plant) {
        for (ArrayList<Plant> hang : lanePlants) {
            hang.remove(plant);
        }
        repaint();
    }

    public void removeZombie(Zombie zombie) {
        for (ArrayList<Zombie> lane : laneZombies) {
            lane.remove(zombie);
        }
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        for (ArrayList<Plant> lane : lanePlants) {
            for (Plant plant : lane) {
                plant.draw(g, this);
            }
        }

        for (ArrayList<Zombie> lane : laneZombies) {
            for (Zombie zombie : lane) {
                zombie.draw(g, this);
            }
        }

        for (Sun sun : suns) {
            sun.draw(g, this);
        }
    }
}