package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.PlayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class TheWorldAction extends AbstractGameAction {
    @Override
    public void update() {
        CardGroup exhaustedCopies = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        HashMap<AbstractCard, AbstractCard> originalCards = new HashMap<>();

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            AbstractCard copy = c.makeSameInstanceOf();
            exhaustedCopies.addToRandomSpot(copy);
            originalCards.put(copy, c);
        }

        for (AbstractCard c : exhaustedCopies.group)
        {
            AbstractDungeon.actionManager.addToTop(new PlayCardAction(c, originalCards.get(c), null, false));
        }

        this.isDone = true;
    }
}
