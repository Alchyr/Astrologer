package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Powers.Starlit;
import Astrologer.Util.CardInfo;
import Astrologer.Util.Sounds;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class ShiningStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShiningStar",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 3;
    private final static int UPG_DEBUFF = 1;

    public ShiningStar()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);

        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m != null)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new Starlit(m, this.magicNumber), this.magicNumber));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new SFXAction(Sounds.Sparkle.key, 0.3f));
    }
}