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
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 16;
    private final static int UPG_DAMAGE = 3;

    private final static int BONUS_HITS = 2;

    private final static int STELLAR = 16;

    public TheTower()
    {
        super(cardInfo, false, STELLAR);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BONUS_HITS);

        loadFrames(cardInfo.cardName, 7, 0.13f);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int hitAmount = 1 + (stellarActive() ? this.magicNumber : 0);

        for (int i = 0; i < hitAmount; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new TowerAction(p, this.baseDamage, this.damageTypeForTurn));
        }
    }
}