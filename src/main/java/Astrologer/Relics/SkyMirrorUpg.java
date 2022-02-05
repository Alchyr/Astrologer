package Astrologer.Relics;

import Astrologer.Abstracts.BaseRelic;
import Astrologer.AstrologerMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SkyMirrorUpg extends BaseRelic {
    public static final String IMG = "UniversalMirror";
    public static final String ID = AstrologerMod.makeID("SkyMirrorUpg");

    public SkyMirrorUpg()
    {
        super(ID, IMG, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onTrigger() {
        this.flash();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SkyMirrorUpg();
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(SkyMirror.ID);
    }

    @Override
    public void obtain() {
        for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
            if (AbstractDungeon.player.relics.get(i).relicId.equals(SkyMirror.ID)) {
                instantObtain(AbstractDungeon.player, i, true);
                return;
            }
        }
        super.obtain();
    }

    //Hooray for hardcoded!
    //Check - UseCardActionPatch
}