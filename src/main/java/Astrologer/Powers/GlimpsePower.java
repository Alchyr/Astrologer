package Astrologer.Powers;

import Astrologer.Abstracts.BasePower;
import Astrologer.AstrologerMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class GlimpsePower extends BasePower implements OnCardDrawPower {
    public static final String NAME = "Glimpse";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private Color renderColor;

    private int colorPhase = 0;

    public GlimpsePower(final AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
        this.renderColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard) {
        if (abstractCard.isEthereal)
        {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        float change = Gdx.graphics.getDeltaTime() * 0.33f;
        switch (colorPhase)
        {
            case 0:
                this.renderColor.r -= change;
                this.renderColor.g += change;
                if (this.renderColor.r <= 0)
                {
                    colorPhase += 1;
                    this.renderColor.r = 0;
                    this.renderColor.g = 1;
                }
                break;
            case 1:
                this.renderColor.g -= change;
                this.renderColor.b += change;
                if (this.renderColor.g <= 0)
                {
                    colorPhase += 1;
                    this.renderColor.g = 0;
                    this.renderColor.b = 1;
                }
                break;
            default:
                this.renderColor.b -= change;
                this.renderColor.r += change;
                if (this.renderColor.b <= 0)
                {
                    colorPhase = 0;
                    this.renderColor.b = 0;
                    this.renderColor.r = 1;
                }
                break;
        }
        this.renderColor.a = c.a;
        sb.setColor(this.renderColor);
        if (this.img != null) {
            sb.draw(this.img, x - 12.0F, y - 12.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 32, 32, false, false);
        } else {
            sb.draw(this.region48, x - (float)this.region48.packedWidth / 2.0F, y - (float)this.region48.packedHeight / 2.0F, (float)this.region48.packedWidth / 2.0F, (float)this.region48.packedHeight / 2.0F, (float)this.region48.packedWidth, (float)this.region48.packedHeight, Settings.scale, Settings.scale, 0.0F);
        }

        try
        {
            Iterator itr = ((ArrayList)ReflectionHacks.getPrivate(this, AbstractPower.class, "effect")).iterator();

            while(itr.hasNext()) {
                AbstractGameEffect e = (AbstractGameEffect)itr.next();
                e.render(sb, x, y);
            }
        }
        catch (Exception e)
        {
            AstrologerMod.logger.info("Error rendering GlimpsePower effects.");
        }
    }

    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}