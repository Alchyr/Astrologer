package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.CrystalBallPower;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class CrystalBall extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CrystalBall",
            2,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 11;
    private final static int UPG_DAMAGE = 3;

    public CrystalBall()
    {
        super(cardInfo, false);

        setMagic(DAMAGE, UPG_DAMAGE);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new CrystalBallPower(m, this.magicNumber), this.magicNumber));
    }
}