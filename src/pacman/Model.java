package pacman;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class Model extends Observable implements Runnable{
    public Plateau plateau;
    public int score;
    public Pacman pacman;
    public ArrayList<Monster> monsters;

    public Model() {
      plateau = new Plateau(Map1.MAP, Map1.ROW, Map1.COLUMN);

      pacman = new Pacman(new Coordonnees(10, 15), plateau);

      Monster monster1 = new Monster(new Coordonnees(4, 2), plateau);
      Monster monster2 = new Monster(new Coordonnees(16, 2), plateau);
      Monster monster3 = new Monster(new Coordonnees(11, 9), plateau);

      monsters = new ArrayList<Monster>();
      monsters.add(monster1);
      monsters.add(monster2);
      monsters.add(monster3);

      pacman.init(monsters);

      monster1.init(pacman, monsters);
      monster2.init(pacman, monsters);
      monster3.init(pacman, monsters);

    }

    @Override
    public void run() {
        while(pacman.alive)
        {
            int o = Map1.MAP[9][10]; // Le spawn du milieu des fantom
            int in = Map1.MAP[10][9]; // devrait etre un mur

            //pacman.run();
             int i = 0;
            System.out.println("-----------------------");
             for(Monster mst : this.monsters)
             {
                 System.out.println("BEFORE m" + i + ": " + mst.pos.getX() + " | y : " + mst.pos.getY());
                 mst.run();
                 System.out.println("AFTER m" + i + ": " + mst.pos.getX() + " | y : " + mst.pos.getY());
                 i++;
             }
             setChanged();

             notifyObservers();

             try {
                 TimeUnit.SECONDS.sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

        }
        setChanged();
        notifyObservers();
    }


    public Plateau getPlateau() { return plateau; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

}
