package com.models;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

public class Spray extends Ponto {
    public Spray(double x, double y, String color, double thickness) {
        super(x, y, color, thickness);
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.web(getColor()));
        Random random = new Random();
        int particles = 50;
        double radius = getThickness() / 2;
        for (int i = 0; i < particles; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double dist = random.nextDouble() * radius;
            double offsetX = dist * Math.cos(angle);
            double offsetY = dist * Math.sin(angle);
            gc.fillOval(getX() + offsetX, getY() + offsetY, getThickness() / 10, getThickness() / 10);
        }
    }
}
