package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.DarkMatterPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class DarkMatter extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DarkMatter",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BUFF = 1;

    public DarkMatter()
    {
        super(cardInfo, true);

        setMagic(BUFF);
        setInnate(false, true);

        loadFrames(cardInfo.cardName, 57,0.04f);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkMatterPower(p, this.magicNumber), this.magicNumber));
    }
}