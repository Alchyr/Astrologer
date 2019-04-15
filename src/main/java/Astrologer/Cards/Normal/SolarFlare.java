package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Generic.TriggerPowerAction;
import Astrologer.Powers.Starlit;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.scene.LightFlareSEffect;

import static Astrologer.AstrologerMod.makeID;

public class SolarFlare extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SolarFlare",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 2;
    private final static int UPG_DEBUFF = 1;

    public SolarFlare()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightFlareSEffect(m.hb.cX, m.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Starlit(m, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new TriggerPowerAction(m, Starlit.ID));
        }
    }
}