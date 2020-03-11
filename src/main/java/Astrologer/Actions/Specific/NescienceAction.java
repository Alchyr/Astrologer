package Astrologer.Actions.Specific;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class NescienceAction extends AbstractGameAction {
    private AbstractPlayer p;

    public NescienceAction() {
        this.actionType = ActionType.CARD_MANIPULATION;// 14
        this.p = AbstractDungeon.player;// 15
        this.duration = Settings.ACTION_DUR_FAST;// 16
    }// 17

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 20
            Iterator var1;
            AbstractCard c;
            for(var1 = AbstractDungeon.player.hand.group.iterator(); var1.hasNext(); c.flash(new Color(0.4F, 0.05F, 0.05F, 1.0F))) {// 21 31
                c = (AbstractCard)var1.next();
                if (c.costForTurn > 1) {// 23
                    c.costForTurn = 1;// 24
                    c.isCostModifiedForTurn = true;// 25
                }

                if (c.cost > 1) {// 27
                    c.cost = 1;// 28
                    c.isCostModified = true;// 29
                }
            }

            var1 = AbstractDungeon.player.discardPile.group.iterator();// 33

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.costForTurn > 1) {// 35
                    c.costForTurn = 1;// 36
                    c.isCostModifiedForTurn = true;// 37
                }

                if (c.cost > 1) {// 39
                    c.cost = 1;// 40
                    c.isCostModified = true;// 41
                }
            }

            var1 = AbstractDungeon.player.drawPile.group.iterator();// 44

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.costForTurn > 1) {// 46
                    c.costForTurn = 1;// 47
                    c.isCostModifiedForTurn = true;// 48
                }

                if (c.cost > 1) {// 50
                    c.cost = 1;// 51
                    c.isCostModified = true;// 52
                }
            }

            var1 = AbstractDungeon.player.exhaustPile.group.iterator();// 55

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.costForTurn > 1) {// 57
                    c.costForTurn = 1;// 58
                    c.isCostModifiedForTurn = true;// 59
                }

                if (c.cost > 1) {// 61
                    c.cost = 1;// 62
                    c.isCostModified = true;// 63
                }
            }
        }

        this.tickDuration();// 68
    }// 69
}