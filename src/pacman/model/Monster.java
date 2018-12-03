package pacman.model;

import java.util.ArrayList;

public class Monster extends Movable {
    protected Pacman pacman;
    public MonsterName name;
    public int num;
    // 0 : chase, 1 : scatter
    public int mode = 1;
    public int remainingDeathTime = 0;

    // Savoir où ils sont pour ne pas aller sur les mêmes cases qu'eux
    protected ArrayList<Monster> othersMonsters;

    public Monster(Coordonnees pos, Plateau plateau, MonsterName name, int num)
    {
        super(pos, plateau, Direction.RIGHT);
        this.othersMonsters = new ArrayList<>();
        this.name = name;
        this.num = num;
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
        int mx = pos.getX();
        int my = pos.getY();
        System.out.println("alive : " + alive);
        if(alive) {

            // On test si pacman est sur notre case, si oui on peut le manger
            // ssi il n'est pas sous forme super gomme
            if (pacman.pos.equals(this.pos)) {
                if (!isInFear()) {
                    // On mange pacman
                    pacman.alive = false;
                    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
                    System.out.println("    mangé");
                    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
                }
            }


            if (isInFear()) {
                ArrayList<Coordonnees> coords = getAvailableCoord();
                int choice = (int) (Math.random() * 3 + 1);
                Coordonnees coordToGo;
                switch (choice) {
                    case 0:
                        coordToGo = new Coordonnees(mx, my + 1);
                        if (plateau.getCase(mx, my + 1).isMur())
                            this.dir = this.getDirection(coordToGo);
                        else {
                            this.dir = this.getDirection(coordToGo);
                            this.pos = coordToGo;
                        }
                        break;

                    case 1:
                        coordToGo = new Coordonnees(mx, my - 1);
                        if (plateau.getCase(mx, my - 1).isMur())
                            this.dir = this.getDirection(coordToGo);
                        else {
                            this.dir = this.getDirection(coordToGo);
                            this.pos = coordToGo;
                        }
                        break;

                    case 2:
                        coordToGo = new Coordonnees(mx + 1, my);
                        if (plateau.getCase(mx + 1, my).isMur())
                            this.dir = this.getDirection(coordToGo);
                        else {
                            this.dir = this.getDirection(coordToGo);
                            this.pos = coordToGo;
                        }
                        break;

                    case 3:
                        coordToGo = new Coordonnees(mx - 1, my);
                        if (plateau.getCase(mx - 1, my).isMur())
                            this.dir = this.getDirection(coordToGo);
                        else {
                            this.dir = this.getDirection(coordToGo);
                            this.pos = coordToGo;
                        }
                        break;
                }

            } else {
                int x, y;

                int px = pacman.pos.getX();
                int py = pacman.pos.getY();
                Coordonnees coordToGo = null;

                //System.out.println("mode 1");
                x = 0;
                y = 0;


                int cmx = (mx - 2) / 3;
                int cmy = (my - 1) / 4;

                int cpx = (px - 2) / 3;
                int cpy = (py - 1) / 4;

                System.out.println("cm : " + cmx + " " + cmy);
                System.out.println("cp : " + cpx + " " + cpy);
                System.out.println("m : " + mx + " " + my);
                System.out.println("p : " + px + " " + py);

                int[][] posDiv = new int[6][5];


                for (Monster mst : othersMonsters) {
                    posDiv[(mst.pos.getX() - 2) / 3][(mst.pos.getY() - 1) / 4] = 1;
                }

                //posDiv[cmx][cmy] = 3;
                posDiv[cpx][cpy] = 2;

                if (((cmx == cpx && (cmy == cpy || cmy == cpy + 1 || cmy == cpy - 1)) || (cmy == cpy & (cmx == cpx || cmx == cpx + 1 || cmx == cpx - 1))))
                    mode = 0;
                else if (monstreLePlusLoin())
                    mode = 1;
                else
                    mode = 1;

                if (mode == 1) {
                    coordToGo = scatter();
                } else if (mode == 0)
                    coordToGo = chase();

                //System.out.println("posdiv");
                //System.out.println(posDiv[0][0] + " " + posDiv[1][0] + " " + posDiv[2][0]);
                //System.out.println(posDiv[0][1] + " " + posDiv[1][1] + " " + posDiv[2][1]);
                //System.out.println(posDiv[0][2] + " " + posDiv[1][2] + " " + posDiv[2][2]);

                // On met à jour notre position
                if (coordToGo != null) {
                    //System.out.println("a des coord");
                    this.dir = this.getDirection(coordToGo);
                    this.pos = coordToGo;
                    //System.out.println("dir :" + dir + " | pos : " + pos);
                } else {
                    System.out.println("error : pas de next coord");
                }
            }

            if (pacman.pos.equals(this.pos)) {
                if (!isInFear()) {
                    // On mange pacman
                    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
                    System.out.println("    mangé");
                    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
                    pacman.alive = false;
                }
            }
        } else { // if dead
            Coordonnees coordToGo;
            if(mx == 10 && my == 9) {
                if (remainingDeathTime == 0) {
                    //System.out.println("    im alive!!!!!!!!!!");
                    coordToGo = scatter();
                    alive = true;
                    this.dir = this.getDirection(coordToGo);
                    this.pos = coordToGo;
                } else {
                    //System.out.println("    arrived but dead");
                    this.dir = Direction.UP;
                    remainingDeathTime--;
                }
            } else {
                //System.out.println("    just dead");
                coordToGo = pathFinding(10, 9);
                this.dir = this.getDirection(coordToGo);
                this.pos = coordToGo;
            }
        }


    }

    public boolean monstreLePlusLoin() {
        boolean plusLoin = true;
        for (Monster mst : othersMonsters){
            if (this.pos.distance(pacman.pos) < mst.pos.distance(pacman.pos))
                plusLoin = false;
        }

        return plusLoin;
    }

    public Coordonnees coordPlusProcheDansCase(int cx, int cy) {

        Plateau p = plateau;
        Coordonnees bestCoord = null;
        Coordonnees coord = null;
        //System.out.println("    plus proch");
        //System.out.println("    de " + (2+cx*3) + " à " + (2 + (+cx+1) * 3));
        //System.out.println("    de " + (1+cy*4) + " à " + (1 + (cy+1) * 4));
        for (int i = 2 + cx * 3; i < 2 + (cx+1) * 3 && i < 20; i++) {
            for (int j = 1 + cy * 4; j < 1 + (cy+1) * 4 && i < 19; j++) {
                Case cas = p.getCase(i, j);
                if(!cas.isMur() && !this.monsterOnThisCase(cas)) {
                    coord = new Coordonnees(i, j);
                    if(bestCoord == null) {
                        bestCoord = coord;
                    }

                    if(coord.distance(pacman.pos) < bestCoord.distance(pacman.pos)) {
                        bestCoord = coord;
                        //System.out.println("    new best " + bestCoord);
                    }
                }
            }
        }
        //System.out.println("    corrd la plus proche : " + bestCoord);
        return bestCoord;
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

    public Coordonnees pathFinding(int x, int y) {
        //SearchCoord test = (SearchCoord) this.pos;
        System.out.println("Pathfinding x y");
        System.out.println("start pos x: " + pos.getX() + " | y: " + pos.getY());
        System.out.println("goal pos x: " + x + " | y: " + y);
        BFS bfs = new BFS(plateau, new SearchCoord(pos.getX(), pos.getY()), new SearchCoord(x, y), othersMonsters, isInFear());
        bfs.performBFS();
        SearchCoord[] path = bfs.getPath();
        System.out.println("next coord " + path[path.length-2]);

        return path[path.length-2];
    }

    public Coordonnees pathFinding(Coordonnees coord) {
        System.out.println("Pathfinding coord");
        System.out.println("start pos x: " + pos.getX() + " | y: " + pos.getY());
        System.out.println("goal pos " + coord);
        System.out.println("pacman coord " + pacman.pos);
        BFS bfs = new BFS(plateau, new SearchCoord(pos.getX(), pos.getY()), new SearchCoord(coord.getX(), coord.getY()), othersMonsters, isInFear());
        bfs.performBFS();
        SearchCoord[] path = bfs.getPath();
        System.out.println("next coord " + path[path.length-2]);

        return path[path.length-2];
    }

    public boolean isInFear() {
        return pacman.remainingTimeForSuperPacGomme > 0;
    }

    public Coordonnees chase() {
        return pathFinding(pacman.pos);
    }

    public Coordonnees scatter() {
        System.out.println("Scatter");
        Coordonnees coordToGo = null;

        int px = pacman.pos.getX();
        int py = pacman.pos.getY();

        int mx = pos.getX();
        int my = pos.getY();

        int cmx = (mx-2)/3;
        int cmy = (my-1)/4;

        int cpx = (px-2)/3;
        int cpy = (py-1)/4;

        int[][] posDiv = new int[6][5];



        for (Monster mst : othersMonsters)
            posDiv[(mst.pos.getX()-2)/3][(mst.pos.getY()-1)/4] = 1;

        //posDiv[cmx][cmy] = 3;
        posDiv[cpx][cpy] = 2;

        switch (num) {
            case 1:
                if(cpx + 1 < 5 && !(posDiv[4][1] == 2 || posDiv[2][4] == 2)) {
                    //System.out.println("    droite " + (cpx+1) + " " + cpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx+1, cpy));
                } else if (cpy + 1 < 4 && !(posDiv[0][0] == 2 || posDiv[0][1] == 2 || posDiv[4][0] == 2 || posDiv[4][1] == 2)) {
                    //System.out.println("    haut " + cpx + " " + (cpy+1));
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy+1));
                } else {
                    //System.out.println("    bas " + cpx + " " + (cpy-1));
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy-1));
                }
                break;

            case 2:
                if(cpx - 1 >= 0 && !(posDiv[1][1] == 2 || posDiv[1][2] == 2))
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx-1, cpy));
                else if (cpy - 1 >= 0 && !(posDiv[4][3] == 2 || posDiv[5][3] == 2 || posDiv[0][3] == 2))
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy-1));
                else
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy+1));
                break;

            case 3:
                if(cpy - 1 >= 0 && !(posDiv[4][3] == 2 || posDiv[5][3] == 2 || posDiv[0][3] == 2))
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy-1));
                else if (cpx + 1 < 5 && !(posDiv[4][1] == 2 || posDiv[2][4] == 2))
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx+1, cpy));
                else
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx-1, cpy));
                break;

            case 4:
                if(cpy + 1 < 4 && !(posDiv[0][0] == 2 || posDiv[0][1] == 2 || posDiv[4][0] == 2 || posDiv[4][1] == 2))
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy+1));
                else if (cpx - 1 >= 0 && !(posDiv[1][1] == 2 || posDiv[1][2] == 2))
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx-1, cpy));
                else
                    coordToGo = pathFinding(coordPlusProcheDansCase(cpx, cpy));
                break;
        }

        return coordToGo;
    }
}
