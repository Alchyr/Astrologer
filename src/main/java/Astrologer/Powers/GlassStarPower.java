package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GlassStarPower extends BasePower implements NonStackablePower {
    public static final String NAME = "GlassStar";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private boolean triggered = false;

    public GlassStarPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));

        if (!triggered)
        {
            this.flash();
            action.exhaustCard = true;

            int cost = card.costForTurn;
            if (card.cost == -1)
                cost = card.energyOnUse;

            if (cost > 0)
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, cost * this.amount));

            triggered = true;
        }
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}

