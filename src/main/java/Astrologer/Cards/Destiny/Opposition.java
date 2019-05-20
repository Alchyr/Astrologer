package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Enums.AttackEffectEnum;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Astrologer.AstrologerMod.makeID;

public class Opposition extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Opposition",
            1,
            CardType.ATTACK,
            CardTarget.NONE,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 11;
    private final static int UPG_DAMAGE = 3;

    public Opposition()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m != null)
        {
            this.calculateCardDamage(m);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL), AttackEffectEnum.STAR));
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}