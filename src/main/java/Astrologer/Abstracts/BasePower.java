package Astrologer.Abstracts;

import Astrologer.Util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Astrologer.AstrologerMod.makeID;

public abstract class BasePower extends AbstractPower {
    protected static PowerStrings getPowerStrings(String ID)
    {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }
    protected AbstractCreature source;

    protected PowerStrings powerStrings;

    protected String[] descriptions;

    public BasePower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount) {
        this(NAME, powerType, isTurnBased, owner, source, amount, "");
    }
    public BasePower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, String IDModifier) {
        this.ID = makeID(NAME);
        this.isTurnBased = isTurnBased;

        this.powerStrings = getPowerStrings(this.ID);
        this.name = powerStrings.NAME;

        this.descriptions = powerStrings.DESCRIPTIONS;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;

        Texture normalTexture = TextureLoader.getPowerTexture(NAME);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(NAME);
        if (hiDefImage != null)
        {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        }
        else
        {
            this.img = normalTexture;
        }
        region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());

        this.ID += IDModifier;

        this.updateDescription();
    }
}
