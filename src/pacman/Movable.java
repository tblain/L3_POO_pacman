package pacman;

public abstract class Movable implements Runnable{
    protected Coordonnees pos;
    protected boolean alive;

    protected Plateau plateau;

    public Movable(Coordonnees pos, Plateau plateau)
    {
        this.pos = pos;
        this.alive = true;
        this.plateau = plateau;
    }

    //public abstract void run(Plateau plateau, ArrayList<Movable> list);

    /**
     * Intelligence des movables, pour Les Monsters celà correspond à leurs IA
     * @return
     */
    public abstract Coordonnees getNextMove();


}
