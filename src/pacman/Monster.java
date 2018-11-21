package pacman;

import java.util.ArrayList;

public class Monster extends Movable {
    protected Pacman pacman;

    // Savoir où ils sont pour ne pas aller sur les mêmes cases qu'eux
    protected ArrayList<Monster> othersMonsters;

    public Monster(Coordonnees pos, Plateau plateau)
    {
        super(pos, plateau, Direction.RIGHT);
        this.othersMonsters = new ArrayList<>();
    }
    
    public void init(Pacman pacman, ArrayList<Monster> othersMonsters) {
        this.pacman = pacman;

        for(Monster mst : othersMonsters)
        {
            // On garde seulement les autres monstres et pas nous
            if(mst != this)
            {
                this.othersMonsters.add(mst);
            }
        }
    }

    @Override
    public void run() {
        // On test si pacman est sur notre case, si oui on peut le manger
        // ssi il n'est pas sous forme super gomme
        if(pacman.pos.equals(this.pos))
        {
            if(pacman.remainingTimeForSuperPacGomme == 0)
            {
                // On mange pacman
                pacman.alive = false;
            }
        }
        // On met à jour notre position

        Coordonnees nextPossibleCoord = this.getAI();
        if(nextPossibleCoord != null)
        {
            this.pos = nextPossibleCoord;
        }
    }

    /**
     * Retourne les coordonnées de la position suivante, null si aucune position possible
     * @return
     */
    public Coordonnees getAI() {
        Coordonnees nextCoord = this.getNextPosition();
        ArrayList<Coordonnees> availableCoord = getAvailableCoord();
        Coordonnees res = null;

        // Check si notre position suivante en fonction de la direction est dans availableCoord, si oui
        // y aller => donc continuer dans la même direction
        boolean sameDir = false;
        for(Coordonnees coord : availableCoord)
        {
            if(coord.equals(nextCoord))
            {
                sameDir = true;
            }
        }

        if(sameDir)
        {
            res = nextCoord;
        }
        // sinon choisir aléatoirement un autre direction
        // en privilègiant celle qui ne nous feras pas retourner sur nos pas
        else
        {
            // On enlève la coordonnée nous faisant revenir sur notre position précédente
            Coordonnees lastPosition = this.getLastPosition();
            for(Coordonnees coord: availableCoord)
            {
                if(coord.equals(lastPosition))
                {
                    availableCoord.remove(coord);
                }
            }

            // On choisi aléatoirement entre la ou les directions restantes
            // donc entre 0 et availableCoord.size() si il en reste
            if(availableCoord.size() > 0)
            {
                int nombreAleatoire = (int) (Math.random() * ((availableCoord.size())));

                res = availableCoord.get(nombreAleatoire);
            }

        }

        return res;
    }

    /**
     * Retourne dans une ArrayList les coordonnées des caches atteignables(pas de mur)
     * @return
     */
    public ArrayList<Coordonnees> getAvailableCoord()
    {
        ArrayList<Coordonnees> availableCoord = new ArrayList<>();

        // On test les 4 possibilités
        Case caseToTest = this.plateau.getCase(this.pos.x++,this.pos.y);
        if(!caseToTest.isMur() && !this.monsterOnThisCase(caseToTest))
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        caseToTest = this.plateau.getCase(this.pos.x--, this.pos.y);
        if(!caseToTest.isMur() && !this.monsterOnThisCase(caseToTest))
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        caseToTest = this.plateau.getCase(this.pos.x, this.pos.y++);
        if(!caseToTest.isMur() && !this.monsterOnThisCase(caseToTest))
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        caseToTest = this.plateau.getCase(this.pos.x, this.pos.y--);
        if(!caseToTest.isMur() && !this.monsterOnThisCase(caseToTest))
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        return availableCoord;
    }


    public Coordonnees getLastPosition()
    {
        Coordonnees position = this.pos;

        switch(this.dir)
        {
            case UP:
            {
                position.y--;
            }
            case DOWN:
            {
                position.y++;
            }
            case LEFT:
            {
                position.x++;
            }
            case RIGHT:
            {
                position.x--;
            }
        }
        return position;
    }

    /**
     * Ne pas tester avec la case courante de notre monstre car renverra false
     * @param Case cas
     * @return boolean
     */
    public boolean monsterOnThisCase(Case cas)
    {
        boolean monsterOnThisCase = false;
        for(Monster mst : this.othersMonsters)
        {
            if(mst.pos.equal(cas))
            {
                monsterOnThisCase = true;
            }
        }
        return monsterOnThisCase;
    }
}
