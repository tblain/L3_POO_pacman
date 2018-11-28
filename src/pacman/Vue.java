package pacman;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
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

    // Utilisé pour les animations car pas threadées
    public int compteur;

    // Pour les animations des monstres
    MonsterAnimatorFactory mstAnimFact = new MonsterAnimatorFactory();

    public void setContent()
    {
        // gestion du placement (permet de placer le champ Text affichage en haut, et GridPane gPane au centre)
        border = new BorderPane();

        // permet de placer les diffrents boutons dans une grille
        gPane = new GridPane();

        border.setCenter(gPane);

        scene = new Scene(border, Color.BLACK);
    }

    public void setEvent()
    {
        // Contrôle pour Pacman
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                switch (ke.getCode()) {
                    case RIGHT:
                        m.pacman.setDir(Direction.RIGHT);
                        break;

                    case LEFT:
                        m.pacman.setDir(Direction.LEFT);
                        break;

                    case UP:
                        m.pacman.setDir(Direction.UP);
                        break;

                    case DOWN:
                        m.pacman.setDir(Direction.DOWN);
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
        compteur = 1;

        // la vue observe les "update" du "?", et réalise les mises à jour graphiques
        m.addObserver((o, arg) -> Platform.runLater(this::afficherPlateau));

        afficherPlateau();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while(m.getPacman().alive)
                {
                    m.run();
                    compteur += 1;
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
        // Un passage par défaut


        if(pacman.alive)
        {
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
                    Case c = p.getCase(i, j);
                    Node node = node  = Passage.getPassage();
                    if (c.isSuperGomme()) {
                        node = SuperGomme.getSuperGomme();
                    }
                    else if (c.isGomme()) {
                        node = Gomme.getGomme();
                    }
                    else if (c.isMur()) {
                        node = Wall.getWall();
                    }


                    gPane.add(node, i, j);
                }
            }
            for (Monster monster : monsters) {
                gPane.add(mstAnimFact.getAnimatorOfMonster(monster.name).getImageView(monster.getDir(), pacman.remainingTimeForSuperPacGomme != 0)
                        , monster.pos.getX(), monster.pos.getY());
            }

            gPane.add(PacmanAnimator.getImageView(pacman.getDir(),this.compteur % 2 == 0), pacman.pos.getX(), pacman.pos.getY());

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
