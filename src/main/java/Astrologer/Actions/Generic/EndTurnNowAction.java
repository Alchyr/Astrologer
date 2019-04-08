package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class EndTurnNowAction extends AbstractGameAction {
    @Override
    public void update() {
        AbstractDungeon.actionManager.cardQueue.clear();

        for (AbstractCard c : AbstractDungeon.player.limbo.group)
            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));

        AbstractDungeon.player.limbo.group.clear();
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);

        this.isDone = true;
    }
}