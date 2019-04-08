package Astrologer.Actions.Astrologer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StarOfCourageAction extends AbstractGameAction {
    public StarOfCourageAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c.type != AbstractCard.CardType.ATTACK)
            {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }

        this.isDone = true;
    }
}
