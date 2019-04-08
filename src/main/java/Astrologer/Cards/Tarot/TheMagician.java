package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.MagicianAction;
import Astrologer.Patches.CardsPlayedThisCombatPatch;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheMagician extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheMagician",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 1;

    public TheMagician()
    {
        super(cardInfo, true, STELLAR);

        setDamage(CardsPlayedThisCombatPatch.cardsPlayedThisCombatCount);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.damage = CardsPlayedThisCombatPatch.cardsPlayedThisCombatCount;

        super.applyPowers();

        if (upgraded)
        {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[2];
        }
        else
        {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new MagicianAction(this.upgraded));
        }

        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }

        this.initializeDescription();
    }
}