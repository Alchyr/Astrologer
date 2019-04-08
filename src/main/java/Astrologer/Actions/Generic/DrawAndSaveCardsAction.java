package Astrologer.Actions.Generic;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import java.util.ArrayList;

public class DrawAndSaveCardsAction extends AbstractGameAction {
    private boolean shuffleCheck;
    private ArrayList<AbstractCard> drawnCards;

    private DrawAndSaveCardsAction recursiveSource = null;

    public ArrayList<AbstractCard> getDrawnCards()
    {
        if (recursiveSource != null)
        {
            drawnCards.addAll(recursiveSource.drawnCards);
        }
        return drawnCards;
    }

    public DrawAndSaveCardsAction(AbstractCreature source, int amount, boolean endTurnDraw) {
        this.shuffleCheck = false;
        if (endTurnDraw) {
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
        } else if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
            AbstractDungeon.player.getPower(NoDrawPower.POWER_ID).flash();
            this.setValues(AbstractDungeon.player, source, amount);
            this.isDone = true;
            this.duration = 0.0F;
            this.actionType = ActionType.WAIT;
            return;
        }

        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        drawnCards = new ArrayList<>();
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }

    }

    public DrawAndSaveCardsAction(AbstractCreature source, int amount) {
        this(source, amount, false);
    }

    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.isDone = true;
                } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                } else {
                    if (!this.shuffleCheck) {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                            tmp = BaseMod.MAX_HAND_SIZE - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > deckSize) {
                            tmp = this.amount - deckSize;
                            recursiveSource = new DrawAndSaveCardsAction(AbstractDungeon.player, tmp, false);
                            AbstractDungeon.actionManager.addToTop(recursiveSource);
                            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0) {
                                this.amount = deckSize;
                            }
                            else
                            {
                                this.amount = 0;
                                this.isDone = true;
                            }
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty()) {
                            drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        } else {
                            this.isDone = true;
                        }

                        if (this.amount == 0) {
                            this.isDone = true;
                        }
                    }

                }
            }
        }
    }
}