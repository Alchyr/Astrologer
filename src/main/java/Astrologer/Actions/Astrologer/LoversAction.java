package Astrologer.Actions.Astrologer;

import Astrologer.Powers.LoversPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class LoversAction extends AbstractGameAction {
    private static final float DURATION = 0.1f;

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:Lovers");
    public static final String[] TEXT = uiStrings.TEXT;

    private boolean selecting;
    private boolean stellar;

    public LoversAction(boolean stellar)
    {
        this.duration = DURATION;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.stellar = stellar;

        selecting = false;
    }

    @Override
    public void update() {
        AbstractCard a = null;
        AbstractCard b = null;

        if (!selecting)
        {
            if (AbstractDungeon.player.hand.size() > 2) //Open window to choose two cards
            {
                selecting = true;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false);
            }
            else if (AbstractDungeon.player.hand.size() == 2)
            {
                //Auto-select
                a = AbstractDungeon.player.hand.group.get(0);
                b = AbstractDungeon.player.hand.group.get(1);
                this.isDone = true;
            }
            else //not enough cards
            {
                this.isDone = true;
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (AbstractDungeon.handCardSelectScreen.selectedCards.size() == 2)
                {
                    a = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                    b = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    AbstractDungeon.player.hand.addToTop(c);
                }
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            this.isDone = true;
        }


        if (a != null && b != null)
        {
            boolean costModifiedA = a.isCostModified;
            boolean turnCostModifiedA = a.isCostModifiedForTurn;
            boolean freeToPlayOnceA = a.freeToPlayOnce;
            int costA = a.cost;
            int costForTurnA = a.costForTurn;

            a.isCostModified = b.isCostModified;
            a.isCostModifiedForTurn = b.isCostModifiedForTurn;
            a.freeToPlayOnce = b.freeToPlayOnce;
            a.cost = b.cost;
            a.costForTurn = b.costForTurn;

            b.isCostModified = costModifiedA;
            b.isCostModifiedForTurn = turnCostModifiedA;
            b.freeToPlayOnce = freeToPlayOnceA;
            b.cost = costA;
            b.costForTurn = costForTurnA;

            a.flash();
            b.flash();

            if (stellar)
            {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoversPower(AbstractDungeon.player, false, b), 1));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoversPower(AbstractDungeon.player, true, a), 1));
            }
        }
    }
}
