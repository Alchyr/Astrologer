package Astrologer.Abstracts;

import Astrologer.Enums.CustomTags;
import Astrologer.Interfaces.ActivateStellarPower;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.CardInfo;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

import static Astrologer.AstrologerMod.makeID;

public abstract class StellarCard extends BaseCard {
    public int originalStellarValue;
    public int stellarValue;
    public boolean stellarModified;
    public boolean upgradesStellar;

    public boolean forceActive = false;

    private static ArrayList<TooltipInfo> StellarTooltip;

    public StellarCard(CardInfo cardInfo, boolean upgradesDescription, int stellarValue)
    {
        super(cardInfo, upgradesDescription);
        this.originalStellarValue = stellarValue;
        this.stellarValue = stellarValue;
        this.stellarModified = false;
        this.upgradesStellar = false;
        tags.add(CustomTags.STELLAR);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();

        if (c instanceof StellarCard)
        {
            ((StellarCard) c).originalStellarValue = this.originalStellarValue;
            ((StellarCard) c).stellarValue = this.stellarValue;
            ((StellarCard) c).forceActive = this.forceActive;
            ((StellarCard) c).stellarModified = this.stellarModified;
            ((StellarCard) c).upgradesStellar = this.upgradesStellar;
        }

        return c;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (StellarTooltip == null)
        {
            StellarTooltip = new ArrayList<>();
            UIStrings StellarStrings = CardCrawlGame.languagePack.getUIString(makeID("Stellar"));

            StellarTooltip.add(new TooltipInfo(
                    StellarStrings.TEXT[0],
                    StellarStrings.TEXT[1]
            ));
        }
        return StellarTooltip;
    }

    public boolean stellarModified()
    {
        return stellarModified || (stellarValue != originalStellarValue);
    }

    public boolean stellarActive()
    {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ActivateStellarPower)
                    return true;
            }

            return forceActive || (stellarValue >= 0 && StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) >= stellarValue);
        }
        return forceActive;
    }
    protected boolean stellarActiveFool()
    {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ActivateStellarPower)
                    return true;
            }
        }
        return forceActive;
    }
}
