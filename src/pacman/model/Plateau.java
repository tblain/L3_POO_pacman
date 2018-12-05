package pacman.model;

public class Plateau {
    private AbstractMap map;
    public Case[][] plateauTab; // tableau de cases

    public Plateau(AbstractMap map) {
        this.map = map;
        plateauTab = new Case[map.getRow()][map.getColumn()];

        this.genererPlateau();
    }

    private void genererPlateau() { // on remplit le plateau avec les cases correspondant au tableau valeur
        int cell;

        for(int x = 0; x < map.getRow(); x++)
        {
            for(int y = 0; y < map.getColumn(); y++)
            {
                cell = map.getMAP()[y][x];
                // 1 = mur, 2 = passage, 3 = gomme, 4 = superGomme
                plateauTab[x][y] = new Case(new Coordonnees(x, y), cell == 1, (cell == 2 || cell == 3 || cell == 4), cell == 3, cell == 4);
            }
        }

        setTpOnPlateau();
    }

    public Case[][] getPlateau() {
        return plateauTab;
    }

    public int getNbLignes() {
        return map.getRow();
    }

    public int getNbColonnes() {
        return map.getColumn();
    }

    public Case getCase(int x, int y){
        return plateauTab[x][y];
    }
    public Case getCase(Coordonnees coord){
        return plateauTab[coord.getX()][coord.getY()];
    }

    public boolean resteGomme() {
        boolean resteGomme = false;

        for(int x = 0; x < map.getColumn() && !resteGomme; x++)
        {
            for(int y = 0; y < map.getRow() && !resteGomme; y++)
            {
                if(getCase(x, y).isGomme() || getCase(x, y).isSuperGomme())
                    resteGomme = true;
            }
        }

        return resteGomme;
    }

    private void setTpOnPlateau()
    {
        Tp tp = map.getTp();
        getCase(tp.getPointGauche()).setTp(tp);
        getCase(tp.getPointDroit()).setTp(tp);
    }
}
