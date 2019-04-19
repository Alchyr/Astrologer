package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class ReduceCostByAmountAction extends AbstractGameAction {
    private UUID uuid;

    public ReduceCostByAmountAction(UUID targetUUID, int amount) {
        this.uuid = targetUUID;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid))
        {
            c.modifyCostForCombat(-this.amount);
        }

        this.isDone = true;
    }
}