package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DarkMatterPower extends BasePower {
    public static final String NAME = "DarkMatter";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private int triggeredThisTurn;

    public DarkMatterPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
        triggeredThisTurn = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!(card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS) && triggeredThisTurn < amount)
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(), 1, true, true));
            triggeredThisTurn++;
            this.flash();
        }
    }

    @Override
    public void atEndOfRound() {
        this.triggeredThisTurn = 0;
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