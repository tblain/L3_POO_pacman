package pacman;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Vue extends Application  {
    public Model m;
    public GridPane gPane;
    public BorderPane border;
    public Scene scene;

    public void setContent()
    {
        // gestion du placement (permet de placer le champ Text affichage en haut, et GridPane gPane au centre)
        border = new BorderPane();

        // permet de placer les diffrents boutons dans une grille
        gPane = new GridPane();

        border.setCenter(gPane);

        scene = new Scene(border, Color.LIGHTBLUE);
    }

    public void setEvent()
    {
        // Contrôle pour Pacman
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                switch (ke.getCode()) {
                    case RIGHT:
                        m.pacman.setDir(Direction.RIGHT);
                        System.out.println("vers la droite");
                        break;

                    case LEFT:
                        m.pacman.setDir(Direction.LEFT);
                        System.out.println("vers la gauche");
                        break;

                    case UP:
                        m.pacman.setDir(Direction.UP);
                        System.out.println("vers le haut");
                        break;

                    case DOWN:
                        m.pacman.setDir(Direction.DOWN);
                        System.out.println("vers le bas");
                        break;
                }
            }
        });

    }


    @Override
    public void start(Stage primaryStage) {

        // Init
        setContent();
        m = new Model();
        setEvent();

        // la vue observe les "update" du "?", et réalise les mises à jour graphiques
        m.addObserver((o, arg) -> Platform.runLater(this::afficherPlateau));

        afficherPlateau();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while(m.getPacman().alive)
                {
                    m.run();
                    if(isCancelled())
                    {
                        break;
                    }
                }
                afficherPlateau();
                return null;
            }
        };


        // Pour arrêter le thread qui calcul le model
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                task.cancel();
            }
        });

        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(task).start();

    }

    public void afficherPlateau() {
        gPane.getChildren().clear();

        int column = Map1.COLUMN;
        int row = Map1.ROW;

        Plateau p = m.getPlateau();
        Pacman pacman = m.getPacman();
        ArrayList<Monster> monsters = m.getMonsters();

        if(pacman.alive)
        {
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
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
                    gPane.add(text, i, j);
                }
            }
            for (Monster monster : monsters) {
                gPane.add(new Text("M"), monster.pos.getX(), monster.pos.getY());
            }

            gPane.add(new Text("P"), pacman.pos.getX(), pacman.pos.getY());

        }
        else
        {
            gPane.add(new Text("Fin de la partie"), m.plateau.nbColonnes/2, m.plateau.nbLignes/2);
        }

    }



    public static void main(String[] args) {
        launch(args);
    }

}
