package Astrologer.Patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = SpirePatch.CLASS
)
public class StellarPhaseValue {
    public static SpireField<Integer> stellarPhase = new SpireField<>(()->1);
    public static SpireField<Boolean> stellarAlignment = new SpireField<>(()->false);
    public static SpireField<Integer> maxStellarPhase = new SpireField<>(()->21);
}