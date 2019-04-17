package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.PlayCardAction;
import Astrologer.Patches.CardsPlayedThisCombatPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class MagicianAction extends AbstractGameAction {
    public MagicianAction()
    {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }

        AbstractCard toPlay = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToTop(new PlayCardAction(toPlay, AbstractDungeon.player.drawPile, false));

        this.isDone = true;
    }
}
