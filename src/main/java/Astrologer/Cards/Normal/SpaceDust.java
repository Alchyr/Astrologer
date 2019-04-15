package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class SpaceDust extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpaceDust",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 5;
    private final static int UPG_DAMAGE = 2;

    public SpaceDust()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpaceDust();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}