package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.cards.colorless.MindBlast;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Stardust extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Stardust",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 1;
    private final static int UPG_DRAW = 1;

    public Stardust()
    {
        super(cardInfo, true);

        setBlock(0);
        setMagic(DRAW, UPG_DRAW);
    }

    @Override
    public void applyPowers() {
        this.baseBlock = StellarPhaseValue.stellarPhase.get(AbstractDungeon.player);

        super.applyPowers();
        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];

        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));

        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }

        this.initializeDescription();
    }
}