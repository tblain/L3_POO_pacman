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

        Monster monster1 = new Monster(new Coordonnees(10, 8), this, MonsterName.Shadow, 1, 0, 0);
        Monster monster2 = new Monster(new Coordonnees(9, 9), this, MonsterName.Pokey, 2, 15, 15 * Constantes.POINT_PAC_GOMME);
        //Monster monster2 = new Monster(new Coordonnees(9, 9), this, MonsterName.Pokey, 2, 0, 0 * Constantes.POINT_PAC_GOMME);
        Monster monster3 = new Monster(new Coordonnees(10, 9), this, MonsterName.Speedy, 3, 30, 30 * Constantes.POINT_PAC_GOMME);
        //Monster monster3 = new Monster(new Coordonnees(10, 9), this, MonsterName.Speedy, 3, 0, 0 * Constantes.POINT_PAC_GOMME);
        Monster monster4 = new Monster(new Coordonnees(11, 9), this, MonsterName.Bashful, 4, 45, 45 * Constantes.POINT_PAC_GOMME);
        //Monster monster4 = new Monster(new Coordonnees(11, 9), this, MonsterName.Bashful, 4, 0, 0 * Constantes.POINT_PAC_GOMME);

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
            System.out.println("\n=============== Nouveau Tour ===============\n");
            for(Monster mst : this.monsters)
            {
                if(pacman.isAlive())
                    //System.out.println("--------------------------------");
                    System.out.println("tour de m" + i++);
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
