package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.TemperanceAction;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static Astrologer.AstrologerMod.makeID;

public class Temperance extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Temperance",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 14;

    private final static int HEAL = 1;

    private static final int UPG_COST = 0;

    public Temperance()
    {
        super(cardInfo, false, STELLAR);

        setMagic(0);
        setCostUpgrade(UPG_COST);

        setExhaust(true);

        tags.add(CardTags.HEALING);

        loadFrames(cardInfo.cardName, 37, 0.12f);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();

        this.magicNumber = stellarCount();
        isMagicNumberModified = magicNumber > 0;

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.exhaust = stellarActive();

        this.magicNumber = stellarCount();
        isMagicNumberModified = magicNumber > 0;

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new TemperanceAction(HEAL));
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public static int stellarCount() {
        int count = 0;

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.hasTag(CustomTags.STELLAR))
                ++count;

        return count;
    }
}