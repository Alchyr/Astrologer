package Astrologer.Actions.Astrologer;

import Astrologer.Enums.AttackEffectEnum;
import Astrologer.Powers.Starlit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NeutronStarDamageAction extends AbstractGameAction {
    private int damage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo.DamageType damageTypeForTurn;

    public NeutronStarDamageAction(AbstractMonster m, AbstractPlayer p, int damage, DamageInfo.DamageType damageTypeForTurn) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.damageTypeForTurn = damageTypeForTurn;

        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update()
    {
        if (!m.isDeadOrEscaped() && m.hasPower(Starlit.ID))
        {
            int amount = m.getPower(Starlit.ID).amount;
            if (amount > 0)
            {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage * amount, damageTypeForTurn), AttackEffectEnum.STAR));
            }
        }
        this.isDone = true;
    }
}
