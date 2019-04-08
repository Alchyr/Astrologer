package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KillEnemyAction extends AbstractGameAction {
    private AbstractMonster m;

    public KillEnemyAction(AbstractMonster m)
    {
        this.m = m;
    }

    @Override
    public void update() {
        int damage = 13;
        m.currentHealth = 0;
        while (!m.isDying && damage < 165191049) { //integer cap / 13
            m.damage(new DamageInfo(null, damage, DamageInfo.DamageType.HP_LOSS));
            damage *= 13;
        }
        if (!m.isDying)
            m.die(true); //backup to ensure it dies (hopefully)
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }
}
