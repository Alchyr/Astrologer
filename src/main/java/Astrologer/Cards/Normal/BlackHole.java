package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Abstracts.SupermassiveCard;
import Astrologer.Actions.Generic.PurgeExhaustPileAction;
import Astrologer.Util.CardInfo;
import Astrologer.Util.TextureLoader;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
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

public class BlackHole extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlackHole",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;
    private final static int UPG_DAMAGE = 2;

    public BlackHole()
    {
        super(cardInfo,false);

        setDamage(DAMAGE, UPG_DAMAGE);

        if (MathUtils.randomBoolean(0.01f))
        {
            img = TextureLoader.getAndLoadCardTextureString("BlackHoleChan", cardInfo.cardType);
            this.textureImg = img;
            loadCardImage(textureImg);
        }

        setExhaust(true);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = Math.max(this.baseDamage, this.baseDamage * AbstractDungeon.player.exhaustPile.size());
        this.magicNumber = this.damage * AbstractDungeon.player.exhaustPile.size();
        this.isMagicNumberModified = this.isDamageModified;

        //if (!this.upgraded) {
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        //} else {
        //this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        //}
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.baseMagicNumber = this.baseDamage * AbstractDungeon.player.exhaustPile.size();
        this.magicNumber = this.damage * AbstractDungeon.player.exhaustPile.size();

        //if (!this.upgraded) {
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        //} else {
        //this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        //}
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.magicNumber > 0 && m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), 0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.magicNumber, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));
        }
        AbstractDungeon.actionManager.addToBottom(new PurgeExhaustPileAction());
    }
}