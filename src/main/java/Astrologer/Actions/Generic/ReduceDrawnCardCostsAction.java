package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class ReduceDrawnCardCostsAction extends AbstractGameAction {
    private DrawAndSaveCardsAction cardSource;

    public ReduceDrawnCardCostsAction(DrawAndSaveCardsAction cardSource, int amount)
    {
        this.cardSource = cardSource;
        this.amount = amount;
    }

    public void update()
    {
        for (AbstractCard c : cardSource.getDrawnCards())
        {
            for (AbstractCard copies : GetAllInBattleInstances.get(c.uuid))
            {
                copies.modifyCostForCombat(-this.amount);
            }
        }

        this.isDone = true;
    }
}
