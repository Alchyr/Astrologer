package Astrologer.Cards.Solar;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Astrologer.StellarCapIncreaseAction;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Sunrise extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Sunrise",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;


    public Sunrise() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);

        tags.add(CustomTags.SOLAR);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        this.magicNumber = this.baseMagicNumber;
        if (PhaseCheck.solarActive())
        {
            this.magicNumber *= 2;
            this.isMagicNumberModified = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new StellarCapIncreaseAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sunrise();
    }
}