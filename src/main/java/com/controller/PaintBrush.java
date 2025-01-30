package com.controller;

import classes.Ponto;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

public class PaintBrush {
    @FXML
    private Canvas drawingCanvas;
    @FXML
    private Slider thicknessSlider;
    
    private GraphicsContext gc;
    private String color = "#000000";


    @FXML
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();

        loadPonto();
    }

    private void loadPonto() {
        drawingCanvas.setOnMouseClicked(event -> {
            Ponto ponto = new Ponto(event.getX(), event.getY(), color,thicknessSlider.getValue());
            ponto.desenhar(gc);
        });

        drawingCanvas.setOnMouseDragged(event -> {
            Ponto ponto = new Ponto(event.getX(), event.getY(), color,thicknessSlider.getValue());
            ponto.desenhar(gc);
        });
    }
}
