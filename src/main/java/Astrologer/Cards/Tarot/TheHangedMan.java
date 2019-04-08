package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.HangedManAction;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheHangedMan extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheHangedMan",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 12;

    public TheHangedMan()
    {
        super(cardInfo,true, STELLAR);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!AlwaysRetainField.alwaysRetain.get(this))
        {
            AlwaysRetainField.alwaysRetain.set(this, true);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
            AbstractDungeon.actionManager.addToBottom(new HangedManAction());
    }
}