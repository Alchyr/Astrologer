package Astrologer.Patches;

import Astrologer.Effects.AppearBigThenShrinkAndFallStarEffect;
import Astrologer.Effects.SmallFallingStarEffect;
import Astrologer.Enums.CharacterEnum;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

import static Astrologer.Util.Sounds.Sparkle;

public class VictoryCutscene {
    private static final float MIN_SPACING = 64 * Settings.scale, MAX_SPACING = 128 * Settings.scale, MID_SPACING = 96 * Settings.scale, OFFSET = MID_SPACING * 0.5f;
    public static void doShiny() {
        AppearBigThenShrinkAndFallStarEffect.load();

        float end = Settings.WIDTH + MAX_SPACING;
        int row = 0, col = 0;
        for (float y = Settings.HEIGHT + MathUtils.random(-MIN_SPACING, MIN_SPACING); y > 0; y -= MID_SPACING) {
            col = 0;
            for (float x = MathUtils.random(-MIN_SPACING, MIN_SPACING); x < end; x += MathUtils.random(MIN_SPACING, MAX_SPACING)) {
                AbstractDungeon.topLevelEffects.add(
                        new AppearBigThenShrinkAndFallStarEffect(x, y + MathUtils.random(-OFFSET, OFFSET), (row / 2f) + col)
                );
                ++col;
            }
            ++row;
        }
        CardCrawlGame.sound.play(Sparkle.key, 0.05F);
    }

    @SpirePatch(
            clz = Cutscene.class,
            method = "updateFadeOut"
    )
    public static class StarsInTheMiddle {
        @SpireInsertPatch(
                rloc = 5
        )
        public static void vfx(Cutscene __instance) {
            if (CharacterEnum.ASTROLOGER.equals(AbstractDungeon.player.chosenClass)) {
                doShiny();
            }
        }
    }

    /*@SpirePatch(
            clz = ProceedButton.class,
            method = "goToTrueVictoryRoom"
    )
    public static class DontShow {
        @SpirePostfixPatch
        public static void vfx(ProceedButton __instance) {
            if (AbstractDungeon.player instanceof Astrologer) {
                doShiny();
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "nextRoomTransition",
            paramtypez = { SaveFile.class }
    )
    public static class Saved {
        @SpirePostfixPatch
        public static void bringThemBack(AbstractDungeon __instance, SaveFile file) {
            AbstractDungeon.topLevelEffects.addAll(saveEffects);
            saveEffects.clear();
        }
    }*/

    @SpirePatch(
            clz = Cutscene.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class Skip {
        @SpirePostfixPatch
        public static void noCutscene(Cutscene __instance) {
            if (CharacterEnum.ASTROLOGER.equals(AbstractDungeon.player.chosenClass)) {
                ReflectionHacks.setPrivate(__instance, Cutscene.class, "bgImg", null);
                //ReflectionHacks.setPrivate(__instance, Cutscene.class, "isDone", true);
                AstrologerVfx.timer = AstrologerVfx.STAR_TIME;
            }
        }
    }

    @SpirePatch(
            clz = VictoryScreen.class,
            method = "updateVfx"
    )
    public static class AstrologerVfx {
        static float STAR_TIME = 0.148383857f;
        private static float timer = STAR_TIME;

        @SpirePostfixPatch
        public static void starry(VictoryScreen __instance, ArrayList<AbstractGameEffect> ___effect) {
            if (AbstractDungeon.player.chosenClass == CharacterEnum.ASTROLOGER) {
                timer -= Gdx.graphics.getDeltaTime();
                if (timer < 0.0F) {
                    if (___effect.size() < 100) {
                        ___effect.add(new SmallFallingStarEffect());
                        ___effect.add(new SmallFallingStarEffect());
                        ___effect.add(new SmallFallingStarEffect());
                        ___effect.add(new SmallFallingStarEffect());
                        ___effect.add(new SmallFallingStarEffect());
                    }

                    timer = STAR_TIME;
                }
            }
        }
    }
}
