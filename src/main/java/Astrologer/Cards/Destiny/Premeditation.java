package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Generic.BottomToTopAction;
import Astrologer.Actions.Generic.DrawAndSaveCardsAction;
import Astrologer.Actions.Generic.PlayCardAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Premeditation extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Premeditation",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 0;

    public Premeditation()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BottomToTopAction(p.drawPile));
        DrawAndSaveCardsAction cardDraw = new DrawAndSaveCardsAction(p,1);
        AbstractDungeon.actionManager.addToBottom(cardDraw);
        AbstractDungeon.actionManager.addToBottom(new PlayCardAction(cardDraw,true));
    }
}