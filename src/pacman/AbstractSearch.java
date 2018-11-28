package pacman;

import java.util.ArrayList;

public class AbstractSearch {

    protected Plateau p;
    protected SearchCoord [] searchPath = null;
    protected int pathCount;
    protected int maxDepth;
    protected SearchCoord startCoord, goalCoord, currentCoord;
    protected boolean isSearching = true;
    public ArrayList<Monster> mst;
    public boolean isInFear;

    public AbstractSearch(Plateau _p, SearchCoord _startCoord, SearchCoord _goalCoord, ArrayList<Monster> _mst, boolean _isInFear) {
        //System.out.println("contruct");
        p = _p;
        startCoord = _startCoord;
        goalCoord = _goalCoord;
        mst = _mst;
        isInFear = _isInFear;
        initSearch();
    }

    protected void initSearch() {
        //System.out.println("init");
        if (searchPath == null) {
            searchPath = new SearchCoord[1000];
            for (int i=0; i<1000; i++) {
                searchPath[i] = new SearchCoord(0, 0);
            }
        }
        pathCount = 0;
        currentCoord = startCoord;
        searchPath[pathCount++] = currentCoord;
        //System.out.println("fin init");
    }

    public Plateau getPlateau() { return p; }

    protected boolean equals(SearchCoord c1, SearchCoord c2) {
        return c1.getX() == c2.getX() && c1.getY() == c2.getY();
    }

    public SearchCoord [] getPath() {
        SearchCoord [] ret = new SearchCoord[maxDepth];

        for (int i=0; i<maxDepth; i++) {
            ret[i] = searchPath[i];
        }
        return ret;
    }


    protected SearchCoord [] getPossibleMoves(SearchCoord coord) {
        SearchCoord tempMoves [] = new SearchCoord[4];
        tempMoves[0] = tempMoves[1] = tempMoves[2] = tempMoves[3] = null;
        int x = coord.getX();
        int y = coord.getY();
        int num = 0;

        if (!p.getCase(x - 1, y).isMur() && pasDeMonstreSurCoord(new Coordonnees(x -1, y))) {
            tempMoves[num++] = new SearchCoord(x - 1, y);
        }
        if (!p.getCase(x + 1, y).isMur() && pasDeMonstreSurCoord(new Coordonnees(x + 1, y))) {
            tempMoves[num++] = new SearchCoord(x + 1, y);
        }

        if (!p.getCase(x, y + 1).isMur() && pasDeMonstreSurCoord(new Coordonnees(x, y + 1))) {
            tempMoves[num++] = new SearchCoord(x, y + 1);
        }

        if (!p.getCase(x, y - 1).isMur() && pasDeMonstreSurCoord(new Coordonnees(x, y - 1))) {
            tempMoves[num++] = new SearchCoord(x, y - 1);
        }
        return tempMoves;
    }

    public boolean pasDeMonstreSurCoord(Coordonnees coord) {
        boolean bool = true;

        for (Monster monster : mst) {
            if (monster.pos.equals(coord)){
                bool = false;
            }
        }

        return bool;
    }
}