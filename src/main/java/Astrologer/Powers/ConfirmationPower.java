package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Cards.Stars.OminousStar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ConfirmationPower extends BasePower {
    public static final String NAME = "Confirmation";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ConfirmationPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if (c.cardID.equals(OminousStar.ID))
        {
            int[] dmg = createThornsDamageMatrix();

            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(this.owner, dmg, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    private int[] createThornsDamageMatrix()
    {
        int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];

        for(int i = 0; i < retVal.length; ++i) {
            DamageInfo info = new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS);

            info.applyEnemyPowersOnly(AbstractDungeon.getMonsters().monsters.get(i));

            retVal[i] = info.output;
        }

        return retVal;
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}
