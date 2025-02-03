package com.view;

import java.io.IOException;

import com.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintBrush {
    @FXML
    private Canvas drawingCanvas;
    @FXML
    private HBox colorPalette;
    @FXML
    private VBox toolsPanel;

    private GraphicsContext gc;
    private String currentColor = "#000000";
    private String currentColor2D = "#000000";
    private double startX, startY;
    private WritableImage canvasSnapshot;
    private final String BACKGROUND_COLOR = "#e0e0e0";
    private  ColorPalette paletteController;
    private ToolGroup toolGroupController;

    @FXML
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        gc.setFill(Color.web(BACKGROUND_COLOR));
        gc.fillRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    
        addColor();
        addToolGroup();

        setupCanvas();
    }

    private void addColor(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/colorPalette.fxml"));
            HBox paletteContent = loader.load();
            paletteController = loader.getController();
            colorPalette.getChildren().add(paletteContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToolGroup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toolsGroup.fxml"));
            VBox toolGroupContent = loader.load();
            toolGroupController = loader.getController();
            toolsPanel.getChildren().add(toolGroupContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupCanvas() {
        drawingCanvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            canvasSnapshot = drawingCanvas.snapshot(null, null);
            handleMouseAction(event);
        });

        drawingCanvas.setOnMouseDragged(event -> handleMouseAction(event));

        drawingCanvas.setOnMouseReleased(event -> {
            handleMouseAction(event);
            canvasSnapshot = null;
        });
    }

    private void handleMouseAction(javafx.scene.input.MouseEvent event) {
        currentColor = paletteController.getSelectedColor();

        Ferramenta ferramenta = toolGroupController.getSelectedTool();
        drawShape(ferramenta, event.getX(), event.getY(), false);
        switch (ferramenta) {
            case LAPIS:
                drawPoint(event.getX(), event.getY());
                break;
            case BORRACHA:
                drawEraser(event.getX(), event.getY());
                break;
            case SPRAY:
                drawSpray(event.getX(), event.getY());
                break;
            default:
                drawShape(ferramenta, event.getX(), event.getY(), true);
        }
    }

    private void drawShape(Ferramenta ferramenta, double x, double y, boolean isRelease) {
        if (isRelease) {
            gc.drawImage(canvasSnapshot, 0, 0);
        }
            if (ferramenta == Ferramenta.LINHA) {
            if (isRelease) drawLine(startX, startY, x, y);
        } else if (ferramenta == Ferramenta.CIRCULO || ferramenta == Ferramenta.RETANGULO) {
            currentColor2D = toolGroupController.getSelectedColor2D();
            if (isRelease) {
                if (ferramenta == Ferramenta.CIRCULO) {
                    drawCircle(startX, startY, x, y);
                } else {
                    drawRectangle(startX, startY, x, y);
                }
            }
        } else if ((ferramenta == Ferramenta.CILINDRO || ferramenta == Ferramenta.PIRAMIDE) && isRelease) {
            if (ferramenta == Ferramenta.CILINDRO) {
            drawCilindro(startX, startY, x, y);
            } else {
            drawPiramide(startX, startY, x, y);
            }
        }
    }

    private void drawPoint(double x, double y) {
        double thickness = toolGroupController.getThickness();
        Ponto ponto = new Ponto(x, y, currentColor, thickness);
        ponto.desenhar(gc);
    }

    private void drawLine(double x1, double y1, double x2, double y2) {
        double thickness = toolGroupController.getThickness();
        Reta reta = new Reta(x1, y1, x2, y2, currentColor, thickness);
        reta.desenhar(gc);
    }

    private void drawCircle(double x1, double y1, double x2, double y2) {
        double thickness = toolGroupController.getThickness();
        double raio = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        Circulo circulo = new Circulo(new Ponto(x1, y1, currentColor, thickness), currentColor, thickness,
                raio, toolGroupController.isFillShape()? currentColor2D :  "rgba(255, 0, 0, 0)", toolGroupController.isArea());
        circulo.desenhar(gc);
    }

    private void drawRectangle(double x1, double y1, double x2, double y2) {
        double thickness = toolGroupController.getThickness();
        double base = Math.abs(x2 - x1);
        double altura = Math.abs(y2 - y1);
        Retangulo retangulo = new Retangulo(new Ponto(x1, y1, currentColor, thickness), currentColor, thickness,
                base, altura, toolGroupController.isFillShape() ? currentColor2D : "rgba(255, 0, 0, 0)", toolGroupController.isArea());
        retangulo.desenhar(gc);
    }

    private void drawCilindro(double x1, double y1, double x2, double y2) {
        double thickness = toolGroupController.getThickness();
        double diametro = Math.abs(x2 - x1);
        double profundidade = Math.abs(y2 - y1);
        Cilindro cilindro = new Cilindro(new Ponto(x1, y1, currentColor, thickness), diametro / 2, profundidade,
                currentColor, thickness, toolGroupController.isArea());
        cilindro.desenhar(gc);
    }

    private void drawPiramide(double x1, double y1, double x2, double y2) {
        double thickness = toolGroupController.getThickness();
        double base = Math.abs(x2 - x1);
        double largura = Math.abs(y2 - y1);
        double profundidade = Math.abs(y2 - y1);
        Piramide piramide = new Piramide(new Ponto(x1, y1, currentColor, thickness), base, largura, profundidade,
                currentColor, thickness, toolGroupController.isArea());
        piramide.desenhar(gc);
    }

    private void drawEraser(double x, double y) {
        double thickness = toolGroupController.getThickness();
        Borracha borracha = new Borracha(x, y, thickness);
        borracha.desenhar(gc);
    }

    private void drawSpray(double x, double y) {
        double thickness = toolGroupController.getThickness();
        Spray spray = new Spray(x, y, currentColor, thickness);
        spray.desenhar(gc);
    }
}