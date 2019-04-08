package Astrologer.Patches;

import Astrologer.Relics.SkyMirror;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class UseCardActionPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = { "targetCard", "duration" }
    )
    public static SpireReturn ReturnToBottom(UseCardAction __instance, AbstractCard targetCard, @ByRef float[] duration)
    {
        if (AbstractDungeon.player.hasRelic(SkyMirror.ID) && AbstractDungeon.player.getRelic(SkyMirror.ID).counter == SkyMirror.ACTIVE_VALUE)
        {
            AbstractDungeon.player.getRelic(SkyMirror.ID).onTrigger();

            AbstractDungeon.player.hand.moveToBottomOfDeck(targetCard);

            duration[0] -= Gdx.graphics.getDeltaTime();
            if (duration[0] < 0.0f)
                __instance.isDone = true;

            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "moveToDiscardPile");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
