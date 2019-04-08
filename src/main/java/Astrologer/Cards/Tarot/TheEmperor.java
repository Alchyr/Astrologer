package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Astrologer.AstrologerMod.makeID;

public class TheEmperor extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheEmperor",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 4;

    private final static int DEBUFF = 3;
    private final static int UPG_DEBUFF = 2;

    public TheEmperor()
    {
        super(cardInfo, false, STELLAR);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int debuffAmount = this.magicNumber * (stellarActive() ? 3 : 1);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -debuffAmount), -debuffAmount));
        if (m != null && !m.hasPower("Artifact")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, debuffAmount), debuffAmount));
        }
    }
}