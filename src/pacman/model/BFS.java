package pacman.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;


public class BFS extends AbstractSearch {

    protected class SearchCoordQueue {
        private Stack stack;

        public SearchCoordQueue() {
            this.stack = new Stack();
        }

        public void push(SearchCoord searchCoord) {
            this.stack.push(searchCoord);
        }

        public SearchCoord pop() {
            return (SearchCoord) this.stack.pop();
        }

        public SearchCoord peek() {
            return (SearchCoord) this.stack.peek();
        }

        public boolean empty() {
            return this.stack.empty();
        }

        @Override
        public String toString() {
            return String.valueOf(stack.size());
        }

        public void sort() {
            ArrayList<SearchCoord> tmp = new ArrayList<>();

            while (!stack.empty()) {
                tmp.add((SearchCoord) stack.pop());
            }

            tmp.sort(new SortCoordonees());
            for (SearchCoord coord : tmp) {
                stack.add(coord);
            }
        }
    }

    protected class SortCoordonees implements Comparator<SearchCoord> {

        @Override
        public int compare(SearchCoord o1, SearchCoord o2) {
            if(o1.distance(goalCoord) > o2.distance(goalCoord)) {
                return -1;
            } else {
                return 1;
            }
        }

    }

    public BFS(Plateau _p, SearchCoord _startCoord, SearchCoord _goalCoord, ArrayList<Monster> mst, boolean isInFear) {
        super(_p, _startCoord, _goalCoord, mst, isInFear);
    }

    public void performBFS() {
        //System.out.println("perform bfs");
        SearchCoord searchCoord = null;
        ArrayList<SearchCoord> alreadyVisited = new ArrayList<SearchCoord>(); // list of every visited searchCoord
        ArrayList<SearchCoord> listSearchCoord = new ArrayList<SearchCoord>();
        SearchCoord[] possibleMoves;
        SearchCoord possiblePos;

        SearchCoordQueue queue = new SearchCoordQueue(); // queue used to stored the next moves to compute
        queue.push(startCoord); // we add the start searchCoord to the queue

        boolean trouve = false;

        while(!queue.empty() && !trouve) { // while the queue is not empty or the goal has not been found

            searchCoord = queue.pop(); // remove the searchCoord from the queue and return the searchCoord

            if(!alreadyVisited.contains(searchCoord)) { // if the searchCoord has not been visited

                if(searchCoord.equals(goalCoord)) { // we check if the searchCoord is not the goal
                    trouve = true;
                } else {
                    possibleMoves = getPossibleMoves(searchCoord);

                    for (int i = 0; i < possibleMoves.length; i++) { // for each possible moves
                        if(possibleMoves[i] != null) { // we check if it's a correct searchCoord
                            possiblePos = possibleMoves[i];
                            queue.push(possiblePos); // add it to the list
                            possiblePos.predecessor = searchCoord; // set the current searchCoord as the predecessor of the possible searchCoord
                        }
                    }

                    queue.sort(); // we sort the list with the closest searchCoord from the goal in first


                    listSearchCoord.clear();

                }
            }
            alreadyVisited.add(searchCoord);

        }

        int i = 0;
        if(searchCoord.equals(goalCoord)) { // if the last searchCoord is the goal
            while(!searchCoord.equals(startCoord)) { // we start from the goal to the start
                searchPath[i++] = searchCoord; // we add the predecessor of the current searchCoord to the array searchPath
                searchCoord = searchCoord.predecessor; // then do the same for the predecessor
                this.maxDepth++;
            }
            searchPath[i++] = searchCoord.predecessor;
            this.maxDepth++;
            System.out.println(maxDepth);
        } else {
            searchPath[0] = startCoord;
            searchPath[1] = startCoord;
            searchPath[2] = startCoord;
            searchPath[3] = startCoord;
            this.maxDepth = 4;
        }

    }

}