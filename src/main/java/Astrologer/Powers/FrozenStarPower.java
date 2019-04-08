package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.AstrologerMod;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FrozenStarPower extends BasePower {
    public static final String NAME = "FrozenStar";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true; //white number makes more sense

    public FrozenStarPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (PhaseCheck.isStar(card))
        {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}
