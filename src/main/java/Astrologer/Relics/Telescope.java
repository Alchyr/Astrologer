package Astrologer.Relics;

import Astrologer.Abstracts.BaseRelic;
import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Telescope extends BaseRelic {
    public static final String NAME = "Telescope";
    public static final String ID = AstrologerMod.makeID("Telescope");

    private static final int BOOST = 4;

    public Telescope()
    {
        super(ID, NAME, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        int newValue = StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) + BOOST;
        if (newValue > StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player)) {
            newValue = StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player);
        }
        StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, newValue);
        AstrologerMod.stellarUI.updateStellarPhase(newValue);
        AstrologerMod.stellarUI.updateTooltip();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Telescope();
    }
}