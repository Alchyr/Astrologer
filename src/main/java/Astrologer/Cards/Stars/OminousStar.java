package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Actions.Generic.SFXVAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class OminousStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "OminousStar",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.SPECIAL
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 0;

    public OminousStar()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction("POWER_INTANGIBLE", 0.2f));
    }
}