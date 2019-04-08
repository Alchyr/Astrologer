package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Actions.Generic.MoveCardToBottomOfDrawAction;
import Astrologer.Actions.Generic.UpdateHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.StrengthPotion;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class PrestidigitationPower extends BasePower {
    public static final String NAME = "Prestidigitation";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public PrestidigitationPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.amount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
            AbstractDungeon.actionManager.addToBottom(new MoveCardToBottomOfDrawAction(this.amount, false, false));
            AbstractDungeon.actionManager.addToBottom(new UpdateHandAction());
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = descriptions[0] + this.amount + descriptions[1] + this.amount + descriptions[3];
        }
        else {
            this.description = descriptions[0] + this.amount + descriptions[2] + this.amount + descriptions[4];
        }
    }
}
