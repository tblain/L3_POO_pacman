package pacman;

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
    public void run() {

        // On récupère la case sur laquelle pacman est
        Case cas = this.plateau.getCase(this.pos);

        // On gére le temps restant pour le super pac gomme
        if(this.remainingTimeForSuperPacGomme > 0)
        {
            this.remainingTimeForSuperPacGomme--;
        }

        // On gère le cas où on peut manger un Monster
        if(this.remainingTimeForSuperPacGomme > 0)
        {
            Monster monster = null;
            int i = 0;

            while(i < this.monsters.size())
            {
                if(this.monsters.get(i).pos.equals(this.getPos()))
                {
                    monster = this.monsters.get(i);

                    // Pour quitter la boucle si sur un monstre dans la même case
                    i = this.monsters.size();
                }
                i++;
            }

            // Si on peut manger un monster
            if(monster != null)
            {
                monster.alive = false;
                this.monsters.remove(monster);
                this.score += Constantes.POINT_ON_EAT_MONSTER;
            }
        }
        // TODO

        // On gère le cas où on peut manger une gomme
        if(cas.isGomme())
        {
            this.score += Constantes.POINT_PAC_GOMME;
            // On enlève la gomme
            cas.setGomme(false);
            plateau.setCase(cas);
        }

        // On gère le cas où l'on peut manger une super gomme
        if(cas.isSuperGomme())
        {
            this.remainingTimeForSuperPacGomme = Constantes.TIME_SUPER_PAC_GOMME;
            cas.setSuperGomme(false);
            plateau.setCase(cas);
        }

        // On met à jour la position si pas de mur devant nous
        Coordonnees nextMove = this.getNextPosition();
        if(!this.plateau.getCase(nextMove).isMur())
        {
            this.pos = nextMove;
        }
    }
}
