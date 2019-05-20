package Astrologer;

import Astrologer.Abstracts.StellarCard;
import Astrologer.Actions.Astrologer.AdvancePhaseAction;
import Astrologer.Character.Astrologer;
import Astrologer.Enums.CardColorEnum;
import Astrologer.Enums.CustomTags;
import Astrologer.Interfaces.OnExhaustCardCard;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Relics.SkyMirror;
import Astrologer.Relics.Telescope;
import Astrologer.UI.StellarUI;
import Astrologer.Util.*;
import Astrologer.Variables.StellarVariable;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javafx.util.Pair;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.clapper.util.classutil.*;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static Astrologer.Enums.CharacterEnum.ASTROLOGER;
import static Astrologer.Patches.CardsPlayedThisCombatPatch.cardsPlayedThisCombatCount;

@SpireInitializer
public class AstrologerMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber,
        EditCharactersSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber, AddAudioSubscriber,
        OnCardUseSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, PostExhaustSubscriber,
        PreRoomRenderSubscriber, PreStartGameSubscriber
{
    public static final Logger logger = LogManager.getLogger("Astrologer");

    // Character color
    public static final Color STARS = CardHelper.getColor(200.0f, 200.0f, 220.0f);

    // Mod panel stuff
    private static final String BADGE_IMAGE = "img/Badge.png";
    private static final String MODNAME = "Astrologist";
    private static final String AUTHOR = "Alchyr";
    private static final String DESCRIPTION = "";

    // Card backgrounds/basic images
    private static final String ATTACK_BACK = "img/Character/CardGeneric/bg_attack.png";
    private static final String POWER_BACK = "img/Character/CardGeneric/bg_power.png";
    private static final String SKILL_BACK = "img/Character/CardGeneric/bg_skill.png";
    private static final String ENERGY_ORB = "img/Character/CardGeneric/card_orb.png";
    private static final String CARD_ENERGY_ORB = "img/Character/CardGeneric/card_small_orb.png";

    private static final String ATTACK_PORTRAIT = "img/Character/CardGeneric/portrait_attack.png";
    private static final String POWER_PORTRAIT = "img/Character/CardGeneric/portrait_power.png";
    private static final String SKILL_PORTRAIT = "img/Character/CardGeneric/portrait_skill.png";
    private static final String CARD_ENERGY_ORB_PORTRAIT = "img/Character/CardGeneric/card_large_orb.png";

    // Character images
    private static final String BUTTON = "img/Character/CharacterButton.png";
    private static final String PORTRAIT = "img/Character/CharacterPortrait.png";


    //Card Lists
    public static ArrayList<AbstractCard> StellarCards = null;

    //Tracking
    public static int starsPlayedThisCombat;
    public static int stellarPlayedThisCombat;
    public static int starsExhaustedThisCombat;

    //Stellar UI
    public static StellarUI stellarUI;
    public static boolean drawStellarUI = false;

    @Override
    public void receivePreRoomRender(SpriteBatch sb) {
        if (AbstractDungeon.getCurrRoom() != null && drawStellarUI && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            stellarUI.render(sb, false);
        }
        else
        {
            stellarUI.render(sb, true);
        }
    }

    public AstrologerMod()
    {
        BaseMod.subscribe(this);

        BaseMod.addColor(CardColorEnum.ASTROLOGER, STARS,
                assetPath(ATTACK_BACK), assetPath(SKILL_BACK), assetPath(POWER_BACK),
                assetPath(ENERGY_ORB),
                assetPath(ATTACK_PORTRAIT), assetPath(SKILL_PORTRAIT), assetPath(POWER_PORTRAIT),
                assetPath(CARD_ENERGY_ORB_PORTRAIT), assetPath(CARD_ENERGY_ORB));
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Astrologer(Astrologer.characterStrings.NAMES[1], ASTROLOGER),
                assetPath(BUTTON), assetPath(PORTRAIT), ASTROLOGER);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new StellarVariable());

        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveAddAudio() {
        addAudio(Sounds.Sparkle);
        addAudio(Sounds.Ring);
        addAudio(Sounds.Tinkle);
    }

    @Override
    public void receivePostInitialize() {
        //Setup UI
        stellarUI = new StellarUI();

        //Setup mod menu info stuff
        Texture badgeTexture = TextureLoader.getTexture(assetPath(BADGE_IMAGE));

        if (badgeTexture != null)
        {
            //ModPanel panel = new ModPanel();

            BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, null);
        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new SkyMirror(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new Telescope(), CardColorEnum.ASTROLOGER);
    }

    @Override
    public void receiveEditStrings()
    {
        String lang = getLangString();

        try
        {
            BaseMod.loadCustomStringsFile(RelicStrings.class, assetPath("localization/" + lang + "/RelicStrings.json"));
            BaseMod.loadCustomStringsFile(CardStrings.class, assetPath("localization/" + lang + "/CardStrings.json"));
            BaseMod.loadCustomStringsFile(CharacterStrings.class, assetPath("localization/" + lang + "/CharacterStrings.json"));
            BaseMod.loadCustomStringsFile(PowerStrings.class, assetPath("localization/" + lang + "/PowerStrings.json"));
            BaseMod.loadCustomStringsFile(UIStrings.class, assetPath("localization/" + lang + "/UIStrings.json"));
        }
        catch (Exception e)
        {
            lang = "eng";
            BaseMod.loadCustomStringsFile(RelicStrings.class, assetPath("localization/" + lang + "/RelicStrings.json"));
            BaseMod.loadCustomStringsFile(CardStrings.class, assetPath("localization/" + lang + "/CardStrings.json"));
            BaseMod.loadCustomStringsFile(CharacterStrings.class, assetPath("localization/" + lang + "/CharacterStrings.json"));
            BaseMod.loadCustomStringsFile(PowerStrings.class, assetPath("localization/" + lang + "/PowerStrings.json"));
            BaseMod.loadCustomStringsFile(UIStrings.class, assetPath("localization/" + lang + "/UIStrings.json"));
        }
    }

    @Override
    public void receiveEditKeywords()
    {
        String lang = getLangString();

        try
        {
            Gson gson = new Gson();
            String json = Gdx.files.internal(assetPath("localization/" + lang + "/Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
            KeywordWithProper[] keywords = gson.fromJson(json, KeywordWithProper[].class);

            if (keywords != null) {
                for (KeywordWithProper keyword : keywords) {
                    BaseMod.addKeyword("astrologer", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                }
            }
        }
        catch (Exception e)
        {
            Gson gson = new Gson();
            String json = Gdx.files.internal(assetPath("localization/eng/Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
            KeywordWithProper[] keywords = gson.fromJson(json, KeywordWithProper[].class);

            if (keywords != null) {
                for (KeywordWithProper keyword : keywords) {
                    BaseMod.addKeyword("astrologer", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                }
            }
        }
    }

    private String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }

    private void addAudio(Pair<String, String> audioData)
    {
        BaseMod.addAudio(audioData.getKey(), audioData.getValue());
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        new AstrologerMod();
    }

    //I totally didn't copy this from Hubris, made by kiooeht.
    private static void autoAddCards() throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, CannotCompileException
    {
        ClassFinder finder = new ClassFinder();
        URL url = AstrologerMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        ArrayList<AbstractCard> addedCards = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());

            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }

            AbstractCard card = (AbstractCard) Loader.getClassPool().toClass(cls).newInstance();

            BaseMod.addCard(card);
            addedCards.add(card);

        }
        /*for (AbstractCard c : addedCards)
        {
            UnlockTracker.unlockCard(c.cardID);
        }*/
    }

    public static String makeID(String partialID)
    {
        return "astrologer:" + partialID;
    }
    public static String assetPath(String partialPath)
    {
        return "Astrologer/" + partialPath;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (!drawStellarUI && abstractCard.color == CardColorEnum.ASTROLOGER)
        {
            drawStellarUI = true;
        }
        if (PhaseCheck.isStar(abstractCard))
        {
            AbstractDungeon.actionManager.addToBottom(new AdvancePhaseAction());
            starsPlayedThisCombat++;
        }
        if (abstractCard instanceof StellarCard)
        {
            stellarPlayedThisCombat++;
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        if (PhaseCheck.isStar(abstractCard))
        {
            AbstractDungeon.actionManager.addToBottom(new AdvancePhaseAction());
            starsExhaustedThisCombat++;
        }

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c instanceof OnExhaustCardCard)
                    ((OnExhaustCardCard) c).onExhaustCard(abstractCard);
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                if (c instanceof OnExhaustCardCard)
                    ((OnExhaustCardCard) c).onExhaustCard(abstractCard);
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if (c instanceof OnExhaustCardCard)
                    ((OnExhaustCardCard) c).onExhaustCard(abstractCard);
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
            {
                if (c instanceof OnExhaustCardCard)
                    ((OnExhaustCardCard) c).onExhaustCard(abstractCard);
            }
        }
    }

    public static AbstractCard getRandomStellarCard()
    {
        if (StellarCards == null)
        {
            StellarCards = new ArrayList<>();

            for (AbstractCard c : CardLibrary.getAllCards())
            {
                if (c.hasTag(CustomTags.STELLAR) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                    StellarCards.add(c);
                }
            }
        }

        return StellarCards.get(AbstractDungeon.cardRandomRng.random(StellarCards.size() - 1));
    }

    @Override
    public void receivePreStartGame() {
        stellarUI.updateStellarPhase(1);
        cardsPlayedThisCombatCount = 0;
        starsPlayedThisCombat = 0;
        stellarPlayedThisCombat = 0;
        starsExhaustedThisCombat = 0;
        stellarUI.hide();
        drawStellarUI = false;
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        StellarPhaseValue.stellarPhase.set(AbstractDungeon.player, 1);
        StellarPhaseValue.stellarAlignment.set(AbstractDungeon.player, false);
        StellarPhaseValue.maxStellarPhase.set(AbstractDungeon.player, 21);
        stellarUI.reset(1);
        cardsPlayedThisCombatCount = 0;
        starsPlayedThisCombat = 0;
        stellarPlayedThisCombat = 0;
        starsExhaustedThisCombat = 0;

        if (AbstractDungeon.player.chosenClass == ASTROLOGER)
        {
            drawStellarUI = true;
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        cardsPlayedThisCombatCount = 0;
        starsPlayedThisCombat = 0;
        stellarPlayedThisCombat = 0;
        starsExhaustedThisCombat = 0;
        drawStellarUI = false;
    }
}
