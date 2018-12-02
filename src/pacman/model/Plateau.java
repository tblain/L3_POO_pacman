package pacman;

import pacman.model.Coordonnees;

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

        for(int x = 0; x < nbColonnes; x++)
        {
            for(int y = 0; y < nbLignes; y++)
            {
                cell = tab[y][x];
                // 1 = mur, 2 = passage, 3 = gomme, 4 = superGomme
                plateauTab[x][y] = new Case(new Coordonnees(x, y), cell == 1, (cell == 2 || cell == 3 || cell == 4), cell == 3, cell == 4);
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
        return plateauTab[coord.getX()][coord.getY()];
    }
    public void setCase(int x, int y, Case cas){
        this.plateauTab[x][y] = cas;
    }

    public void setCase(Case cas)
    {
        this.plateauTab[cas.coordonnees.getX()][cas.coordonnees.getY()] = cas;
    }
}
