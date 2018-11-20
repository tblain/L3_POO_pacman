package pacman;

public class Coordonnees {
    public int x;
    public int y;

    public Coordonnees(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equal(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(o instanceof Coordonnees)
        {
            Coordonnees coord = (Coordonnees)o;

            if(coord.x != this.x)
            {
                return false;
            }

            if(coord.y != this.y)
            {
                return false;
            }

            return true;
        }
        return false;
    }
}
