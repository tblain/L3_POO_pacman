package pacman.model;

import java.util.ArrayList;

public class Monster extends Movable {
    private Model model;
    protected Pacman pacman;
    public MonsterName name;
    public int num; // numero du monstre qui aura un comportement différent en fonction
    public int mode = 1; // 0 : chase, 1 : surround
    public int remainingDeathTime = 0; // temps avant que le monstre respawn à partir du moment où il est revenu dans la maison

    // pour que le monstre apparaisse il faut que :
    private int timeToAppear; // un certain temps se soit écoulé
    private int pointToAppear; // ET que pacman ait atteint un certain score


    protected ArrayList<Monster> othersMonsters;

    public void setRemainingDeathTime(int remainingDeathTime) {
        this.remainingDeathTime = remainingDeathTime;
    }

    public Monster(Coordonnees pos, Model model, MonsterName name, int num, int timeToAppear, int pointToAppear)
    {
        super(pos, Direction.RIGHT);
        this.othersMonsters = new ArrayList<>();
        this.name = name;
        this.num = num;
        this.timeToAppear = timeToAppear;
        this.model = model;
        this.pointToAppear = pointToAppear;
    }

    public void init(Pacman pacman, ArrayList<Monster> othersMonsters, Plateau plateau) {
        this.pacman = pacman;
        this.plateau = plateau;

        for(Monster mst : othersMonsters)
        {
            // On garde seulement les autres monstres et pas nous
            if(mst != this)
                this.othersMonsters.add(mst);
        }
    }

    @Override
    public void run() {
        int mx = pos.getX(); // x du monstres
        int my = pos.getY(); // y du monstre

        //System.out.println("alive : " + alive);

        if (timeToAppear > 0 || pacman.score < pointToAppear) // est ce que le monstre réunis les conditions pour apparaitre
            timeToAppear--;
        else {
            if(isAlive()) { // si il est vivant

                // On test si pacman est sur notre case et si le monstre n'est pas en mode peur(pacman mode supergomme), si oui on peut le manger
                if (pacman.pos.equals(this.pos) && !isInFear())
                    pacman.alive = false; // on mange le pacman
                else {

                }
                if (isInFear()) {
                    // les monstres se déplacent de façon random
                    Coordonnees coordToGo = randomAdjCoord(); // on recup une case adj au monstre random
                    if(plateau.getCase(coordToGo).isMur()) // si c'est un mur le monstre ne se déplace pas
                        this.dir = Direction.UP;
                    else { // sinon il se deplace
                        this.dir = this.getDirection(coordToGo);
                        this.pos = coordToGo;
                    }

                } else {
                    int x, y;

                    int px = pacman.pos.getX();
                    int py = pacman.pos.getY();
                    Coordonnees coordToGo = null;

                    x = 0;
                    y = 0;

                    // on divise le plateau en grands groupes de cases
                    // longueur: 3, hauteur: 4

                    // groupe auquel appartient le montre
                    int gmx = (mx-2)/3;
                    int gmy = (my-1)/4;

                    // groupe auquel appartient le pacman
                    int gpx = (px-2)/3;
                    int gpy = (py-1)/4;

                    if (monstreLePlusLoin()) // le monstre le plus éloigné du pacman passe en mode surround
                        mode = 1;
                    else if (((gmx == gpx && (gmy == gpy || gmy == gpy + 1 || gmy == gpy - 1)) || (gmy == gpy & (gmx == gpx || gmx == gpx + 1 || gmx == gpx - 1))))
                        // si le monstre est dans un groupe de cases adjacent à celui du pacman ou le meme groupe
                        // il passe en mode chase (fonce sur le pacman)
                        mode = 0;
                    else
                        mode = 1;

                    if (mode == 1) {
                        coordToGo = surround();
                    } else if (mode == 0)
                        coordToGo = chase();
                    //System.out.println("sdf*dsklflùdsfjslfjsmflksdmlfsdf");
                    // On met à jour notre position
                    if (coordToGo != null) {
                        //System.out.println("a des coord");
                        this.dir = this.getDirection(coordToGo);
                        this.pos = coordToGo;
                        //System.out.println("dir :" + dir + " | pos : " + pos);
                    } else {
                        System.out.println("error : pas de next coord");
                    }

                    if (pacman.pos.equals(this.pos) && !isInFear()) // On mange pacman
                        pacman.alive = false;
                }


            } else { // if dead
                Coordonnees coordToGo;
                if(mx == 10 && my == 9) { // si le monstre est arrivé dans la maison
                    if (remainingDeathTime == 0) { // si son temps de réaparition est fini
                        coordToGo = surround();
                        alive = true;
                        this.dir = this.getDirection(coordToGo);
                        this.pos = coordToGo;
                    } else { // sinon il attend
                        this.dir = Direction.UP;
                        remainingDeathTime--;
                    }
                } else { // sinon il va vers la maison
                    coordToGo = pathFinding(10, 9);
                    this.dir = this.getDirection(coordToGo);
                    this.pos = coordToGo;
                }
            }
        }
    }

    private Coordonnees randomAdjCoord() { // renvoie les coordonnées d'une case aléatoire parmi celles qui touchent celle du monstre
        int mx = pos.getX();
        int my = pos.getY();

        int choice = (int) (Math.random() * 3 + 1);
        Coordonnees coordToGo = null;
        switch (choice) {
            case 0:
                coordToGo = new Coordonnees(mx, my + 1);
                break;

            case 1:
                coordToGo = new Coordonnees(mx, my - 1);
                break;

            case 2:
                coordToGo = new Coordonnees(mx + 1, my);
                break;

            case 3:
                coordToGo = new Coordonnees(mx - 1, my);
                break;
        }

        return coordToGo;
    }

    public boolean monstreLePlusLoin() {
        boolean plusLoin = true;

        for (Monster mst : othersMonsters){
            if (this.pos.distance(pacman.pos) < mst.pos.distance(pacman.pos))
                plusLoin = false;
        }

        if (othersMonsters.size() == 0) { // s'il n'y a pas d'autre monstres, il n'est pas le plus loin
            plusLoin = false;
        }
        return plusLoin;
    }

    public Coordonnees coordPlusProcheDansCase(int gx, int gy) { // x et y coordonnées du groupe de cases cible
        // le plateau est divise en grands groupes de cases
        // longueur: 3, hauteur: 4
        // renvoie la coordonnée la plus proche du pacman

        Plateau p = plateau;
        Coordonnees bestCoord = null;
        Coordonnees coord = null;

        for (int i = 2 + gx * 3; i < 2 + (gx+1) * 3 && i < 20; i++) {
            for (int j = 1 + gy * 4; j < 1 + (gy+1) * 4 && i < 19; j++) {
                Case cas = p.getCase(i, j);
                if(!cas.isMur() && !this.monsterOnThisCase(cas)) {
                    coord = new Coordonnees(i, j);
                    if(bestCoord == null)
                        bestCoord = coord;

                    if(coord.distance(pacman.pos) < bestCoord.distance(pacman.pos))
                        bestCoord = coord;
                }
            }
        }
        //System.out.println("coord plus proche " + bestCoord);
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
        Direction dir = Direction.UP;

        // UP et DOWN sont inversés à cause des indexes du tableau de jeu
        // on monte avec index - 1 et descend avec index + 1

        // Si on se dirige vers le haut (UP)
        if(coord.getY() < 0)
            dir = Direction.UP;

        // Si on se dirige vers le bas (DOWN)
        if(coord.getY() > 0)
            dir = Direction.DOWN;

        // Si on se dirige vers la gauche (LEFT)
        if(coord.getX() < 0)
            dir = Direction.LEFT;

        // Si on se dirige vers la droite (RIGHT)
        if(coord.getX() > 0)
            dir = Direction.RIGHT;

        if(dir == null)
        {
            return this.dir;
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
        for(Monster mst : this.othersMonsters) {
            if(mst.pos.equals(cas.coordonnees))
                monsterOnThisCase = true;
        }
        return monsterOnThisCase;
    }

    public Coordonnees pathFinding(int x, int y) {
        // renvoie la case où aller aux coordonnées voulu

        System.out.println("Pathfinding x y");
        System.out.println("start pos x: " + pos.getX() + " | y: " + pos.getY());
        System.out.println("goal pos x: " + x + " | y: " + y);
        System.out.println("monster coord " + pos);

        Coordonnees coordTest = new Coordonnees(x, y);

        if (pos.equals(coordTest) || pos.distance(coordTest) == 1) {
            return coordTest;
        } else  {
            BFS bfs = new BFS(plateau, new SearchCoord(pos.getX(), pos.getY()), new SearchCoord(x, y), othersMonsters, isInFear());
            bfs.performBFS();
            SearchCoord[] path = bfs.getPath();
            System.out.println("next coord " + path[path.length-1]);

            return path[path.length-1];
        }
    }

    public Coordonnees pathFinding(Coordonnees coord) {
        // renvoie la case où aller aux coordonnées voulu

        System.out.println("Pathfinding coord");
        System.out.println("start pos x: " + pos.getX() + " | y: " + pos.getY());
        System.out.println("goal pos " + coord);
        System.out.println("monster coord " + pos);

        if (pos.equals(coord) || pos.distance(coord) == 1) {
            return coord;
        } else {
            BFS bfs = new BFS(plateau, new SearchCoord(pos.getX(), pos.getY()), new SearchCoord(coord.getX(), coord.getY()), othersMonsters, isInFear());
            bfs.performBFS();
            SearchCoord[] path = bfs.getPath();
            System.out.println("next coord " + path[path.length-1]);

            return path[path.length-1];
        }
    }

    public boolean isInFear() {
        return pacman.remainingTimeForSuperPacGomme > 0;
    }

    public Coordonnees chase() { // le monstre fonce directement sur le pacman
        return pathFinding(pacman.pos);
    }

    public Coordonnees surround() {
        System.out.println("Surround");
        Coordonnees coordToGo = null;

        int px = pacman.pos.getX();
        int py = pacman.pos.getY();

        int mx = pos.getX();
        int my = pos.getY();

        // on divise le plateau en grands groupes de cases
        // longueur: 3, hauteur: 4

        // groupe auquel appartient le montre
        int gmx = (mx-2)/3;
        int gmy = (my-1)/4;

        // groupe auquel appartient le pacman
        int gpx = (px-2)/3;
        int gpy = (py-1)/4;

        int[][] posDiv = new int[6][5]; // init d'un tableau double dimension qui contient tous les groupes

        for (Monster mst : othersMonsters)
            posDiv[(mst.pos.getX()-2)/3][(mst.pos.getY()-1)/4] = 1;

        posDiv[gpx][gpy] = 2;
        /*
         l'objectif ici est d'envoyé les monstres dans un groupe de cases adjacent à celui du pacman
         chaque monstre a une tendance :
            - le 1 prend le coté droit
            - le 2 prend le coté gauche
            - le 3 prend le coté haut
            - le 4 prend le coté bas

         on regarde d'abord si le groupe de case n'est pas en dehors du plateau ou s'il n'est pas dans
         un emplacement génant (par ex: autour des 2 groupes au dessus et dessous des téléporteurs
            - si oui on lui donne un autre coté
          ensuite on trouve dans le groupe cible la case la plus proche du pacman
          puis on lance le cherche la prochaine case pour se rendre sur la case cible
        */

        switch (num) {
            case 1:
                if(gpx + 1 < 5 && !(posDiv[4][1] == 2 || posDiv[4][2] == 2)) {
                    System.out.println("    droite " + gpx+1 + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx+1, gpy));
                }
                else if (gpy + 1 < 5 && !(posDiv[0][1] == 2 || posDiv[4][0] == 2 || posDiv[4][1] == 2 || posDiv[5][1] == 2)) {
                    //System.out.println("    bas " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy+1));
                }
                else
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy));
                break;

            case 2:
                if(gpx - 1 >= 0 && !(posDiv[1][1] == 2 || posDiv[1][2] == 2)) {
                    //System.out.println("    gauche" + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx-1, gpy));
                }
                else if (gpy - 1 >= 0 && !(posDiv[4][3] == 2 || posDiv[5][3] == 2 || posDiv[0][3] == 2)) {
                    //System.out.println("    haut " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy-1));
                }
                else
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy+1));
                break;

            case 3:
                if(gpy - 1 >= 0 && !(posDiv[4][3] == 2 || posDiv[5][3] == 2 || posDiv[0][3] == 2)) {
                    //System.out.println("    haut " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy-1));
                }
                else if (gpx + 1 < 5 && !(posDiv[4][1] == 2 || posDiv[2][4] == 2)) {
                    //System.out.println("    droite " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx+1, gpy));
                }
                else {
                    //System.out.println("    straight " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx-1, gpy));
                }
                break;

            case 4:
                if(gpy + 1 < 5 && !(posDiv[0][1] == 2 || posDiv[4][0] == 2 || posDiv[4][1] == 2)) {
                    //System.out.println("    bas " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy+1));
                }
                else if (gpx - 1 >= 0 && !(posDiv[1][1] == 2 || posDiv[1][2] == 2)) {
                    //System.out.println("    gauche " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx-1, gpy));
                }
                else {
                    //System.out.println("    straight " + gpx + " " + gpy);
                    coordToGo = pathFinding(coordPlusProcheDansCase(gpx, gpy));
                }
                break;
        }

        return coordToGo;
    }
}
