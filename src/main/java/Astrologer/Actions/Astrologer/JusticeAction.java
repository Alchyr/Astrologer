package Astrologer.Actions.Astrologer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class JusticeAction extends AbstractGameAction {
    public JusticeAction(AbstractCreature target, AbstractCreature source)
    {
        this.source = source;
        this.target = target;
    }

    @Override
    public void update() {
        int hpLoss = target.currentBlock * 2;
        AbstractDungeon.actionManager.addToTop(new LoseHPAction(target, source, hpLoss));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(target.drawX, target.drawY),0.1f));
        AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));

        this.isDone = true;
    }
}
