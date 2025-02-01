package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circulo extends D2 {
    private double raio;

    public Circulo(Ponto coordenada, String color, double thickness, String estrutura, double raio) {
        super(coordenada, color, thickness, estrutura);
        this.raio = raio;
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(raio, 2);
    }

    @Override
    public double perimetro() {
        return 2 * Math.PI * raio;
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.web(super.getColor()));
        gc.setLineWidth(super.getThickness());
        double x = super.getX() - raio;
        double y = super.getY() - raio;
        double diameter = raio * 2;
        gc.strokeOval(x, y, diameter, diameter);
        gc.restore();
    }

}