package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cilindro extends D3{
    private double raio;

    public Cilindro(Ponto coordenada, String color, double thickness, double raio, double profundidade) {
        super(coordenada, color, thickness,  profundidade);
        this.raio = raio;
    }

    @Override
    public double volume() {
        return Math.PI * Math.pow(raio, 2) * getProfundidade();
    }

    @Override
    public double areaSuperficial() {
        return 2 * Math.PI * raio * (raio + getProfundidade());
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        
        double x = getX();
        double y = getY();
        double altura = getProfundidade();
        double largura = 2 * raio;
        double ovalAltura = raio / 2; 
        
        gc.setFill(Color.web(getColor()));
    
        gc.setFill(Color.web(getColor()).darker());
        gc.fillOval(x - raio, y + altura - ovalAltura / 2, largura, ovalAltura);
    
        gc.setFill(Color.web(getColor()));
        gc.fillRect(x - raio, y, largura, altura);
    
        gc.setStroke(Color.web(getColor()).darker());
        gc.strokeRect(x - raio, y, largura, altura);
    
        gc.setFill(Color.web(getColor()).brighter());
        gc.fillOval(x - raio, y - ovalAltura / 2, largura, ovalAltura);
        gc.setStroke(Color.web(getColor()).darker());
        gc.strokeOval(x - raio, y - ovalAltura / 2, largura, ovalAltura);
        
        gc.restore();
    }
    
}
