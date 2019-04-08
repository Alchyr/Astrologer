package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Astrologer.AstrologerMod.makeID;

public class Quincunx extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Quincunx",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 3;
    private final static int UPG_DEBUFF = 2;

    public Quincunx()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);

        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        AbstractPlayer p = AbstractDungeon.player;
        if (m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            if (!m.hasPower(ArtifactPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}