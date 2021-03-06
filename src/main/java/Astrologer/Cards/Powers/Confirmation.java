package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Cards.Stars.OminousStar;
import Astrologer.Powers.ConfirmationPower;
import Astrologer.Powers.GlimpsePower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Confirmation extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Confirmation",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 8;
    private final static int UPG_DAMAGE = 5;

    public Confirmation()
    {
        super(cardInfo, false);

        cardsToPreview = new OminousStar();
        setMagic(DAMAGE, UPG_DAMAGE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new OminousStar(), 1, true, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConfirmationPower(p, this.magicNumber), this.magicNumber));
    }
}