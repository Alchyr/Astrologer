package Astrologer.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

public class StellarUIStarPosition {
    public float yOffset;
    public float progress;
    public float progressRate;
    public float rotationSpeed;

    public float angle;

    public Color renderColor = new Color(1, 1, 1, 0);

    public boolean isDone = false;

    public StellarUIStarPosition()
    {
        this.yOffset = MathUtils.random(-180.0f * Settings.scale, 0.0f);
        this.progress = MathUtils.random(-1.0f, 1.0f);
        this.progressRate = MathUtils.random(0.033f, 0.1f);
        this.rotationSpeed = MathUtils.random(15.0f, 45.0f) * (MathUtils.randomBoolean() ? 1.0f : -1.0f);
        this.angle = MathUtils.random(0.0f, 360.0f);
    }
    
    public void update()
    {
        //update render color
        if (this.isDone)
        {
            if (this.renderColor.a > 0.0f)
            {
                this.renderColor.a -= 0.5f * Gdx.graphics.getDeltaTime();
                if (this.renderColor.a < 0.0f)
                    this.renderColor.a = 0.0f;
            }
        }
        else if (this.renderColor.a < 1.0f) {
            this.renderColor.a += 2.0f * Gdx.graphics.getDeltaTime();
            if (this.renderColor.a > 1.0f)
                this.renderColor.a = 1.0f;
        }

        //update position
        this.progress += this.progressRate * Gdx.graphics.getDeltaTime();
        if (progress > 1.0f)
            progress = -1.0f;
        if (progress < -1.0f)
            progress = 1.0f;
        this.angle = (this.angle + this.rotationSpeed * Gdx.graphics.getDeltaTime()) % 360.0f;
    }
}
