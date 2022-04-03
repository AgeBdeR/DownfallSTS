package timeeater.cards.alternateDimension;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.util.Wiz;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class ArsenalGear extends AbstractDimensionalCard {
    public final static String ID = makeID("ArsenalGear");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public ArsenalGear() {
        super(ID, 2, CardType.SKILL, CardTarget.SELF);

        setFrame("arsenalgearframe.png");
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        AbstractCard c;
        validCards = Wiz.getCardsMatchingPredicate(c2 -> c2.rarity==CardRarity.UNCOMMON||c2.rarity==CardRarity.RARE);
        for (int i = 0; i < magicNumber; i++) {
            if (validCards.size() > 0){
                c = validCards.get(0).makeCopy();
                c.modifyCostForCombat(-9);
                addToBot(new MakeTempCardInHandAction(c));
                validCards.remove(0);
            }
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}