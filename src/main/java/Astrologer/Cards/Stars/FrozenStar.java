package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Powers.FrozenStarPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class FrozenStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FrozenStar",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 9;
    private final static int UPG_BLOCK = 3;

    public FrozenStar()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrozenStarPower(p, this.block), this.block));
    }
}