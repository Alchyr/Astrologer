package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.TheMoonAction;
import Astrologer.AstrologerMod;
import Astrologer.Enums.CustomTags;
import Astrologer.Util.CardInfo;
import Astrologer.Util.PhaseCheck;
import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Astrologer.AstrologerMod.makeID;

public class TheMoon extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheMoon",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 18;

    private final static int BLOCK = 3;
    private final static int UPG_BLOCK = 1;

    public TheMoon()
    {
        super(cardInfo, false, STELLAR);
        tags.add(CustomTags.LUNAR);

        setBlock(BLOCK, UPG_BLOCK);
    }

    /*
    public void applyPowers()
    {
        int totalDamage = 0;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
        {
            if (!mo.isDeadOrEscaped())
            {
                try
                {
                    if (mo.intent == AbstractMonster.Intent.ATTACK ||
                            mo.intent == AbstractMonster.Intent.ATTACK_DEFEND ||
                            mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                            mo.intent == AbstractMonster.Intent.ATTACK_BUFF)
                    {
                        int damage = (int) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentDmg");
                        if ((boolean)ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg"))
                        {
                            damage *= (int)ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
                        }

                        totalDamage += damage;
                    }
                }
                catch (Exception e)
                {
                    AstrologerMod.logger.info("Failed to read intent of " + mo.name);
                }
            }
        }
        this.baseBlock = this.block = MathUtils.floor(totalDamage / 2.0f);

        super.applyPowers();

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();

        if (stellarActive())
        {
            this.target = CardTarget.ALL_ENEMY;
        }
        else
        {
            this.target = CardTarget.NONE;
        }
        this.exhaust = stellarActive();
    }*/

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (PhaseCheck.lunarActive())
        {
            AbstractDungeon.actionManager.addToBottom(new TheMoonAction(p, stellarActive() ? this.block : 0));
        }
    }
}