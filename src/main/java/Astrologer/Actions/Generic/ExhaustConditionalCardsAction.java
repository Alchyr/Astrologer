package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ExhaustConditionalCardsAction extends AbstractGameAction {
    private Predicate<AbstractCard> condition;

    public ArrayList<AbstractCard> exhaustedCards;

    public ExhaustConditionalCardsAction(Predicate<AbstractCard> condition)
    {
        this.condition = condition;
        this.actionType = ActionType.EXHAUST;

        exhaustedCards = new ArrayList<>();
    }

    @Override
    public void update() {
        HashMap<AbstractCard, CardGroup> cardsToExhaust = new HashMap<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (condition.test(c))
                cardsToExhaust.put(c, AbstractDungeon.player.hand);
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (condition.test(c))
                cardsToExhaust.put(c, AbstractDungeon.player.drawPile);
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (condition.test(c))
                cardsToExhaust.put(c, AbstractDungeon.player.discardPile);
        }

        for (Map.Entry<AbstractCard, CardGroup> entry : cardsToExhaust.entrySet())
        {
            entry.getValue().moveToExhaustPile(entry.getKey());
            exhaustedCards.add(entry.getKey());
            CardCrawlGame.dungeon.checkForPactAchievement();
            entry.getKey().exhaustOnUseOnce = false;
            entry.getKey().freeToPlayOnce = false;
        }
        this.isDone = true;
    }
}
