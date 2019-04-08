package Astrologer.Actions.Astrologer;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

import java.util.UUID;

public class JudgementAction extends AbstractGameAction {
    private AbstractCard toReduce;

    public JudgementAction(AbstractCard toReduce, AbstractMonster toCheck, int reduction)
    {
        this.toReduce = toReduce;
        this.target = toCheck;
        this.actionType = ActionType.DAMAGE; //prevents being removed by end of combat
        this.amount = reduction;
    }

    @Override
    public void update() {
        if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower(MinionPower.POWER_ID)) {
            UUID uuid = toReduce.uuid;

            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            {
                if (c.uuid.equals(uuid)) {
                    c.misc += this.amount;
                    c.updateCost(-this.amount);
                }
            }

            for(AbstractCard c : GetAllInBattleInstances.get(uuid)) {
                c.misc += this.amount;
                c.updateCost(-this.amount);
                c.flash(Color.WHITE.cpy());
            }
        }
        this.isDone = true;
    }
}
