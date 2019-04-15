package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DamagePerExhaustedAction extends AbstractGameAction {
    private ExhaustConditionalCardsAction exhaustAction;

    public DamagePerExhaustedAction(AbstractCreature source, ExhaustConditionalCardsAction cardSource, int baseDamage, DamageInfo.DamageType damageType)
    {
        this.source = source;
        this.amount = baseDamage;
        this.damageType = damageType;

        this.exhaustAction = cardSource;

        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        for (int i = 0; i < exhaustAction.exhaustedCards.size(); ++i)
        {
            AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(this.source, this.amount, this.damageType, AttackEffect.FIRE));
        }

        this.isDone = true;
    }
}
