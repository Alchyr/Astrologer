package Astrologer.Patches;

import Astrologer.Enums.AttackEffectEnum;
import Astrologer.Util.Sounds;
import Astrologer.Util.TextureLoader;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageHeartEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Astrologer.AstrologerMod.assetPath;

public class FlashAtkImgEffectPatches {
    private static final String StarTexturePath = assetPath("img/Attack/BigStar.png");

    private static Texture starTexture = TextureLoader.getTexture(StarTexturePath);


    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "loadImage"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "loadImage"
    )
    public static class loadImagePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<TextureAtlas.AtlasRegion> tryLoadImage(AbstractGameEffect __instance)
        {
            try {
                AbstractGameAction.AttackEffect effect = (AbstractGameAction.AttackEffect) ReflectionHacks.getPrivate(__instance, FlashAtkImgEffect.class, "effect");
                if (effect == AttackEffectEnum.STAR)
                {
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(starTexture, 0, 0, starTexture.getWidth(), starTexture.getHeight()));
                }
            }
            catch (Exception e)
            {

            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "playSound"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "playSound"
    )
    public static class playSoundPatch
    {
        @SpirePrefixPatch
        public static SpireReturn tryPlaySound(AbstractGameEffect __instance, AbstractGameAction.AttackEffect effect)
        {
            if (effect == AttackEffectEnum.STAR)
            {
                CardCrawlGame.sound.play(Sounds.Tinkle.getKey(), 0.15f);
            }
            return SpireReturn.Continue();
        }
    }
}