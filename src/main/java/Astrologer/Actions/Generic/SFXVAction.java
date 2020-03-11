package Astrologer.Actions.Generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SFXVAction extends AbstractGameAction {
    private String key;
    private float volumeMod = 1.0F;

    public SFXVAction(String key, float volumeMod) {
        this.key = key;
        this.volumeMod = volumeMod;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        CardCrawlGame.sound.playV(this.key, this.volumeMod);
        this.isDone = true;
    }
}
