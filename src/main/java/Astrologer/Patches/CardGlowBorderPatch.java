package Astrologer.Patches;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.PhaseCheck;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import java.sql.Ref;

@SpirePatch(
        clz = CardGlowBorder.class,
        method = SpirePatch.CONSTRUCTOR
)
public class CardGlowBorderPatch {
    @SpirePostfixPatch
    public static void PostFix(CardGlowBorder __instance, AbstractCard c)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (c.hasTag(CustomTags.SOLAR) && PhaseCheck.solarActive()) {
                ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", new Color(0.93f, 0.85f, 0.0f, 1.0f));
            }
            if (c.hasTag(CustomTags.LUNAR) && PhaseCheck.lunarActive()) {
                ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", new Color(0.9f, 0.9f, 0.9f, 1.0f));
            }
            if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard) {
                if (((StellarCard) c).stellarActive()) {
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", new Color(0.55f, 0.1f, 1.0f, 1.0f));
                }
            }
        }
    }
}
