package Astrologer.Cards.Basic;

import Astrologer.Abstracts.BaseCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;
import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;

public class Defend extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Defend",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 5;
    private final static int UPG_BLOCK = 2;

    private final static int BASE_BONUS = 0;
    private final static int LUNAR_BONUS = 2;

    public Defend()
    {
        super(cardInfo, true);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(BASE_BONUS, LUNAR_BONUS);

        tags.add(BASIC_DEFEND);
    }

    @Override
    public void upgrade() {
        super.upgrade();

        if (!hasTag(CustomTags.LUNAR))
            tags.add(CustomTags.LUNAR);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockBonus = 0;
        if (upgraded && PhaseCheck.lunarActive())
            blockBonus = this.magicNumber;

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block + blockBonus));
    }
}