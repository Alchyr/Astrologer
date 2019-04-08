package Astrologer.Cards.Destiny;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Conjunction extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Conjunction",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 8;
    private final static int UPG_BLOCK = 3;

    public Conjunction()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);

        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        this.applyPowers(); //Powers are not applied before the action is added otherwise
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}