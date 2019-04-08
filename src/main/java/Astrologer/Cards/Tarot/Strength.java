package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Astrologer.AstrologerMod.makeID;

public class Strength extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Strength",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 8;

    private final static int BUFF = 2;
    private final static int UPG_BUFF = 1;

    public Strength()
    {
        super(cardInfo, false, STELLAR);

        setMagic(BUFF, UPG_BUFF);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
        {
            int strength = 0;
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
            {
                if (!mo.isDeadOrEscaped())
                {
                    strength += this.magicNumber;
                }
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, strength), strength));
        }
    }
}