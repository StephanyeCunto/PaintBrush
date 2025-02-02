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
    protected double area() {
        return Math.PI * Math.pow(raio, 2);
    }

    @Override
    protected double perimetro() {
        return 2 * Math.PI * raio;
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.web(super.getColor()));
        gc.setFill(Color.web(super.getColorPreenchimento()));
        gc.setLineWidth(super.getThickness());
    
        gc.fillOval(getX(), getY() - raio/4, raio, raio);
        gc.strokeOval(getX(), getY() - raio/4, raio, raio);
        
        if (isExibirArea()) {
            gc.save();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            String areaText = String.format("Area: %.2f px²", area());
            String perimetroText = String.format("Perimetro: %.2f px", perimetro());
            gc.strokeText(areaText, getX()+raio+getThickness() , getY() );
            gc.strokeText(perimetroText,getX()+raio+getThickness() , getY()+ 15);
            gc.restore();
        }
        
        gc.restore();
    }
}