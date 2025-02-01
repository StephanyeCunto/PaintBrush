package com.view;

import com.models.Cilindro;
import com.models.Circulo;
import com.models.Ponto;
import com.models.Reta;
import com.models.Retangulo;

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
    private boolean isDrawing = false;
    private boolean isErasing = false;
    private WritableImage canvasSnapshot;
    private final String BACKGROUND_COLOR = "#e0e0e0"; 

    @FXML
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        gc.setFill(Color.web(BACKGROUND_COLOR));
        gc.fillRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        setupColorPicker();
        setupToolsGroup();
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

    private void setupToolsGroup() {
        toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleToolChange((ToggleButton) newValue);
            }
        });
    }

    private void setupCanvas() {
        drawingCanvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            isDrawing = true;
            
            canvasSnapshot = drawingCanvas.snapshot(null, null);
            
            if (isErasing || ((ToggleButton) toolsGroup.getSelectedToggle()).getText().equals("Borracha")) {
                drawPoint(event.getX(), event.getY());
            }
        });

        drawingCanvas.setOnMouseDragged(event -> {
            if (isDrawing) {
                ToggleButton selectedTool = (ToggleButton) toolsGroup.getSelectedToggle();
                switch (selectedTool.getText()) {
                    case "Linha":
                        gc.drawImage(canvasSnapshot, 0, 0);
                        drawLine(startX, startY, event.getX(), event.getY());
                        break;
                    case "Lápis":
                    case "Borracha":
                        drawPoint(event.getX(), event.getY());
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
                }
            }
        });

        drawingCanvas.setOnMouseReleased(event -> {
            if (isDrawing) {
                ToggleButton selectedTool = (ToggleButton) toolsGroup.getSelectedToggle();
                if (selectedTool != null) {
                    switch (selectedTool.getText()) {
                        case "Linha":
                            gc.drawImage(canvasSnapshot, 0, 0);
                            drawLine(startX, startY, event.getX(), event.getY());
                            break;
                        case "Lápis":
                        case "Borracha":
                            drawPoint(event.getX(), event.getY());
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
                    }
                }
                isDrawing = false;
                canvasSnapshot = null;
            }
        });
    }

    private void updateColorFromToggle(ToggleButton button) {
        if (!isErasing) {
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
    }

    private void handleToolChange(ToggleButton newTool) {
        if (newTool != null && newTool.getText().equals("Borracha")) {
            previousColor = currentColor;
            currentColor = BACKGROUND_COLOR;
            isErasing = true;
        } else if (isErasing) {
            currentColor = previousColor;
            isErasing = false;
        }
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
        Ponto ponto = new Ponto(x1, y1, currentColor, thickness);
        Retangulo retangulo = new Retangulo(ponto, currentColor, thickness, base, altura);
        retangulo.desenhar(gc);
    }

    private void drawCilindro(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double raio = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        Ponto ponto = new Ponto(x1, y1, currentColor, thickness);
        Cilindro cilindro = new Cilindro(ponto, currentColor, thickness, raio, raio);
        cilindro.desenhar(gc);
    }
}