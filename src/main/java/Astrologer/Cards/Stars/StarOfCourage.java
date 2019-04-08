package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Actions.Astrologer.StarOfCourageAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class StarOfCourage extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StarOfCourage",
            1,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardTarget.NONE,
            AbstractCard.CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 0;

    private final static int BUFF = 1;

    public StarOfCourage()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Add a light shine
        AbstractDungeon.actionManager.addToBottom(new StarOfCourageAction(this.magicNumber));
    }
}