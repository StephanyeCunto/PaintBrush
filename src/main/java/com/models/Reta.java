package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Reta extends Ponto {
    private double x1;
    private double y1;

    public Reta(double x, double y, double x1, double y1, String color, double thickness) {
        super(x, y, color, thickness);
        this.x1 = x1;
        this.y1 = y1;
    }
    
    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.web(super.getColor()));
        gc.setLineWidth(super.getThickness());
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeLine(super.getX(), super.getY(), x1, y1);
        gc.restore();
    }
}
