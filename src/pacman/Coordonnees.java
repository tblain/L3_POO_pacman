package pacman;

public class Coordonnees implements Cloneable{
    private int x;
    private int y;

    public Coordonnees(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public String toString()
    {
        return "x = " + this.x + " || y = " + this.y;
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

    @Override
    public boolean equals(Object o)
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

    public Object clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
