package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class StarOfChange extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StarOfChange",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public StarOfChange()
    {
        super(cardInfo, true);

        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(1, false, false, false));
        AbstractCard toAdd = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        if (upgraded)
            toAdd.upgrade();

        toAdd.setCostForTurn(0);

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(toAdd, 1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}