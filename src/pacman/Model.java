package pacman;

import java.util.Observable;

public class Model extends Observable implements Runnable{
    public Plateau plateau;
    public int score;

    public Model() {
    }

    public void init() {
        // 1 = mur, 2 = passage, 3 = gomme, 4 = superGomme
        int[][] tabInit = { {0, 1, 1, 1, 1, 1, 1, 1, 1, 1,  1,  1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                            {0, 1, 3, 3, 3, 3, 3, 3, 3, 3,  1,  3, 3, 3, 3, 3, 3, 3, 3, 1, 0},
                            {0, 1, 4, 1, 1, 3, 1, 1, 1, 3,  3,  3, 1, 1, 1, 3, 1, 1, 4, 1, 0},
                            {0, 1, 3, 3, 3, 3, 3, 3, 3, 3,  1,  3, 3, 3, 3, 3, 3, 3, 3, 1, 0},
                            {0, 1, 3, 1, 1, 3, 1, 3, 1, 1,  1,  1, 1, 3, 1, 3, 1, 1, 3, 1, 0},
                            {0, 1, 3, 3, 3, 3, 1, 3, 3, 3,  1,  3, 3, 3, 1, 3, 3, 3, 3, 1, 0},
                            {0, 1, 1, 1, 1, 3, 1, 1, 1, 0,  1,  0, 1, 1, 1, 3, 1, 1, 1, 1, 0},
                            {0, 0, 0, 0, 1, 3, 1, 0, 0, 0,  0,  0, 0, 0, 1, 3, 1, 0, 0, 0, 0},
                            {0, 1, 1, 1, 1, 3, 1, 0, 1, 1,  0,  1, 1, 0, 1, 3, 1, 1, 1, 1, 1},

                            {0, 0, 0, 0, 0, 3, 0, 0, 1, 0,  0,  0, 1, 0, 0, 3, 0, 0, 0, 0, 0},

                            {0, 1, 1, 1, 1, 3, 1, 0, 1, 1,  1,  1, 1, 0, 1, 3, 1, 1, 1, 1, 1},
                            {0, 0, 0, 0, 1, 3, 1, 0, 0, 0,  0,  0, 0, 0, 1, 3, 1, 0, 0, 0, 0},
                            {0, 1, 1, 1, 1, 3, 1, 0, 1, 1,  1,  1, 1, 0, 1, 3, 1, 1, 1, 1, 0},
                            {0, 1, 3, 3, 3, 3, 3, 3, 3, 3,  1,  3, 3, 3, 3, 3, 3, 3, 3, 1, 0},
                            {0, 1, 3, 1, 1, 3, 1, 1, 1, 3,  1,  3, 1, 1, 1, 3, 1, 1, 3, 1, 0},
                            {0, 1, 4, 3, 1, 3, 3, 3, 3, 3,  2,  3, 3, 3, 3, 3, 1, 3, 4, 1, 0},
                            {0, 1, 1, 3, 1, 3, 1, 1, 1, 1,  1,  1, 1, 3, 1, 3, 1, 3, 1, 1, 0},
                            {0, 1, 3, 3, 3, 3, 1, 3, 3, 3,  1,  3, 3, 3, 1, 3, 3, 3, 3, 1, 0},
                            {0, 1, 3, 1, 1, 1, 1, 1, 1, 3,  1,  1, 1, 1, 1, 1, 1, 1, 3, 1, 0},
                            {0, 1, 3, 3, 3, 3, 3, 3, 3, 3,  3,  3, 3, 3, 3, 3, 3, 3, 3, 1, 0},
                            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1,  1,  1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                          };
        plateau = new Plateau(tabInit, 21, 21);
    }

    @Override
    public void run() {
//        while(actif) {
//            réaliserAction() ;
//            setChanged(); // notification de la vue, (4) sur le schéma MVC ci-dessous
//            notifyObservers();
//            sleep(tempsEntreActions) ; /* par exemple, Pac-Man est plus rapide durant quelques secondes
//                                          après avoir mangé une super-pac-gomme, tempsEntreActions peut varier */
//        }
//        grille.retirerDeLEnvironnement(this) ;
//        setChanged(); // notification de la vue
//        notifyObservers();
    }
}
