package pacman;

public class SearchCoord extends Coordonnees{
    public SearchCoord predecessor;
    public int x, y;
    public SearchCoord(int _x, int _y) {
        super(_x, _y);
    }

    @Override
    public String toString() {
        return "SearchCoord{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
