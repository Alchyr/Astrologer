package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.AstrologerMod;
import Astrologer.Interfaces.OnExhaustCardCard;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Wish extends BaseCard implements OnExhaustCardCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Wish",
            4,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 10;
    private final static int UPG_BLOCK = 5;
    private final static int DRAW = 1;

    public Wish()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(DRAW);

        if (CardCrawlGame.dungeon != null) {
            this.configureCostsOnNewCard();
        }
    }

    private void configureCostsOnNewCard()
    {
        this.updateCost(-AstrologerMod.starsExhaustedThisCombat);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void onExhaustCard(AbstractCard c) {
        if (PhaseCheck.isStar(c)) {
            this.updateCost(-1);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
}