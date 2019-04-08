package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.TowerAction;
import Astrologer.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Astrologer.AstrologerMod.makeID;

public class TheTower extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheTower",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 7;

    private final static int BONUS_HITS = 3;
    private final static int UPG_BONUS_HITS = 1;

    private final static int STELLAR = 16;

    public TheTower()
    {
        super(cardInfo, false, STELLAR);

        setDamage(DAMAGE);
        setMagic(BONUS_HITS, UPG_BONUS_HITS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int hitAmount = 1 + (stellarActive() ? this.magicNumber : 0);

        for (int i = 0; i < hitAmount; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new TowerAction(p, this.baseDamage, this.damageTypeForTurn));
        }
    }
}