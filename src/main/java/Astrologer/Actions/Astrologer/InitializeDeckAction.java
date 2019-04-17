package Astrologer.Actions.Astrologer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class InitializeDeckAction extends AbstractGameAction {
    private ArrayList<AbstractCard> noReset;

    public InitializeDeckAction(ArrayList<AbstractCard> noReset)
    {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.4f;
        this.noReset = noReset;
    }

    @Override
    public void update() {
        CardGroup finalDeck = new CardGroup(AbstractDungeon.player.masterDeck, CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : noReset)
        {
            finalDeck.removeCard(c);
        }

        AbstractDungeon.player.drawPile.initializeDeck(finalDeck);

        this.tickDuration();
    }
}
