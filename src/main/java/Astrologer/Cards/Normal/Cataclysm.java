package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlurWaveChaoticEffect;

import static Astrologer.AstrologerMod.makeID;

public class Cataclysm extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Cataclysm",
            5,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 28;
    private final static int UPG_DAMAGE = 7;

    public Cataclysm()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        setExhaust(true);

        this.isMultiDamage = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.costForTurn == 0 || this.freeToPlayOnce)
        {
            return super.canUse(p, m);
        }
        return false;
    }

    @Override
    public void triggerWhenDrawn()
    {
        updateCost(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (float c = 0.0f; c <= 1.0f; c += 0.2f)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlurWaveChaoticEffect(p.hb.cX, p.hb.cY, new Color(c, c, c, 1.0f), c * 1000.0f * Settings.scale)));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }
}