package com.view;

import com.models.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox brushSettings;
    @FXML
    private VBox shapeSettings;
    @FXML
    private CheckBox area;
    @FXML
    private ToggleGroup colorToggleGroup2D;
    @FXML
    private ColorPicker colorPicker2D;
    @FXML
    private CheckBox fillShape;

    private GraphicsContext gc;
    private String currentColor = "#000000";
    private String currentColor2D = "#000000";
    private double startX, startY;
    private WritableImage canvasSnapshot;
    private final String BACKGROUND_COLOR = "#e0e0e0";

    @FXML
    private void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        gc.setFill(Color.web(BACKGROUND_COLOR));
        gc.fillRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    
        ColorManager primaryColorManager = new ColorManager(colorToggleGroup, colorPicker, color -> currentColor = color);
        ColorManager secondaryColorManager = new ColorManager(colorToggleGroup2D, colorPicker2D, color -> currentColor2D = color);
    
        setupCanvas();
        setupVisibilityForSettings();
    }

    private void setupCanvas() {
        drawingCanvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            canvasSnapshot = drawingCanvas.snapshot(null, null);
            handleMouseAction(event, true);
        });

        drawingCanvas.setOnMouseDragged(event -> handleMouseAction(event, true));

        drawingCanvas.setOnMouseReleased(event -> {
            handleMouseAction(event, true);
            canvasSnapshot = null;
        });
    }

    private void handleMouseAction(javafx.scene.input.MouseEvent event, boolean isRelease) {
        ToggleButton selectedTool = (ToggleButton) toolsGroup.getSelectedToggle();
        Ferramenta ferramenta = Ferramenta.porNome(selectedTool.getText()); 
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
        
        switch (ferramenta) {
            case LINHA:
                if (isRelease) drawLine(startX, startY, x, y);
                break;
            case CIRCULO:
                if (isRelease) drawCircle(startX, startY, x, y);
                break;
            case RETANGULO:
                if (isRelease) drawRectangle(startX, startY, x, y);
                break;
            case CILINDRO:
                if (isRelease) drawCilindro(startX, startY, x, y);
                break;
            case PIRAMIDE:
                if (isRelease) drawPiramide(startX, startY, x, y);
                break;
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
        Circulo circulo = new Circulo(new Ponto(x1, y1, currentColor, thickness), currentColor, thickness,
                raio, fillShape.isSelected() ? currentColor2D :  "rgba(255, 0, 0, 0)", area.isSelected());
        circulo.desenhar(gc);
    }

    private void drawRectangle(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double base = Math.abs(x2 - x1);
        double altura = Math.abs(y2 - y1);
        Retangulo retangulo = new Retangulo(new Ponto(x1, y1, currentColor, thickness), currentColor, thickness,
                base, altura, fillShape.isSelected() ? currentColor2D : "rgba(255, 0, 0, 0)", area.isSelected());
        retangulo.desenhar(gc);
    }

    private void drawCilindro(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double diametro = Math.abs(x2 - x1);
        double profundidade = Math.abs(y2 - y1);
        Cilindro cilindro = new Cilindro(new Ponto(x1, y1, currentColor, thickness), diametro / 2, profundidade,
                currentColor, thickness, area.isSelected());
        cilindro.desenhar(gc);
    }

    private void drawPiramide(double x1, double y1, double x2, double y2) {
        double thickness = thicknessSlider.getValue();
        double base = Math.abs(x2 - x1);
        double largura = Math.abs(y2 - y1);
        double profundidade = Math.abs(y2 - y1);
        Piramide piramide = new Piramide(new Ponto(x1, y1, currentColor, thickness), base, largura, profundidade,
                currentColor, thickness, area.isSelected());
        piramide.desenhar(gc);
    }

    private void drawEraser(double x, double y) {
        double thickness = thicknessSlider.getValue();
        Borracha borracha = new Borracha(x, y, thickness);
        borracha.desenhar(gc);
    }

    private void drawSpray(double x, double y) {
        double thickness = thicknessSlider.getValue();
        Spray spray = new Spray(x, y, currentColor, thickness);
        spray.desenhar(gc);
    }

    private void setupVisibilityForSettings() {
        toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            ToggleButton selectedTool = (ToggleButton) newValue;
            Ferramenta ferramenta = Ferramenta.porNome(selectedTool.getText());
            brushSettings.setVisible(ferramenta.ehPreenchida());
            shapeSettings.setVisible(ferramenta.ehForma());
        });
    }
}