package Astrologer.Actions.Generic;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.ArrayList;

public class ReduceRandomCostAction extends AbstractGameAction {
    public ReduceRandomCostAction(int amount)
    {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.size() == 1)
        {
            AbstractCard c = AbstractDungeon.player.hand.getBottomCard();
            if (c.cost >= 0 || c.costForTurn >= 0)
            {
                reduceCost(c);
            }
        }
        else if (AbstractDungeon.player.hand.size() > 1)
        {
            ArrayList<AbstractCard> canReduce = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c.cost >= 1 && c.costForTurn >= 1) //prioritize cards that definitely can be reduced
                {
                    canReduce.add(c);
                }
            }

            if (canReduce.size() == 0) //didn't find any good choices, now less picky.
            {
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                {
                    if (c.cost >= 1 || c.costForTurn >= 1)
                    {
                        canReduce.add(c);
                    }
                }
            }

            if (canReduce.size() == 1)
            {
                reduceCost(canReduce.get(0));
            }
            else if (canReduce.size() > 1)
            {
                reduceCost(canReduce.get(AbstractDungeon.cardRandomRng.random(canReduce.size() - 1)));
            }
        }

        this.isDone = true;
    }

    private void reduceCost(AbstractCard c)
    {
        for (AbstractCard card : GetAllInBattleInstances.get(c.uuid))
        {
            card.modifyCostForCombat(-this.amount);
            card.superFlash(Color.GOLD.cpy());
        }
    }
}
