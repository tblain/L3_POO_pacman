package pacman;

public class MonsterAnimatorFactory {
    public PokeyAnimator pkanim;
    public ShadowAnimator shanim;
    public SpeedyAnimator spanim;

    public MonsterAnimatorFactory() {
        this.pkanim = new PokeyAnimator();
        this.shanim = new ShadowAnimator();
        this.spanim = new SpeedyAnimator();
    }

    public MonsterAnimator getAnimatorOfMonster(MonsterName name)
    {
        switch (name)
        {
            case Pokey:
                return this.pkanim;
            case Shadow:
                return this.shanim;
            case Speedy:
                return this.spanim;
        }
        return null;
    }
}
