package pacman;

import javafx.print.PageLayout;

import java.util.ArrayList;

public class Pacman extends Movable {
    protected Direction dir;
    protected ArrayList<Monster> monsters;
    protected boolean init;

    // Variable qui correspond au temps restant sous l'état super pac gomme, décrémenté à chaque tic
    // Si 0 alors le pacman n'est pas sous cet état
    protected int remainingTimeForSuperPacGomme;


    public Pacman(Coordonnees pos, Plateau plateau)
    {
        super(pos, plateau);
        this.dir = Direction.LEFT;
        this.init = false;
        this.remainingTimeForSuperPacGomme = 0;
    }

    public void init(ArrayList<Monster> monsters)
    {
        this.monsters = monsters;
        this.init = true;
    }

    @Override
    public void run() {
        // On gére le temps restant pour le super pac gomme
        if(this.remainingTimeForSuperPacGomme != 0)
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
                if(this.monsters.get(i).pos == this.pos)
                {
                    monster = this.monsters.get(i);
                    // Pour quitter la boucle si sur un monstre dans la même case
                    i = this.monsters.size();
                }
            }

            // Si on peut manger un monster
            if(monster != null)
            {
                monster.alive = false;
            }
        }
        // TODO

        // On gère le cas où on peut mange un gomme


        // On met à jour la position si pas de mur devant nous
    }

    public void setDirection(Direction dir)
    {
        this.dir = dir;
    }

    public Direction getDirection()
    {
        return this.dir;
    }

    public Coordonnees getNextMove()
    {
        Coordonnees nextPos = this.pos;
        switch (this.dir)
        {
            case UP:
            {
                nextPos.x++;
            }
            case DOWN:
            {
                nextPos.x--;
            }
            case LEFT:
            {
                nextPos.y--;
            }
            case RIGHT:
            {
                nextPos.y++;
            }

        }
        return nextPos;
    }
}
