package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Enums.AttackEffectEnum;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class StarOfRegret extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StarOfRegret",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 8;
    private final static int UPG_DAMAGE = 2;

    public StarOfRegret()
    {
        super(cardInfo,false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void triggerOnExhaust() {
        int[] dmg = DamageInfo.createDamageMatrix(this.damage * 2);
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, dmg, damageTypeForTurn, AttackEffectEnum.STAR));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffectEnum.STAR));
    }
}