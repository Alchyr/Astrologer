package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class MoveCardToBottomOfDrawAction extends AbstractGameAction {
    private static final float DURATION = 0.1f;

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:ToDeck");
    public static final String[] TEXT = uiStrings.TEXT;

    private boolean selecting;

    private boolean anyNumber;
    private boolean canPickZero;

    public MoveCardToBottomOfDrawAction(int amount, boolean anyNumber, boolean canPickZero)
    {
        this.duration = DURATION;
        this.actionType = ActionType.CARD_MANIPULATION;

        this.amount = amount;

        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;

        selecting = false;
    }

    @Override
    public void update() {
        CardGroup selectedCards = null;

        if (!selecting)
        {
            if (AbstractDungeon.player.hand.isEmpty())
            {
                this.isDone = true;
            }
            else if (anyNumber || canPickZero || AbstractDungeon.player.hand.size() > amount) //Open window to choose two cards
            {
                selecting = true;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, anyNumber, canPickZero);
            }
            else //cards in hand <= cards to move, and they have to move as many as possible
            {
                //Auto-select
                selectedCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                    selectedCards.addToRandomSpot(c);
                this.isDone = true;
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

                selectedCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                    selectedCards.addToRandomSpot(c);

                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            this.isDone = true;
        }


        if (selectedCards != null && selectedCards.size() > 0)
        {
            while (selectedCards.size() != 0)
            {
                selectedCards.moveToBottomOfDeck(selectedCards.getBottomCard());
            }
        }
    }
}
