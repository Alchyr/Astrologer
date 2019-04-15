package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class ForceUpgradeCardAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    private ArrayList<AbstractCard> toReUpgrade = new ArrayList<>();
    private boolean upgraded = false;

    public ForceUpgradeCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (!c.canUpgrade() && !c.upgraded) {
                    this.cannotUpgrade.add(c);
                }
            }

            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {
                for (AbstractCard card : this.p.hand.group)
                {
                    if (!this.cannotUpgrade.contains(card))
                    {
                        forceUpgrade(card);

                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                for (AbstractCard card : this.p.hand.group)
                {
                    if (card.upgraded)
                    {
                        toReUpgrade.add(card);
                        card.upgraded = false;
                    }
                }
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, true);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                forceUpgrade(this.p.hand.getTopCard());
                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();

                forceUpgrade(c);

                this.p.hand.addToTop(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        Iterator var1 = this.cannotUpgrade.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        for (AbstractCard c : toReUpgrade)
        {
            c.upgraded = true;
        }

        this.p.hand.refreshHandLayout();
    }

    private void forceUpgrade(AbstractCard c)
    {
        boolean wasUpgraded = c.upgraded;

        c.upgraded = false;

        c.upgrade();
        c.superFlash();

        if (wasUpgraded)
            c.upgraded = true;

        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.uuid.equals(c.uuid)) {
                wasUpgraded = card.upgraded;

                if (wasUpgraded)
                    card.upgraded = false;

                card.upgrade();
                card.superFlash();

                if (wasUpgraded)
                    card.upgraded = true;
            }
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
        TEXT = uiStrings.TEXT;
    }
}