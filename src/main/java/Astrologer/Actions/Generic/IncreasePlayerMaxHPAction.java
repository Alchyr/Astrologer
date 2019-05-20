package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

public class IncreasePlayerMaxHPAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:MaxHP");
    public static final String[] TEXT = uiStrings.TEXT;

     public IncreasePlayerMaxHPAction(int amount)
     {
         this.amount = amount;
         this.actionType = ActionType.HEAL;
     }

    @Override
    public void update() {
         AbstractDungeon.player.increaseMaxHp(amount, true);
         this.isDone = true;
    }
}
