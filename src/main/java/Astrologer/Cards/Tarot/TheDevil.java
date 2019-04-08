package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Generic.IncreasePlayerMaxHPAction;
import Astrologer.Actions.Generic.KillEnemyAction;
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
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 6;
    private final static int BUFF = 6;

    private final static int STELLAR = 15;

    public TheDevil()
    {
        super(cardInfo, true, STELLAR);

        setDamage(DAMAGE);
        setMagic(BUFF);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));

        if (stellarActive()) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, 6, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            if (upgraded)
            {
                AbstractDungeon.actionManager.addToBottom(new IncreasePlayerMaxHPAction(3));
            }
        }
    }
}