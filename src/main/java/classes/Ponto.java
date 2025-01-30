package classes;

import lombok.Setter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;

@Setter
@AllArgsConstructor
public class Ponto {
    private double x;
    private double y;
    private String color;
    private double thickness;

   public void desenhar(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.web(color));
        gc.fillOval(x - thickness/2, y - thickness/2, thickness, thickness);
        gc.restore();
    }
}
