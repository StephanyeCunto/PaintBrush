package com.models;

import lombok.Setter;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

@Getter
@Setter
public abstract class D2 extends Ponto{
    private Ponto coordenada;
    private String estrutura;

    public D2(Ponto coordenada, String color, double thickness, String estrutura) {
        super(coordenada.getX(), coordenada.getY(), color, thickness);
        this.coordenada = coordenada;
        this.estrutura = estrutura;
    }

    public abstract double area();

    public abstract double perimetro();

    public abstract void desenhar(GraphicsContext gc);
}
