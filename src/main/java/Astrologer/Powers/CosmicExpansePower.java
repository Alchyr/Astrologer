package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CosmicExpansePower extends BasePower {
    public static final String NAME = "CosmicExpanse";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CosmicExpansePower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onInitialApplication() {
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player) + this.amount);
        AstrologerMod.stellarUI.updateTooltip();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player) + stackAmount);
        AstrologerMod.stellarUI.updateTooltip();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player) - this.amount);
        AstrologerMod.stellarUI.updateTooltip();
    }

    @Override
    public void reducePower(int reduceAmount) {
        int reduction = amount;
        super.reducePower(reduceAmount);
        reduction = reduction - amount;
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player) - reduction);
        AstrologerMod.stellarUI.updateTooltip();
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}