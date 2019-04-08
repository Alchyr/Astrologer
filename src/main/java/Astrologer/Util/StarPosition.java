package Astrologer.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class StarPosition {
    public float x;
    public float y;

    public float angle;

    public Color renderColor = new Color(1, 1, 1, 0);

    public boolean fading = false;
    public boolean isDone = false;

    public StarPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.angle = MathUtils.random(0.0f, 360.0f);
    }
}
