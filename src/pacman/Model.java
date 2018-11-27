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
        // On init tout
        plateau = new Plateau(Map1.MAP, Map1.ROW, Map1.COLUMN);
        pacman = new Pacman(new Coordonnees(10, 15), plateau);

        Monster monster1 = new Monster(new Coordonnees(7, 4), plateau);
        monster1.setDir(Direction.UP);
        Monster monster2 = new Monster(new Coordonnees(16, 2), plateau);
        Monster monster3 = new Monster(new Coordonnees(11, 2), plateau);

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
        if(pacman.alive)
        {
             for(Monster mst : this.monsters)
             {
                 mst.run();
             }
             pacman.run();
             setChanged();
             notifyObservers();

             try {
                 TimeUnit.MILLISECONDS.sleep(500);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

        }
    }


    public Plateau getPlateau() { return plateau; }

    public int getScore() {
        return pacman.score;
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
