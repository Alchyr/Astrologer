package Astrologer.Abstracts;

import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class MeteorCard extends BaseCard {
    public MeteorCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        super(cardInfo, upgradesDescription);
        this.tags.add(CustomTags.METEOR);
    }
}
