package com.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorPalette {
    @FXML
    private ToggleGroup colorToggleGroup;
    @FXML
    private ColorPicker colorPicker;

    private String selectedColor = "#000000";

    private static final Map<String, String> COLOR_MAP = new HashMap<>();

    @FXML
    private void initialize() {
        setupColorPicker(colorToggleGroup, colorPicker, color -> selectedColor = color);

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

    private void updateColorFromToggle(ToggleButton button, java.util.function.Consumer<String> colorConsumer) {
        String buttonId = button.getId();
        selectedColor = COLOR_MAP.get(buttonId);
    }

    static {
        COLOR_MAP.put("blackColorBtn", "#000000");
        COLOR_MAP.put("whiteColorBtn", "#FFFFFF");
        COLOR_MAP.put("redColorBtn", "#FF4444");
        COLOR_MAP.put("greenColorBtn", "#44FF44");
        COLOR_MAP.put("blueColorBtn", "#4444FF");
        COLOR_MAP.put("yellowColorBtn", "#FFFF44");
        COLOR_MAP.put("magentaColorBtn", "#FF44FF");
        COLOR_MAP.put("cyanColorBtn", "#44FFFF");
    }
}
