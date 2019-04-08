package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Novile extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Novile",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int ENERGY = 1;
    private final static int UPG_ENERGY = 1;

    public Novile()
    {
        super(cardInfo, false);

        setMagic(ENERGY, UPG_ENERGY);

        this.initializeDescription();

        this.isEthereal = true;
    }

    @Override
    public void initializeDescription() {
        if (cardStrings != null)
        {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            for (int i = 0; i < this.baseMagicNumber; i++)
            {
                this.rawDescription = this.rawDescription.concat(cardStrings.EXTENDED_DESCRIPTION[1]);
            }
            this.rawDescription = this.rawDescription.concat(cardStrings.EXTENDED_DESCRIPTION[2]);
        }
        super.initializeDescription();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.initializeDescription();
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}