package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class PlayCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private AbstractCard card;
    private DrawAndSaveCardsAction sourceAction;
    private CardGroup sourceGroup;

    public PlayCardAction(AbstractCard cardToUse, AbstractCard originalCard, CardGroup source, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.card = cardToUse;

        if (originalCard != null)
        {
            card.current_x = originalCard.current_x;
            card.current_y = originalCard.current_y;
        }
        else
        {
            card.current_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            card.current_y = (float)Settings.HEIGHT / 2.0F;
        }

        this.sourceAction = null;
        this.sourceGroup = source;
        this.exhaustCards = exhausts;
    }
    public PlayCardAction(AbstractCard cardToUse, CardGroup source, boolean exhausts) {
        this(cardToUse, null, source, exhausts);
    }

    public PlayCardAction(DrawAndSaveCardsAction sourceAction, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.card = null;
        this.sourceAction = sourceAction;
        this.sourceGroup = AbstractDungeon.player.hand;
        this.exhaustCards = exhausts;
    }

    public void update() {
        if (card == null && sourceAction != null && !sourceAction.getDrawnCards().isEmpty())
        {
            card = sourceAction.getDrawnCards().get(0);
        }

        if (card == null || (sourceGroup != null && !sourceGroup.contains(card)))
        {
            this.isDone = true;
            return;
        }

        //card.freeToPlayOnce = true;
        card.exhaustOnUseOnce = this.exhaustCards && !(card.type == AbstractCard.CardType.POWER);

        AbstractDungeon.actionManager.addToTop(new UpdateHandAction());

        if (sourceGroup == null)
        {
            AbstractDungeon.player.limbo.addToBottom(card);

            card.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            card.target_y = (float)Settings.HEIGHT / 2.0F;

            card.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, true, EnergyPanel.getCurrentEnergy(),true,true));
        } else {
            if (sourceGroup.type != CardGroup.CardGroupType.HAND)
            {
                sourceGroup.removeCard(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);

                AbstractDungeon.player.limbo.group.add(card);

                card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
                card.target_y = (float)Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.targetDrawScale = 0.75F;


                card.applyPowers();
                this.addToTop(new NewQueueCardAction(card, true, false, true));
                this.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
            else
            {
                card.applyPowers();
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, true, EnergyPanel.getCurrentEnergy(), true, true));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            }
        }


        this.isDone = true;
    }
}