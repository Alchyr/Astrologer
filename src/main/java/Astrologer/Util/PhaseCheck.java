package Astrologer.Util;

import Astrologer.Enums.CustomTags;
import Astrologer.Interfaces.AllStarsPower;
import Astrologer.Patches.StellarPhaseValue;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PhaseCheck {
    public static boolean lunarActive()
    {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null)
        {
            return !StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player);
        }
        return false;
    }
    public static boolean solarActive()
    {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null)
        {
            return StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player);
        }
        return false;
    }
    public static boolean isStar(AbstractCard c)
    {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
                if (p instanceof AllStarsPower)
                    return true;
        }
        return c.hasTag(CustomTags.STAR);
    }
}
