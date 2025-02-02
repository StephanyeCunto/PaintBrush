package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circulo extends D2 {
    private double raio;

    public Circulo(Ponto coordenada, String color, double thickness, double raio, String colorPreenchimento, boolean exibirArea) {
        super(coordenada, color, thickness,colorPreenchimento, exibirArea);
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
        
        if (isExibirArea()) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            String areaText = String.format("Area: %.2f px²", area());
            String perimetroText = String.format("Perimetro: %.2f px", perimetro());
            gc.strokeText(areaText, x + diameter + 15, y + (diameter/2));
            gc.strokeText(perimetroText, x + diameter + 15, y + (diameter/2) + 15);
        }
        
        gc.restore();
    }
}