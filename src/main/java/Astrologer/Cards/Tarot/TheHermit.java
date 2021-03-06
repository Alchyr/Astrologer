package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Generic.BottomToTopAction;
import Astrologer.Actions.Generic.DrawAndSaveCardsAction;
import Astrologer.Actions.Generic.ReduceDrawnCardCostsAction;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheHermit extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheHermit",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 2;
    private final static int UPG_BLOCK = 1;

    private final static int STELLAR = 9;

    public TheHermit()
    {
        super(cardInfo, false, STELLAR);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(0);

        loadFrames(cardInfo.cardName, 5, 0.1f);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int played = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (played > 0)
        {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.baseMagicNumber = this.baseBlock * played;
            this.magicNumber = this.block * played;

            this.isMagicNumberModified = this.baseMagicNumber != this.magicNumber;
            initializeDescription();
        }
        else
        {
            this.magicNumber = this.baseMagicNumber = 0;
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.magicNumber > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
        }
        if (stellarActive())
        {
            if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.player.discardPile.isEmpty())
            {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
                AbstractDungeon.actionManager.addToBottom(new BottomToTopAction(p.drawPile));
                DrawAndSaveCardsAction drawAction = new DrawAndSaveCardsAction(p,1);
                AbstractDungeon.actionManager.addToBottom(drawAction);
                AbstractDungeon.actionManager.addToBottom(new ReduceDrawnCardCostsAction(drawAction, 1));
            }
            else if (!AbstractDungeon.player.drawPile.isEmpty())
            {
                AbstractDungeon.actionManager.addToBottom(new BottomToTopAction(p.drawPile));
                DrawAndSaveCardsAction drawAction = new DrawAndSaveCardsAction(p,1);
                AbstractDungeon.actionManager.addToBottom(drawAction);
                AbstractDungeon.actionManager.addToBottom(new ReduceDrawnCardCostsAction(drawAction, 1));
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}