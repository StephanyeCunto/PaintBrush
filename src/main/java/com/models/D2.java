package com.models;

import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

@Getter
public abstract class D2 extends Ponto{

    public D2(Ponto coordenada, String color, double thickness) {
        super(coordenada.getX(), coordenada.getY(), color, thickness);
    }

    public abstract double area();

    public abstract double perimetro();

    public abstract void desenhar(GraphicsContext gc);
}
