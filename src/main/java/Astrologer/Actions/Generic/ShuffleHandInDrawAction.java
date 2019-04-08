package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ShuffleHandInDrawAction extends AbstractGameAction {
    private static final float DURATION = 0.2f;

    public ShuffleHandInDrawAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || AbstractDungeon.player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> cardsToMove = new ArrayList<>(AbstractDungeon.player.hand.group);
            for(AbstractCard c : cardsToMove) { //avoiding co-modification error
                AbstractDungeon.player.hand.moveToDeck(c, true);
            }
        }

        this.tickDuration();
    }
}
