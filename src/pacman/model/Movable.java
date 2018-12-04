package pacman.model;

import pacman.model.Coordonnees;
import pacman.model.Direction;

public abstract class Movable implements Runnable{
    protected Coordonnees pos;
    protected boolean alive;
    protected Direction dir;

    protected Plateau plateau;

    Movable(Coordonnees pos, Direction dir)
    {
        this.pos = pos;
        this.alive = true;
        this.dir = dir;
    }

    public synchronized void setAlive(boolean alive)
    {
        this.alive = alive;
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

    /**
     * On set la dir uniquement si ce n'est pas un mur
     * @param dir
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }

    /**
     * Retourne la position suivante par rapport à la position et direction actuelle
     * @return
     */
    Coordonnees getNextPosition()
    {
        Coordonnees nextPos = (Coordonnees)this.pos.clone();

        return getCoordByDir(this.dir, nextPos);
    }

    /**
     * Retourne la position suivante par rapport à la position actuelle et la direction donnée
     * @param dir
     * @return
     */
    Coordonnees getNextPosition(Direction dir)
    {
        Coordonnees nextPos = (Coordonnees)this.pos.clone();


        return getCoordByDir(dir, nextPos);
    }

    /**
     * Retourne des coordonnées par rapport à une des coordonnées et une direction
     * @param dir
     * @param coord
     * @return
     */
    Coordonnees getCoordByDir(Direction dir, Coordonnees coord)
    {
        switch (dir) {
            case UP: {
                coord.setY(coord.getY() - 1);
                break;
            }
            case DOWN: {
                coord.setY(coord.getY() + 1);
                break;
            }
            case LEFT: {
                coord.setX(coord.getX() - 1);
                break;
            }
            case RIGHT: {
                coord.setX(coord.getX() + 1);
                break;
            }
        }
        return coord;
    }

    public boolean isAlive()
    {
        return this.alive;
    }

}
