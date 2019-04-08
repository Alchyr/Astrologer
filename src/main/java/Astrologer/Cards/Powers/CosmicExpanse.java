package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.CosmicExpansePower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class CosmicExpanse extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CosmicExpanse",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public final static int UPG_COST = 0;

    public final static int BUFF = 7;

    public CosmicExpanse()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CosmicExpansePower(p, this.magicNumber), this.magicNumber));
    }
}