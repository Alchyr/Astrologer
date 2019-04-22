package Astrologer.Actions.Astrologer;

import Astrologer.Actions.Generic.ShowCardAndPurgeAction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.scene.LightFlareLEffect;

import java.util.ArrayList;
import java.util.HashMap;

public class ResetDeckAction extends AbstractGameAction {
    public static ArrayList<AbstractCard> noReset = new ArrayList<>();

    public ResetDeckAction(AbstractCard source)
    {
        noReset.add(source);
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new InitializeDeckAction(noReset));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new LightFlareLEffect(AbstractDungeon.overlayMenu.combatDeckPanel.current_x, AbstractDungeon.overlayMenu.combatDeckPanel.current_y)));
        for (int i = 0; i < 30; i++)
        {
            AbstractDungeon.actionManager.addToTop(new VFXAction(new LightFlareParticleEffect(AbstractDungeon.overlayMenu.combatDeckPanel.current_x, AbstractDungeon.overlayMenu.combatDeckPanel.current_y, new Color(MathUtils.random(0.5f, 1.0f), MathUtils.random(0.5f, 1.0f), MathUtils.random(0.5f, 1.0f), 1.0f))));
        }
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));

        HashMap<AbstractCard, CardGroup> cardLocations = new HashMap<>();

        CardGroup toPurge = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            cardLocations.put(c, AbstractDungeon.player.hand);
            toPurge.addToRandomSpot(c);
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            cardLocations.put(c, AbstractDungeon.player.drawPile);
            toPurge.addToRandomSpot(c);
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            cardLocations.put(c, AbstractDungeon.player.discardPile);
            toPurge.addToRandomSpot(c);
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            cardLocations.put(c, AbstractDungeon.player.exhaustPile);
            toPurge.addToRandomSpot(c);
        }

        for (AbstractCard c : toPurge.group)
        {
            AbstractDungeon.actionManager.addToTop(new ShowCardAndPurgeAction(c, cardLocations.get(c)));
        }

        this.isDone = true;
    }
}
