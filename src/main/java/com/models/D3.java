package com.models;

public class D3 extends Ponto {
    private double profundidade;

    public D3(double x, double y, double z, String color, double thickness) {
        super(x, y, color, thickness);
        this.profundidade = profundidade;
    }

    public double area(){
        return 0;
    }

    public double volume(){
        return 0;
    }
    
    public double perimetro(){
        return 0;
    }
    
    public void desenhar(){}
    
}
