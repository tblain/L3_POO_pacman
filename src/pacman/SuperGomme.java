package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SuperGomme {
    public static final double relative_size = 0.35;

    public static Circle getSuperGomme()
    {
        Circle cir = new Circle();

        cir.setFill(Color.YELLOW);
        cir.setRadius((ConstantesVue.SIZE_CASE_HEIGHT) * relative_size);

        return cir;
    }
}
