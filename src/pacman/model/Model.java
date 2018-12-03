package pacman.model;

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

        Monster monster1 = new Monster(new Coordonnees(7, 4), plateau, MonsterName.Shadow, 1);
        Monster monster2 = new Monster(new Coordonnees(13, 4), plateau, MonsterName.Pokey, 2);
        Monster monster3 = new Monster(new Coordonnees(10, 7), plateau, MonsterName.Speedy, 3);
        Monster monster4 = new Monster(new Coordonnees(11, 3), plateau, MonsterName.Bashful, 4);

        monster1.setDir(Direction.UP);
        monster2.setDir(Direction.UP);
        monster3.setDir(Direction.UP);
        monster4.setDir(Direction.UP);

        monsters = new ArrayList<Monster>();
        monsters.add(monster1);
        monsters.add(monster2);
        monsters.add(monster3);
        monsters.add(monster4);

        pacman.init(monsters);

        monster1.init(pacman, monsters);
        monster2.init(pacman, monsters);
        monster3.init(pacman, monsters);
        monster4.init(pacman, monsters);
    }

    @Override
    public void run() {
        if(pacman.alive)
        {
            int i = 1;
            pacman.run();
            for(Monster mst : this.monsters)
            {
                System.out.println("--------------------------------");
                System.out.println("tour de m" + i++);
                mst.run();
            }

            setChanged();
            notifyObservers();

            try {
                TimeUnit.MILLISECONDS.sleep(175);
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
