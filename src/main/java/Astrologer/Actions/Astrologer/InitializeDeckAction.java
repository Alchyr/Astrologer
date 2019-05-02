package Astrologer.Actions.Astrologer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.ArrayList;

public class InitializeDeckAction extends AbstractGameAction {
    private AbstractCard noReset;

    public InitializeDeckAction(AbstractCard noReset)
    {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.4f;
        this.noReset = noReset;
    }

    @Override
    public void update() {
        CardGroup finalDeck = new CardGroup(AbstractDungeon.player.masterDeck, CardGroup.CardGroupType.UNSPECIFIED);

        ArrayList<AbstractCard> toRemove = new ArrayList<>();
        for (AbstractCard c : finalDeck.group)
        {
            if (c.uuid.equals(noReset.uuid))
            {
                toRemove.add(c);
            }
        }

        finalDeck.group.removeAll(toRemove);

        AbstractDungeon.player.drawPile.initializeDeck(finalDeck);

        this.tickDuration();
    }
}
