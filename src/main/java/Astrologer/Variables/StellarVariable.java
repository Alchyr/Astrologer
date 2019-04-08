package Astrologer.Variables;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Enums.CustomTags;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class StellarVariable extends DynamicVariable {
    private static final String KEY = "STELLAR_VARIABLE";
    @Override
    public final String key()
    {
        return KEY;
    }

    @Override
    public boolean isModified(AbstractCard c)
    {
        if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard)
        {
            return ((StellarCard) c).stellarModified();
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard c, boolean v) {
        if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard)
        {
            ((StellarCard) c).stellarModified = v;
        }
    }

    @Override
    public int value(AbstractCard c) {
        if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard)
        {
            return ((StellarCard) c).stellarValue;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard c) {
        if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard)
        {
            return ((StellarCard) c).originalStellarValue;
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard c) {
        if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard)
        {
            return ((StellarCard) c).upgradesStellar;
        }
        return false;
    }
}
