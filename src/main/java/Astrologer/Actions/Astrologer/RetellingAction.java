package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.ShuffleHandInDrawAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RetellingAction extends AbstractGameAction {
    public RetellingAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        amount += AbstractDungeon.player.hand.size();

        if (amount > 0)
        {
            AbstractDungeon.actionManager.addToTop(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, amount));
        }

        AbstractDungeon.actionManager.addToTop(new ShuffleHandInDrawAction());

        this.isDone = true;
    }
}
