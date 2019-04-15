package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.PrestidigitationPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Premonition extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Premonition",
            3,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    public final static int UPG_COST = 2;

    public Premonition()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PrestidigitationPower(p, 1), 1));
    }
}