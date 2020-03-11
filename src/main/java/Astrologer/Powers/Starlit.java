package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Actions.Generic.PowerFlashAction;
import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.Sounds;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Astrologer.AstrologerMod.makeID;
import static Astrologer.Util.Sounds.Sparkle;

public class Starlit extends BasePower implements HealthBarRenderPower {
    public static final String NAME = "Starlit";
    public static final String ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    private static final Color hpColor = Color.valueOf("d3fffcff");

    public Starlit(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play(Sparkle.key, 0.05F);
    }

    public void atStartOfTurn() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        } else if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new PowerFlashAction(this, false));
            AbstractDungeon.actionManager.addToBottom(new SFXAction(Sparkle.key, 0.05F));
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, StellarPhaseValue.stellarPhase.get(AbstractDungeon.player)));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, ID, 1));
        }
    }

    @Override
    public void onSpecificTrigger() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        } else if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, ID, 1));
            AbstractDungeon.actionManager.addToTop(new LoseHPAction(this.owner, this.owner, StellarPhaseValue.stellarPhase.get(AbstractDungeon.player)));
            AbstractDungeon.actionManager.addToTop(new SFXAction(Sparkle.key, 0.05F));
            AbstractDungeon.actionManager.addToTop(new PowerFlashAction(this, false));
        }
    }

    @Override
    public int getHealthBarAmount() {
        return StellarPhaseValue.stellarPhase.get(AbstractDungeon.player);
    }
    @Override
    public Color getColor() {
        return hpColor;
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = descriptions[1];
        } else {
            this.description = descriptions[0];
        }
    }
}