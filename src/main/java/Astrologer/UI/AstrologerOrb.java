package Astrologer.UI;

import Astrologer.Util.StarPosition;
import Astrologer.Util.TextureLoader;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

import static Astrologer.AstrologerMod.assetPath;


public class AstrologerOrb extends CustomEnergyOrb {
    private static final String[] orbTextures = {
            assetPath("img/Character/orb/layer1.png"),
            assetPath("img/Character/orb/layer2.png"),
            assetPath("img/Character/orb/layer3.png"),
            assetPath("img/Character/orb/layer4.png"),
            assetPath("img/Character/orb/layer5.png"),
            assetPath("img/Character/orb/layer6.png"),
            assetPath("img/Character/orb/layer1d.png"),
            assetPath("img/Character/orb/layer2d.png"),
            assetPath("img/Character/orb/layer3d.png"),
            assetPath("img/Character/orb/layer4d.png"),
            assetPath("img/Character/orb/layer5d.png") };

    private static final String VFXTexture = assetPath("img/Character/orb/vfx.png");

    private static final float[] layerSpeeds = new float[] {10.0F, 30.0F, 15.0F, -20.0F, 0.0F};

    private static final float MIN_STAR_DELAY = 0.1f;
    private static final float MAX_STAR_DELAY = 0.3f;
    private static final float STAR_ALPHA_GROWTH = 5.0f;
    private static final float STAR_ALPHA_DECAY = 1.0f;
    private static int starWidth;
    private static int starHeight;
    private static float starOffset;
    private static final float MIN_OFFSET = 70.0f * Settings.scale;
    private static final float MAX_OFFSET = 110.0f * Settings.scale;

    private static final String StarTexturePath = assetPath("img/Character/orb/Star.png");

    private static Texture starTexture;

    private float starTimer;

    private ArrayList<StarPosition> starPositions;

    public AstrologerOrb()
    {
        super(orbTextures, VFXTexture, layerSpeeds);

        starPositions = new ArrayList<>();
        starTimer = MathUtils.random(MIN_STAR_DELAY, MAX_STAR_DELAY);

        if (starTexture == null)
        {
            starTexture = TextureLoader.getTexture(StarTexturePath);

            if (starTexture != null)
            {
                starWidth = starTexture.getWidth();
                starHeight = starTexture.getHeight();

                starOffset = starWidth / 2.0f;
            }
        }
    }

    @Override
    public void updateOrb(int energyCount) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (energyCount == 0) {
            for (int i = 0; i < Math.min(this.angles.length, layerSpeeds.length); ++i)
            {
                this.angles[i] += deltaTime * layerSpeeds[i] / 4.0F;
            }
        } else {
            for (int i = 0; i < Math.min(this.angles.length, layerSpeeds.length); ++i)
            {
                this.angles[i] += deltaTime * layerSpeeds[i];
            }
            starTimer -= deltaTime;
            while (starTimer <= 0)
            {
                float distance = MathUtils.random(MIN_OFFSET, MAX_OFFSET) * (MathUtils.randomBoolean() ? 1 : -1);
                float direction = MathUtils.random(-180.0f, 180.0f);
                float xOffset = MathUtils.cos(direction) * distance;
                float yOffset = MathUtils.sin(direction) * distance;

                starPositions.add(new StarPosition(xOffset, yOffset));
                starTimer += MathUtils.random(MIN_STAR_DELAY, MAX_STAR_DELAY);
            }
        }
        for (StarPosition s : starPositions)
        {
            if (s.fading)
            {
                s.renderColor.a = Math.max(0.0f, s.renderColor.a - STAR_ALPHA_DECAY * deltaTime);
                if (s.renderColor.a <= 0.0f)
                {
                    s.isDone = true;
                }
            }
            else
            {
                s.renderColor.a = Math.min(1.0f, s.renderColor.a + STAR_ALPHA_GROWTH * deltaTime);
                if (s.renderColor.a >= 1.0f)
                {
                    s.fading = true;
                }
            }
        }
        starPositions.removeIf((s)->s.isDone);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        super.renderOrb(sb, enabled, current_x, current_y);

        for (StarPosition s : starPositions)
        {
            sb.setColor(s.renderColor);
            sb.draw(starTexture, current_x + s.x - starOffset, current_y + s.y - starOffset, starOffset, starOffset, starWidth, starHeight, Settings.scale, Settings.scale, s.angle, 0, 0, starWidth, starHeight, false, false);
        }
    }
}
