package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.DrawAndSaveCardsAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TheMoonAction extends AbstractGameAction {
    private AbstractPlayer p;
    private DrawAndSaveCardsAction drawnSource;

    public TheMoonAction(AbstractPlayer source, int block)
    {
        p = source;
        this.amount = block;

        this.actionType = ActionType.BLOCK;
    }

    @Override
    public void update() {
        int drawnCards = 0;

        if (drawnSource != null)
        {
            drawnCards = drawnSource.getDrawnCards().size();

            if (drawnCards == 0)
                drawnCards = -1; //failed to draw
        }

        if (p.hand.size() < BaseMod.MAX_HAND_SIZE && drawnCards >= 0) //hand not full, didn't fail to draw
        {
            TheMoonAction recursiveDraw = new TheMoonAction(p, this.amount);
            recursiveDraw.drawnSource = new DrawAndSaveCardsAction(p, 1);

            AbstractDungeon.actionManager.addToTop(recursiveDraw);
            AbstractDungeon.actionManager.addToTop(recursiveDraw.drawnSource);
        }

        if (this.amount > 0 && drawnCards > 0)
        {
            for (int i = 0; i < drawnCards; ++i)
            {
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.amount));
            }
        }

        this.isDone = true;
    }
}
