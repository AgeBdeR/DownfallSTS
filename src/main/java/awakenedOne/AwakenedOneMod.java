package awakenedOne;

import automaton.cards.AbstractBronzeCard;
import automaton.util.CardFilter;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.cardvars.SecondDamage;
import awakenedOne.cards.cardvars.SecondMagicNumber;
import awakenedOne.cards.cardvars.ThirdMagicNumber;
import awakenedOne.relics.FinalFeather;
import awakenedOne.ui.AwakenButton;
import awakenedOne.ui.AwakenedIcon;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.Wiz;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.CardIgnore;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class AwakenedOneMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        OnStartBattleSubscriber,
        StartGameSubscriber,
        PostDungeonUpdateSubscriber, PostPlayerUpdateSubscriber {
    public static final String SHOULDER1 = "awakenedResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "awakenedResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "awakenedResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "awakenedResources/images/512/card_bronze_orb.png";
    public static final String TEXT_ENERGY = "awakenedResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "awakenedResources/images/512/bg_attack_bronze.png";
    private static final String SKILL_S_ART = "awakenedResources/images/512/bg_skill_bronze.png";
    private static final String POWER_S_ART = "awakenedResources/images/512/bg_power_bronze.png";
    private static final String ATTACK_L_ART = "awakenedResources/images/1024/bg_attack_bronze.png";
    private static final String SKILL_L_ART = "awakenedResources/images/1024/bg_skill_bronze.png";
    private static final String POWER_L_ART = "awakenedResources/images/1024/bg_power_bronze.png";
    private static final String CARD_ENERGY_L = "awakenedResources/images/1024/card_bronze_orb.png";
    private static final String CHARSELECT_BUTTON = "awakenedResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "awakenedResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(214F / 255F, 202F / 255F, 158F / 255F, 1);
    public static Color potionLabColor = new Color(214F / 255F, 202F / 255F, 158F / 255F, 1);
    private static String modID = "awakened";

    public AwakenedOneMod() {
        BaseMod.subscribe(this);

        modID = "awakened";

        BaseMod.addColor(AwakenedOneChar.Enums.AWAKENED_BLUE, placeholderColor, placeholderColor, placeholderColor,
                placeholderColor, placeholderColor, placeholderColor, placeholderColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }


    public static String makeBetaCardPath(String resourcePath) {
        return "awakenedResources/images/cards/joke/" + resourcePath;
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }


    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        AwakenedOneMod awakenedOneMod = new AwakenedOneMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = AwakenedOneMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());
            if (cls.hasAnnotation(CardIgnore.class)) {
                continue;
            }
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
            System.out.println(classInfo.getClassName());
            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();
            BaseMod.addCard(card);

        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new AwakenedOneChar("The Awakened One", AwakenedOneChar.Enums.AWAKENED_ONE), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, AwakenedOneChar.Enums.AWAKENED_ONE);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new FinalFeather(), AwakenedOneChar.Enums.AWAKENED_BLUE);
    }


    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(AwakenedIcon.get());

        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());

        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // CONTENT STUFF

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        OrbitingSpells.atBattleStart();
        ConjureAction.conjuresThisCombat = 0;
    }

    @Override
    public void receivePostPlayerUpdate() {
        OrbitingSpells.update();
    }

    private static AwakenButton becomeAwesomeButton;

    @Override
    public void receiveStartGame() {
        becomeAwesomeButton = new AwakenButton();
    }

    public static void renderCombatUiElements(SpriteBatch sb) {
        if (Wiz.isInCombat() && AbstractDungeon.player.chosenClass.equals(AwakenedOneChar.Enums.AWAKENED_ONE)) {
            becomeAwesomeButton.render(sb);
        }
    }

    @Override
    public void receivePostDungeonUpdate() {
        becomeAwesomeButton.update();
    }

}