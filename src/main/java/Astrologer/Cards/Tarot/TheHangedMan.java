package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.HangedManAction;
import Astrologer.Powers.HangedPower;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheHangedMan extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheHangedMan",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 12;

    private final static int DEBUFF = 4;
    private final static int UPG_DEBUFF = 3;

    public TheHangedMan()
    {
        super(cardInfo,false, STELLAR);

        loadFrames(cardInfo.cardName,16,0.20f);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new HangedPower(m, p, this.magicNumber), this.magicNumber));
        }
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 3));
        }
    }
}