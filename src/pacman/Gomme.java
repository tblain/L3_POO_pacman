package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Gomme {
    public static final double relative_size = 0.1;

    public static Circle getGomme()
    {
        Circle cir = new Circle();

        cir.setFill(Color.WHITE);
        cir.setRadius((ConstantesVue.SIZE_CASE_HEIGHT) * relative_size);

        return cir;
    }
}
