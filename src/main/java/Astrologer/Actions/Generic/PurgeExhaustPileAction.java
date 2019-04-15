package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PurgeExhaustPileAction extends AbstractGameAction {
    private static final float DURATION = 0.5f;

    public PurgeExhaustPileAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.exhaustPile.isEmpty())
        {
            this.isDone = true;
            return;
        }

        CardCrawlGame.sound.play("CARD_BURN");
        AbstractDungeon.player.exhaustPile.clear();

        this.isDone = true;
    }
}
