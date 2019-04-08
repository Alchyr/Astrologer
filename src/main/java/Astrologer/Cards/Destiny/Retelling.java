package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Astrologer.RetellingAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Retelling extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Retelling",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 0;
    private final static int UPG_DRAW = 1;

    public Retelling()
    {
        super(cardInfo, true);

        setMagic(DRAW, UPG_DRAW);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new RetellingAction(this.magicNumber));
        else
            AbstractDungeon.actionManager.addToBottom(new RetellingAction(0)); //to ensure even if magic number is modified by some effect it matches card description
    }
}