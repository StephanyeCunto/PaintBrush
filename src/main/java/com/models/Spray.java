package com.models;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

public class Spray extends Ponto {
    public Spray(double x, double y, String color, double thickness) {
        super(x, y, color, thickness);
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setFill(javafx.scene.paint.Color.web(getColor()));
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            double offsetX = random.nextDouble() * getThickness() - getThickness() / 2;
            double offsetY = random.nextDouble() * getThickness() - getThickness() / 2;
            gc.fillOval(getX() + offsetX, getY() + offsetY, 1, 1);
        }
        gc.restore();
    }
}
