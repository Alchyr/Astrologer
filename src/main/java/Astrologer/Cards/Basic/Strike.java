package Astrologer.Cards.Basic;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;

public class Strike extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Strike",
            1,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardTarget.ENEMY,
            AbstractCard.CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 6;
    private final static int UPG_DAMAGE = 2;

    private final static int BASE_BONUS = 0;
    private final static int SOLAR_BONUS = 2;

    public Strike()
    {
        super(cardInfo, true);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BASE_BONUS, SOLAR_BONUS);

        tags.add(CardTags.STRIKE);
        tags.add(BASIC_STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void upgrade() {
        super.upgrade();

        if (!hasTag(CustomTags.SOLAR))
            tags.add(CustomTags.SOLAR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int damageBonus = 0;
        if (upgraded && PhaseCheck.solarActive())
            damageBonus = this.magicNumber;

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage + damageBonus, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}