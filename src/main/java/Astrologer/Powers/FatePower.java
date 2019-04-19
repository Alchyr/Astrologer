package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Enums.CustomTags;
import Astrologer.Interfaces.ActivateStellarPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FatePower extends BasePower implements ActivateStellarPower {
    public static final String NAME = "Fate";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public FatePower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomTags.STELLAR))
        {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0];
        }
        else
        {
            this.description = descriptions[1] + this.amount + descriptions[2];
        }
    }
}
