package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Retangulo extends D2{
    private double base;
    private double altura;

    public Retangulo(Ponto coordenada, String color, double thickness, double base, double altura, String colorPreenchimento, boolean exibirArea) {
        super(coordenada, color, thickness,colorPreenchimento, exibirArea);
        this.base = base;
        this.altura = altura;
    }

    protected double area() {
        return base * altura;
    }

    protected double perimetro() {
        return 2 * (base + altura);
    }

    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.web(super.getColor()));
        gc.setLineWidth(super.getThickness());
        gc.strokeRect(super.getX(), super.getY(), base, altura);
        gc.restore();

        if (isExibirArea()) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            String areaText = String.format("Area: %.2f px²", area());
            String perimetroText = String.format("Perimetro: %.2f px", perimetro());
            gc.strokeText(areaText, super.getX() + base + 15, super.getY() + (base/2));
            gc.strokeText(perimetroText, super.getX() + base + 15, super.getY() + (base/2) + 15);
        }
    }
}
