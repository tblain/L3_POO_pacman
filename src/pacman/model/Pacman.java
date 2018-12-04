package pacman.model;

import pacman.model.Constantes;
import pacman.model.Coordonnees;
import pacman.model.Direction;
import pacman.model.Monster;

import java.util.ArrayList;

public class Pacman extends Movable {
    protected ArrayList<Monster> monsters;
    protected int score;

    // Variable qui correspond au temps restant sous l'état super pac gomme, décrémenté à chaque tic
    // Si 0 alors le pacman n'est pas sous cet état
    protected int remainingTimeForSuperPacGomme;


    public Pacman(Coordonnees pos, Plateau plateau)
    {
        super(pos, plateau, Direction.LEFT);
        this.remainingTimeForSuperPacGomme = 0;
        this.score = 0;
    }

    public void init(ArrayList<Monster> monsters)
    {
        this.monsters = monsters;
    }

    @Override
    public void run()
    {

        // On récupère la case sur laquelle pacman est
        Case currentCase = this.plateau.getCase(this.getPos());

        // On gére le temps restant pour le super pac gomme
        if(this.remainingTimeForSuperPacGomme > 0)
        {
            this.remainingTimeForSuperPacGomme--;
        }

        // On gère le cas où on peut manger un Monster avant le déplacement
        killMonster();

        // On gère le cas où on peut manger une gomme
        if(currentCase.isGomme())
        {
            this.score += Constantes.POINT_PAC_GOMME;
            currentCase.setGomme(false);
        }

        // On gère le cas où l'on peut manger une super gomme
        if(currentCase.isSuperGomme())
        {
            this.remainingTimeForSuperPacGomme = Constantes.TIME_SUPER_PAC_GOMME;
            currentCase.setSuperGomme(false);
        }

        // On met à jour la position si pas de mur devant nous
        Coordonnees nextMove = this.getNextPosition();

        if(currentCase.isTp())
        {
            this.pos = currentCase.teleportTo(currentCase.getCoordonnees());
        }
        else
        {
            if(!this.plateau.getCase(nextMove).isMur())
            {
                this.pos = nextMove;
            }
        }

        // On gère le cas où on peut manger un Monster après le déplacement
        killMonster();
    }

    public void killMonster()
    {
        if(this.remainingTimeForSuperPacGomme > 0)
        {

            // Les monstres peuvent se stacker sur la même case donc on les parcours tous
            for(Monster monster : monsters)
            {
                if(monster.pos.equals(this.getPos()) && monster.isAlive())
                {
                    monster.remainingDeathTime = Constantes.MONSTER_REMAINING_DEATH_TIME;
                    monster.setAlive(false);
                    this.score += Constantes.POINT_ON_EAT_MONSTER;
                    System.out.println("BOOM one shot !!!!");
                }
            }
        }
    }

    /**
     * On met à jour la Direction du Pacman seulement s'il ne regardera pas un mur
     * @param dir
     */
    public void setDir(Direction dir)
    {
        if(!plateau.getCase(this.getNextPosition(dir)).isMur())
        {
            this.dir = dir;
        }
    }

    public int getRemainingTimeForSuperPacGomme()
    {
        return this.remainingTimeForSuperPacGomme;
    }

    public int getScore()
    {
        return this.score;
    }
}
