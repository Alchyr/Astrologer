package Astrologer.Actions.Astrologer;

import Astrologer.AstrologerMod;
import Astrologer.Interfaces.NoStellarChangePower;
import Astrologer.Patches.StellarPhaseValue;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AdvancePhaseAction extends AbstractGameAction {
    public AdvancePhaseAction()
    {
        this.amount = 1;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        for (AbstractPower p : AbstractDungeon.player.powers)
        {
            if (p instanceof NoStellarChangePower)
            {
                p.flash();
                this.isDone = true;
                return;
            }
        }

        int newValue = StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) + this.amount;
        if (newValue < 0) {
            newValue = 0;
        }
        if (newValue > StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player)) {
            newValue = StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player);
        }
        StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, newValue);
        StellarPhaseValue.stellarAlignment.set(AbstractDungeon.player, !StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player));
        AstrologerMod.stellarUI.updateStellarPhase(newValue);
        AstrologerMod.stellarUI.updateStellarAlignment(StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player));
        AstrologerMod.stellarUI.updateTooltip();
        this.isDone = true;
    }
}
