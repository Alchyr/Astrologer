package Astrologer.Abstracts;

import Astrologer.Actions.Astrologer.AdvancePhaseAction;
import Astrologer.AstrologerMod;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class StarCard extends BaseCard {
    public StarCard(CardInfo cardInfo, boolean upgradesDescription) {
        super(cardInfo, upgradesDescription);
        tags.add(CustomTags.STAR);
    }
}
