package pacman;

import java.util.ArrayList;

public class Monster extends Movable {
    protected Pacman pacman;
    public MonsterName name;

    // Savoir où ils sont pour ne pas aller sur les mêmes cases qu'eux
    protected ArrayList<Monster> othersMonsters;

    public Monster(Coordonnees pos, Plateau plateau, MonsterName name)
    {
        super(pos, plateau, Direction.RIGHT);
        this.othersMonsters = new ArrayList<>();
        this.name = name;
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
        //Coordonnees nextPossibleCoord = this.getAI();
        Coordonnees mescouillesauborddeleau = this.dijkstra();

        if(mescouillesauborddeleau != null)
        {
            this.dir = this.getDirection(mescouillesauborddeleau);
            this.pos = mescouillesauborddeleau;
        } else {
            System.out.println("error : pas de next coord");
        }

        if(pacman.pos.equals(this.pos))
        {
            if(pacman.remainingTimeForSuperPacGomme == 0)
            {
                // On mange pacman
                pacman.alive = false;
            }
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
            System.out.println("    same dir : " + pos.getX() + " | y : " + pos.getY());
        }
        // sinon choisir aléatoirement un autre direction
        // en privilègiant celle qui ne nous feras pas retourner sur nos pas
        else
        {
            // On enlève la coordonnée nous faisant revenir sur notre position précédente
            Coordonnees lastPosition = this.getLastPosition();
            for(int i = 0; i < availableCoord.size(); i++)
            {
                if(availableCoord.get(i).equals(lastPosition))
                {
                    availableCoord.remove(i);
                    break;
                }
            }

            // On choisi aléatoirement entre la ou les directions restantes
            // donc entre 0 et availableCoord.size() si il en reste
            if(availableCoord.size() > 0)
            {
                int nombreAleatoire = (int) (Math.random() * ((availableCoord.size())));

                res = availableCoord.get(nombreAleatoire);

            }
            // Si on a pas de choix possible (aller à gauche ou à droite) on retourne sur nos pas
            else {
                res = lastPosition;
                switch(this.dir) {
                    case UP: {
                        dir = Direction.DOWN;
                        break;
                    }
                    case DOWN: {
                        dir = Direction.UP;
                        break;
                    }
                    case LEFT: {
                        dir = Direction.RIGHT;
                        break;
                    }
                    case RIGHT: {
                        dir = Direction.LEFT;
                        break;
                    }
                }
            }

        }
        //System.out.println("x : " + res.getX() + " | y : " + res.getY());
        return res;
    }

    /**
     * Retourne dans une ArrayList les coordonnées des caches atteignables(pas de mur)
     * @return
     */
    public ArrayList<Coordonnees> getAvailableCoord()
    {
        ArrayList<Coordonnees> availableCoord = new ArrayList<>();
        Case caseToTest;
        //System.out.println("test available coord : " + pos.getX() + " | y : " + pos.getY());

        // On test les 4 possibilités avec des try catch ou cas ou caseToTest serait demandé en index 21 par exemple
        try{
            caseToTest = this.plateau.getCase(this.pos.getX() + 1,this.pos.getY());
            if(this.isAvailable(caseToTest))
            {
                availableCoord.add(caseToTest.coordonnees);
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBoundsException "+e.getMessage());
        }

        try{
            caseToTest = this.plateau.getCase(this.pos.getX() - 1, this.pos.getY());
            if(this.isAvailable(caseToTest))
            {
                availableCoord.add(caseToTest.coordonnees);
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBoundsException " +e.getMessage());
        }

        try{
            caseToTest = this.plateau.getCase(this.pos.getX(), this.pos.getY() + 1);
            if(this.isAvailable(caseToTest))
            {
                availableCoord.add(caseToTest.coordonnees);
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBoundsException "+e.getMessage());
        }

        try{
            caseToTest = this.plateau.getCase(this.pos.getX(), this.pos.getY() - 1);
            if(this.isAvailable(caseToTest))
            {
                availableCoord.add(caseToTest.coordonnees);
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBoundsException " + e.getMessage());
        }


        for(Coordonnees coord : availableCoord)
        {
            System.out.println(coord);
        }
        return availableCoord;
    }


    public boolean isAvailable(Case caseToTest)
    {
        if(!caseToTest.isMur() && !this.monsterOnThisCase(caseToTest) && pos.getY() > 0)
        {
            //System.out.println("    up  x : " + pos.getX() + " | y : " + (pos.getY()-1));
            return true;
        }
        return false;
    }



    public Coordonnees getLastPosition()
    {
        Coordonnees position = (Coordonnees) this.pos.clone();

        switch(this.dir)
        {
            case UP:
            {
                position.setY(position.getY()+1);
                break;
            }
            case DOWN:
            {
                position.setY(position.getY()-1);
                break;
            }
            case LEFT:
            {
                position.setX(position.getX()+1);
                break;
            }
            case RIGHT:
            {
                position.setX(position.getX()-1);
                break;
            }
        }
        return position;
    }

    /**
     * On donne la direction en fonction des Coordonnées données et de notre position actuelle
     * @param coord
     * @return
     */
    public Direction getDirection(Coordonnees in_coord)
    {
        // On obtient un vecteur
        Coordonnees coord = new Coordonnees(in_coord.getX() - this.pos.getX(), in_coord.getY() - this.pos.getY());
        Direction dir = null;

        // UP et DOWN sont inversés à cause des indexes du tableau de jeu
        // on monte avec index - 1 et descend avec index + 1

        // Si on se dirige vers le haut (UP)
        if(coord.getY() < 0)
        {
            dir = Direction.UP;
        }

        // Si on se dirige vers le bas (DOWN)
        if(coord.getY() > 0)
        {
            dir = Direction.DOWN;
        }

        // Si on se dirige vers la gauche (LEFT)
        if(coord.getX() < 0)
        {
            dir = Direction.LEFT;
        }

        // Si on se dirige vers la droite (RIGHT)
        if(coord.getX() > 0)
        {
            dir = Direction.RIGHT;
        }

        return dir;
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
            if(mst.pos.equals(cas.coordonnees))
            {
                monsterOnThisCase = true;
            }
        }
        return monsterOnThisCase;
    }

    public Coordonnees dijkstra() {
        //SearchCoord test = (SearchCoord) this.pos;
        //System.out.println("=============================");
        //System.out.println("Dijkstra start");
        //System.out.println("start pos x: " + pos.getX() + " | y: " + pos.getY());
        //System.out.println("goal pos x: " + pacman.pos.getX() + " | y: " + pacman.pos.getY());
        BFS bfs = new BFS(plateau, new SearchCoord(pos.getX(), pos.getY()), new SearchCoord(pacman.pos.getX(), pacman.pos.getY()), othersMonsters, isInFear());
        bfs.performBFS();
        SearchCoord[] path = bfs.getPath();

        return path[path.length-2];
    }

    public boolean isInFear() {
        return pacman.remainingTimeForSuperPacGomme > 0;
    }
}
