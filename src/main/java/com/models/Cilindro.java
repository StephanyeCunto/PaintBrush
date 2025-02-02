package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cilindro extends D3 {
    private double raio;
    
    public Cilindro(Ponto coordenada, double raio, double profundidade, String color, double thickness, boolean exibirArea) {
        super(coordenada, color, thickness, profundidade, exibirArea);
        this.raio = raio;
    }

    @Override
    protected double volume() {
        return Math.PI * Math.pow(raio / 2, 2) * getProfundidade();
    }

    @Override
    protected double areaSuperficial() {
        return 2 * Math.PI * Math.pow(raio , 2) + 2 * Math.PI * (raio ) * getProfundidade();
    }

    @Override
    protected double perimetro() {
        return 2 * Math.PI * raio;
    }
    
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setLineWidth(getThickness());
        gc.setStroke(Color.web(getColor()));
        gc.strokeOval(getX(), getY() - raio/4, raio, raio/2);
        gc.strokeLine(getX(), getY(), getX(), getY() + getProfundidade());
        gc.strokeLine(getX() + raio, getY(), getX() + raio, getY() + getProfundidade());
        gc.strokeOval(getX(), getY() + getProfundidade() - raio/4, raio, raio/2); 
        gc.restore();

        if(isExibirArea()) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            String volumeText = String.format("Volume: %.2f px³", volume());
            String areaText = String.format("Area Superficial: %.2f px²", areaSuperficial());
            String perimetroText = String.format("Perimetro: %.2f px", perimetro());
            gc.strokeText(volumeText, getX() + raio + 15, getY());
            gc.strokeText(areaText, getX() + raio + 15, getY() + 15);
            gc.strokeText(perimetroText, getX() + raio + 15, getY() +  30);
        }
    }
}
