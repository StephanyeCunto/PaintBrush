package com.view;

import com.models.*;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PaintBrush {
    @FXML
    private Canvas drawingCanvas;
    @FXML
    private Slider thicknessSlider;
    @FXML
    private ToggleGroup toolsGroup;
    @FXML
    private ToggleGroup colorToggleGroup;
    @FXML
    private ColorPicker colorPicker;

    private GraphicsContext gc;
    private String currentColor = "#000000";
    private String previousColor = "#000000";
    private double startX, startY;
    private WritableImage canvasSnapshot;
    private final String BACKGROUND_COLOR = "#e0e0e0"; 

    @FXML
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        gc.setFill(Color.web(BACKGROUND_COLOR));
        gc.fillRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        setupColorPicker();
        setupCanvas();
    }

    private void setupColorPicker() {
        colorToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateColorFromToggle((ToggleButton) newValue);
            }
        });

        colorPicker.setOnAction(event -> {
            currentColor = colorPicker.getValue().toString();
            if (colorToggleGroup.getSelectedToggle() != null) {
                colorToggleGroup.getSelectedToggle().setSelected(false);
            }
        });
    }

    private void setupCanvas() {
        drawingCanvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            canvasSnapshot = drawingCanvas.snapshot(null, null);
        });

        drawingCanvas.setOnMouseDragged(event -> {
                ToggleButton selectedTool = (ToggleButton) toolsGroup.getSelectedToggle();
                switch (selectedTool.getText()) {
                    case "Linha":
                        gc.drawImage(canvasSnapshot, 0, 0);
                        drawLine(startX, startY, event.getX(), event.getY());
                        break;
                    case "Lápis":
                        drawPoint(event.getX(), event.getY());
                        break;
                    case "Borracha":
                        drawEraser(event.getX(), event.getY());
                        break;
                    case "Circulo":
                        gc.drawImage(canvasSnapshot, 0, 0);
                        drawCircle(startX, startY, event.getX(), event.getY());
                        break;
                    case "Retângulo":
                        gc.drawImage(canvasSnapshot, 0, 0);
                        drawRectangle(startX, startY, event.getX(), event.getY());
                        break;
                    case "Cilindro":
                        gc.drawImage(canvasSnapshot, 0, 0);
                        drawCilindro(startX, startY, event.getX(), event.getY());
                        break;
                    case "Pirâmide":
                        gc.drawImage(canvasSnapshot, 0, 0);
                        drawPiramide(startX, startY, event.getX(), event.getY());
                        break;
                }
        });

        drawingCanvas.setOnMouseReleased(event -> {
                ToggleButton selectedTool = (ToggleButton) toolsGroup.getSelectedToggle();
                if (selectedTool != null) {
                    switch (selectedTool.getText()) {
                        case "Linha":
                            gc.drawImage(canvasSnapshot, 0, 0);
                            drawLine(startX, startY, event.getX(), event.getY());
                            break;
                        case "Lápis":
                            drawPoint(event.getX(), event.getY());
                            break;
                        case "Borracha":
                            drawEraser(event.getX(), event.getY());
                            break;
                        case "Circulo":
                            gc.drawImage(canvasSnapshot, 0, 0);
                            drawCircle(startX, startY, event.getX(), event.getY());
                            break;
                        case "Retângulo":
                            gc.drawImage(canvasSnapshot, 0, 0);
                            drawRectangle(startX, startY, event.getX(), event.getY());
                            break;
                        case "Cilindro":
                            gc.drawImage(canvasSnapshot, 0, 0);
                            drawCilindro(startX, startY, event.getX(), event.getY());
                            break;
                        case "Pirâmide":
                            gc.drawImage(canvasSnapshot, 0, 0);
                            drawPiramide(startX, startY, event.getX(), event.getY());
                            break;
                    }
                canvasSnapshot = null;
            }
        });
    }

    private void updateColorFromToggle(ToggleButton button) {
            String buttonId = button.getId();
            switch (buttonId) {
                case "blackColorBtn":   currentColor = "#000000"; break;
                case "whiteColorBtn":   currentColor = "#FFFFFF"; break;
                case "redColorBtn":     currentColor = "#FF4444"; break;
                case "greenColorBtn":   currentColor = "#44FF44"; break;
                case "blueColorBtn":    currentColor = "#4444FF"; break;
                case "yellowColorBtn":  currentColor = "#FFFF44"; break;
                case "magentaColorBtn": currentColor = "#FF44FF"; break;
                case "cyanColorBtn":    currentColor = "#44FFFF"; break;
            }
            previousColor = currentColor;
    }

    private void drawPoint(double x, double y) {
        double thickness = thicknessSlider.getValue();
        Ponto ponto = new Ponto(x, y, currentColor, thickness);
        ponto.desenhar(gc);
    }

    private void drawLine(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        Reta reta = new Reta(x1, y1, x2, y2, currentColor, thickness);
        reta.desenhar(gc);
    }

    private void drawCircle(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double raio = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        Circulo circulo = new Circulo(new Ponto(x1, y1, currentColor, thickness), currentColor, thickness, "Circulo", raio);
        circulo.desenhar(gc);
    }

    private void drawRectangle(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double base = Math.abs(x2 - x1);
        double altura = Math.abs(y2 - y1);
        Retangulo retangulo = new Retangulo(new Ponto(x1, y1, currentColor, thickness), currentColor, thickness, base, altura);
        retangulo.desenhar(gc);
    }

    private void drawCilindro(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double diametro = Math.abs(x2 - x1);
        double profundidade = Math.abs(y2 - y1);
        Cilindro cilindro = new Cilindro(new Ponto(x1, y1, currentColor, thickness), diametro/2, profundidade, currentColor, thickness);
        cilindro.desenhar(gc);
    }

    private void drawPiramide(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double base = Math.abs(x2 - x1);  
        double largura = Math.abs(y2 - y1); 
        double profundidade = Math.abs(y2 - y1); 
        Piramide piramide = new Piramide(new Ponto(x1, y1, currentColor, thickness), base, largura, profundidade, currentColor, thickness);
        piramide.desenhar(gc);  
    }
    
    private void drawEraser(double x, double y) {
        double thickness = thicknessSlider.getValue();
        Borracha borracha = new Borracha(x, y, thickness);
        borracha.desenhar(gc);
    }
}