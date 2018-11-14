package pacman;

public class Plateau {
    public Case[][] plateauTab;

    public int nbLignes;
    public int nbColonnes;

    public Plateau(int[][] tab, int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;

        this.genererPlateau(tab);
    }

    private void genererPlateau(int[][] tab) {
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
}
