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
            boolean changeColor = false;
            if (c.hasTag(CustomTags.SOLAR) && PhaseCheck.solarActive()) {
                changeColor = true;
            }
            if (c.hasTag(CustomTags.LUNAR) && PhaseCheck.lunarActive()) {
                changeColor = true;
            }
            if (c.hasTag(CustomTags.STELLAR) && c instanceof StellarCard) {
                if (((StellarCard) c).stellarActive()) {
                    changeColor = true;
                }
            }

            if (changeColor) {
                ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", Color.valueOf("edd900ff"));
            }
        }
    }
}
