package com.view;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.util.*;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ColorManager {
    private final ToggleGroup colorToggleGroup;
    private final ColorPicker colorPicker;
    private String selectedColor = "#000000";

    private static final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("blackColorBtn", "#000000");
        COLOR_MAP.put("whiteColorBtn", "#FFFFFF");
        COLOR_MAP.put("redColorBtn", "#FF4444");
        COLOR_MAP.put("greenColorBtn", "#44FF44");
        COLOR_MAP.put("blueColorBtn", "#4444FF");
        COLOR_MAP.put("yellowColorBtn", "#FFFF44");
        COLOR_MAP.put("magentaColorBtn", "#FF44FF");
        COLOR_MAP.put("cyanColorBtn", "#44FFFF");

                COLOR_MAP.put("blackColorBtn2D", "#000000");
        COLOR_MAP.put("whiteColorBtn2D", "#FFFFFF");
        COLOR_MAP.put("redColorBtn2D", "#FF4444");
        COLOR_MAP.put("greenColorBtn2D", "#44FF44");
        COLOR_MAP.put("blueColorBtn2D", "#4444FF");
        COLOR_MAP.put("yellowColorBtn2D", "#FFFF44");
        COLOR_MAP.put("magentaColorBtn2D", "#FF44FF");
        COLOR_MAP.put("cyanColorBtn2D", "#44FFFF");
    }

    public ColorManager(ToggleGroup colorToggleGroup, ColorPicker colorPicker, Consumer<String> onColorChange) {
        this.colorToggleGroup = colorToggleGroup;
        this.colorPicker = colorPicker;

        setupColorSelection(onColorChange);
    }

    private void setupColorSelection(Consumer<String> onColorChange) {
        colorToggleGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) newVal;
                selectedColor = COLOR_MAP.getOrDefault(button.getId(), "#000000");
                onColorChange.accept(selectedColor);
            }
        });

        colorPicker.setOnAction(event -> {
            selectedColor = toHex(colorPicker.getValue());
            onColorChange.accept(selectedColor);
            if (colorToggleGroup.getSelectedToggle() != null) {
                colorToggleGroup.getSelectedToggle().setSelected(false);
            }
        });
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
