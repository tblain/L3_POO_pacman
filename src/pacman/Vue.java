package pacman;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class Vue extends Application  {


    @Override
    public void start(Stage primaryStage) {

        // gestion du placement (permet de placer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();

        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();

        Model m = new Model();

        // la vue observe les "update" du "?", et réalise les mises à jour graphiques

        m.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                System.out.println("    update");
                Platform.runLater(() -> {
                    afficherPlateau((Model)o, gPane);
                });
                System.out.println("    fin update");
            }
        });

        //gPane.setGridLinesVisible(true);

        afficherPlateau(m, gPane);

        border.setCenter(gPane);

        Scene scene = new Scene(border, Color.LIGHTBLUE);

        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Platform.runLater(m);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                m.run();
                return null;
            }
        };
        new Thread(task).start();
    }

    public void afficherPlateau(Model m, GridPane gPane) {
        gPane.getChildren().clear();
        int column = 21;
        int row = 21;
        Plateau p = m.getPlateau();
        Pacman pacman = m.getPacman();
        ArrayList<Monster> monsters = m.getMonsters();

        for (int i = 0; i < row; i++)
        {
            System.out.println("        i : " + i);
            for (int j = 0; j < column; j++)
            {
                System.out.println("        j : " + j);
                Case c = p.getCase(i, j);
                String stringCase = "";

                if (c.isSuperGomme()) {
                    stringCase = " * ";
                }
                else if (c.isGomme()) {
                    stringCase = " . ";
                }
                else if (c.isPassage()) {
                    stringCase = "   ";
                }
                else if (c.isMur()) {
                    stringCase = "[|]";
                }

                final Text text = new Text(stringCase);
                gPane.add(text, j, i);
            }
        }
        for (Monster monster : monsters) {
            gPane.add(new Text("M"), monster.pos.getX(), monster.pos.getY());
        }
        gPane.add(new Text("C"), pacman.pos.getX(), pacman.pos.getY());
        System.out.println("    PLateau chargé  ");

    }



    public static void main(String[] args) {
        launch(args);
    }

}
