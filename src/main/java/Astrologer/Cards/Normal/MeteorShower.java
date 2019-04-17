package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class MeteorShower extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MeteorShower",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 6;
    private final static int UPG_DAMAGE = 3;

    public MeteorShower()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        isMultiDamage = true;

        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Add a meteor type effect
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }
}