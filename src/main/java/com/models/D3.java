package com.models;

import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

@Getter
public abstract class D3 extends Ponto {
    private double profundidade;
    private boolean exibirArea;

    public D3(Ponto coordenada, String color, double thickness, double profundidade, boolean exibirArea) {
        super(coordenada.getX(), coordenada.getY(), color, thickness);
        this.profundidade = profundidade;
        this.exibirArea = exibirArea;
    }

    protected abstract double volume();

    protected abstract double areaSuperficial();

    protected abstract double perimetro();

    public abstract void desenhar(GraphicsContext gc);

}
