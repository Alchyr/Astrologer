package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;

public class BlockPerCardAction extends AbstractGameAction {
    private CardGroup cardSource;
    private Predicate<AbstractCard> condition;

    public BlockPerCardAction(int block, CardGroup source, Predicate<AbstractCard> condition)
    {
        this.amount = block;
        this.cardSource = source;
        this.condition = condition;
        this.actionType = ActionType.BLOCK;
    }

    @Override
    public void update() {
        for (AbstractCard c : cardSource.group)
        {
            if (condition.test(c))
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
            }
        }
        this.isDone = true;
    }
}
