package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Generic.BlockPerCardAction;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class RejectReality extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RejectReality",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 13;
    private final static int UPG_DAMAGE = 2;
    private final static int BLOCK = 26;
    private final static int UPG_BLOCK = 4;
    private final static int DAZED = 3;
    private final static int UPG_DAZED = -1;

    public RejectReality()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(DAZED, UPG_DAZED);
    }

    @Override
    public AbstractCard makeCopy() {
        return new RejectReality();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (this.magicNumber < 0) //infinite journal safety.
        {
            this.baseMagicNumber = this.magicNumber = 0;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        if (this.magicNumber > 0)
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), this.magicNumber, true, true));
    }
}