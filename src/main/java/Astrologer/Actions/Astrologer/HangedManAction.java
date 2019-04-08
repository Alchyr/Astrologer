package Astrologer.Actions.Astrologer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class HangedManAction extends AbstractGameAction {
    private static final float DURATION = 0.1f;

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:HangedMan");
    public static final String[] TEXT = uiStrings.TEXT;

    private boolean selecting;

    public HangedManAction()
    {
        this.duration = DURATION;
        this.actionType = ActionType.CARD_MANIPULATION;

        selecting = false;
    }


    @Override
    public void update() {
        AbstractCard a = null;

        if (!selecting)
        {
            if (AbstractDungeon.player.hand.size() > 1) //Open window to choose two cards
            {
                selecting = true;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
            }
            else if (AbstractDungeon.player.hand.size() == 1)
            {
                //Auto-select
                a = AbstractDungeon.player.hand.group.get(0);
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
                if (AbstractDungeon.handCardSelectScreen.selectedCards.size() == 1)
                {
                    a = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
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


        if (a != null)
        {
            a.flash();

            AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(a.makeStatEquivalentCopy(), AbstractDungeon.player.drawPile.size(), true, true));
            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
            }
        }
    }
}
