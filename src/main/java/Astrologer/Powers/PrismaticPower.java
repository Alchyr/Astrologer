package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrismaticPower extends BasePower {
    public static final String NAME = "Prismatic";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private boolean triggered;

    public PrismaticPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        triggered = false;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        triggered = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (PhaseCheck.isStar(card) && !this.triggered) {
            this.flash();
            this.triggered = true;
            AbstractMonster m = AbstractDungeon.getRandomMonster();

            if (m != null && !m.isDeadOrEscaped())
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new Starlit(m, this.amount), this.amount));
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0] + this.amount + descriptions[1];
        }
        else
        {
            this.description = descriptions[0] + this.amount + descriptions[2];
        }
    }
}