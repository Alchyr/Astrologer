package Astrologer.Effects;

import Astrologer.Util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static Astrologer.AstrologerMod.assetPath;

public class SmallFallingStarEffect extends AbstractGameEffect {
    private static final String texturePath = assetPath("img/Attack/BigStar.png");
    private static final Texture starTexture = TextureLoader.getTexture(texturePath);
    private static final int width = starTexture.getWidth(), height = starTexture.getHeight();
    private static final float offset_x = width / 2.0f, offset_y = height / 2.0f;

    private float x, y, vx = 0, vy = 0;
    private float rotateSpeed;
    private float gravity;
    private float initialScale, scaling;

    public SmallFallingStarEffect() {
        this.color = Color.WHITE.cpy();
        this.color.a = MathUtils.random(0.66f, 1.0f);
        this.x = MathUtils.random(-10, Settings.WIDTH + 10);
        this.y = Settings.HEIGHT + MathUtils.random(64, 128);
        this.rotation = MathUtils.random(-180f, 180f);
        this.gravity = -320f * Settings.scale;
        this.vx = MathUtils.random(-20f, 20f) * Settings.scale;
        this.rotateSpeed = MathUtils.random(13f, 17f) * (vx > 0 ? 1 : -1);
        this.vy = MathUtils.random(-150, -100);
        this.scale = this.initialScale = MathUtils.random(0.3f, 1.0f) * Settings.scale;
        scaling = this.initialScale / MathUtils.random(1.5f, 4f);
    }

    @Override
    public void update() {
        this.rotation += rotateSpeed * Gdx.graphics.getDeltaTime();
        this.x += this.vx * Gdx.graphics.getDeltaTime();
        this.vy += gravity * Gdx.graphics.getDeltaTime();
        this.y += this.vy * Gdx.graphics.getDeltaTime();
        this.scale = this.initialScale - (scaling * (1 - (this.y / Settings.HEIGHT)));

        this.isDone = this.y < -100;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(starTexture, x - offset_x, y - offset_y, offset_x, offset_y, width, height, scale, scale, rotation, 0, 0, width, height, false, false);
    }

    @Override
    public void dispose() {

    }
}
