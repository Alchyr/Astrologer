package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Generic.IncreasePlayerMaxHPAction;
import Astrologer.Actions.Generic.KillEnemyAction;
import Astrologer.Actions.Specific.IfAliveHpCostAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Astrologer.AstrologerMod.makeID;

public class TheDevil extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheDevil",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 10;
    private final static int UPG_DAMAGE = 4;

    private final static int HP_COST = 6;

    private final static int STELLAR = 15;

    public TheDevil()
    {
        super(cardInfo, false, STELLAR);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void applyPowers() {
        int originalBaseDamage = this.baseDamage;
        if (stellarActive())
        {
            this.baseDamage += AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;

            if (baseDamage < originalBaseDamage)
                baseDamage = originalBaseDamage;
        }
        super.applyPowers();

        this.baseDamage = originalBaseDamage;

        if (this.damage > this.baseDamage)
            this.isDamageModified = true;
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalBaseDamage = this.baseDamage;
        if (stellarActive())
        {
            this.baseDamage += AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;

            if (baseDamage < originalBaseDamage)
                baseDamage = originalBaseDamage;
        }
        super.calculateCardDamage(mo);

        this.baseDamage = originalBaseDamage;

        if (this.damage > this.baseDamage)
            this.isDamageModified = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));

        AbstractDungeon.actionManager.addToBottom(new IfAliveHpCostAction(m, HP_COST));
    }
}