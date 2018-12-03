package pacman.vue_controleur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpriteManager {


    public static Image getImageOf(String spriteUrl)
    {
        File file = new File(spriteUrl);

        Image im = null;
        try {
            im = new Image(new FileInputStream(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return im;
    }
}
