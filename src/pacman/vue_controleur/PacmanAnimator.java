package pacman.vue_controleur;


import javafx.scene.image.ImageView;
import pacman.model.Direction;

public class PacmanAnimator {
    public static final String RIGHT_MOUSE_OPEN = "sprites/pacman/pacman_mouse_open.png";
    public static final String RIGHT_MOUSE_CLOSE = "sprites/pacman/pacman.png";

    public static ImageView getImageView(Direction dir, boolean mouse_open)
    {
        int rotation = 0;

        switch (dir)
        {
            case DOWN:
                rotation = 90;
                break;
            case LEFT:
                rotation = 180;
                break;
            case UP:
                rotation = 270;
                break;
        }

        ImageView iv = new ImageView();

        if (mouse_open)
        {
            iv.setImage(SpriteManager.getImageOf(RIGHT_MOUSE_OPEN));
        }
        else
        {
            iv.setImage(SpriteManager.getImageOf(RIGHT_MOUSE_CLOSE));
        }
        iv.setRotate(rotation);

        iv.setFitWidth(ConstantesVue.SIZE_CASE_WIDTH);
        iv.setFitHeight(ConstantesVue.SIZE_CASE_HEIGHT);

        return iv;
    }
}
