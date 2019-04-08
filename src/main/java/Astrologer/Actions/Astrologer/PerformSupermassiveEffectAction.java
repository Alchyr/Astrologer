package Astrologer.Actions.Astrologer;

import Astrologer.Abstracts.SupermassiveCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class PerformSupermassiveEffectAction extends AbstractGameAction {
    private SupermassiveCard c;

    public PerformSupermassiveEffectAction(SupermassiveCard supermassiveCard)
    {
        this.c = supermassiveCard;
    }

     @Override
     public void update()
     {
        c.doSupermassiveEffect(c);
        this.isDone = true;
     }
}
