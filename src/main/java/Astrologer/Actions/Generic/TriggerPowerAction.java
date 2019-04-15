package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TriggerPowerAction extends AbstractGameAction {
    private String powerID;

    public TriggerPowerAction(AbstractCreature target, String powerID)
    {
        this.target = target;
        this.powerID = powerID;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        if (this.target.hasPower(powerID))
        {
            AbstractPower toTrigger = this.target.getPower(powerID);
            toTrigger.onSpecificTrigger();
        }

        this.isDone = true;
    }
}
