package pacman;

public class Monster extends Movable {
    protected Pacman pacman;
    protected boolean init;

    public Monster(Coordonnees pos, Plateau plateau)
    {
        super(pos, plateau);
        this.init = false;
    }
    
    public void init(Pacman pacman) {
        this.pacman = pacman;
        this.init = true;
    }

    @Override
    public Coordonnees getNextMove() {
        return null;
    }

    @Override
    public void run() {

    }
}
