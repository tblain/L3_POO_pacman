package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Passage {

    public static Rectangle getPassage()
    {
        Rectangle rect = new Rectangle();
        rect.setFill(Color.BLACK);
        rect.setWidth(ConstantesVue.SIZE_CASE_WIDTH);
        rect.setHeight(ConstantesVue.SIZE_CASE_HEIGHT);

        return rect;
    }
}
