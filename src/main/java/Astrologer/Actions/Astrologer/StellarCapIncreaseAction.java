package Astrologer.Actions.Astrologer;

import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StellarCapIncreaseAction extends AbstractGameAction {
    public StellarCapIncreaseAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player) + this.amount);
        AstrologerMod.stellarUI.updateTooltip();
        this.isDone = true;
    }
}
