package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.NovaPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Nova extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Nova",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BUFF = 1;
    private final static int UPG_BUFF = 1;

    public Nova()
    {
        super(cardInfo, true);

        setMagic(BUFF, UPG_BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NovaPower(p, this.magicNumber), this.magicNumber));
    }
}