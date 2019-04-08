package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.TemperanceAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Temperance extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Temperance",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 14;

    private final static int HEAL = 2;
    private final static int UPG_HEAL = 1;

    public Temperance()
    {
        super(cardInfo, false, STELLAR);

        setMagic(HEAL, UPG_HEAL);

        setExhaust(true);

        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new TemperanceAction(this.magicNumber));
        }
    }
}