package Astrologer.Patches;

import Astrologer.Cards.Tarot.Judgement;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.ui.buttons.DynamicButton;
import javassist.CtBehavior;

import static Astrologer.AstrologerMod.logger;

public class LoadMiscPatch {
    @SpirePatch(
            clz = CardLibrary.class,
            method = "getCopy",
            paramtypez = { String.class, int.class, int.class }
    )
    public static class MiscPatch
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "retVal" }
        )
        public static void LoadMisc(String key, int upgradeTime, int misc, AbstractCard retVal)
        {
            if (retVal.cardID.equals(Judgement.ID))
            {
                if (misc != 0)
                {
                    retVal.updateCost(-misc);
                    retVal.initializeDescription();
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "misc");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
