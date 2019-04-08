package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheHighPriestess extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheHighPriestess",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int REDUCTION = 1;

    private final static int UPG_COST = 0;

    private final static int STELLAR = 2;

    public TheHighPriestess()
    {
        super(cardInfo, false, STELLAR);

        setCostUpgrade(UPG_COST);
        setMagic(REDUCTION);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //add sfx
        if (!p.drawPile.group.isEmpty())
        {
            //add sparkle effect on deck
            p.drawPile.getTopCard().modifyCostForCombat(-this.magicNumber);
        }
        if (stellarActive())
        {
            //card.use is called after card is added to actionmanager.cardsPlayedThisCombat, so get size-2 instead of size-1
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2)
            {
                AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).modifyCostForCombat(-this.magicNumber);
            }
        }
    }
}