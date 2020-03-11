package Astrologer.Cards.Crossover.infinitespire;

import Astrologer.AstrologerMod;
import Astrologer.Actions.Specific.NescienceAction;
import Astrologer.Patches.AnimatedCardsPatch;
import Astrologer.Util.CardInfo;
import Astrologer.Util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import infinitespire.abstracts.BlackCard;


public class Nescience extends BlackCard {
    private static final CardInfo cardInfo;
    public static final String ID;
    private static final CardStrings cardStrings;
    private static final String NAME;

    private static final int UPG_COST = 1;

    public Nescience() {
        super(AstrologerMod.makeID(cardInfo.cardName), NAME, "img/infinitespire/cards/collect.png", cardInfo.cardCost, cardStrings.DESCRIPTION, cardInfo.cardType, cardInfo.cardTarget);// 37
        this.exhaust = true;

        try {
            AnimatedCardsPatch.load(this, 15, 0.08F, TextureLoader.getAnimatedCardTextures(cardInfo.cardName, this.type));
        } catch (Exception var2) {
            AstrologerMod.logger.error("Failed to load animated card image for " + cardInfo.cardName + ".");
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPG_COST);
        }
    }

    public void useWithEffect(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new NescienceAction());
    }

    public AbstractCard makeCopy() {
        return new Nescience();
    }

    static {
        cardInfo = new CardInfo("Nescience", 2, CardType.SKILL, CardTarget.NONE, CardRarity.SPECIAL);
        ID = AstrologerMod.makeID(cardInfo.cardName);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
    }
}
