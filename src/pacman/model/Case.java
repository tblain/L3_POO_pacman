package pacman.model;

public class Case {
    public Coordonnees coordonnees;
    public boolean mur;
    public boolean passage;
    public boolean gomme;
    public boolean superGomme;

    // Par défaut null car toutes les cases ne sont pas des tp
    public Tp tp;

    public Case(Coordonnees coordonnees, boolean mur, boolean passage, boolean gomme, boolean superGomme) {
        this.coordonnees = coordonnees;
        this.mur = mur;
        this.passage = passage;
        this.gomme = gomme;
        this.superGomme = superGomme;
        this.tp = null;
    }

    public void setTp(Tp tp)
    {
        this.tp = tp;
    }

    public boolean isTp()
    {
        return this.tp != null;
    }

    public Coordonnees teleportTo(Coordonnees entrance)
    {
        return tp.getExit(entrance);
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public boolean isMur() {
        return mur;
    }

    public boolean isPassage() {
        return passage;
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
