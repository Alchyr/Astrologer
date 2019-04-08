package Astrologer.Cards.Meteors;

import Astrologer.Abstracts.StarCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Powers.Starlit;
import Astrologer.Util.CardInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;

import static Astrologer.AstrologerMod.makeID;

public class Fireball extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Fireball",
            2,
            CardType.ATTACK,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 16;
    private final static int UPG_DAMAGE = 3;

    private final static int DEBUFF = 1;
    private final static int UPG_DEBUFF = 1;

    public Fireball()
    {
        super(cardInfo, true);
        this.tags.add(CustomTags.METEOR);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY + MathUtils.random(200, 250) * Settings.scale, m.hb.cX, m.hb.cY), 0.5f));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Starlit(m, this.magicNumber), this.magicNumber));
    }
}