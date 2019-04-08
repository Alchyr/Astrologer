package Astrologer.Actions.Astrologer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class TemperanceAction extends AbstractGameAction {
    private static final float DURATION = 0.1f;

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public TemperanceAction(int heal) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = heal;

        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (p.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], p.hand.size(), true, true);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int healAmount = 0;

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, p.hand, true));
                healAmount += this.amount;
            }
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            AbstractDungeon.actionManager.addToTop(new HealAction(p, p, healAmount));

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}