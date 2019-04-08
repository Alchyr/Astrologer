package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Generic.ChangeGoldAction;
import Astrologer.Actions.Generic.PlayCardAction;
import Astrologer.AstrologerMod;
import Astrologer.Effects.CoinEffect;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class WheelofFortune extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "WheelofFortune",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int COST = 15;

    private final static int PLAY = 2;
    private final static int UPG_PLAY = 1;

    private final static int STELLAR = 10;

    public WheelofFortune()
    {
        super(cardInfo, false, STELLAR);

        setMagic(PLAY, UPG_PLAY);

        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < COST; i ++)
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CoinEffect(p.hb.cX, p.hb.cY)));

        AbstractDungeon.actionManager.addToBottom(new ChangeGoldAction(-COST));

        if (stellarActive())
        {
            for (int i = 0; i < this.magicNumber; ++i)
            {
                AbstractCard toPlay = AstrologerMod.getRandomStellarCard().makeCopy();

                if (toPlay instanceof StellarCard)
                {
                    ((StellarCard) toPlay).forceActive = true;
                    AbstractDungeon.actionManager.addToBottom(new PlayCardAction(toPlay, null, true));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
                }
            }
        }
    }
}