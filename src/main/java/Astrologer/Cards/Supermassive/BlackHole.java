package Astrologer.Cards.Supermassive;

import Astrologer.Abstracts.SupermassiveCard;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;

import static Astrologer.AstrologerMod.makeID;

public class BlackHole extends SupermassiveCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlackHole",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 10;

    private final static int SUPERMASSIVE = 2;

    private final static int GROWTH = 5;
    private final static int UPG_GROWTH = 3;

    public BlackHole()
    {
        super(cardInfo, false, SUPERMASSIVE);

        setDamage(DAMAGE);
        setMagic(GROWTH, UPG_GROWTH);
    }

    @Override
    public void doSupermassiveEffect(AbstractCard self) {
        this.baseDamage += this.magicNumber;

        applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.damage > 0 && m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), 0.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));
    }
}