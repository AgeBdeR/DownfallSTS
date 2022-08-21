package automaton.potions;


import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.actions.AddToFuncAction;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.SpaghettiCode;
import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;

import static automaton.cards.AbstractBronzeCard.masterUI;


public class BuildAFunctionPotion extends CustomPotion {
    public static final String POTION_ID = "bronze:BuildAFunctionPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BuildAFunctionPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();

        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
     }

    public void use(AbstractCreature target) {
        AbstractCard newCard;
        for (int i = 0; i < potency; i++) {
                newCard = CardLibrary.getCard(AutomatonMod.spaghettiOptions.get(AbstractDungeon.cardRandomRng.random(0, AutomatonMod.spaghettiOptions.size() - 1))).makeStatEquivalentCopy();
                addToBot(new AddToFuncAction(newCard, null));

        }

    }

    public CustomPotion makeCopy() {
        return new BuildAFunctionPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }
}
