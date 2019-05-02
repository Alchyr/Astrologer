package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Interfaces.EnemyOnDrawPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HangedPower extends BasePower implements EnemyOnDrawPower {
    public static final String NAME = "Hanged";
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public HangedPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, source, amount);
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void onPlayerDrawCard(AbstractCard c) {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new LoseHPAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.NONE));
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}