package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheSun extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheSun",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 16;
    private final static int UPG_DAMAGE = 6;

    private final static int HP_LOSS = 33;
    private final static int UPG_HP_LOSS = 11;

    private final static int STELLAR = 19;

    public TheSun()
    {
        super(cardInfo, false, STELLAR);
        tags.add(CustomTags.SOLAR);
        tags.add(CustomTags.STAR);

        setExhaust(true);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(HP_LOSS, UPG_HP_LOSS);

        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (PhaseCheck.solarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }
        if (stellarActive())
        {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
            {
                if (!mo.isDeadOrEscaped())
                {
                    AbstractDungeon.actionManager.addToBottom(new LoseHPAction(mo, p, this.magicNumber, AbstractGameAction.AttackEffect.FIRE));
                }
            }
        }
    }
}