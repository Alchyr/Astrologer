package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.MagicianAction;
import Astrologer.Actions.Generic.KillEnemyAction;
import Astrologer.Patches.CardsPlayedThisCombatPatch;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Death extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Death",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 13;

    private final static int STELLAR = 13;

    private final static int UPG_COST = 2;

    public Death()
    {
        super(cardInfo, false, STELLAR);

        setDamage(DAMAGE);
        setCostUpgrade(UPG_COST);

        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));

        if (stellarActive()) {
            if (m != null && m.type != AbstractMonster.EnemyType.BOSS) {
                AbstractDungeon.actionManager.addToBottom(new KillEnemyAction(m));
            }
        }
    }
}