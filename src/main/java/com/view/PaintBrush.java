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
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        gc.setFill(Color.web(BACKGROUND_COLOR));
        gc.fillRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        setupColorPicker(colorToggleGroup, colorPicker, (color) -> currentColor = color);
        setupColorPicker(colorToggleGroup2D, colorPicker2D, (color) -> currentColor2D = color);
        setupCanvas();
        setupVisibilityForSettings();
    }

    private void setupColorPicker(ToggleGroup toggleGroup, ColorPicker colorPicker, java.util.function.Consumer<String> colorConsumer) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateColorFromToggle((ToggleButton) newValue, colorConsumer);
            }
        });

        colorPicker.setOnAction(event -> {
            colorConsumer.accept(colorPicker.getValue().toString());
            if (toggleGroup.getSelectedToggle() != null) {
                toggleGroup.getSelectedToggle().setSelected(false);
            }
        });
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
        String toolText = selectedTool.getText();
       
            drawShape(toolText, event.getX(), event.getY(), false);
            if (toolText.equals("Lápis")) {
                drawPoint(event.getX(), event.getY());
            } else if (toolText.equals("Borracha")) {
                drawEraser(event.getX(), event.getY());
            } else if (toolText.equals("Spray")) {
                drawSpray(event.getX(), event.getY());
            }else{
                drawShape(toolText, event.getX(), event.getY(), true);
            }
        
    }

    private void drawShape(String toolText, double x, double y, boolean isRelease) {
        if (isRelease) {
            gc.drawImage(canvasSnapshot, 0, 0);
        }
        switch (toolText) {
            case "Linha":
                if (isRelease) drawLine(startX, startY, x, y);
                break;
            case "Lápis":
                drawPoint(x, y);
                break;
            case "Borracha":
                drawEraser(x, y);
                break;
            case "Circulo":
                if (isRelease) drawCircle(startX, startY, x, y);
                break;
            case "Retângulo":
                if (isRelease) drawRectangle(startX, startY, x, y);
                break;
            case "Cilindro":
                if (isRelease) drawCilindro(startX, startY, x, y);
                break;
            case "Pirâmide":
                if (isRelease) drawPiramide(startX, startY, x, y);
                break;
            case "Spray":
                drawSpray(x, y);
                break;
        }
    }

    private void updateColorFromToggle(ToggleButton button, java.util.function.Consumer<String> colorConsumer) {
        String buttonId = button.getId();
        switch (buttonId) {
            case "blackColorBtn":
            case "blackColorBtn2D":
                colorConsumer.accept("#000000");
                break;
            case "whiteColorBtn":
            case "whiteColorBtn2D":
                colorConsumer.accept("#FFFFFF");
                break;
            case "redColorBtn":
            case "redColorBtn2D":
                colorConsumer.accept("#FF4444");
                break;
            case "greenColorBtn":
            case "greenColorBtn2D":
                colorConsumer.accept("#44FF44");
                break;
            case "blueColorBtn":
            case "blueColorBtn2D":
                colorConsumer.accept("#4444FF");
                break;
            case "yellowColorBtn":
            case "yellowColorBtn2D":
                colorConsumer.accept("#FFFF44");
                break;
            case "magentaColorBtn":
            case "magentaColorBtn2D":
                colorConsumer.accept("#FF44FF");
                break;
            case "cyanColorBtn":
            case "cyanColorBtn2D":
                colorConsumer.accept("#44FFFF");
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
            boolean isShapeTool = selectedTool.getText().matches("Circulo|Retângulo|Cilindro|Pirâmide");
            brushSettings.setVisible(isShapeTool);
            shapeSettings.setVisible(isShapeTool);
        });
    }
}