package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.PlayCardAction;
import Astrologer.AstrologerMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class HighPriestessAction extends AbstractGameAction
{
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(AstrologerMod.makeID("HighPriestess"));
    private static String[] TEXT = uiStrings.TEXT;

    private ArrayList<AbstractCard> cannotDouble = new ArrayList<>();

    public HighPriestessAction()
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

            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c.baseDamage <= 0)
                {
                    cannotDouble.add(c);
                }
            }

            if (cannotDouble.size() == AbstractDungeon.player.hand.size())
            {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hand.size() - this.cannotDouble.size() == 1) {
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                {
                    if (!cannotDouble.contains(c))
                    {
                        doubleDamage(c);
                        this.isDone = true;
                        return;
                    }
                }
            }

            AbstractDungeon.player.hand.group.removeAll(this.cannotDouble);

            if (AbstractDungeon.player.hand.size() == 1)
            {
                doubleDamage(AbstractDungeon.player.hand.getBottomCard());
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
                AbstractDungeon.player.hand.addToTop(card);
                doubleDamage(card);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotDouble)
        {
            AbstractDungeon.player.hand.addToTop(c);
        }

        AbstractDungeon.player.hand.refreshHandLayout();
    }

    private void doubleDamage(AbstractCard c)
    {
        for (AbstractCard card : GetAllInBattleInstances.get(c.uuid))
        {
            card.baseDamage *= 2;
            card.applyPowers();
            card.superFlash();
        }
    }
}