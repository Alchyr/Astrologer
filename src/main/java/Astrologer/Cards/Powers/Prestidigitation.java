package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.PrestidigitationPower;
import Astrologer.Powers.StarfieldPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Prestidigitation extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Prestidigitation",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public final static int UPG_COST = 0;

    private final static int BUFF = 1;

    public Prestidigitation()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PrestidigitationPower(p, this.magicNumber), this.magicNumber));
    }
}