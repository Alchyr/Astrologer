package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class PutAnyNumberOnDeckAction extends AbstractGameAction {
    private static final float DURATION = 0.1f;

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean top;

    public PutAnyNumberOnDeckAction(boolean top) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.top = top;

        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (p.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }
            this.amount = this.p.hand.size();

            if (top)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[1], this.amount, true, true);
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            }
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator i = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (i.hasNext()) {
                AbstractCard c = (AbstractCard)i.next();
                if (top)
                {
                    this.p.hand.moveToDeck(c, false);
                }
                else
                {
                    this.p.hand.moveToBottomOfDeck(c);
                }
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:ToDeck");
        TEXT = uiStrings.TEXT;
    }
}
