package Astrologer.Abstracts;

import Astrologer.Actions.Astrologer.SupermassiveAction;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class SupermassiveCard extends BaseCard {
    private int supermassiveAmount;

    public SupermassiveCard(CardInfo cardInfo, boolean upgradesDescription, int supermassiveAmount)
    {
        super(cardInfo, upgradesDescription);
        this.supermassiveAmount = supermassiveAmount;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();

        if (c instanceof SupermassiveCard)
            ((SupermassiveCard) c).supermassiveAmount = this.supermassiveAmount;

        return c;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        AbstractDungeon.actionManager.addToTop(new SupermassiveAction(this, supermassiveAmount));
    }

    public abstract void doSupermassiveEffect(AbstractCard self);
}