package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CalculateLaterDamageAllAction extends AbstractGameAction {
    private AbstractCard c;

    public CalculateLaterDamageAllAction(AbstractCard c) {
        this.c = c;
    }

    public void update() {
        this.c.calculateCardDamage(null);
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, this.c.multiDamage, this.c.damageTypeForTurn, AttackEffect.BLUNT_HEAVY));
        this.isDone = true;
    }
}
