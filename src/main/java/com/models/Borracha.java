package com.models;

import javafx.scene.canvas.GraphicsContext;

public class Borracha extends Ponto{
    public Borracha(double x, double y, double thickness) {
        super(x, y, "#e0e0e0", thickness);
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setFill(javafx.scene.paint.Color.web(getColor()));
        gc.fillOval(getX() - getThickness() / 2, getY() - getThickness() / 2, getThickness() , getThickness() );
        gc.restore();
    }

}
