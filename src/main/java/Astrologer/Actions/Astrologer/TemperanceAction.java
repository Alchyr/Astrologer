package Astrologer.Actions.Astrologer;

import Astrologer.Cards.Tarot.Temperance;
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
    private AbstractPlayer p;

    public TemperanceAction(int heal) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = heal;

        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == DURATION) {
            int amt = Temperance.stellarCount();
            for (int i = 0; i < amt; ++i)
            {
                AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.amount));
            }
        }

        this.tickDuration();
    }
}