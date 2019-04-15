package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Stack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Observe extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Observe",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BONUS = 0;
    private final static int UPG_BONUS = 6;

    public Observe()
    {
        super(cardInfo, true);

        setBlock(0);
        setMagic(BONUS, UPG_BONUS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Observe();
    }

    public void applyPowers() {
        this.baseBlock = AbstractDungeon.player.drawPile.size();
        if (this.upgraded) {
            this.baseBlock += this.magicNumber;
        }

        super.applyPowers();

        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }

        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }
}