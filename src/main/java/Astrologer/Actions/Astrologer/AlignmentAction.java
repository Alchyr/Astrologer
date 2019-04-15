package Astrologer.Actions.Astrologer;

import Astrologer.Abstracts.AbstractXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AlignmentAction extends AbstractXAction {
    private int bonusAmt;

    public AlignmentAction(int bonusAmt)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;

        this.bonusAmt = bonusAmt;
    }

    public void update() {
        amount += bonusAmt;

        if (amount > 0 && !AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractCard copy = AbstractDungeon.player.drawPile.getBottomCard().makeStatEquivalentCopy();

            copy.modifyCostForTurn(-amount);

            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(copy, amount,true));
        }

        this.isDone = true;
    }
}