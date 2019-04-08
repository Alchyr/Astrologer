package Astrologer.Util;

import javafx.util.Pair;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;

import static Astrologer.AstrologerMod.assetPath;

public class Sounds {
    public static final Pair<String, String> Sparkle = new Pair<>("astrologer:Sparkle", assetPath("audio/Sparkle.wav"));
    public static final Pair<String, String> Ring = new Pair<>("astrologer:Ring", assetPath("audio/ShipBell.wav"));
    public static final Pair<String, String> Tinkle = new Pair<>("astrologer:Tinkle", assetPath("audio/Tinkle.ogg"));
}
