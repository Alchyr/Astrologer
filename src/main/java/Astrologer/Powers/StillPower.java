package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Interfaces.NoStellarChangePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StillPower extends BasePower implements NoStellarChangePower {
    public static final String NAME = "Still";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public StillPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = descriptions[0] + this.amount + descriptions[1];
        } else {
            this.description = descriptions[0] + this.amount + descriptions[2];
        }

    }
}
