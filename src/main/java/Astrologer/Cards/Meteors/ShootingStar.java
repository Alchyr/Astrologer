package Astrologer.Cards.Meteors;

import Astrologer.Abstracts.StarCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class ShootingStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShootingStar",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 1;

    public ShootingStar()
    {
        super(cardInfo, true);
        this.tags.add(CustomTags.METEOR);

        setExhaust(false, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
    }
}