package Astrologer.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;


public class PurgeCardEffect extends AbstractGameEffect {
    private AbstractCard c;
    private static final float DUR = 1.0F;

    public PurgeCardEffect(AbstractCard c, CardGroup source) {
        this.duration = DUR;
        this.c = c;

        source.removeCard(c);

        c.target_x = Settings.WIDTH * MathUtils.random(1.0f);
        c.target_y = Settings.HEIGHT * MathUtils.random(1.0f);
        c.targetDrawScale = 1.0F;
        c.targetAngle = 0.0f;

        c.isGlowing = false;

        switch (source.type)
        {
            case DRAW_PILE:
                c.current_x = AbstractDungeon.overlayMenu.combatDeckPanel.current_x;
                c.current_y = AbstractDungeon.overlayMenu.combatDeckPanel.current_y;
                c.drawScale = 0.01f;
            case DISCARD_PILE:
                c.current_x = AbstractDungeon.overlayMenu.discardPilePanel.current_x;
                c.current_y = AbstractDungeon.overlayMenu.discardPilePanel.current_y;
                c.drawScale = 0.01f;
                break;
            case EXHAUST_PILE:
                c.current_x = AbstractDungeon.overlayMenu.exhaustPanel.current_x;
                c.current_y = AbstractDungeon.overlayMenu.exhaustPanel.current_y;
                c.drawScale = 0.01f;
                break;
        }
    }

    public void update() {
        this.c.update();
        if (this.duration == DUR) {
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

            int i;
            for(i = 0; i < 90; ++i) {
                AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(this.c.current_x, this.c.current_y));
            }

            for(i = 0; i < 50; ++i) {
                AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.c.current_x, this.c.current_y));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (!this.c.fadingOut && this.duration < 0.7F) {
            this.c.fadingOut = true;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.c.resetAttributes();
        }
    }

    public void render(SpriteBatch sb) {
        this.c.render(sb);
    }

    public void dispose() {
    }
}