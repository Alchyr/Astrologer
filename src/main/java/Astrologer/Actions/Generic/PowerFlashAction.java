package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PowerFlashAction extends AbstractGameAction {
    private AbstractPower p;
    private boolean sound;

    public PowerFlashAction(AbstractPower toFlash)
    {
        this(toFlash, true);
    }
    public PowerFlashAction(AbstractPower toFlash, boolean sound)
    {
        this.p = toFlash;
        this.sound = sound;
    }

    @Override
    public void update() {
        if (sound)
        {
            p.flashWithoutSound();
        }
        else
        {
            p.flash();
        }
        this.isDone = true;
    }
}
