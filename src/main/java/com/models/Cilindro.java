package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cilindro extends D3 {
    private double raio;
    
    public Cilindro(Ponto coordenada, double raio, double profundidade, String color, double thickness) {
        super(coordenada, color, thickness, profundidade);
        this.raio = raio;
    }

    @Override
    public double volume() {
        return Math.PI * Math.pow(raio / 2, 2) * getProfundidade();
    }

    @Override
    public double areaSuperficial() {
        return 2 * Math.PI * Math.pow(raio , 2) + 2 * Math.PI * (raio ) * getProfundidade();
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
    }
}
