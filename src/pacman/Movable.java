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

    public Coordonnees getPos() {
        return pos;
    }

    public void setPos(Coordonnees pos) {
        this.pos = pos;
    }

    /**
     * retourne la position + 1 grâce à la direction
     * @return
     */
    public Coordonnees getNextPosition()
    {
        Coordonnees nextPos = this.pos;

        switch (this.dir)
        {
            case UP:
            {
                nextPos.y--;
                break;
            }
            case DOWN:
            {
                nextPos.y++;
                break;
            }
            case LEFT:
            {
                nextPos.x--;
                break;
            }
            case RIGHT:
            {
                nextPos.x++;
                break;
            }

        }
        return nextPos;
    }


}
