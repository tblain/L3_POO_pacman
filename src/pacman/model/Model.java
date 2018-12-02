package pacman.model;

import java.util.ArrayList;
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

        Monster monster1 = new Monster(new Coordonnees(7, 4), plateau, MonsterName.Shadow);
        monster1.setDir(Direction.UP);
        Monster monster2 = new Monster(new Coordonnees(13, 4), plateau, MonsterName.Pokey);
        Monster monster3 = new Monster(new Coordonnees(10, 7), plateau, MonsterName.Speedy);

        monster1.setDir(Direction.UP);
        monster2.setDir(Direction.UP);
        monster3.setDir(Direction.UP);

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

            System.out.println("\n=============== Nouveau Tour ===============\n");
            for(Monster mst : this.monsters)
            {
                //System.out.println("tour de m" + i++);
                mst.run();
            }
            pacman.run();

            setChanged();
            notifyObservers();

            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Fin de tour");

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
