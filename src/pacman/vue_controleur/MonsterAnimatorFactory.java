package pacman.vue_controleur;

import pacman.model.MonsterName;

public class MonsterAnimatorFactory {
    public PokeyAnimator pkanim;
    public ShadowAnimator shanim;
    public SpeedyAnimator spanim;
    public BashfulAnimator baanim;

    public MonsterAnimatorFactory() {
        this.pkanim = new PokeyAnimator();
        this.shanim = new ShadowAnimator();
        this.spanim = new SpeedyAnimator();
        this.baanim = new BashfulAnimator();
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
            case Bashful:
                return this.baanim;
        }
        return null;
    }
}

