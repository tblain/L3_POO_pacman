package pacman.vue_controleur;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameOver {
    BorderPane borderpane;
    Scene scene;
    Button quitButt;
    Button replayButt;

    GameOver()
    {
        borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: black");

        quitButt = new Button("QUITTER");
        quitButt.setStyle("-fx-background-color: black; -fx-font-size: 40; -fx-text-fill: White;");

        replayButt = new Button("PLAY AGAIN");
        replayButt.setStyle("-fx-background-color: black; -fx-font-size: 40; -fx-text-fill: White;");

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().add(replayButt);
        vb.getChildren().add(quitButt);

        borderpane.setCenter(vb);
        BorderPane.setMargin(vb, new Insets(0,12,0,12));

        scene = new Scene(borderpane, Color.BLACK);
    }

}
