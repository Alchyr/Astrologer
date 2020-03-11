package Astrologer.Cards.Crossover.infinitespire;

import Astrologer.AstrologerMod;
import Astrologer.Actions.Generic.CalculateLaterDamageAllAction;
import Astrologer.Patches.AnimatedCardsPatch;
import Astrologer.Util.CardInfo;
import Astrologer.Util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;
import infinitespire.abstracts.BlackCard;
import infinitespire.effects.BlackCardEffect;
import java.util.Iterator;

public class Pulse extends BlackCard {
    private static final CardInfo cardInfo;
    public static final String ID;
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int DAMAGE = 30;
    private static final int UPG_DAMAGE = 8;
    private static final int DEBUFF = 2;
    private static final int UPG_DEBUFF = 1;

    public Pulse() {
        super(AstrologerMod.makeID(cardInfo.cardName), NAME, "img/infinitespire/cards/collect.png", cardInfo.cardCost, cardStrings.DESCRIPTION, cardInfo.cardType, cardInfo.cardTarget);// 49
        this.damage = this.baseDamage = DAMAGE;// 51
        this.magicNumber = this.baseMagicNumber = DEBUFF;// 52
        this.isMultiDamage = true;// 54
        this.isEthereal = true;// 56

        try {
            AnimatedCardsPatch.load(this, 20, 0.04F, TextureLoader.getAnimatedCardTextures(cardInfo.cardName, this.type));// 60
        } catch (Exception var2) {// 62
            AstrologerMod.logger.error("Failed to load animated card image for " + cardInfo.cardName + ".");// 64
        }

    }// 66

    public void upgrade() {
        if (!this.upgraded) {// 70
            this.upgradeName();// 71
            this.upgradeDamage(UPG_DAMAGE);// 72
            this.upgradeMagicNumber(UPG_DEBUFF);// 73
        }

    }// 75

    public void triggerWhenDrawn() {
        this.calculateCardDamage(null);// 79
        this.flash(Color.RED.cpy());// 80
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlackCardEffect(), 0.1F));// 81
        AbstractPlayer p = AbstractDungeon.player;// 82

        for(float r = 1.0F; r >= 0.2F; r -= 0.2F) {// 84 85
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(r, 0.1F, 0.1F, 1.0F), ShockWaveType.ADDITIVE)));// 86
        }

        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AWAKENED_ATTACK", 0.1F));// 88
        AbstractDungeon.actionManager.addToBottom(new CalculateLaterDamageAllAction(this));// 90
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 92

        while(var3.hasNext()) {
            AbstractMonster m = (AbstractMonster)var3.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AttackEffect.NONE));// 94
        }
    }

    public AbstractCard makeCopy() {
        return new Pulse();// 105
    }

    static {
        cardInfo = new CardInfo("Pulse", 2, CardType.ATTACK, CardTarget.NONE, CardRarity.SPECIAL);// 28
        ID = AstrologerMod.makeID(cardInfo.cardName);// 36
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);// 38
        NAME = cardStrings.NAME;// 39
    }
}