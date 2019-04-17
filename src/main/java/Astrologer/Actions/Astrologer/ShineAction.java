package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.PlayCardAction;
import Astrologer.AstrologerMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class ShineAction extends AbstractGameAction {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(AstrologerMod.makeID("PlayAction"));
    private static String[] TEXT = uiStrings.TEXT;

    private ArrayList<AbstractCard> cannotPlay = new ArrayList<>();

    public ShineAction()
    {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            int actualCost;
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                actualCost = c.costForTurn;
                c.costForTurn = -1;
                if (!c.canUse(AbstractDungeon.player, AbstractDungeon.getRandomMonster()))
                {
                    cannotPlay.add(c);
                }
                c.costForTurn = actualCost;
            }

            if (cannotPlay.size() == AbstractDungeon.player.hand.size())
            {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hand.size() - this.cannotPlay.size() == 1) {
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                {
                    if (!cannotPlay.contains(c))
                    {
                        doublePlay(c, AbstractDungeon.player.hand);
                        this.isDone = true;
                        return;
                    }
                }
            }

            AbstractDungeon.player.hand.group.removeAll(this.cannotPlay);

            if (AbstractDungeon.player.hand.size() == 1)
            {
                doublePlay(AbstractDungeon.player.hand.getBottomCard(), AbstractDungeon.player.hand);
                this.returnCards();
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                AbstractDungeon.player.limbo.addToRandomSpot(card);
                doublePlay(card, AbstractDungeon.player.limbo);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotPlay)
        {
            AbstractDungeon.player.hand.addToTop(c);
        }

        AbstractDungeon.player.hand.refreshHandLayout();
    }

    private void doublePlay(AbstractCard c, CardGroup source)
    {
        AbstractCard tmp = c.makeSameInstanceOf();
        AbstractDungeon.actionManager.addToTop(new PlayCardAction(tmp, null, true));
        AbstractDungeon.actionManager.addToTop(new PlayCardAction(c, source, true));
    }
}