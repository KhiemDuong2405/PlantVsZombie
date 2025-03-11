package org.example;

import org.example.Plant.LyliPad;
import org.example.Plant.Plant;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Collider extends JPanel {
    private Plant plant;
    private Plant plantOnLyliPad;
    private ActionListener al;

    public Collider(int width, int height) {
        //setBorder(new LineBorder(Color.RED));
        setOpaque(false);
        setSize(width, height);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (al != null) {
                    al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
    }

    public void setAction(ActionListener al) {
        this.al = al;
    }

    public void setPlant(Plant plant) {
        if (this.plant instanceof LyliPad && this.plantOnLyliPad == null) {
            this.plantOnLyliPad = plant;
        } else {
            this.plant = plant;
        }
        plant.setCollider(this);
        repaint();
    }

    public Plant getPlant() {
        return plant;
    }

    public Plant getPlantOnLyliPad() {
        return plantOnLyliPad;
    }

    public boolean hasLyliPad() {
        return plant != null && plant instanceof LyliPad;
    }

    public boolean hasPlant() {
        return plant != null || plantOnLyliPad != null;
    }

    public void removePlant() {
        if (plantOnLyliPad != null)
            this.plantOnLyliPad = null;
        else
            this.plant = null;
        repaint();
    }

    public void removePlantOnLyliPad() {
        this.plantOnLyliPad = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (plant != null) {
            plant.draw(g, this);
        }
        if (plantOnLyliPad != null) {
            plantOnLyliPad.draw(g, this);
        }
    }
}