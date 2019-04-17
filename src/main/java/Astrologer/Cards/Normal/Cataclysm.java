package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlurWaveChaoticEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static Astrologer.AstrologerMod.makeID;

public class Cataclysm extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Cataclysm",
            5,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 26;
    private final static int UPG_DAMAGE = 6;

    public Cataclysm()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        setExhaust(true);

        this.isMultiDamage = true;
    }

    @Override
    public void triggerWhenDrawn()
    {
        updateCost(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 5; ++i)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
        }
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_PIERCING_WAIL"));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }
}