package Astrologer.Character;

import Astrologer.Cards.Basic.Defend;
import Astrologer.Cards.Basic.Strike;
import Astrologer.Cards.Stars.NorthStar;
import Astrologer.Cards.Tarot.TheFool;
import Astrologer.Enums.AttackEffectEnum;
import Astrologer.Enums.CardColorEnum;
import Astrologer.Relics.SkyMirror;
import Astrologer.UI.AstrologerOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Astrologer.AstrologerMod.assetPath;
import static Astrologer.Util.Sounds.Sparkle;

public class Astrologer extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(Astrologer.class.getName());

    public static final com.megacrit.cardcrawl.localization.CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("astrologer:Astrologer");

    // =============== BASE STATS =================

    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_HP = 70;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int CARD_DRAW = 5;
    private static final int ORB_SLOTS = 0;

    private static final String SpritePath = assetPath("img/Character/Spriter/Character.scml");

    private static final Color cardRenderColor = Color.WHITE.cpy();
    private static final Color cardTrailColor = Color.GOLD.cpy();
    private static final Color slashAttackColor = Color.GOLDENROD.cpy();

    public Astrologer(String name, PlayerClass setClass) {
        super(name, setClass, new AstrologerOrb(),
                new SpriterAnimation(SpritePath));

        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout
                assetPath("img/Character/shoulder.png"), // campfire pose
                assetPath("img/Character/shoulder2.png"), // another campfire pose
                assetPath("img/Character/corpse.png"), // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== TEXT BUBBLE LOCATION =================

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 120.0F * Settings.scale);
    }

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startDeck = new ArrayList<>();

        startDeck.add(Strike.ID);
        startDeck.add(Strike.ID);
        startDeck.add(Strike.ID);
        startDeck.add(Strike.ID);
        startDeck.add(Defend.ID);
        startDeck.add(Defend.ID);
        startDeck.add(Defend.ID);
        startDeck.add(Defend.ID);
        startDeck.add(NorthStar.ID);
        startDeck.add(TheFool.ID);

        return startDeck;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();

        startingRelics.add(SkyMirror.ID);

        return startingRelics;
    }

    // Character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(Sparkle.key, -0.5f);
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return Sparkle.key;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.ASTROLOGER;
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        return Collections.emptyList();
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new NorthStar();
    }
    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return characterStrings.NAMES[1];
    }


    @Override
    public AbstractPlayer newInstance() {
        return new Astrologer(this.name, this.chosenClass);
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor.cpy();
    }
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }
    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        int amount = MathUtils.random(7, 21);
        AbstractGameAction.AttackEffect[] effects = new AbstractGameAction.AttackEffect[amount];
        for (int i = 0; i < effects.length; i++)
        {
            if (MathUtils.randomBoolean())
            { //50%
                effects[i] = AbstractGameAction.AttackEffect.FIRE;
            }
            else
            { //50%
                effects[i] = AttackEffectEnum.STAR;
            }
        }
        return effects;
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }
    @Override
    public String getVampireText() {
        return characterStrings.TEXT[2];
    }
}