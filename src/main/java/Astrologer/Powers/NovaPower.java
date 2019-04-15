package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Actions.Generic.TriggerPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NovaPower extends BasePower {
    public static final String NAME = "Nova";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public NovaPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            if (!m.isDeadOrEscaped() && m.hasPower(Starlit.ID))
            {
                for (int i = 0; i < this.amount; ++i)
                    AbstractDungeon.actionManager.addToBottom(new TriggerPowerAction(m, Starlit.ID));
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = descriptions[0] + this.amount + descriptions[1];
        }
        else {
            this.description = descriptions[0] + this.amount + descriptions[2];
        }
    }
}