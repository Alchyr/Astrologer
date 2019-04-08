package Astrologer.Actions.Astrologer;

import Astrologer.Abstracts.SupermassiveCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SupermassiveAction extends AbstractGameAction {
    private SupermassiveCard triggerCard;

    public SupermassiveAction(SupermassiveCard card, int amount)
    {
        triggerCard = card;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        CardGroup canExhaust = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (!c.equals(triggerCard))
                canExhaust.addToRandomSpot(c);
        }

        int exhaustAmount = Math.min(this.amount, canExhaust.size());

        for (int i = 0; i < exhaustAmount; i++)
        {
            AbstractDungeon.actionManager.addToTop(new PerformSupermassiveEffectAction(triggerCard));
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(canExhaust.getTopCard(), AbstractDungeon.player.hand));
            canExhaust.removeTopCard();
        }

        this.isDone = true;
    }
}
