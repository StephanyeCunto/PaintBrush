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
    public double area(){
        return Math.PI * Math.pow(raio, 2);
    }

    @Override
    public double perimetro(){
        return 2 * Math.PI * raio;
    }

    @Override
    public void desenhar(GraphicsContext gc){
        gc.save(); 
        gc.setFill(Color.web(super.getColor()));
        gc.fillOval(super.getX() - super.getThickness()/2, super.getY() - super.getThickness()/2, super.getThickness(), super.getThickness());
        gc.restore();    
    }
}
