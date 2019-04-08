package Astrologer.Cards.Lunar;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Astrologer.AstrologerMod.makeID;

public class Moonlight extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Moonlight",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 7;
    private final static int UPG_BLOCK = 2;

    private final static int WEAK = 1;
    private final static int UPG_WEAK = 1;

    public Moonlight()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(WEAK, UPG_WEAK);

        tags.add(CustomTags.LUNAR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (PhaseCheck.lunarActive())
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
    }
}