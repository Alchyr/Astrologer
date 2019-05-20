package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Actions.Astrologer.AdvancePhaseAction;
import Astrologer.AstrologerMod;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.PhaseCheck;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CosmicExpansePower extends BasePower {
    public static final String NAME = "CosmicExpanse";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CosmicExpansePower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (PhaseCheck.isStar(card))
        {
            for (int i = 0; i < this.amount; ++i)
                AbstractDungeon.actionManager.addToBottom(new AdvancePhaseAction(false));
        }
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}