package Astrologer.Actions.Astrologer;

import Astrologer.Abstracts.AbstractXAction;
import Astrologer.Powers.Starlit;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NeutronStarAction extends AbstractXAction {
    private AbstractPlayer p;
    private AbstractMonster m;

    public NeutronStarAction(AbstractPlayer p, AbstractMonster m)
    {
        this.p = p;
        this.m = m;

        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (amount > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new Starlit(m, this.amount), this.amount));
        }

        this.isDone = true;
    }
}
