package pacman.model;

public class Tp {
    /**
     * On modèlise un télépoteur bidirectionnelle
     * Si on rentre à gauche on sort à droite
     * on rentrera toujours sur le point gauche par la droite
     * et sur le point droite par la gauche
     * car on détermine la position d'arriver comme
     * la position du tp.x + 1 si on rentre par le droite ou
     * tp.x - 1 si on rentre par la gauche
     *
     * POUR L INSTANT, LES TP SONT SEULEMENT A L HORIZONTALE
     */
    private Coordonnees pointGauche;
    private Coordonnees pointDroit;

    Tp(Coordonnees pointGauche, Coordonnees pointDroit)
    {
        this.pointGauche = pointGauche;
        this.pointDroit = pointDroit;
    }

    /**
     * On donne en paramètre les coordonnées du point d'entrer pour savoir ou l'on doit sortir
     * @param coordOfEnter
     */
    public Coordonnees getExit(Coordonnees coordOfEnter)
    {
        // Coord par défaut, je sais pas où c'est
        Coordonnees exit = new Coordonnees(5,5);
        if(coordOfEnter.equals(pointDroit))
        {
            exit = new Coordonnees(pointGauche.getX()+1, pointGauche.getY());
        }
        else if(coordOfEnter.equals(pointGauche))
        {
            exit = new Coordonnees(pointDroit.getX()-1, pointDroit.getY());
        }

        return exit;
    }

}
