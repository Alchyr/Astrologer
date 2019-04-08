package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.PlayCardAction;
import Astrologer.Patches.CardsPlayedThisCombatPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class MagicianAction extends AbstractGameAction {
    private boolean upgraded;

    public MagicianAction(boolean upgraded)
    {
        this.upgraded = upgraded;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        HashMap<AbstractCard, CardGroup> unplayedCardMap = new HashMap<>();
        CardGroup unplayedCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (upgraded)
        {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.contains(c))
                {
                    unplayedCardMap.put(c, AbstractDungeon.player.discardPile);
                    unplayedCards.addToRandomSpot(c);
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.contains(c))
            {
                unplayedCardMap.put(c, AbstractDungeon.player.drawPile);
                unplayedCards.addToRandomSpot(c);
            }
        }

        if (!unplayedCardMap.isEmpty())
        {
            AbstractCard toPlay = unplayedCards.getBottomCard();
            if (toPlay != null)
            {
                CardGroup sourceGroup = unplayedCardMap.get(toPlay);
                AbstractDungeon.actionManager.addToTop(new PlayCardAction(toPlay, sourceGroup, false));
            }
        }

        this.isDone = true;
    }
}
