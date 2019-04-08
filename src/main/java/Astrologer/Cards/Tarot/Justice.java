package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.JusticeAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Justice extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Justice",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 11;
    private final static int UPG_BLOCK = 2;

    private final static int STELLAR = 11;

    public Justice()
    {
        super(cardInfo,false, STELLAR);

        setBlock(BLOCK, UPG_BLOCK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) //Block first, then damage
        {
            if (!mo.isDeadOrEscaped())
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, p, this.block));
        }
        if (stellarActive())
        {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
            {
                if (!mo.isDeadOrEscaped())
                    AbstractDungeon.actionManager.addToBottom(new JusticeAction(mo, p));
            }
        }
    }
}