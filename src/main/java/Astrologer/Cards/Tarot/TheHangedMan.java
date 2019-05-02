package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.HangedManAction;
import Astrologer.Powers.HangedPower;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheHangedMan extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheHangedMan",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 12;

    private final static int DEBUFF = 3;
    private final static int UPG_DEBUFF = 2;

    public TheHangedMan()
    {
        super(cardInfo,false, STELLAR);

        loadFrames(cardInfo.cardName,16,0.20f);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (stellarActive())
            this.target = CardTarget.ALL_ENEMY;
        else
            this.target = CardTarget.ENEMY;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
        {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
            {
                if (!mo.isDeadOrEscaped())
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new HangedPower(mo, p, this.magicNumber), this.magicNumber));
                }
            }
        }
        else if (m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new HangedPower(m, p, this.magicNumber), this.magicNumber));
        }
    }
}