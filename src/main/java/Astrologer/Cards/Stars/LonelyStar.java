package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Util.CardInfo;
import Astrologer.Util.Sounds;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class LonelyStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LonelyStar",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 2;
    private final static int UPG_DRAW = 1;

    public LonelyStar()
    {
        super(cardInfo, false);

        setMagic(DRAW, UPG_DRAW);

        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction(Sounds.Sparkle.getKey(), 0.3f));
    }
}