package Astrologer.Cards.Tarot;

import Astrologer.Abstracts.StellarCard;
import Astrologer.AstrologerMod;
import Astrologer.Util.CardInfo;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;

import static Astrologer.AstrologerMod.makeID;

public class TheEmpress extends StellarCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TheEmpress",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int STELLAR = 3;

    private final static int UPG_COST = 1;

    public TheEmpress()
    {
        super(cardInfo, false, STELLAR);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (stellarActive())
        {
            this.target = CardTarget.ALL_ENEMY;
        }
        else
        {
            this.target = CardTarget.NONE;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (stellarActive())
        {
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
                            int damage = (int)ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentDmg");
                            if ((boolean)ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg"))
                            {
                                damage *= (int)ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
                            }

                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(mo.hb.cX, mo.hb.cY, true)));
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));
                        }
                    }
                    catch (Exception e)
                    {
                        AstrologerMod.logger.info("Failed to read intent of " + mo.name);
                    }
                }
            }
        }
    }
}