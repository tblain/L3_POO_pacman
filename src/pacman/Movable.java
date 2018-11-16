package pacman;

public abstract class Movable implements Runnable{
    protected Coordonnees pos;
    protected boolean alive;
    protected Direction dir;

    protected Plateau plateau;

    public Movable(Coordonnees pos, Plateau plateau, Direction dir)
    {
        this.pos = pos;
        this.alive = true;
        this.plateau = plateau;
        this.dir = dir;
    }

    //public abstract void run(Plateau plateau, ArrayList<Movable> list);

    /**
     * Intelligence des movables, pour Les Monsters celà correspond à leurs IA
     * @return
     */
    public Coordonnees getNextMove()
    {
        Coordonnees nextPos = this.pos;
        switch (this.dir)
        {
            case UP:
            {
                nextPos.x++;
            }
            case DOWN:
            {
                nextPos.x--;
            }
            case LEFT:
            {
                nextPos.y--;
            }
            case RIGHT:
            {
                nextPos.y++;
            }

        }
        return nextPos;
    }


}
