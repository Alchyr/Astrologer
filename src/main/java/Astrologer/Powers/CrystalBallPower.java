package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Enums.AttackEffectEnum;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CrystalBallPower extends BasePower implements NonStackablePower, HealthBarRenderPower {
    public static final String NAME = "CrystalBall";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private AbstractMonster.Intent initialIntent;

    private AbstractMonster monsterSource;

    private static Color hpBarColor = new Color(0.5f,0.5f,1.0f,1.0f);

    public CrystalBallPower(final AbstractMonster owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        this.monsterSource = owner;
        this.initialIntent = AbstractMonster.Intent.NONE;
    }

    @Override
    public void onInitialApplication() {
        initialIntent = this.monsterSource.intent;
        this.updateDescription();
    }

    @Override
    public int getHealthBarAmount() {
        if (monsterSource.intent == initialIntent)
            return this.amount;

        return 0;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (monsterSource.intent == initialIntent)
            {
                AbstractDungeon.actionManager.addToTop(new LoseHPAction(this.owner, this.owner, this.amount, AttackEffectEnum.STAR));
            }
        }
    }

    public void updateDescription() {
        this.description = descriptions[0] +
                intentString() + descriptions[1] +
                this.amount + descriptions[2];
    }

    private String intentString()
    {
        if (initialIntent == null)
            return descriptions[19];
        return descriptions[Math.min(initialIntent.ordinal() + 3, 19)];
    }
}