package Astrologer.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static Astrologer.AstrologerMod.assetPath;

public class AppearBigThenShrinkAndFallStarEffect extends AbstractGameEffect {
    private static final String texturePath = assetPath("img/UI/VeryBigStar.png");
    private static Texture t = null;
    public static void load() {
        if (t == null) {
            t = ImageMaster.loadImage(texturePath);
        }
    }
    public static void cleanup() {
        if (t != null) {
            t.dispose();
            t = null;
        }
    }

    private static final int T_OFFSET = 128, T_SIZE = 256;
    private int stage;
    private float delay;
    private float x, y, vx = 0, vy = 0;
    private float rotateSpeed;
    private float gravity;
    private float initialScale;
    private float finishTime;

    public AppearBigThenShrinkAndFallStarEffect(float x, float y, float delay) {
        this.x = x;
        this.y = y;
        this.rotation = MathUtils.random(-180f, 180f);
        this.rotateSpeed = MathUtils.random(2f, 6f) * (MathUtils.randomBoolean() ? 1 : -1);
        this.gravity = -MathUtils.random(50f, 66f) * Settings.scale;
        this.vx = MathUtils.random(-10f, 10f) * Settings.scale;
        this.initialScale = MathUtils.random(2.0f, 2.6f) * Settings.scale;
        this.scale = 0;
        this.color = Color.WHITE.cpy();
        this.color.a = 0;
        this.delay = delay * 0.02f;
        this.finishTime = MathUtils.random(3.0f, 3.4f) + (delay * 0.05f);
        stage = 0;
    }

    @Override
    public void update() {
        this.rotation += rotateSpeed * Gdx.graphics.getDeltaTime();
        this.x += this.vx * Gdx.graphics.getDeltaTime();
        this.vy += gravity * Gdx.graphics.getDeltaTime();
        this.y += this.vy * Gdx.graphics.getDeltaTime();
        switch (stage) {
            case 0:
                this.delay -= Gdx.graphics.getDeltaTime();
                if (this.delay <= 0) {
                    this.delay = 0;
                    stage = 1;
                }
                break;
            case 1:
                this.color.a += Gdx.graphics.getDeltaTime() * 2.5f;
                if (this.color.a >= 1) {
                    stage = 2;
                    this.delay = 0.5f - (this.color.a - 1);
                    this.color.a = 1;
                }
                this.scale = Math.min(1, this.color.a * 1.5f) * this.initialScale;
                break;
            case 2:
                this.delay -= Gdx.graphics.getDeltaTime();
                if (this.delay <= 0) {
                    this.delay = 0;
                    stage = 3;
                }
                break;
            default:
                delay += Gdx.graphics.getDeltaTime();
                this.scale = Interpolation.pow4Out.apply(initialScale, 0, delay / finishTime);

                if (this.scale <= 0.25f) {
                    if (this.scale < 0)
                        this.scale = 0;
                    this.color.a = this.scale * 4;
                }

                this.isDone = delay >= finishTime;
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (t != null) {
            sb.setColor(this.color);
            sb.draw(t, x - T_OFFSET, y - T_OFFSET, T_OFFSET, T_OFFSET, T_SIZE, T_SIZE, scale, scale, rotation, 0, 0, T_SIZE, T_SIZE, false, false);
        }
    }

    @Override
    public void dispose() {
        if (AbstractDungeon.topLevelEffects.isEmpty()) {
            cleanup(); //if it doesn't trigger that's fine, it just means there's one extra texture sitting around.
        }
    }
}
