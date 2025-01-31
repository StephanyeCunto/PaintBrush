package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Retangulo extends Ponto{
    private double base;
    private double altura;

    public Retangulo(Ponto coordenada, String color, double thickness, double base, double altura) {
        super(coordenada.getX(), coordenada.getY(), color, thickness);
        this.base = base;
        this.altura = altura;
    }

    public double area() {
        return base * altura;
    }

    public double perimetro() {
        return 2 * (base + altura);
    }

    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.web(super.getColor()));
        gc.setLineWidth(super.getThickness());
        gc.strokeRect(super.getX(), super.getY(), base, altura);
        gc.restore();
    }
}
