package Astrologer.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;

public class CoinEffect extends AbstractGameEffect {
    private static final float GRAVITY = 1000.0F * Settings.scale;
    private static final float START_VY = 300.0F * Settings.scale;
    private static final float START_VY_JITTER = 700.0F * Settings.scale;
    private static final float START_VX = 90.0F * Settings.scale;
    private static final float START_VX_JITTER = 150.0F * Settings.scale;

    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;

    private TextureAtlas.AtlasRegion img;

    private boolean fadeIn;
    private boolean fadeOut;

    public CoinEffect(float x, float y)
    {
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.COPPER_COIN_1;
        } else {
            this.img = ImageMaster.COPPER_COIN_2;
        }

        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y - (float)this.img.packedHeight / 2.0F;
        this.vX = MathUtils.random(START_VX * Settings.scale, START_VX_JITTER);
        this.rotationSpeed = MathUtils.random(500.0F, 2000.0F);

        if (MathUtils.randomBoolean()) {
            this.vX = -this.vX;
            this.rotationSpeed = -this.rotationSpeed;
        }

        this.vY = MathUtils.random(START_VY, START_VY_JITTER);
        this.scale = Settings.scale;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);

        fadeIn = true;
        fadeOut = false;
    }

    public void update() {
        this.rotation += Gdx.graphics.getDeltaTime() * this.rotationSpeed;
        this.x += Gdx.graphics.getDeltaTime() * this.vX;
        this.y += Gdx.graphics.getDeltaTime() * this.vY;
        this.vY -= Gdx.graphics.getDeltaTime() * GRAVITY;

        if (fadeIn) {
            this.color.a += Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.color.a > 1.0F) {
                this.color.a = 1.0F;
                fadeIn = false;
                fadeOut = true;
                if (MathUtils.randomBoolean()) {
                    CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
                }
            }
        }
        else if (fadeOut)
        {
            this.color.a -= Gdx.graphics.getDeltaTime() * 0.5f;
            if (this.color.a < 0.0F) {
                this.color.a = 0.0F;
                fadeOut = false;
                AbstractDungeon.effectsQueue.add(new ShineLinesEffect(this.x, this.y));
                this.isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
