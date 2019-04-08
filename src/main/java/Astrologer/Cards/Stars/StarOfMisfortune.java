package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Powers.Starlit;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Astrologer.AstrologerMod.makeID;

public class StarOfMisfortune extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StarOfMisfortune",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 1;
    private final static int UPG_DEBUFF = 1;

    public StarOfMisfortune()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }
}