package pacman.vue_controleur;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pacman.model.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Vue extends Application  {
    public Model m;
    public GridPane gPane;
    public BorderPane border;
    public Scene scene;
    public Label labelScore;
    public Label label_labelScore;
    public Label labelNiveau;
    public Label label_labelNiveau;
    public Label labelSuperGommeRestant;
    public Label label_labelSuperGommeRestant;

    public Stage primaryStage;


    // Utilisé pour les animations car pas threadées
    public int compteur;

    // Pour les animations des monstres
    MonsterAnimatorFactory mstAnimFact = new MonsterAnimatorFactory();

    // Thread pour model
    Task<Void> task;

    public void setContent()
    {
        // gestion du placement (permet de placer le champ Text affichage en haut, et GridPane gPane au centre)
        border = new BorderPane();
        border.setStyle("-fx-background-color: black");

        // permet de placer les diffrents boutons dans une grille
        gPane = new GridPane();

        border.setCenter(gPane);
        BorderPane.setMargin(gPane, new Insets(100,100,0,125));

        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_LEFT);

        // affichage pour le score
        labelScore = new Label();
        labelScore.setStyle("-fx-text-fill: white;-fx-font-size: 20");

        label_labelScore = new Label("Score : ");
        label_labelScore.setStyle("-fx-text-fill: white;-fx-font-size: 20");

        hb.getChildren().add(label_labelScore);
        HBox.setMargin(label_labelScore, new Insets(5,0,0,5));

        hb.getChildren().add(labelScore);
        HBox.setMargin(labelScore, new Insets(5,0,0,5));

        // affichage pour le niveau
        labelNiveau = new Label();
        labelNiveau.setStyle("-fx-text-fill: white;-fx-font-size: 20");

        label_labelNiveau = new Label("Niveau : ");
        label_labelNiveau.setStyle("-fx-text-fill: white;-fx-font-size: 20");

        hb.getChildren().add(label_labelNiveau);
        HBox.setMargin(label_labelNiveau, new Insets(5,0,0,5));

        hb.getChildren().add(labelNiveau);
        HBox.setMargin(labelNiveau, new Insets(5,0,0,5));

        // affichage pour le temps de super gomme restant
        labelSuperGommeRestant = new Label();
        labelSuperGommeRestant.setStyle("-fx-text-fill: white;-fx-font-size: 20");

        label_labelSuperGommeRestant = new Label("Temps Super-Gomme Restant : ");
        label_labelSuperGommeRestant.setStyle("-fx-text-fill: white;-fx-font-size: 20");

        hb.getChildren().add(label_labelSuperGommeRestant);
        HBox.setMargin(label_labelSuperGommeRestant, new Insets(5,0,0,5));

        hb.getChildren().add(labelSuperGommeRestant);
        HBox.setMargin(labelSuperGommeRestant, new Insets(5,0,0,5));

        border.setTop(hb);

        scene = new Scene(border, Color.BLACK);
    }

    public class PacmanDirectionHandler implements EventHandler<KeyEvent>
    {

        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
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
    }

    public void setEvent()
    {
        // Contrôle pour Pacman setOnKeyReleased
        // On key pressed et Released pour meilleur ergo
        scene.setOnKeyPressed(new PacmanDirectionHandler());
        scene.setOnKeyReleased(new PacmanDirectionHandler());

    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        StartScreen start_screen = new StartScreen();

        start_screen.button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startGame(primaryStage);
            }
        });

        primaryStage.setTitle("Pacman");
        primaryStage.setScene(start_screen.scene);
        primaryStage.setHeight(ConstantesVue.HEIGHT_STAGE);
        primaryStage.setWidth(ConstantesVue.WIDTH_STAGE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void startGame(Stage primaryStage)
    {
        // Init
        setContent();
        m = new Model();
        setEvent();
        compteur = 1;

        // la vue observe les "update" du "model", et réalise les mises à jour graphiques
        m.addObserver((o, arg) -> {
            if(m.pacman.isAlive()) {
                Platform.runLater(this::afficherPlateau);
            }
            else {
                System.out.println("end of game");
                Platform.runLater(this::endOfGame);
            }
        });

        afficherPlateau();

        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while(true)
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

        primaryStage.setScene(scene);

        new Thread(task).start();

    }

    public void afficherPlateau() {
        gPane.getChildren().clear();

        int column = m.plateau.getNbColonnes();
        int row = m.plateau.getNbLignes();

        Plateau p = m.getPlateau();
        Pacman pacman = m.getPacman();
        ArrayList<Monster> monsters = m.getMonsters();


        // On met à jour le score
        labelScore.setText(Integer.toString(pacman.getScore()));
        labelNiveau.setText(Integer.toString(m.niveau));
        labelSuperGommeRestant.setText(Integer.toString(pacman.getRemainingTimeForSuperPacGomme()));

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                Case c = p.getCase(i, j);
                Node node = node  = CaseSpriteGenerator.getPassage();
                if (c.isSuperGomme()) {
                    node = CaseSpriteGenerator.getSuperGomme();
                    // On recentre la gomme dans la case
                    GridPane.setMargin(node, new Insets(0,0,0,5));
                }
                else if (c.isGomme()) {
                    node = CaseSpriteGenerator.getGomme();
                    GridPane.setMargin(node, new Insets(0,0,0,12));
                }
                else if (c.isMur()) {
                    node = CaseSpriteGenerator.getWall();
                }


                gPane.add(node, i, j);

            }
        }
        for (Monster monster : monsters) {

            gPane.add(mstAnimFact.getAnimatorOfMonster(monster.name).getImageView(monster.getDir(),pacman.getRemainingTimeForSuperPacGomme() != 0,!monster.isAlive())
                    , monster.getPos().getX(), monster.getPos().getY());
        }

        gPane.add(PacmanAnimator.getImageView(pacman.getDir(),this.compteur % 2 == 0), pacman.getPos().getX(), pacman.getPos().getY());

    }

    public void endOfGame()
    {
        afficherPlateau();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameOver gameOver = new GameOver(m.pacman.getScore());

        gameOver.replayButt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startGame(primaryStage);
            }
        });

        gameOver.quitButt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                Platform.exit();
            }
        });
        // On arrête le thread du model
        task.cancel();
        this.primaryStage.setScene(gameOver.scene);
    }





    public static void main(String[] args) {
        launch(args);
    }

}
