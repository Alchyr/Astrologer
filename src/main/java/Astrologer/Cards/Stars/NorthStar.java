package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Powers.Starlit;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class NorthStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "NorthStar",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 2;
    private final static int UPG_DEBUFF = 1;

    public NorthStar()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Add a light shine
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Starlit(m, this.magicNumber), this.magicNumber));
    }
}