package Astrologer.UI;

import Astrologer.Interfaces.ActivateStellarPower;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.DrawInfo;
import Astrologer.Util.StellarUIStarPosition;
import Astrologer.Util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.Necronomicon;

import java.util.ArrayList;

import static Astrologer.AstrologerMod.assetPath;

public class StellarUI {
    private static final String SkyTexturePath = assetPath("img/UI/LargeSky.png");
    private static final String GlowTexturePath = assetPath("img/UI/Glow.png");
    private static final String StarTexturePath = assetPath("img/Character/orb/Star.png");
    private static final String SunTexturePath = assetPath("img/UI/Sun.png");
    private static final String MoonTexturePath = assetPath("img/UI/Moon.png");

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("astrologer:StellarUI");
    private static final String[] TEXT = uiStrings.TEXT;

    private static final float SKY_SCALE = Settings.WIDTH / 1920.0f;
    private static final int ORIG_SKY_WIDTH = 1920;
    private static final int ORIG_SKY_HEIGHT = 825;
    private static final float SKY_WIDTH = ORIG_SKY_WIDTH * SKY_SCALE;
    private static final float SKY_HEIGHT = ORIG_SKY_HEIGHT * SKY_SCALE;

    //private static final float SKY_OFFSET = SKY_WIDTH / 2.0f;

    private static final float SKY_Y = Settings.HEIGHT / 2.0f;

    private static final float TEXT_X = Settings.WIDTH / 2.0f;
    private static final float TEXT_Y = Settings.HEIGHT - 225.0F * Settings.scale;

    private static final float STAR_RIGHT_X = Settings.WIDTH + 100 * Settings.scale;
    private static final float STAR_LEFT_X = -100 * Settings.scale;

    private static final float STAR_TOP_Y = Settings.HEIGHT - 50.0f * Settings.scale;
    private static final float STAR_BOTTOM_Y = Settings.HEIGHT - 240.0f * Settings.scale;

    private static final int STAR_WIDTH = 21;
    private static final int STAR_HEIGHT = 21;
    private static final float STAR_OFFSET = STAR_WIDTH / 2.0f;

    private static final int CELESTIAL_BODY_SIZE = 400;
    private static final float CELESTIAL_BODY_OFFSET = CELESTIAL_BODY_SIZE / 2.0f;
    private static final float CELESTIAL_BODY_X = (Settings.WIDTH / 2.0f) - CELESTIAL_BODY_OFFSET;
    private static final float CELESTIAL_BODY_Y = Settings.HEIGHT - (CELESTIAL_BODY_OFFSET + 80.0f);

    private static Texture skyTexture = TextureLoader.getTexture(SkyTexturePath);
    private static Texture glowTexture = TextureLoader.getTexture(GlowTexturePath);
    private static Texture starTexture = TextureLoader.getTexture(StarTexturePath);
    private static Texture sunTexture = TextureLoader.getTexture(SunTexturePath);
    private static Texture moonTexture = TextureLoader.getTexture(MoonTexturePath);

    //private ArrayList<DrawInfo> skyPositions;
    private ArrayList<StellarUIStarPosition> starPositions;

    private int roomLastRendered;

    private Color renderColor;
    private Color sunColor;
    private Color moonColor;

    private float sunAlpha;
    private float moonAlpha;

    private float celestialBodyAngle;

    private Hitbox hitbox;
    private ArrayList<PowerTip> tips;

    private boolean solar; //starts as false, 1 = lunar, 2 = solar, etc

    private PowerTip tooltip;

    public StellarUI()
    {
        //skyPositions = new ArrayList<>();
        starPositions = new ArrayList<>();
        tips = new ArrayList<>();

        /*for (float x = 0; x < Settings.WIDTH; x += SKY_WIDTH * Settings.scale)
        {
            skyPositions.add(new DrawInfo(x, SKY_Y, 0.0f));
        }

        skyPositions.add(new DrawInfo(-SKY_OFFSET * Settings.scale, SKY_Y - (100 * Settings.scale), 45.0f));
        skyPositions.add(new DrawInfo(Settings.WIDTH + SKY_OFFSET * Settings.scale, SKY_Y - (100 * Settings.scale), -45.0f));
        skyPositions.add(new DrawInfo(0, SKY_Y - (50 * Settings.scale), 22.5f));
        skyPositions.add(new DrawInfo(Settings.WIDTH, SKY_Y - (50 * Settings.scale), -22.5f));*/

        roomLastRendered = -1;

        float hitboxSize = 60 * Settings.scale;
        float hitboxOffset = hitboxSize / 2.0f;

        hitbox = new Hitbox(TEXT_X - hitboxOffset, TEXT_Y - hitboxOffset, hitboxSize, hitboxSize);

        renderColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
        sunColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
        moonColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);

        tooltip = new PowerTip(TEXT[0], TEXT[1]);

        tips.add(tooltip);

        sunAlpha = 0.5f;
        moonAlpha = 0.5f;

        celestialBodyAngle = 0.0f;

        updateTooltip();
    }

    public void updateStellarPhase(int newPhase) //add a star
    {
        if (newPhase < starPositions.size())
        {
            for (int i = newPhase; i < starPositions.size(); i++)
            {
                starPositions.get(i).isDone = true; //start fade
            }
        }

        while (starPositions.size() < newPhase)
        {
            starPositions.add(new StellarUIStarPosition());
        }
    }
    public void updateStellarAlignment(boolean solar)
    {
        this.solar = solar;
    }

    public void reset(int initialPhase)
    {
        starPositions.clear();
        renderColor.a = 0.0f;
        updateStellarAlignment(false);
        sunAlpha = 0.0f;
        moonAlpha = 1.0f;
        if (initialPhase > 0)
            updateStellarPhase(initialPhase);

        updateTooltip();
    }
    public void hide()
    {
        renderColor.a = 0.0f;
    }

    public void updateTooltip()
    {
        if (AbstractDungeon.player != null)
        {
            tooltip.body = TEXT[1] + StellarPhaseValue.stellarPhase.get(AbstractDungeon.player) +
                    (solar ? TEXT[3] : TEXT[2]) +
                    TEXT[4] + StellarPhaseValue.maxStellarPhase.get(AbstractDungeon.player);
        }
    }


    public void render(SpriteBatch sb, boolean fading) {
        if (renderColor.a > 0.0f || !fading) {
            boolean glow = false;
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof ActivateStellarPower)
                {
                    glow = true;
                    break;
                }
            }

            hitbox.update();
            if (hitbox.hovered) {
                TipHelper.queuePowerTips(TEXT_X - 150 * Settings.scale, TEXT_Y - 80.0F * Settings.scale, this.tips);
            }

            if (AbstractDungeon.floorNum != roomLastRendered) {
                roomLastRendered = AbstractDungeon.floorNum;
                renderColor.a = 0.0f;
            } else if (fading) {
                if (renderColor.a > 0.0f)
                    renderColor.a -= Gdx.graphics.getDeltaTime() * 0.5f;
                if (renderColor.a < 0.0f)
                    renderColor.a = 0.0f;
            } else if (renderColor.a < 1.0f) {
                renderColor.a += Gdx.graphics.getDeltaTime() * 0.5f;
                if (renderColor.a > 1.0f)
                    renderColor.a = 1.0f;
            }

            //For sun/moon
            if (solar)
            {
                sunAlpha = Math.min(1.0f, sunAlpha + Gdx.graphics.getDeltaTime() * 1.5f);
                moonAlpha = 1.0f - sunAlpha;
            }
            else
            {
                moonAlpha = Math.min(1.0f, moonAlpha + Gdx.graphics.getDeltaTime() * 1.5f);
                sunAlpha = 1.0f - moonAlpha;
            }

            celestialBodyAngle = (celestialBodyAngle + (10.0f * Gdx.graphics.getDeltaTime())) % 360.0f;

            sunColor.a = renderColor.a * sunAlpha;
            moonColor.a = renderColor.a * moonAlpha;

            for (StellarUIStarPosition pos : starPositions) //for stars
            {
                if (fading)
                    pos.isDone = true;
                pos.update();
            }

            starPositions.removeIf((star) -> star.isDone && star.renderColor.a <= 0.0f);

            sb.setColor(renderColor);

            /*for (DrawInfo info : skyPositions) {
                sb.draw(skyTexture, info.x, info.y, SKY_OFFSET, SKY_OFFSET, SKY_WIDTH, SKY_HEIGHT, Settings.scale, Settings.scale, info.angle, 0, 0, SKY_WIDTH, SKY_HEIGHT, false, false);
            }*/

            sb.draw(skyTexture, 0, SKY_Y, 0, 0, SKY_WIDTH, SKY_HEIGHT, 1.0f, 1.0f, 0, 0, 0, ORIG_SKY_WIDTH, ORIG_SKY_HEIGHT, false, false);

            //Render a glow behind each start if player has a power that implements ActivateStellarPower
            for (StellarUIStarPosition pos : starPositions) {
                float x = MathUtils.lerp(STAR_RIGHT_X, STAR_LEFT_X, (pos.progress + 1) / 2.0f);
                float y = Interpolation.circleIn.apply(STAR_TOP_Y, STAR_BOTTOM_Y, Math.abs(pos.progress)) + pos.yOffset;

                sb.setColor(pos.renderColor);
                if (glow)
                    sb.draw(glowTexture, x - STAR_OFFSET, y - STAR_OFFSET, STAR_OFFSET, STAR_OFFSET, STAR_WIDTH, STAR_HEIGHT, Settings.scale, Settings.scale, pos.angle, 0, 0, STAR_WIDTH, STAR_HEIGHT, false, false);
                sb.draw(starTexture, x - STAR_OFFSET, y - STAR_OFFSET, STAR_OFFSET, STAR_OFFSET, STAR_WIDTH, STAR_HEIGHT, Settings.scale, Settings.scale, pos.angle, 0, 0, STAR_WIDTH, STAR_HEIGHT, false, false);
            }

            sb.setColor(moonColor);
            sb.draw(moonTexture, CELESTIAL_BODY_X, CELESTIAL_BODY_Y, CELESTIAL_BODY_OFFSET, CELESTIAL_BODY_OFFSET, CELESTIAL_BODY_SIZE, CELESTIAL_BODY_SIZE, Settings.scale, Settings.scale, celestialBodyAngle, 0, 0, CELESTIAL_BODY_SIZE, CELESTIAL_BODY_SIZE, false, false);
            sb.setColor(sunColor);
            sb.draw(sunTexture, CELESTIAL_BODY_X, CELESTIAL_BODY_Y, CELESTIAL_BODY_OFFSET, CELESTIAL_BODY_OFFSET, CELESTIAL_BODY_SIZE, CELESTIAL_BODY_SIZE, Settings.scale, Settings.scale, celestialBodyAngle, 0, 0, CELESTIAL_BODY_SIZE, CELESTIAL_BODY_SIZE, false, false);

            FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, Integer.toString(StellarPhaseValue.stellarPhase.get(AbstractDungeon.player)), TEXT_X, TEXT_Y, renderColor);
        }
    }
}
