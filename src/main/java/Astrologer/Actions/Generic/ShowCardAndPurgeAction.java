package Astrologer.Actions.Generic;

import Astrologer.Effects.PurgeCardEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShowCardAndPurgeAction extends AbstractGameAction {
    private AbstractCard card = null;
    private CardGroup group;
    private static final float PURGE_DURATION = 0.1F;

    public ShowCardAndPurgeAction(AbstractCard card, CardGroup group) {
        this.card = card;
        this.group = group;
        this.duration = PURGE_DURATION;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (this.duration == PURGE_DURATION) {
            AbstractDungeon.effectList.add(new PurgeCardEffect(this.card, this.group));
            if (AbstractDungeon.player.limbo.contains(this.card)) {
                AbstractDungeon.player.limbo.removeCard(this.card);
            }

            AbstractDungeon.player.cardInUse = null;
        }

        this.tickDuration();
    }
}
