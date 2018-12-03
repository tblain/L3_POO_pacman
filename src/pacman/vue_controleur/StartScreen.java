package pacman.vue_controleur;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

class StartScreen {
    static final String START_SCREEN = "sprites/start_screen_logo.png";
    BorderPane borderPane;
    Scene scene;
    Button button;


    StartScreen()
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: black");

        ImageView iv = new ImageView();
        iv.setImage(SpriteManager.getImageOf(START_SCREEN));
        iv.setFitWidth(900);

        borderPane.setTop(iv);
        BorderPane.setAlignment(iv, Pos.CENTER);
        BorderPane.setMargin(iv, new Insets(0, 12, 0 ,12));

        button = new Button("PLAY");
        button.setStyle("-fx-background-color: black; -fx-font-size: 40; -fx-text-fill: White;");

        borderPane.setCenter(button);
        BorderPane.setMargin(button, new Insets(0, 12, 0, 12));

        scene = new Scene(borderPane);
        scene.setFill(Color.BLACK);
    }

}
