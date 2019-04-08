package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Enums.AttackEffectEnum;
import Astrologer.Enums.CustomTags;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheStar extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheStar",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 17;

    public TheStar()
    {
        super(cardInfo, true, STELLAR);
        tags.add(CustomTags.STAR);

        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null) {
            setDamage(StellarPhaseValue.stellarPhase.get(AbstractDungeon.player));
        }
        else
        {
            setDamage(0);
        }

        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null) {
            setDamage(StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) * 2);
        }
        else
        {
            setDamage(0);
        }
    }

    public void applyPowers()
    {
        this.baseDamage = StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) * (this.upgraded ? 2 : 1);
        if (stellarActive())
            baseDamage *= 2;

        super.applyPowers();

        this.rawDescription = upgraded ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = upgraded ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AttackEffectEnum.STAR, true));
    }
}
