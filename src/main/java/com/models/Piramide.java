package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Piramide extends D3 {
    private double base;
    private double largura;

    public Piramide(Ponto coordenada, double base, double largura, double profundidade, String color, double thickness) {
        super(coordenada, color, thickness, profundidade);
        this.base = base;
        this.largura = largura;
    }

    @Override
    public double volume() {
        return (base * largura * getProfundidade()) / 3;
    }

    @Override
    public double areaSuperficial() {
        double areaBase = base * largura;
        double apotemaBase = Math.sqrt(Math.pow(base / 2, 2) + Math.pow(getProfundidade(), 2));
        double apotemaLargura = Math.sqrt(Math.pow(largura / 2, 2) + Math.pow(getProfundidade(), 2));
        double areaLateral = base * apotemaLargura + largura * apotemaBase;
        return areaBase + areaLateral;
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.web(super.getColor()));
        gc.setLineWidth(super.getThickness());

        double x = super.getX();
        double y = super.getY();

        gc.strokeRect(x, y, base, largura);

        double verticeX = x + base / 2;
        double verticeY = y - getProfundidade();

        gc.strokeLine(x, y, verticeX, verticeY); 
        gc.strokeLine(x + base, y, verticeX, verticeY); 
        gc.strokeLine(x, y + largura, verticeX, verticeY); 
        gc.strokeLine(x + base, y + largura, verticeX, verticeY); 

        gc.restore();
    }
}
