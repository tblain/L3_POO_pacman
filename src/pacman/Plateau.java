package pacman;

public class Plateau {
    public Case[][] plateauTab; // tableau de cases

    public int nbLignes;   // nombre de ligne du tableau
    public int nbColonnes; // nombre de colonnes du tableau

    public Plateau(int[][] tab, int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;

        plateauTab = new Case[nbLignes][nbColonnes];
        this.genererPlateau(tab);
    }

    private void genererPlateau(int[][] tab) { // on remplit le plateau avec les cases correspondant au tableau valeur
        int cell;

        for (int i = 0; i < nbLignes; i++) {

            for (int j = 0; j < nbColonnes; j++) {
                cell = tab[i][j];
                // 1 = mur, 2 = passage, 3 = gomme, 4 = superGomme
                plateauTab[i][j] = new Case(new Coordonnees(i, j), cell == 1, cell == 2 || cell == 3 || cell == 4, cell == 3, cell == 4);

            }
        }
    }

    public Case[][] getPlateau() {
        return plateauTab;
    }

    public void setPlateau(Case[][] plateau) {
        this.plateauTab = plateau;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public void setNbLignes(int nbLignes) {
        this.nbLignes = nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public void setNbColonnes(int nbColonnes) {
        this.nbColonnes = nbColonnes;
    }

    public Case getCase(int x, int y){
        return plateauTab[x][y];
    }
    public Case getCase(Coordonnees coord){
        return plateauTab[coord.x][coord.y];
    }
    public void setCase(int x, int y, Case cas){
        this.plateauTab[x][y] = cas;
    }

    public void setCase(Case cas)
    {
        this.plateauTab[cas.coordonnees.x][cas.coordonnees.y] = cas;
    }
}
