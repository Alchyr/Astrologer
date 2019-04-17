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
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 12;

    private final static int UPG_COST = 0;

    public TheHangedMan()
    {
        super(cardInfo,false, STELLAR);

        loadFrames(cardInfo.cardName, 0.25f);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
            AbstractDungeon.actionManager.addToBottom(new HangedManAction());
    }
}