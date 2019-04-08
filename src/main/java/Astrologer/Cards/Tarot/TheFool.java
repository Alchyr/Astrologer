package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheFool extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheFool",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 7;
    private final static int UPG_DAMAGE = 2;

    private final static int STELLAR = 0;

    public TheFool()
    {
        super(cardInfo, false, STELLAR);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public boolean stellarActive() {
        return super.stellarActiveFool() || MathUtils.randomBoolean();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
}