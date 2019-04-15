package Astrologer.Cards.Normal;

import Astrologer.Abstracts.StarCard;
import Astrologer.Actions.Astrologer.AlignmentAction;
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

public class Alignment extends StarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Alignment",
            -1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BONUS = 0;
    private final static int UPG_BONUS = 1;

    public Alignment()
    {
        super(cardInfo, true);

        setMagic(BONUS, UPG_BONUS);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AlignmentAction action = new AlignmentAction(this.magicNumber);
        AbstractDungeon.actionManager.addToBottom(new PerformXAction(action, p, this.energyOnUse, this.freeToPlayOnce));
    }
}