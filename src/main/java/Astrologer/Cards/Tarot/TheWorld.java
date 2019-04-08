package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Generic.EndTurnNowAction;
import Astrologer.Actions.Generic.SetHPAction;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheWorld extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheWorld",
            2,
            CardType.SKILL,
            CardTarget.ALL,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 1;

    private final static int STELLAR = 21;

    public TheWorld()
    {
        super(cardInfo, false, STELLAR);

        this.isEthereal = true;
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.exhaust = stellarActive();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.0f, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
            if (MathUtils.randomBoolean())
            {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("CEILING_DUST_2"));
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("CEILING_DUST_3"));
            }
            int targetValue = MathUtils.floor(p.maxHealth / 2.0f);

            AbstractDungeon.actionManager.addToBottom(new SetHPAction(p, targetValue));
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
            {
                if (!mo.isDeadOrEscaped())
                    AbstractDungeon.actionManager.addToBottom(new SetHPAction(mo, targetValue));
            }

            AbstractDungeon.actionManager.addToBottom(new EndTurnNowAction());
        }
    }
}