package Astrologer.Cards.Normal;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Actions.Astrologer.ShineAction;
import Astrologer.Actions.Generic.ForceUpgradeCardAction;
import Astrologer.Util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Armaments;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Shine extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Shine",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 1;

    public Shine()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ShineAction());
    }
}