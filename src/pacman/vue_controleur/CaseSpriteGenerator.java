package pacman.vue_controleur;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CaseSpriteGenerator {
    public static final double supergomme_relative_size = 0.35;
    public static final double gomme_relative_size = 0.1;

    public static Circle getGomme()
    {
        Circle cir = new Circle();

        cir.setFill(Color.WHITE);
        cir.setRadius((ConstantesVue.SIZE_CASE_HEIGHT) * gomme_relative_size);

        return cir;
    }

    public static Circle getSuperGomme()
    {
        Circle cir = new Circle();

        cir.setFill(Color.YELLOW);
        cir.setRadius((ConstantesVue.SIZE_CASE_HEIGHT) * supergomme_relative_size);

        return cir;
    }

    public static Rectangle getPassage()
    {
        Rectangle rect = new Rectangle();
        rect.setFill(Color.BLACK);
        rect.setWidth(ConstantesVue.SIZE_CASE_WIDTH);
        rect.setHeight(ConstantesVue.SIZE_CASE_HEIGHT);

        return rect;
    }

    public static Rectangle getWall()
    {
        Rectangle rect = new Rectangle();
        rect.setHeight(ConstantesVue.SIZE_CASE_HEIGHT);
        rect.setWidth(ConstantesVue.SIZE_CASE_WIDTH);
        rect.setFill(Color.web("#0009E1"));

        return rect;
    }
}
