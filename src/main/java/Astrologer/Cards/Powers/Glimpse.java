package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.GlimpsePower;
import Astrologer.Powers.PrestidigitationPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Glimpse extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Glimpse",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BUFF = 2;
    private final static int UPG_BUFF = 1;

    public Glimpse()
    {
        super(cardInfo, false);

        setMagic(BUFF, UPG_BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GlimpsePower(p, this.magicNumber), this.magicNumber));
    }
}