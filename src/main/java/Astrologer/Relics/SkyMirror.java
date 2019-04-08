package Astrologer.Relics;

import Astrologer.Abstracts.BaseRelic;
import Astrologer.AstrologerMod;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.relics.VelvetChoker;

public class SkyMirror extends BaseRelic {
    public static final String NAME = "SkyMirror";
    public static final String ID = AstrologerMod.makeID("SkyMirror");

    public static final int ACTIVE_VALUE = -2;

    public SkyMirror()
    {
        super(ID, NAME,
        RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.beginLongPulse();
        this.pulse = true;
        this.counter = ACTIVE_VALUE;
    }

    @Override
    public void onTrigger() {
        this.stopPulse();
        this.counter = -1;
        this.flash();
    }

    @Override
    public void onVictory() {
        this.stopPulse();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SkyMirror();
    }

    //Hooray for hardcoded!
    //Check - UseCardActionPatch
}