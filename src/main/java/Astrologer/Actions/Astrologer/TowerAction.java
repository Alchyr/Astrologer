package Astrologer.Actions.Astrologer;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class TowerAction extends AbstractGameAction {
    public TowerAction(AbstractCreature source, int damage, DamageInfo.DamageType damageType)
    {
        this.source = source;
        this.amount = damage;
        this.damageType = damageType;
    }

    @Override
    public void update() {
        AbstractMonster target = AbstractDungeon.getRandomMonster();

        if (target != null && !target.isDeadOrEscaped() && target.currentHealth > 0)
        {
            DamageInfo damageInfo = new DamageInfo(source, amount, damageType);

            damageInfo.applyPowers(damageInfo.owner, this.target);

            AbstractDungeon.actionManager.addToTop(new DamageAction(target, damageInfo, AttackEffect.NONE));
            if (MathUtils.randomBoolean())
            {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ExplosionSmallEffect(target.hb.cX + MathUtils.random(-35.0f * Settings.scale, 35.0f * Settings.scale), target.hb.cY + MathUtils.random(0, 20.0f * Settings.scale))));
            }
            else
            {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(target.drawX, target.drawY),0.1f));
                AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
        }
    }
}
