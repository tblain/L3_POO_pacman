package pacman.model;

public abstract class AbstractMap {
    private int ROW;
    private int COLUMN;

    private int[][]MAP;

    private Tp tp;

    public AbstractMap(int row, int column, Tp tp)
    {
        ROW = row;
        COLUMN = column;
        this.tp = tp;
    }

    public void setMap(int[][]map)
    {
        this.MAP = map;
    }

    public int getRow()
    {
        return ROW;
    }

    public int getColumn()
    {
        return COLUMN;
    }

    public int[][] getMAP()
    {
        return MAP;
    }

    public Tp getTp() {
        return tp;
    }
}
