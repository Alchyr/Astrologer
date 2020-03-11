package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.HighPriestessAction;
import Astrologer.Actions.Generic.ReduceCostByAmountAction;
import Astrologer.Actions.Generic.ReduceRandomCostAction;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;

import java.util.ArrayList;

import static Astrologer.AstrologerMod.makeID;

public class TheHighPriestess extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheHighPriestess",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 2;

    public TheHighPriestess()
    {
        super(cardInfo, true, STELLAR);

        loadFrames(cardInfo.cardName, 30, 0.13f);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.selfRetain = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new HighPriestessAction());
        }
    }
}