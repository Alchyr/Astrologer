package Astrologer.Cards.Powers;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Powers.StarfieldPower;
import Astrologer.Util.CardInfo;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class Starfield extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Starfield",
            3,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    public Starfield()
    {
        super(cardInfo, true);

        isEthereal = true;

        this.tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.isEthereal = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StarfieldPower(p, -1), 1));
    }
}