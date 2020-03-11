package Astrologer.Util;

import org.apache.logging.log4j.core.jmx.RingBufferAdmin;

import static Astrologer.AstrologerMod.assetPath;

public class Sounds {
    public static final AudioPair Sparkle = new AudioPair("astrologer:Sparkle", assetPath("audio/Sparkle.wav"));
    public static final AudioPair Ring = new AudioPair("astrologer:Ring", assetPath("audio/ShipBell.wav"));
    public static final AudioPair Tinkle = new AudioPair("astrologer:Tinkle", assetPath("audio/Tinkle.ogg"));

    public static class AudioPair
    {
        public String key;
        public String file;

        public AudioPair(String key, String file)
        {
            this.key = key;
            this.file = file;
        }
    }
}
