package pacman;

public class Case {
    public Coordonnees coordonnees;
    public boolean mur;
    public boolean passage;
    public boolean gomme;
    public boolean superGomme;

    public Case(Coordonnees coordonnees, boolean mur, boolean passage, boolean gomme, boolean superGomme) {
        this.coordonnees = coordonnees;
        this.mur = mur;
        this.passage = passage;
        this.gomme = gomme;
        this.superGomme = superGomme;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public boolean isMur() {
        return mur;
    }

    public void setMur(boolean mur) {
        this.mur = mur;
    }

    public boolean isPassage() {
        return passage;
    }

    public void setPassage(boolean passage) {
        this.passage = passage;
    }

    public boolean isGomme() {
        return gomme;
    }

    public void setGomme(boolean gomme) {
        this.gomme = gomme;
    }

    public boolean isSuperGomme() {
        return superGomme;
    }

    public void setSuperGomme(boolean superGomme) {
        this.superGomme = superGomme;
    }
}
