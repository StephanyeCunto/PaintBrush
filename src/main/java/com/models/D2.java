package com.models;

import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

@Getter
public abstract class D2 extends Ponto{
    private String colorPreenchimento;
    private boolean exibirArea;

    public D2(Ponto coordenada, String color, double thickness, String colorPreenchimento, boolean exibirArea) {
        super(coordenada.getX(), coordenada.getY(), color, thickness);
        this.colorPreenchimento = colorPreenchimento;
        this.exibirArea = exibirArea;
    }

    protected abstract double area();

    protected abstract double perimetro();

    public abstract void desenhar(GraphicsContext gc);
}
