package Astrologer.Cards.Stars;

import Astrologer.Abstracts.StarCard;
import Astrologer.Actions.Astrologer.NeutronStarAction;
import Astrologer.Actions.Astrologer.NeutronStarDamageAction;
import Astrologer.Actions.Generic.PerformXAction;
import Astrologer.Powers.Starlit;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Astrologer.AstrologerMod.makeID;

public class NeutronStar extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "NeutronStar",
            -1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 2;
    private final static int UPG_DAMAGE = 1;

    public NeutronStar()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    public void applyPowers()
    {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        int amount = 0;

        if (!mo.hasPower(ArtifactPower.POWER_ID))
            amount += EnergyPanel.totalCount + (AbstractDungeon.player.hasRelic(ChemicalX.ID) ? 2 : 0); //Get the amount of Starlit that will be applied

        if (mo.hasPower(Starlit.ID))
            amount += mo.getPower(Starlit.ID).amount; //Add the target's current Starlit

        //Calculate base final damage and actual final damage to get correct number color
        this.baseMagicNumber = this.baseDamage * amount;
        this.magicNumber = this.damage * amount;

        this.isMagicNumberModified = this.isDamageModified;

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        NeutronStarAction action = new NeutronStarAction(p, m);
        AbstractDungeon.actionManager.addToBottom(new PerformXAction(action, p, this.energyOnUse, this.freeToPlayOnce));
        AbstractDungeon.actionManager.addToBottom(new NeutronStarDamageAction(m, p, damage, damageTypeForTurn));
    }
}