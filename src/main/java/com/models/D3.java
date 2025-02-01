package com.models;

import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class D3 extends Ponto {
    private double profundidade;

    public D3(Ponto coordenada, String color, double thickness, double profundidade) {
        super(coordenada.getX(), coordenada.getY(), color, thickness);
        this.profundidade = profundidade;
    }

    public abstract double volume();

    public abstract double areaSuperficial();

    public abstract void desenhar(GraphicsContext gc);

}
