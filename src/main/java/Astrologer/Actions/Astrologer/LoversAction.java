package Astrologer.Actions.Astrologer;

import Astrologer.Powers.LoversPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class LoversAction extends AbstractGameAction {
    private static final float DURATION = 0.1f;

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:Lovers");
    public static final String[] TEXT = uiStrings.TEXT;

    private boolean selecting;
    private boolean stellar;

    private ArrayList<AbstractCard> cannotSwap = new ArrayList<>();

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

        //exclude anything with cost < 0

        if (!selecting) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cost < 0 || c.costForTurn < 0) {
                    this.cannotSwap.add(c);
                }
            }
            if (AbstractDungeon.player.hand.group.size() - this.cannotSwap.size() <= 1) {
                this.isDone = true;
                return;
            }
            else if (AbstractDungeon.player.hand.group.size() - this.cannotSwap.size() == 2)
            {
                ArrayList<AbstractCard> canSwap = new ArrayList<>(AbstractDungeon.player.hand.group);
                canSwap.removeAll(cannotSwap);

                if (canSwap.size() == 2)
                {
                    a = canSwap.get(0);
                    b = canSwap.get(1);
                }
                else
                {
                    this.isDone = true;
                    return;
                }
            }
            else
            {
                AbstractDungeon.player.hand.group.removeAll(this.cannotSwap);
                if (AbstractDungeon.player.hand.size() >= 2)
                {
                    selecting = true;
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false);
                    return;
                }
                else
                {
                    this.returnCards();
                    this.isDone = true;
                    return;
                }
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
                this.returnCards();
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

    private void returnCards() {
        for (AbstractCard c : this.cannotSwap)
        {
            AbstractDungeon.player.hand.addToTop(c);
        }

        AbstractDungeon.player.hand.refreshHandLayout();
    }
}
