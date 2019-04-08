package Astrologer.Patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class CardsPlayedThisCombatPatch {
    public static int cardsPlayedThisCombatCount = 0;

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void trackPlayedCards(GameActionManager __instance)
    {
        ++cardsPlayedThisCombatCount;
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "energyOnUse");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
