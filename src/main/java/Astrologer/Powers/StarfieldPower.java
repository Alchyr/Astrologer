package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.Interfaces.AllStarsPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class StarfieldPower extends BasePower implements AllStarsPower {
    public static final String NAME = "Starfield";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public StarfieldPower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    //view isStar check in PhaseCheck Util class

    public void updateDescription() {
        this.description = descriptions[0];
    }
}
