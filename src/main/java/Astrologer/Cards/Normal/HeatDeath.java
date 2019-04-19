package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Generic.DamagePerExhaustedAction;
import Astrologer.Actions.Generic.ExhaustConditionalCardsAction;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class HeatDeath extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "HeatDeath",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 7;
    private final static int UPG_DAMAGE = 2;

    public HeatDeath()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        setExhaust(true);

        loadFrames(cardInfo.cardName,25,0.04f);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ExhaustConditionalCardsAction exhaustAction = new ExhaustConditionalCardsAction(PhaseCheck :: isStar);

        AbstractDungeon.actionManager.addToBottom(exhaustAction);
        AbstractDungeon.actionManager.addToBottom(new DamagePerExhaustedAction(p, exhaustAction, this.baseDamage, this.damageTypeForTurn));
    }
}