package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Generic.MoveCardToBottomOfDrawAction;
import Astrologer.Actions.Generic.PutAnyNumberOnDeckAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Divination extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Divination",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 3;
    private final static int UPG_DRAW = 1;

    public Divination()
    {
        super(cardInfo, true);

        setMagic(DRAW, UPG_DRAW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new PutAnyNumberOnDeckAction(false));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new MoveCardToBottomOfDrawAction(1, false, false));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
}