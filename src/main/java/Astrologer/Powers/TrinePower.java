package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TrinePower extends BasePower {
    public static final String NAME = "Trine";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private ArrayList<AbstractCard> copyCards;

    public TrinePower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        copyCards = new ArrayList<>();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        copyCards.clear();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!copyCards.contains(card) && this.amount > 0) {
            this.amount--;
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.freeToPlayOnce = true;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            copyCards.add(tmp);
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
        }
        if (this.amount <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0];
        }
        else
        {
            this.description = descriptions[1] + descriptions[2];
        }
    }
}
