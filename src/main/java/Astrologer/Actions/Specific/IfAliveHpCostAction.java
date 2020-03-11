package Astrologer.Actions.Specific;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

public class IfAliveHpCostAction extends AbstractGameAction {
    public IfAliveHpCostAction(AbstractMonster target, int amount)
    {
        this.target = target;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (target != null && !target.isDeadOrEscaped())
        {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToTop(new LoseHPAction(p, p, this.amount));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new OfferingEffect(), 0.1F));
        }
        this.isDone = true;
    }
}
