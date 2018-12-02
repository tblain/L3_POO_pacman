package pacman;

import pacman.model.Coordonnees;
import pacman.model.Direction;

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

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    /**
     * retourne la position + 1 grâce à la direction
     * @return
     */
    public Coordonnees getNextPosition()
    {
        Coordonnees nextPos = (Coordonnees)this.pos.clone();

        switch (this.dir)
        {
            case UP:
            {
                nextPos.setY(nextPos.getY()-1);
                break;
            }
            case DOWN:
            {
                nextPos.setY(nextPos.getY()+1);
                break;
            }
            case LEFT:
            {
                nextPos.setX(nextPos.getX()-1);
                break;
            }
            case RIGHT:
            {
                nextPos.setX(nextPos.getX()+1);
                break;
            }

        }
        return nextPos;
    }


}
