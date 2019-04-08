package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LoversPower extends BasePower implements NonStackablePower {
    public static final String NAME = "Lovers";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private AbstractCard toRepeat;

    private boolean triggered;

    public LoversPower(final AbstractCreature owner, boolean a, AbstractCard toRepeat) {
        super(NAME + (a ? "A" : "B"), TYPE, TURN_BASED, owner, null, 0, toRepeat.uuid.toString());

        this.toRepeat = toRepeat;
        this.triggered = false;
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!triggered && card.uuid.equals(toRepeat.uuid))
        {
            this.triggered = true;

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
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            tmp.freeToPlayOnce = true;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));

            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner,this, 1));
        }
    }

    public void updateDescription() {
        if (toRepeat != null)
        {
            this.description = descriptions[0] + toRepeat.name + descriptions[1];
        }
        else
        {
            this.description = "";
        }
    }
}
