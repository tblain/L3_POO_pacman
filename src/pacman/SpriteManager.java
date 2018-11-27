package pacman;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpriteManager {

    public enum Sprite
    {
        PACMAN ("sprites/pacman.png");


        public final String url;

        Sprite(String url)
        {
            this.url = url;
        }

        public String url(){
            return url;
        }
    }

    public static ImageView getImageOf(Sprite sprite)
    {
        String url = null;

        switch (sprite)
        {
            case PACMAN:
                url = Sprite.PACMAN.url();
                break;
        }

        File file = new File(url);

        Image im = null;
        try {
            im = new Image(new FileInputStream(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView iv = new ImageView();
        iv.setImage(im);

        return iv;
    }
}
