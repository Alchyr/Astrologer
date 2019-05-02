package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class FadingStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FadingStar",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 10;
    private final static int UPG_BLOCK = 4;

    public FadingStar()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }
}