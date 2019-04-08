package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.JudgementAction;
import Astrologer.Effects.CustomWeightyImpactEffect;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Judgement extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Judgement",
            3,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardTarget.ENEMY,
            AbstractCard.CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 8;
    private final static int UPG_DAMAGE = 2;

    private final static int REDUCTION = 1;

    private final static int STELLAR = 20;

    public Judgement()
    {
        super(cardInfo, false, STELLAR);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(REDUCTION);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            boolean mute = false;
            for (float r = 1.0f; r > 0.0f; r-=0.34f)
            {
                if (r < 0.0f)
                    r = 0.0f;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CustomWeightyImpactEffect(m.hb.cX, m.hb.cY, new Color(r, 0.975f, 1.0f, 0.0f), mute), 0.05f));
                mute = true;
            }

            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));


            if (stellarActive())
            {
                AbstractDungeon.actionManager.addToBottom(new JudgementAction(this, m, this.magicNumber));
            }
        }

    }
}
