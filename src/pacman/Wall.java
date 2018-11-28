package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall {

    public static Rectangle getWall()
    {
        Rectangle rect = new Rectangle();
        rect.setHeight(ConstantesVue.SIZE_CASE_HEIGHT);
        rect.setWidth(ConstantesVue.SIZE_CASE_WIDTH);
        rect.setFill(Color.web("#0009E1"));

        return rect;
    }
}
