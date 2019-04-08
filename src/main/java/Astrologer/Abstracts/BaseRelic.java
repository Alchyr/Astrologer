package Astrologer.Abstracts;

import Astrologer.Util.TextureLoader;
import basemod.abstracts.CustomRelic;

import static Astrologer.AstrologerMod.assetPath;

public abstract class BaseRelic extends CustomRelic {
    public BaseRelic(String setId, String textureID, RelicTier tier, LandingSound sfx) {
        super(setId, TextureLoader.getTexture(assetPath("img/Relics/") + textureID + ".png"), tier, sfx);
        outlineImg = TextureLoader.getTexture(assetPath("img/Relics/") + textureID + "-outline.png");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}