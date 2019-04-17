package Astrologer.Patches;

import Astrologer.Util.TextureLoader;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimatedCardsPatch {
    public static void load(AbstractCard c, float frameRate, String frames)
    {
        if (!AnimationInfo.cardFrames.containsKey(c.cardID))
        {
            //set framerate
            AnimationInfo.frameRate.put(c.cardID, frameRate);

            //check for portrait animation
            boolean hasPortraitAnimation = true;
            String portraitFrames = "";

            if (!CustomCard.imgMap.containsKey(frames))
            {
                CustomCard.imgMap.put(frames, ImageMaster.loadImage(frames));
            }

            int endingIndex = frames.lastIndexOf(".");

            String portrait = frames.substring(0, endingIndex) + "_p" + frames.substring(endingIndex);
            if (TextureLoader.testTexture(portrait))
            {
                portraitFrames = portrait;
            }
            else
            {
                hasPortraitAnimation = false;
            }

            //load atlas regions and frame counts
            Texture cardFramesTexture = CustomCard.imgMap.get(frames);
            int frameCount = cardFramesTexture.getWidth() / 250;
            TextureAtlas.AtlasRegion[] frameRegions = new TextureAtlas.AtlasRegion[frameCount];

            for (int i = 0; i < frameRegions.length; ++i)
            {
                frameRegions[i] = new TextureAtlas.AtlasRegion(cardFramesTexture, i * 250, 0, 250, 190);
            }

            AnimationInfo.cardFrames.put(c.cardID, frameRegions);

            //load portrait info
            if (hasPortraitAnimation)
            {
                if (!CustomCard.imgMap.containsKey(portraitFrames))
                {
                    CustomCard.imgMap.put(portraitFrames, ImageMaster.loadImage(portraitFrames));
                }

                cardFramesTexture = CustomCard.imgMap.get(portraitFrames);
                frameCount = cardFramesTexture.getWidth() / 500;

                TextureAtlas.AtlasRegion[] portraitFrameRegions = new TextureAtlas.AtlasRegion[frameCount];
                for (int i = 0; i < portraitFrameRegions.length; ++i)
                {
                    portraitFrameRegions[i] = new TextureAtlas.AtlasRegion(cardFramesTexture, i * 500, 0, 500, 380);
                }

                AnimationInfo.cardPortraitFrames.put(c.cardID, portraitFrameRegions);
            }
        }


        if (AnimationInfo.cardFrames.containsKey(c.cardID))
        {
            if (AnimationInfo.cardPortraitFrames.containsKey(c.cardID))
                AnimationInfo.hasPortraitAnimation.set(c, true);
            AnimationInfo.isAnimated.set(c, true);
            AnimationInfo.frameTime.set(c, frameRate);
            AnimationInfo.currentFrame.set(c, 0);
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = SpirePatch.CLASS
    )
    public static class AnimationInfo
    {
        public static HashMap<String, TextureAtlas.AtlasRegion[]> cardFrames = new HashMap<>();
        public static HashMap<String, TextureAtlas.AtlasRegion[]> cardPortraitFrames = new HashMap<>();
        public static HashMap<String, Float> frameRate = new HashMap<>();

        public static SpireField<Boolean> isAnimated = new SpireField<>(()->false);
        public static SpireField<Boolean> hasPortraitAnimation = new SpireField<>(()->false);

        public static SpireField<Float> frameTime = new SpireField<>(()->0.0f);
        public static SpireField<Integer> currentFrame = new SpireField<>(()->0);
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderPortrait"
    )
    public static class renderAnimated
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "drawX", "drawY" }
        )
        public static SpireReturn altRender(AbstractCard __instance, SpriteBatch sb, float drawX, float drawY)
        {
            if (AnimationInfo.isAnimated.get(__instance))
            {
                sb.draw(AnimationInfo.cardFrames.get(__instance.cardID)[AnimationInfo.currentFrame.get(__instance)], drawX, drawY + 72.0F, 125.0F, 23.0F, 250.0F, 190.0F, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                ArrayList<Matcher> prevMatches = new ArrayList<>();
                prevMatches.add(
                        new Matcher.MethodCallMatcher(SpriteBatch.class,
                                "setColor"));

                Matcher finalMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findAllInOrder(ctMethodToPatch, prevMatches, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderPortrait"
    )
    public static class renderSingleCardAnimated
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "card" }
        )
        public static SpireReturn altRender(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card)
        {
            if (AnimationInfo.isAnimated.get(card))
            {
                sb.draw(AnimationInfo.cardPortraitFrames.get(card.cardID)[AnimationInfo.currentFrame.get(card)], (float)Settings.WIDTH / 2.0F - 250.0F, (float)Settings.HEIGHT / 2.0F - 190.0F + 136.0F * Settings.scale, 250.0f, 190.0f, 500.0f, 380.0f, Settings.scale, Settings.scale, 0.0f);

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                ArrayList<Matcher> prevMatches = new ArrayList<>();
                prevMatches.add(
                        new Matcher.MethodCallMatcher(SpriteBatch.class,
                                "draw"));

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(SingleCardViewPopup.class, "portraitImg");
                return LineFinder.findInOrder(ctMethodToPatch, prevMatches, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderInLibrary"
    )
    public static class SaveLibraryFrames
    {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn altRender(AbstractCard __instance, SpriteBatch sb)
        {
            if (AnimationInfo.isAnimated.get(__instance))
            {
                AbstractCard copy = __instance.makeCopy();
                copy.current_x = __instance.current_x;
                copy.current_y = __instance.current_y;
                copy.drawScale = __instance.drawScale;
                copy.upgrade();
                copy.displayUpgrades();
                AnimationInfo.frameTime.set(copy, AnimationInfo.frameTime.get(__instance));
                AnimationInfo.currentFrame.set(copy, AnimationInfo.currentFrame.get(__instance));
                copy.render(sb);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "makeCopy");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "update"
    )
    public static class UpdateAnimation
    {
        @SpirePostfixPatch
        public static void updateAnim(AbstractCard __instance)
        {
            if (AnimationInfo.isAnimated.get(__instance))
            {
                AnimationInfo.frameTime.set(__instance, AnimationInfo.frameTime.get(__instance) - Gdx.graphics.getDeltaTime());

                if (AnimationInfo.frameTime.get(__instance) <= 0.0f)
                {
                    AnimationInfo.frameTime.set(__instance, AnimationInfo.frameRate.get(__instance.cardID));
                    AnimationInfo.currentFrame.set(__instance, (AnimationInfo.currentFrame.get(__instance) + 1) % AnimationInfo.cardFrames.get(__instance.cardID).length);
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "update"
    )
    public static class UpdateSingleViewAnimation
    {
        @SpireInsertPatch(
                rloc = 0,
                localvars = { "card" }
        )
        public static void updateAnim(SingleCardViewPopup __instance, AbstractCard card)
        {
            if (AnimationInfo.isAnimated.get(card))
            {
                AnimationInfo.frameTime.set(card, AnimationInfo.frameTime.get(card) - Gdx.graphics.getDeltaTime());

                if (AnimationInfo.frameTime.get(card) <= 0.0f)
                {
                    AnimationInfo.frameTime.set(card, AnimationInfo.frameRate.get(card.cardID));
                    AnimationInfo.currentFrame.set(card, (AnimationInfo.currentFrame.get(card) + 1) % AnimationInfo.cardFrames.get(card.cardID).length);
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render"
    )
    public static class SaveSingleCardViewFrames
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "card", "copy" }
        )
        public static void transferFrames(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card, AbstractCard copy)
        {
            if (AnimationInfo.isAnimated.get(card))
            {
                AnimationInfo.frameTime.set(copy, AnimationInfo.frameTime.get(card));
                AnimationInfo.currentFrame.set(copy, AnimationInfo.currentFrame.get(card));
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
