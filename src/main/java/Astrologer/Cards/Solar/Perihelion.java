package Astrologer.Cards.Solar;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Perihelion extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Perihelion",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;

    private final static int MULTIPLY = 4;
    private final static int UPG_MULTIPLY = 1;


    public Perihelion()
    {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MULTIPLY, UPG_MULTIPLY);

        tags.add(CustomTags.SOLAR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage * (PhaseCheck.solarActive() ? this.magicNumber : 1), this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
    }
}