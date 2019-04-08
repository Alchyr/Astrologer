package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SetHPAction extends AbstractGameAction {
     public SetHPAction(AbstractCreature target, int setHP)
     {
         this.target = target;
         this.amount = setHP;
     }

    @Override
    public void update() {
         this.target.currentHealth = Math.min(this.amount, target.maxHealth);
         this.target.healthBarUpdatedEvent();
         this.isDone = true;
    }
}
