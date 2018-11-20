package pacman;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class Model extends Observable implements Runnable{
    public Plateau plateau;
    public int score;
    public Pacman pacman;
    public ArrayList<Monster> monsters;

    public Model() {
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

      pacman = new Pacman(new Coordonnees(10, 15), plateau);

      Monster monster1 = new Monster(new Coordonnees(9, 9), plateau);
      Monster monster2 = new Monster(new Coordonnees(10, 9), plateau);
      Monster monster3 = new Monster(new Coordonnees(11, 9), plateau);

      monsters = new ArrayList<Monster>();
      monsters.add(monster1);
      monsters.add(monster2);
      monsters.add(monster3);

      pacman.init(monsters);

      monster1.init(pacman);
      monster2.init(pacman);
      monster3.init(pacman);

    }

    @Override
    public void run() {
        while(pacman.alive)
        {
          System.out.println("model run");
          System.out.println("  pacman run");
          pacman.run();
          System.out.println("  pacman end : " + pacman.pos.getX() + " " + pacman.pos.getY());
            //pacman.pos.setX(8);
          setChanged();
            System.out.println("  setchanged end");
            notifyObservers();
          System.out.println("  notify end");

          System.out.println("model end");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("|||||||||||||||||||||||");
        System.out.println("pacman mort");
        setChanged();
        notifyObservers();
    }


    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }
}
