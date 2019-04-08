package Astrologer.Cards.Stars;

import Astrologer.Abstracts.SupermassiveCard;
import Astrologer.AstrologerMod;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class GiantStar extends SupermassiveCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GiantStar",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int SUPERMASSIVE = 1;

    private final static int GROWTH = 1;

    private final static int CREATE = 2;
    private final static int UPG_CREATE = 1;

    public GiantStar()
    {
        super(cardInfo, false, SUPERMASSIVE);

        setMagic(CREATE, UPG_CREATE);

        this.tags.add(CustomTags.STAR);

        this.exhaust = true;
    }

    @Override
    public void doSupermassiveEffect(AbstractCard self) {
        this.baseMagicNumber += GROWTH;
        this.magicNumber = this.baseMagicNumber;

        applyPowers();
    }

    @Override
    public void triggerOnExhaust() {
        if (this.magicNumber > 0)
        {
            for (int i = 0; i < this.magicNumber; ++i)
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AstrologerMod.getRandomMeteor().makeCopy(), 1));
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}