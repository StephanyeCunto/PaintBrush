package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Piramide extends D3 {
    private double base;
    private double largura;

    public Piramide(Ponto coordenada, double base, double largura, double profundidade, String color, double thickness, boolean exibirArea) {
        super(coordenada, color, thickness, profundidade, exibirArea);
        this.base = base;
        this.largura = largura;
    }

    @Override
    protected double volume() {
        return (base * largura * getProfundidade()) / 3;
    }

    @Override
    protected double areaSuperficial() {
        double areaBase = base * largura;
        double apotemaBase = Math.sqrt(Math.pow(base / 2, 2) + Math.pow(getProfundidade(), 2));
        double apotemaLargura = Math.sqrt(Math.pow(largura / 2, 2) + Math.pow(getProfundidade(), 2));
        double areaLateral = base * apotemaLargura + largura * apotemaBase;
        return areaBase + areaLateral;
    }

    @Override
    protected double perimetro() {
        return 4 * base + 4 * largura;
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

        if(isExibirArea()) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            String volumeText = String.format("Volume: %.2f px³", volume());
            String areaText = String.format("Area Superficial: %.2f px²", areaSuperficial());
            String perimetroText = String.format("Perimetro: %.2f px", perimetro());
            gc.strokeText(volumeText, x + base + 15, y + (largura/2));
            gc.strokeText(areaText, x + base + 15, y + (largura/2) + 15);
            gc.strokeText(perimetroText, x + base + 15, y + (largura/2) + 30);
        }
    }
}
