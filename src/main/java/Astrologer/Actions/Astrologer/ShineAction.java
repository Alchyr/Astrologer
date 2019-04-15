package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.PlayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class ShineAction extends AbstractGameAction {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
    private static String[] TEXT = uiStrings.TEXT;

    public ShineAction()
    {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            else if (AbstractDungeon.player.hand.size() == 1)
            {
                doublePlay(AbstractDungeon.player.hand.getBottomCard(), AbstractDungeon.player.hand);
                this.isDone = true;
                return;
            }
            else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                doublePlay(card, AbstractDungeon.handCardSelectScreen.selectedCards);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void doublePlay(AbstractCard c, CardGroup source)
    {
        AbstractDungeon.actionManager.addToTop(new PlayCardAction(c, source, true));

        AbstractCard tmp = c.makeSameInstanceOf();
        AbstractDungeon.actionManager.addToTop(new PlayCardAction(tmp, null, true));
    }
}