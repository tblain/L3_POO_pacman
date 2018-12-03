package pacman.vue_controleur;


import javafx.scene.image.ImageView;
import pacman.model.Direction;

public class MonsterAnimator {
    public String DOWN;
    public String UP;
    public String LEFT;
    public String RIGHT;

    public String SUPER_GOMME;
    public String DEAD;

    public MonsterAnimator(String DOWN, String UP, String LEFT, String RIGHT, String SUPER_GOMME, String DEAD) {
        this.DOWN = DOWN;
        this.UP = UP;
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.SUPER_GOMME = SUPER_GOMME;
        this.DEAD = DEAD;
    }

    public ImageView getImageView(Direction dir, boolean supergomme, boolean dead)
    {
        // DOWN par défaut
        String chosen = DOWN;

        if(supergomme)
        {
            chosen = SUPER_GOMME;
        } else if(dead) {
          chosen = DEAD;
        } else
        {
            switch (dir)
            {
                case UP:
                {
                    chosen = UP;
                    break;
                }
                case DOWN:
                {
                    chosen = DOWN;
                    break;
                }
                case RIGHT:
                {
                    chosen = RIGHT;
                    break;
                }
                case LEFT:
                {
                    chosen = LEFT;
                }
            }
        }

        ImageView iv = new ImageView();
        iv.setImage(SpriteManager.getImageOf(chosen));

        iv.setFitHeight(ConstantesVue.SIZE_CASE_HEIGHT);
        iv.setFitWidth(ConstantesVue.SIZE_CASE_WIDTH);

        return iv;
    }
}
