package com.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import lombok.Getter;

@Getter
public class ToolGroup {
    @FXML
    private ToggleGroup toolsGroup;
    @FXML
    private VBox brushSettings;
    @FXML
    private VBox shapeSettings;
    @FXML
    private Slider thicknessSlider;
    @FXML
    private CheckBox area;

    private ColorPalette2D paletteController2D;

    @FXML
    public void initialize() {
        setupVisibilityForSettings();
    }

    private void addColor2D() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/colorPalette2D.fxml"));
            VBox paletteContent2D = loader.load();
            paletteController2D = loader.getController();
            brushSettings.getChildren().add(paletteContent2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupVisibilityForSettings() {
        toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            ToggleButton selectedTool = (ToggleButton) newValue;
            Ferramenta ferramenta = Ferramenta.porNome(selectedTool.getText());
            brushSettings.getChildren().clear();
            if (ferramenta.ehPreenchida()) {
                addColor2D();
            }
            shapeSettings.setVisible(ferramenta.ehForma());
        });
    }

    public String getSelectedColor2D() {
        return paletteController2D.getSelectedColor();
    }

    public boolean isFillShape() {
        return paletteController2D.isFillShape();
    }

    public double getThickness() {
        return thicknessSlider.getValue();
    }

    public boolean isArea() {
        return area.isSelected();
    }

    public Ferramenta getSelectedTool() {
        ToggleButton selectedTool = (ToggleButton) toolsGroup.getSelectedToggle();
        return Ferramenta.porNome(selectedTool.getText());
    }
}
