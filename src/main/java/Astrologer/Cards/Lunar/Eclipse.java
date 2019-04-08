package Astrologer.Cards.Lunar;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;

import static Astrologer.AstrologerMod.makeID;

public class Eclipse extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Eclipse",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;
    private final static int UPG_DAMAGE = 1;

    private final static int HITS = 2;
    private final static int LUNAR_BONUS = 1;

    public Eclipse()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(LUNAR_BONUS);

        tags.add(CustomTags.LUNAR);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (this.magicNumber > 1)
        {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int hits = HITS;

        if (PhaseCheck.lunarActive())
            hits += this.magicNumber;

        for (int i = 0; i < hits; i++)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}