package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageRandomEnemyAction extends AbstractGameAction {
    public DamageRandomEnemyAction(AbstractCreature source, int damage, DamageInfo.DamageType damageType, AttackEffect effect)
    {
        this.source = source;
        this.amount = damage;
        this.damageType = damageType;
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        AbstractMonster target = AbstractDungeon.getRandomMonster();

        if (target != null && !target.isDeadOrEscaped() && target.currentHealth > 0)
        {
            DamageInfo damageInfo = new DamageInfo(source, amount, damageType);

            damageInfo.applyPowers(damageInfo.owner, target);

            AbstractDungeon.actionManager.addToTop(new DamageAction(target, damageInfo, this.attackEffect));
        }

        this.isDone = true;
    }
}