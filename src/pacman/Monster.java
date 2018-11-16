package pacman;

import java.util.ArrayList;

public class Monster extends Movable {
    protected Pacman pacman;
    protected boolean init;

    public Monster(Coordonnees pos, Plateau plateau)
    {
        super(pos, plateau, Direction.RIGHT);
        this.init = false;
    }
    
    public void init(Pacman pacman) {
        this.pacman = pacman;
        this.init = true;
    }

    public Coordonnees getAI() {
        Coordonnees nextCoord = this.getNextMove();
        Case cas = this.plateau.getCase(nextCoord);
        ArrayList<Coordonnees> availableCoord = getAvailableCoord();

        // Check si notre position suivante en fonction de la direction est dans availableCoord, si oui
        // y aller => donc continuer dans la même direction, sinon choisir aléatoirement un autre direction
        // en privilègiant celle qui ne nous feras pas retourner sur nos pas

        return null;
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
        if(!caseToTest.isMur())
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        caseToTest = this.plateau.getCase(this.pos.x--, this.pos.y);
        if(!caseToTest.isMur())
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        caseToTest = this.plateau.getCase(this.pos.x, this.pos.y++);
        if(!caseToTest.isMur())
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        caseToTest = this.plateau.getCase(this.pos.x, this.pos.y--);
        if(!caseToTest.isMur())
        {
            availableCoord.add(caseToTest.coordonnees);
        }

        return availableCoord;
    }

    @Override
    public void run() {

    }


}
