package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Powers.FrozenStarPower;
import Astrologer.Powers.GlassStarPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class GlassStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GlassStar",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 6;
    private final static int UPG_BLOCK = 2;

    public GlassStar()
    {
        super(cardInfo, false);

        setMagic(BLOCK, UPG_BLOCK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GlassStarPower(p, this.magicNumber), this.magicNumber));
    }
}