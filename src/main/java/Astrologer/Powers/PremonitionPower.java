package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PremonitionPower extends BasePower {
    public static final String NAME = "Premonition";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public PremonitionPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard) {
        if (abstractCard.isEthereal)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
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